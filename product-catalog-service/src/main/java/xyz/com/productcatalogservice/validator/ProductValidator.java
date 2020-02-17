package xyz.com.productcatalogservice.validator;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import xyz.com.productcatalogservice.entity.Product;

@Component
public class ProductValidator {
	HashMap<String, String> ValidationErrorMap = new HashMap<>();

	public HashMap<String, String> getValidationErrorMap() {
		return ValidationErrorMap;
	}

	public void setValidationErrorMap(HashMap<String, String> validationErrorMap) {
		ValidationErrorMap = validationErrorMap;
	}

	public void ValidationErrorMap() {

	}

	// validation of parameter
	public void validate(Object object) {
		Product product = (Product) object;
	
		if(product.getSeller() =="" || product.getSku() == null) {
			ValidationErrorMap.put("Parameter", "skuList can't be empty or seller info");
		}

	}
}
