package Master;

import static io.restassured.RestAssured.given;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import Auth.Context;
import Properties.PropertiesUrl;
import io.restassured.module.jsv.JsonSchemaValidator;

public class SearchVariety {
	@Test

	void getSearch_variety() {

		PropertiesUrl proper = new PropertiesUrl();

		String url = proper.getbasedUrl();
		String jwtToken = Context.getAttribute("jToken");

		// System.out.println(jwtToken);

		// public Properties properties;
		given().header("Authorization", "Bearer " + jwtToken)

				.when().get(url + "/varieties/autocomplete?c=1").then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("MasterJsonSchema\\Search_variety.json")).log()
				.all();
	}
}
