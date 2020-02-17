package xyz.com.productcatalogservice.daoservice;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import xyz.com.productcatalogservice.entity.Product;
import xyz.com.productcatalogservice.exceptionhandler.CatalogRuntimeException;

@Service
public interface ProductDaoService {

	// find all product for particular brand type;
	Iterable<?> findAllByBrand(String brandType);

	// return all product and filter sku for particular groupBy query parameter and
	// value i.e
	// Group by price or color or size
	Iterable<?> groupBy(String queryParam, String value) throws CatalogRuntimeException;;

	Iterable<?> getBySku(Long productId,List<Long> skuId);

	Long noOfProductBySeller(String sellerName);

	Product save(@Valid Product product) throws CatalogRuntimeException;
	
	void remove(long productId);
	void refresh(Product product);
	
	
}
