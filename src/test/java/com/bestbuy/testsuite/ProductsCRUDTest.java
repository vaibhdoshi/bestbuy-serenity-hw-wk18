package com.bestbuy.testsuite;

import com.bestbuy.bestbuyinfo.ProductsSteps;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import resources.testdata.TestBase;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductsCRUDTest extends TestBase {
    static String name = "Children books" + TestUtils.getRandomValue();
    static String type = "ABC" + TestUtils.getRandomValue();
    static double price = 5.49;
    static String upc = "041333244018";
    static int shipping = 0;
    static String description = "Adventure Books" + TestUtils.getRandomValue();
    static String manufacturer = "Disney";
    static String model = "AB2400B4Z";
    static String url = "http://www.bestbuy.com/site/Amazon-aaa-batteries-4-pack/43900.p?id=1051384074145&skuId=43900&cmp=RMXCC";
    static String image = "http://img.bbystatic.com/BestBuy_US/images/products/4390/43900_sa.jpg";
    static int productID;
    @Steps
    ProductsSteps productsSteps;


    @Title("This will create new product")
    @Test
    public void test001() {

     ValidatableResponse response= productsSteps.createProduct(name, type, price, upc, shipping, description, manufacturer, model, url, image).log().all().statusCode(201);

    }

    @Title("verify if new product has been created")
    @Test
    public void test002(){
        HashMap<String,Object> product=productsSteps.verifyProductInformation(name);
        productID = (int)product.get("id");
        Assert.assertThat(product, hasValue(name));
    }

    @Title("Change product name and verify the updated information")
    @Test
    public void test003(){
        name=name+TestUtils.getRandomValue();
        productsSteps.updateProduct(productID,name,type,price,upc,shipping,description,manufacturer,model,url,image).log().all().statusCode(200);

    }
    @Title("Delete product and verify if product has been deleted")
    @Test
    public void test004() {
        productsSteps.deleteProduct(productID).log().all().statusCode(204);
        productsSteps.verifyProductDeleted(productID).log().all().statusCode(404);
    }

}
