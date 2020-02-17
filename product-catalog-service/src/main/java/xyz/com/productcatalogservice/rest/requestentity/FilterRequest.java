package xyz.com.productcatalogservice.rest.requestentity;

public class FilterRequest {
	private String queryParam;
	private String value;

	public String getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
