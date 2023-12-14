package Master;

import static io.restassured.RestAssured.given;

import Auth.Context;
import Properties.PropertiesUrl;
import io.restassured.module.jsv.JsonSchemaValidator;

public class ActiveCase {
	void getActiveCase() {

		PropertiesUrl proper = new PropertiesUrl();

		String url = proper.getbasedUrl();
		String jwtToken = Context.getAttribute("jToken");

		// System.out.println(jwtToken);

		// public Properties properties;
		given().header("Authorization", "Bearer " + jwtToken)

				.when().get(url + "/cases/active").then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("MasterJsonSchema\\ActiveCase.json")).log()
				.all();
	}

}
