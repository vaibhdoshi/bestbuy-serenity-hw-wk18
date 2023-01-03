package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.Path;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class StoresSteps {
    @Step("Create new store with name:{0},type:{1},address:{2},address2:{3},city:{4},state:{5},zip:{6},lat:{7},lng:{8},hours:{9}")
    public ValidatableResponse createStore(String name, String type, String address, String address2, String city, String state, String zip, double lat, double lng, String hours) {
        StorePojo storePojo = StorePojo.storePojo(name, type, address, address2, city, state, zip, lat, lng, hours);
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(storePojo)
                .when()
                .post(Path.STORE)
                .then();
    }

    @Step("Get Store information with store name:{0}")
    public HashMap<String, Object> getStoreInformationWithName(String name) {
        String p1 = "data.findAll{it.name=='";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .queryParam("name", name)
                .when()
                .get(Path.STORE)
                .then()
                .extract()
                .path(p1 + name + p2);
    }

    @Step("Change store information for store ID: {0}, name: {1}, type: {2}, address: {3}, address2: {4}, city: {5}, state: {6}," +
            "zip: {7}, lat: {8}, lng: {9}, hours: {10} ")
    public ValidatableResponse changeStoreInfo(int storeID, String name, String type, String address, String address2, String city, String state, String zip, double lat, double lng, String hours) {
        StorePojo storePojo = StorePojo.storePojo(name, type, address, address2, city, state, zip, lat, lng, hours);
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("storeID", storeID)
                .body(storePojo)
                .when()
                .put(Path.STORE + EndPoints.CHANGE_STORE_INFO)
                .then();

    }

    @Step("Delete store record for storeID:{0}")
    public ValidatableResponse deleteStoreRecord(int storeID) {
        return SerenityRest.given()
                .log().all()
                .pathParam("storeID", storeID)
                .when()
                .delete(Path.STORE + EndPoints.DELETE_STORE).then();
    }

    @Step("Verify store record has been deleted for storeID:{0}")
    public ValidatableResponse verifyStoreRecord(int storeID) {
        return SerenityRest.given()
                .log().all()
                .pathParam("storeID", storeID)
                .when()
                .get(Path.STORE + EndPoints.GET_STORE_BY_ID)
                .then();
    }


}
