# Outfit Coordination 



## 코드 실행 방법


### Pre Requisite
- Docker 설치
### Docker Image 생성 & 실행
```shell
# project root 에서 실행
$ docker build -t outfit-coordination .
$ docker run -p 8080:8080 outfit-coordination
```

### Swagger UI
- http://localhost:8080/swagger-ui.html

## 구현 내용


### 1. 카테고리별 최저가 브랜드 및 가격 조회
- **설명**: 각 카테고리에서 최저가로 상품을 제공하는 브랜드와 가격 및 합산 총액 조회
- **엔드포인트**: `GET /v1/product-categories/lowest-price-brands`
- **요청**: 추가 파라미터 없음
- **응답**:
    - 형식: `CategoryWiseLowestPriceBrandResponse`
    - 필드:
        - `items`: 카테고리별 최저가 브랜드와 가격 정보 (리스트)
        - `totalPrice`: 모든 카테고리의 최저가 상품 합산 총액 
- **예시**:
  ```json
  {
    "items": [
      { "productCategoryName": "상의", "brandName": "C", "price": "10,000" },
      { "productCategoryName": "하의", "brandName": "A", "price": "20,000" }
    ],
    "totalPrice": "30,000"
  }
  ```

### 2. 단일 브랜드로 전체 카테고리 상품 구매 시 최저가 조회
- **설명**: 하나의 브랜드에서 모든 카테고리의 상품을 구매할 때 가장 저렴한 브랜드와 총 비용 조회
- **엔드포인트**: `GET /v1/brands/lowest-total-price`
- **요청**: 추가 파라미터 없음
- **응답**:
    - 형식: `BrandWiseLowestTotalPriceResponse`
    - 필드:
        - `lowestTotalPrice`: 최저가 브랜드 정보, 카테고리별 가격, 총액
- **예시**:
  ```json
  {
    "lowestTotalPrice": {
      "brandName": "B",
      "items": [
        { "productCategoryName": "상의", "price": "11,000" },
        { "productCategoryName": "하의", "price": "21,000" }
      ],
      "total": "32,000"
    }
  }
  ```

### 3. 특정 카테고리의 최저가 및 최고가 브랜드 조회
- **설명**: 특정 카테고리의 최저가 브랜드와 최고가 브랜드 조회
- **엔드포인트**: `GET /v1/product-categories/{categoryName}/price-range-brands`
- **요청**:
    - 경로 파라미터: `categoryName` (예: "상의")
- **응답**:
    - 형식: `CategoryWisePriceRangeBrandResponse`
    - 필드:
        - `productCategoryName`: 카테고리 이름
        - `lowestPriceBrand`: 최저가 브랜드와 가격
        - `highestPriceBrand`: 최고가 브랜드와 가격
- **예시**:
  ```json
  {
    "productCategoryName": "상의",
    "lowestPriceBrand": { "brandName": "C", "price": "10,000" },
    "highestPriceBrand": { "brandName": "A", "price": "15,000" }
  }
  ```

### 4. 브랜드 및 상품 관리
- **설명**: 브랜드와 상품을 추가, 수정, 삭제
- **엔드포인트**:
    1. **브랜드 추가**: `POST /v1/brands`
        - 요청: `BrandCreateRequest`
        - 응답: `BrandIdResponse` (생성된 브랜드 ID)
    2. **브랜드 업데이트**: `PUT /v1/brands/{id}`
        - 요청: `BrandUpdateRequest`
        - 응답: 204 No Content
    3. **브랜드 삭제**: `DELETE /v1/brands/{id}`
        - 응답: 204 No Content
    4. **상품 추가**: `POST /v1/brands/{brandId}/products`
        - 요청: `ProductCreateRequest`
        - 응답: `ProductIdResponse` (생성된 상품 ID)
    5. **상품 업데이트**: `PUT /v1/brands/{brandId}/products/{id}`
        - 요청: `ProductUpdateRequest`
        - 응답: 204 No Content
    6. **상품 삭제**: `DELETE /v1/brands/{brandId}/products/{id}`
        - 응답: 204 No Content