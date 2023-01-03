package com.bestbuy.testsuite;

import com.bestbuy.bestbuyinfo.StoresSteps;
import com.bestbuy.utils.TestUtils;
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
public class StoreCRUDTest extends TestBase {
    static String name = "Abc" + TestUtils.getRandomValue();
    static String type = "Def"+ TestUtils.getRandomValue();
    static String address = "125 camerose avenue" + TestUtils.getRandomValue();
    static String address2 = "abc";
    static String city = "London";
    static String state = "MN";
    static String zip = "25687";
    static double lat = 44.879314;
    static double lng = 93.077156;
    static String hours = "Mon:9-5;Tues:9-5;Wed:9-5;Thurs:10-6;Fri:9-5;Sat:9-5;Sun:9-5";
    static int storeID;

    @Steps
    StoresSteps storesSteps;

    @Title("Create new store record")
    @Test
    public void test005(){
        storesSteps.createStore(name,type,address,address2,city,state,zip,lat,lng,hours).log().all().statusCode(201);
    }

    @Title("Verify new store has been created")
    @Test
    public void test006(){
        HashMap<String,Object>storeRecord=storesSteps.getStoreInformationWithName(name);
        Assert.assertThat(storeRecord,hasValue(name));
        storeID=(int)storeRecord.get("id");
    }

    @Title("Change store information and verify change has been updated in the record")
    @Test
    public void test007(){
        name=name+TestUtils.getRandomValue();
        storesSteps.changeStoreInfo(storeID,name,type,address,address2,city,state,zip,lat,lng,hours).log().all().statusCode(404);
        HashMap<String,Object>storeRecord=storesSteps.getStoreInformationWithName(name);
        Assert.assertThat(storeRecord,hasValue(name));
    }

    @Title("Delete store record and verify store has been deleted")
    @Test
    public void test008(){
       storesSteps.deleteStoreRecord(storeID).log().all().statusCode(200);
       storesSteps.verifyStoreRecord(storeID).log().all().statusCode(404);
    }

}
