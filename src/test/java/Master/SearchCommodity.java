package Master;

import static io.restassured.RestAssured.given;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import Auth.Context;
import Properties.PropertiesUrl;
import io.restassured.module.jsv.JsonSchemaValidator;

public class SearchCommodity {
	@Test

	void getSearch_commodity(ITestContext context) {

		PropertiesUrl proper = new PropertiesUrl();

		String url = proper.getbasedUrl();

		String jwtToken = Context.getAttribute("jToken");

		// System.out.println(jwtToken);

		// public Properties properties;
		given().header("Authorization", "Bearer " + jwtToken)

				.when().get(url + "/commodities/autocomplete?st=3&tt=1&ct=4&s=ar").then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("MasterJsonSchema\\Search_commodity.json")).log()
				.all();

	}
}
