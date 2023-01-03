package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.Path;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;


public class ProductsSteps {
    @Step("Create new product with name: {0}, type: {1}, price: {2}, upc: {3}, shipping: {4}, description: {5}, manufacturer: {6}, model: {7}, url: {8}, image: {9}")
    public ValidatableResponse createProduct(String name, String type, double price, String upc, int shipping, String description, String manufacturer, String model, String url, String image) {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setShipping(shipping);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(productPojo)
                .when()
                .post(Path.PRODUCT)
                .then();

    }

    @Step("Verify product with name:{0}")
    public HashMap<String,Object> verifyProductInformation(String name){
        String p1 = "data.findAll{it.name=='";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .queryParam("name",name)
                .when()
                .get(Path.PRODUCT)
                .then()
                .extract()
                .path(p1+name+p2);
    }

    @Step("Updating product with productID: {0},name:{1},type:{2},price:{3},upc:{4},shipping:{5},description:{6},manufacturer:{7},model:{8},url:{9},image:{10}")
    public ValidatableResponse updateProduct(int productID, String name,String type, double price, String upc, int shipping, String description, String manufacturer, String model, String url, String image) {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setShipping(shipping);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);
        return SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .pathParam("productID",productID)
                .body(productPojo)
                .when()
                .put(Path.PRODUCT + EndPoints.Update_Product_By_ID)
                .then();
    }

    @Step("Deleting product with productID: {0}")
    public ValidatableResponse deleteProduct(int productID){
        return SerenityRest.given().log().all()
                .pathParam("productID",productID)
                .when()
                .delete(Path.PRODUCT + EndPoints.Delete_Product)
                .then();
    }

    @Step("Verify product has been deleted for productID:{0}")
    public ValidatableResponse verifyProductDeleted(int productID){
        return SerenityRest.given().log().all()
                .pathParam("productID",productID)
                .when()
                .get(Path.PRODUCT+EndPoints.Get_Product_By_ID)
                .then();
    }


}
