package AddCase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Auth.Context;
import Properties.PropertiesUrl;
import RequestData.XLUtils;
import io.restassured.module.jsv.JsonSchemaValidator;

public class SaveCommodity {
	@Test(dataProvider = "Commodity")
	void PatchSaveCommodity(String caseId, String commodityId,String expectedStatus,String expectedMessage,String Output) {

		PropertiesUrl proper = new PropertiesUrl();
		String url = proper.getbasedUrl();
		String id = caseId;
		String requestBody = "{\"commodityId\": \"%s\"}".formatted(commodityId);
		System.out.println(requestBody);

		String jwtToken = Context.getAttribute("jToken");

		io.restassured.response.Response response = given().header("Authorization", "Bearer " + jwtToken)
				.pathParam("caseId", id).contentType("application/json")

				.body(requestBody)

				.when().patch(url + "/cases/{caseId}/commodity").then()

				.log().all().extract().response();

		int statusCode = response.getStatusCode();

		String actualStatus = response.jsonPath().getString("status");
        String actualMessage = response.jsonPath().getString("message");
        String actualOutput = response.asString();
        // Assert that the actual values match the expected values
        assertEquals(actualStatus, expectedStatus);
        assertEquals(actualMessage, expectedMessage);
        assertEquals(actualOutput, Output);
	}

	@DataProvider(name = "Commodity")
	Object[][] getCaseData() throws IOException {
		String path = "C:\\Users\\Kiran\\Unified\\UnifiedAPI\\src\\test\\java\\RequestData\\RequestData.xlsx";
		int rownum = XLUtils.getRowCount(path, "Commodity");
		int colnum = XLUtils.getCellCount(path, "Commodity", 1);

		Object commodityData[][] = new Object[rownum][colnum];

		for (int i = 1; i <= rownum; i++) {
			for (int j = 0; j < colnum; j++) {
				commodityData[i - 1][j] = XLUtils.getCellData(path, "Commodity", i, j);
			}
		}
		return commodityData;
	}

}
