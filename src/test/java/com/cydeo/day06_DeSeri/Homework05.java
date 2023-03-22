package com.cydeo.day06_DeSeri;

import com.cydeo.pojo.FormulaConstructor;
import com.cydeo.pojo.FormulaMRData;
import com.cydeo.utilities.FomulaTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class Homework05 extends FomulaTestBase {

    /*

    TASK 2 :
 NOTE --> Solve same task with 4 different way

 - Use JSONPATH
 int total = jsonpath.getInt(“pathOfField”)

 - Use HAMCREST MATCHERS
 then().body(……….)
Print all names of constructor by using extract method after HamCrest

 - Convert Constructor information to Java Structure
 List<Map<String,Object>>
	 	 	 constructorMap=jsonpath.getList(“pathOfConsts”)

 - Convert Constructor information POJO Class
List<ConstructorPOJO>
	 	 	 constr=getObject(“pathOfConstr”,ConstructorPOJO.class)
NOTE —> There is a class in JAVA That’s why give your class name
ConstrutorPOJO to separate from existing one. Wrong imports may cause issue
 - Given accept type is json
 - When user send request /constructorStandings/1/constructors.json
 - Then verify status code is 200
 - And content type is application/json; charset=utf-8
 - And total is 17
- And limit is 30
 - And each constructor has constructorId
 - And constructor has name
 - And size of constructor is 17 (using with hasSize)
 - And constructor IDs has “benetton”, “mercedes”,”team_lotus”
 - And names are in same order by following list

 List<String> names =Arrays.asList("Benetton", "Brabham-Repco", "Brawn", "BRM", "Cooper-Climax", "Ferrari", "Lotus-Climax", "Lotus-Ford", "Matra-Ford", "McLaren", "Mercedes", "Red Bull", "Renault", "Team Lotus", "Tyrrell", "Vanwall", "Williams");

     */
    List<String> constructorID = Arrays.asList("benetton", "brabham-repco", "brawn", "brm", "cooper-climax", "ferrari", "lotus-climax", "lotus-ford", "matra-ford", "mclaren", "mercedes", "red bull", "renault", "team lotus", "tyrrell", "vanwall", "williams");

    List<String> names = Arrays.asList("Benetton", "Brabham-Repco", "Brawn", "BRM", "Cooper-Climax", "Ferrari", "Lotus-Climax", "Lotus-Ford", "Matra-Ford", "McLaren", "Mercedes", "Red Bull", "Renault", "Team Lotus", "Tyrrell", "Vanwall", "Williams");


    @DisplayName("JsonPath")
    @Test
    public void testWithJsonPath() {
        Response response = given().accept(ContentType.JSON)
                .when().get("/constructorStandings/1/constructors.json")
                .prettyPeek();

        assertEquals(200, response.statusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());

        JsonPath js = response.jsonPath();

        //get total
        int total = js.getInt("MRData.total");
        System.out.println("total = " + total);
        assertEquals(17, total);

//get size of constructor
        List<String> constructorNames = js.getList("MRData.ConstructorTable.Constructors.names");
        assertEquals(17, constructorNames.size());
//not null
        assertThat(constructorNames, everyItem(notNullValue()));

//get constructorStandings
        int constructorStandings = js.getInt("MRData.ConstructorTable.constructorStandings");
        System.out.println("constructorStandings = " + constructorStandings);

        //Get 1st constructorId
        String constructorId = js.getString("MRData.ConstructorTable.Constructors.constructorId[0]");
        System.out.println("constructorId = " + constructorId);

//constructor has constructorId
        assertThat("MRData.ConstructorTable.Constructors.constructorId", notNullValue());

    }

    @DisplayName("HamCrest")
    @Test
    public void testWithHamCrest() {

        JsonPath jsonPath = given().accept(ContentType.JSON)
                .when().get("/constructorStandings/1/constructors.json")
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .body("MRData.url", is("http://ergast.com/api/f1/constructorstandings/1/constructors.json"))
                .body("MRData.ConstructorTable.Constructors.constructorId", everyItem(notNullValue()))
                .body("MRData.ConstructorTable.Constructors", hasSize(17))
                .body("MRData.ConstructorTable.Constructors.constructorId", hasItems("benetton", "mercedes", "team_lotus"))
                .body("MRData.ConstructorTable.Constructors.name", equalTo(names))
                .body("MRData.ConstructorTable.constructorStandings", is("1"))
                .body("MRData.ConstructorTable.Constructors.constructorId[0]", is("benetton"))
                .extract().response().jsonPath();


    }

    @DisplayName("Deserilization")
    @Test
    public void testWithDeserilization() {
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .when().get("/constructorStandings/1/constructors.json")
                //  .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .extract().response().jsonPath();

        List<Map<String, Object>> constructorMap = jsonPath.getList("MRData.ConstructorTable.Constructors");
        System.out.println("constructorMap = " + constructorMap);

        //Get 1st constructorID
        String constructorFirstId = (String) constructorMap.get(0).get("constructorId");
        System.out.println("constructorId = " + constructorFirstId);
        assertEquals("benetton", constructorFirstId);

        //Get 3rd constructorNationality:
        String thirdContructorNatinality = (String) constructorMap.get(2).get("nationality");
        System.out.println("thirdContructorNatinality = " + thirdContructorNatinality);

    }

    @DisplayName("Deserilization with POJO")
    @Test
    public void testWithDeserilizationPojo() {
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .when().get("/constructorStandings/1/constructors.json")
                //  .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .extract().response().jsonPath();

        FormulaMRData formulaMRData = jsonPath.getObject("MRData", FormulaMRData.class);
        System.out.println("formulaMRData = " + formulaMRData);

 //assert Limit and Total:
        assertEquals("30", formulaMRData.getLimit());
        assertEquals("17", formulaMRData.getTotal());

        List<FormulaConstructor> allConstructors = jsonPath.getList("MRData.ConstructorTable.Constructors", FormulaConstructor.class);

        System.out.println("allConstructors = " + allConstructors);

        List<String> allConstructorIds = new ArrayList<>();

        List<String> allConstructorNames = new ArrayList<>();

        for(FormulaConstructor each :allConstructors){
            allConstructorIds.add(each.getConstructorId());
            allConstructorNames.add(each.getName());
        }

        System.out.println("allConstructorNames = " + allConstructorNames);
        System.out.println("allConstructorIds = " + allConstructorIds);

//  And each constructor has constructorId

//  And constructor has name

// And size of constructor is 17 (using with hasSize)
assertThat(allConstructors, hasSize(17));

// And constructor IDs has “benetton”, “mercedes”,”team_lotus”
assertThat(allConstructorIds, hasItems("benetton", "mercedes","team_lotus"));
    }
}