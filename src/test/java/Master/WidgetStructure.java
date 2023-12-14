package Master;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Auth.Context;
import Properties.PropertiesUrl;
import RequestData.XLUtils;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;

public class WidgetStructure {
	@Test(dataProvider = "Casedata")

	void getWidgetStructure(String caseId, String Widget, String Output) {

		PropertiesUrl proper = new PropertiesUrl();

		String url = proper.getbasedUrl();

		String jwtToken = Context.getAttribute("jToken");

		// System.out.println(jwtToken);

		// public Properties properties;
		io.restassured.response.Response response = given().header("Authorization", "Bearer " + jwtToken)

				.pathParam("caseId", caseId).pathParam("Widget", Widget)

				.when().get(url + "/cases/{caseId}/doc-widget-structure?w={Widget}").then().log().all().extract()
				.response();
		;

		int statusCode = response.getStatusCode();

		String actualOutput = response.asString();
		assertEquals(actualOutput, Output);
		// .assertThat()
		// .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("MasterJsonSchema\\Widget.json")).log().all();
	}

	@DataProvider(name = "Casedata")
	Object[][] getCaseData() throws IOException {
		String path = "C:\\Users\\Kiran\\Unified\\UnifiedAPI\\src\\test\\java\\RequestData\\RequestData.xlsx";
		int rownum = XLUtils.getRowCount(path, "Widget");
		int colnum = XLUtils.getCellCount(path, "Widget", 1);

		Object widgetData[][] = new Object[rownum][colnum];

		for (int i = 1; i <= rownum; i++) {
			for (int j = 0; j < colnum; j++) {
				widgetData[i - 1][j] = XLUtils.getCellData(path, "Widget", i, j);
			}
		}
		return widgetData;
	}
}
