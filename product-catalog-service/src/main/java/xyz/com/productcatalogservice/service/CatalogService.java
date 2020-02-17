package xyz.com.productcatalogservice.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import xyz.com.productcatalogservice.entity.Product;

@Service
public interface CatalogService {
	// find all product for particular brand type;
	Iterable<?> findAllByBrand(String brandType);

	// return all product and filter sku for particular groupBy query parameter and
	// value i.e
	// Group by price or color or size
	Iterable<?> groupBy(String queryParam, String value);

	Iterable<?> getBySku(Long productId, List<Long> skuId);

	Long noOfProductBySeller(String sellerName);

	Product save(@Valid Product product);

	void remove(long productId);

}
