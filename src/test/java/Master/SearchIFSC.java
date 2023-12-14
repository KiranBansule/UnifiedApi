package Master;

import static io.restassured.RestAssured.given;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import Auth.Context;
import Properties.PropertiesUrl;
import io.restassured.module.jsv.JsonSchemaValidator;

public class SearchIFSC {
	@Test

	void getSearchIFSC() {

		PropertiesUrl proper = new PropertiesUrl();

		String url = proper.getbasedUrl();
		String jwtToken = Context.getAttribute("jToken");

		// System.out.println(jwtToken);

		// public Properties properties;
		given().header("Authorization", "Bearer " + jwtToken)

				.when().get(url + "/branch?ifsc=ABHY0065001").then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("MasterJsonSchema\\Search_IFSC.json")).log()
				.all();
	}
}
