package xyz.com.productcatalogservice.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.com.productcatalogservice.daoservice.ProductDaoService;
import xyz.com.productcatalogservice.entity.Product;

@Service
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	ProductDaoService daoservice;

	@Override
	public Iterable<?> findAllByBrand(String brandType) {

		return (Iterable<Product>) daoservice.findAllByBrand(brandType);
	}

	@Override
	public Iterable<?> groupBy(String queryParam, String value) {

		return daoservice.groupBy(queryParam, value);
	}

	@Override
	public Iterable<?> getBySku(Long productId, List<Long> skuId) {

		return daoservice.getBySku(productId, skuId);
	}

	@Override
	public Long noOfProductBySeller(String sellerName) {
		return daoservice.noOfProductBySeller(sellerName);
	}

	@Override
	public Product save(@Valid Product product) {

		return daoservice.save(product);
	}

	@Override
	public void remove(long productId) {
		daoservice.remove(productId);
	}

}
