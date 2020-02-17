# case-study
The HTTP API includes the following endpoints:
- [`PUT /v1/catalog/product`]                 - save
- [`POST /v1/catalog/product`]                - group by color/size/price
- [`GET /v1/catalog/brand/{brandName}`]       - filter by brand name
- [`GET /v1/catalog/product/{seller}`]        -  filter by seller name	
- [`POST /v1/catalog/product/skus`]           - filter by sku
- [`DELETE /v1/catalog/product/{productId}`]  - remove product


## `PUT /v1/catalog/product`
`/v1/catalog/product` allows to save or update device product.
`Content-Type` header is set to `application/json`, 
JSON payload:

```json
{
	"category" : "T-Shirt",
	"brand" : "U.S.P",
	"name" : "Black Formal Shirt",
	"seller" : "ABC Retailer",
	 "sku":   [
	 	       { "price":"12.12", "color":"Red", "size":"M"},
	 	       { "price":"12.0", "color":"Green","size":"L"},
	 	       { "price":"20", "color":"White", "size":"S"}
	 	   ]
}
```
# Response
HTTP response - 200 OK - Success. HTTP error codes for any other scenarios as applicable with error message
```json
{
    "id": 2,
    "category": "T-Shirt",
    "name": "Black Formal Shirt",
    "brand": "U.S.P",
    "seller": "ABC Retailer",
    "sku": [
        {
            "id": 4,
            "price": 12.12,
            "color": "Red",
            "size": "M"
        },
        {
            "id": 5,
            "price": 12.0,
            "color": "Green",
            "size": "L"
        },
        {
            "id": 6,
            "price": 20.0,
            "color": "White",
            "size": "S"
        }
    ]
}
```
500 response example 
```json
{
    "Parameter": "skuList can't be empty or seller info"
}
```

## `POST /v1/catalog/product`

`Content-Type` header is set to `application/json`, 
JSON payload:
## Request
```json
{
	"queryParam":"color",
	"value":"Red"
}
```
## Response

```json
[
    {
        "id": 1,
        "category": "T-Shirt",
        "name": "Black Formal Shirt",
        "brand": "U.S.P",
        "seller": "ABC Retailer",
        "sku": [
            {
                "id": 1,
                "price": 12.12,
                "color": "Red",
                "size": "M"
            }
        ]
    },
    {
        "id": 2,
        "category": "T-Shirt",
        "name": "Black Formal Shirt",
        "brand": "U.S.P",
        "seller": "ABC Retailer",
        "sku": [
            {
                "id": 4,
                "price": 12.12,
                "color": "Red",
                "size": "M"
            }
        ]
    }
	]
```
## `GET /v1/catalog/product/{seller}

## request
```json
{
  v1/catalog/product/ABC Retailer
}
```
## POST /v1/catalog/product/skus
#Request
```json
{
   "productId":1,
   "skuIds":[1,2]
}
```
## Response

```json
[
    {
        "id": 1,
        "price": 12.12,
        "color": "Red",
        "size": "M"
    },
    {
        "id": 2,
        "price": 12.0,
        "color": "Green",
        "size": "L"
    }
]
```


