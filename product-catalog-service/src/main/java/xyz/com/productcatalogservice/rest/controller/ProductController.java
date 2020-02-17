package xyz.com.productcatalogservice.rest.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import xyz.com.productcatalogservice.entity.Product;
import xyz.com.productcatalogservice.rest.requestentity.FilterBySku;
import xyz.com.productcatalogservice.rest.requestentity.FilterRequest;
import xyz.com.productcatalogservice.service.CatalogService;
import xyz.com.productcatalogservice.utility.FieldErrorHandler;
import xyz.com.productcatalogservice.validator.ProductValidator;

@RestController
@RequestMapping("/v1/catalog/product")
public class ProductController {

	@Autowired
	private CatalogService service;
	@Autowired
	private FieldErrorHandler fieldErrorHandler;
	@Autowired
	private ProductValidator productValidator;

	/**
	 * 
	 * @param product
	 *            - product object need to be persisted
	 * @param result
	 *            - BindingResult instance
	 * @return errorMap - In case there is validation error else product object
	 *         persisted successfuly.
	 * 
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult result) {
		ResponseEntity<?> errorMap = fieldErrorHandler.mapValidationError(result);
		Product productObj = null;
		if (errorMap != null) {
			return errorMap;
		}
		productValidator.validate(product);

		if (!productValidator.getValidationErrorMap().isEmpty()) {
			return new ResponseEntity<Map<String, String>>(productValidator.getValidationErrorMap(),
					HttpStatus.BAD_REQUEST);
		}
		productObj = service.save(product);

		return new ResponseEntity<Product>(productObj, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> filter(@Valid @RequestBody FilterRequest filter) {
		return new ResponseEntity<Iterable<?>>(service.groupBy(filter.getQueryParam(), filter.getValue()),
				HttpStatus.OK);
	}

	@GetMapping(value = "/brand" + "/{brand}")
	public ResponseEntity<?> groupByBrand(@PathVariable String brand) {
		return new ResponseEntity<Iterable<?>>(service.findAllByBrand(brand), HttpStatus.OK);
	}

	@GetMapping("/{seller}")
	public ResponseEntity<?> listProductQuantityBySeller(@PathVariable String seller) {
		return new ResponseEntity<Long>(service.noOfProductBySeller(seller), HttpStatus.OK);
	}

	@RequestMapping(value = "/skus", method = RequestMethod.POST)
	public ResponseEntity<?> filterBySku(@Valid @RequestBody FilterBySku filter) {
		return new ResponseEntity<Iterable<?>>(service.getBySku(filter.getProductId(), filter.getSkuIds()),
				HttpStatus.OK);
	}

	@DeleteMapping("{productId}")
	public ResponseEntity<?> listProductQuantityBySeller(@PathVariable long productId) {
		service.remove(productId);
		return new ResponseEntity(HttpStatus.OK);
	}
}
