package AddCase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
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
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class SaveCropType {

	@Test(dataProvider = "CropType")
	void PatchCropType(String caseId, String cropTypeId, String expectedStatus, String expectedMessage, String Output) {

		PropertiesUrl proper = new PropertiesUrl();
		String url = proper.getbasedUrl();
		String id = caseId;
		String requestBody = "{\"cropTypeId\": \"%s\"}".formatted(cropTypeId);
		System.out.println(requestBody);

		String jwtToken = Context.getAttribute("jToken");

		io.restassured.response.Response response = given().header("Authorization", "Bearer " + jwtToken)
				.pathParam("caseId", id).contentType("application/json")

				.body(requestBody)

				.when().patch(url + "/cases/{caseId}/crop-type").then()

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

	@DataProvider(name = "CropType")
	Object[][] getCaseData() throws IOException {
		String path = "C:\\Users\\Kiran\\Unified\\UnifiedAPI\\src\\test\\java\\RequestData\\RequestData.xlsx";
		int rownum = XLUtils.getRowCount(path, "CropType");
		int colnum = XLUtils.getCellCount(path, "CropType", 1);

		Object CropData[][] = new Object[rownum][colnum];

		for (int i = 1; i <= rownum; i++) {
			for (int j = 0; j < colnum; j++) {
				CropData[i - 1][j] = XLUtils.getCellData(path, "CropType", i, j);
			}
		}
		return CropData;
	}

}
