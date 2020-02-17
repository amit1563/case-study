package xyz.com.productcatalogservice.daoservice;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.com.productcatalogservice.entity.Product;
import xyz.com.productcatalogservice.entity.Sku;
import xyz.com.productcatalogservice.exceptionhandler.CatalogRuntimeException;
import xyz.com.productcatalogservice.repositories.ProductRepository;

@Service
public class ProductDaoServiceImpl implements ProductDaoService {
	@Autowired
	private ProductRepository repository;
	EntityManager entityManger;
	@Override
	public Iterable<?> findAllByBrand(String brandType) {

		Iterable<Product> itrable = repository.findAll();
		List<Product> productList = new ArrayList<>();
		itrable.forEach(new Consumer<Product>() {

			@Override
			public void accept(Product product) {
				if (product.getBrand().equalsIgnoreCase(brandType)) {
					productList.add(product);
				}
			}
		});
		return productList;
	}

	@Override
	public Iterable<?> groupBy(String queryParam, String value) {
		Iterable<Product> itrable = repository.findAll();
		List<Product> productList = new ArrayList<>();
		itrable.forEach(new Consumer<Product>() {

			@Override
			public void accept(Product product) {

				Product p = filterHelper(product, queryParam, value);
				productList.add(p);
			}

		});
		return productList;

	}

	private Product filterHelper(Product product, String queryParam, String value) {
		List<Sku> skuList = new ArrayList<>();
		switch (queryParam) {
		case "price":
			product.getSku().forEach(new Consumer<Sku>() {

				@Override
				public void accept(Sku sku) {
					// Making asumption to sort on natural order
					if (sku.getPrice() <= Double.valueOf(value)) {
						skuList.add(sku);
					}
				}

			});
			// seting the new list to the sku fileld
			product.setSku(skuList);
			break;
		case "color":
			product.getSku().forEach(new Consumer<Sku>() {

				@Override
				public void accept(Sku sku) {
					// Making asumption to sort on natural order
					if (sku.getColor().equalsIgnoreCase(value)) {
						skuList.add(sku);
					}
				}

			});
			// seting the new list to the sku fileld
			product.setSku(skuList);
			break;
		case "size":
			product.getSku().forEach(new Consumer<Sku>() {

				@Override
				public void accept(Sku sku) {
					// Making asumption to sort on natural order
					if (sku.getSize().equalsIgnoreCase(value)) {
						skuList.add(sku);
					}
				}

			});
			// seting the new list to the sku fileld
			product.setSku(skuList);
			break;
		default:
			System.out.println("Known  query param is not supported");
		}
		return product;
	}

	@Override
	public Iterable<?> getBySku(Long productId, List<Long> skuId) {
		Product p = repository.getById(productId);
		if (p == null)
			throw new CatalogRuntimeException(" Product not found for given productId");
		List<Sku> skus = new ArrayList<Sku>();
		for (Sku sku : p.getSku()) {
			for (Long id : skuId) {
				if (id.equals(sku.getId())) {
					skus.add(sku);
				}
			}
		}
		p.setSku(skus);

		return p.getSku();
	}

	@Override
	public Long noOfProductBySeller(String sellerName) {
		Iterable<Product> itrable = repository.findAll();
		List<Product> productList = new ArrayList<>();
		itrable.forEach(new Consumer<Product>() {

			@Override
			public void accept(Product product) {
				if (product.getSeller().equalsIgnoreCase(sellerName)) {
					productList.add(product);
				}
			}
		});
		return (long) productList.size();
	}

	@Override
	public Product save(@Valid Product product) {

		if (product.getId() == 0) {
			repository.save(product);
			return repository.getById(product.getId());
		}
		Product res = repository.save(product);
		if (res == null)
			throw new CatalogRuntimeException("Exception while persisting to datastore");
		refresh(res);
		return product;
	}

	@Override
	public void remove(long productId) {
		Product p = repository.getById(productId);
		if (p == null)
			throw new CatalogRuntimeException("Product not found for given ID");
		repository.deleteById(productId);
		// refresh after removal
		refresh(p);
	}
	
	@Override
	@Transactional
	public void refresh(Product product) {
		entityManger.refresh(product);
	}
}
