package xyz.com.productcatalogservice.rest.requestentity;

import java.util.List;

public class FilterBySku {
	private Long productId;
	private List<Long> skuIds;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public List<Long> getSkuIds() {
		return skuIds;
	}

	public void setSkuIds(List<Long> skuIds) {
		this.skuIds = skuIds;
	}

}
