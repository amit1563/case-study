package xyz.com.productcatalogservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.com.productcatalogservice.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	Product getById(Long productId);
}
