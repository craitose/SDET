package testCases;


import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;


public class TC_VideoGame_API {

	@Test(priority=5)
	public void test_GetAllVideoGames() {
		
		given()
		
		.when()
		    .get("http://localhost:8080/app/videogames")
		    .then()
		         .statusCode(200);
		
		
	}
	
	@Test(priority=1)
	public void test_AddNewVideoGame() {
		
		HashMap data=new HashMap(); //payload
		data.put("id", "101");
		data.put("name", "Anna");
		data.put("releaseDate", "2021-09-20");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("Rating", "Universal");
		
		Response res=
		
		given()
		     .contentType("application/json")
		     .body(data)
		     .when()
		     .post("http://localhost:8080/app/videogames")
		     .then()
		       
		       .statusCode(200)
		       .log().body()
		       .extract().response();
		
		String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
		
	}
	
	@Test(priority=2)
	public void test_GetVideoGame() {
		
		given()
		
		.when()
		    .get("http://localhost:8080/app/videogames/101")
		    .then()
		         .statusCode(200)
		         .log().body()
		         .body("videoGame.id", equalTo("101"))
		         .body("videoGame.name", equalTo("Anna"));
		
		
	}
	@Test(priority=3)
	public void test_UpdateVideoGame() {
		
		
		HashMap data=new HashMap(); //payload
		data.put("id", "101");
		data.put("name", "Laska");
		data.put("releaseDate", "2001-04-23T00:00:00+01:00");
		data.put("reviewScore", "7");
		data.put("category", "Adventure");
		data.put("Rating", "Universal");
		
		given()
		    .contentType("application/json")
		    .body(data)
		    .when()
		     .put("http://localhost:8080/app/videogames/101")
		     .then()
		         .statusCode(200)
		         .log().body()
		         .body("videoGame.id", equalTo("101"))
		         .body("videoGame.name",equalTo("Laska"))
		         .body("videoGame.reviewScore",equalTo("7"));
	}
	
	@Test(priority=4)
	public void test_DeleteVideoGame() {
		
		Response res=
		
		given()
		.when() 
		    .delete("http://localhost:8080/app/videogames/101")
		.then()
		    .statusCode(200)
		    .log().body()
		    .extract().response();
		
		String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),true);
	}
}