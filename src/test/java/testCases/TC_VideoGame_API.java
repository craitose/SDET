package testCases;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

@FixMethodOrder(MethodSorters.DEFAULT)
public class TC_VideoGame_API {

	@Test
	public void test_GetAllVideoGames() {
		
		given()
		
		.when()
		    .get("http://localhost:8080/app/videogames")
		    .then()
		         .statusCode(200);
		
		
	}
	
	@Test
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
	
	@Test
	public void test_GetVideoGame() {
		
		given()
		
		.when()
		    .get("http://localhost:8080/app/videogames/1")
		    .then()
		         .statusCode(200)
		         .log().body()
		         .body("videoGame.id", equalTo("1"))
		         .body("videoGame.name", equalTo("Resident Evil 4"));
		
		
	}
	@Test
	public void test_UpdateVideoGame() {
		
		
		HashMap data=new HashMap(); //payload
		data.put("id", "10");
		data.put("name", "Grand Theft Auto III");
		data.put("releaseDate", "2001-04-23T00:00:00+01:00");
		data.put("reviewScore", "7");
		data.put("category", "Driving");
		data.put("Rating", "Mature");
		
		given()
		    .contentType("application/json")
		    .body(data)
		    .when()
		     .put("http://localhost:8080/app/videogames/10")
		     .then()
		         .statusCode(200)
		         .log().body()
		         .body("videoGame.id", equalTo("10"))
		         .body("videoGame.releaseDate",equalTo("2001-04-23T00:00:00+01:00"))
		         .body("videoGame.reviewScore",equalTo("7"));
	}
	
	@Test
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