package AddCase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Auth.Context;
import Properties.PropertiesUrl;
import RequestData.XLUtils;
import io.restassured.module.jsv.JsonSchemaValidator;

public class SaveSowingDate {
	@Test(dataProvider = "sowingDate")
	void PatchSaveSowingDate(String caseId, String sowingDate, String sowingAreaSqm, String areaUomId,String isFruitingStarted,String fruitingStartDate,String harvestNumber, String harvestDate,String  expectedStatus,String expectedMessage,String Output) {

		PropertiesUrl proper = new PropertiesUrl();
		String url = proper.getbasedUrl();
		String id = caseId;
		String requestBody = "{\"sowingDate\": \"%s\",\"sowingAreaSqm\": \"%s\",\"areaUomId\": \"%s\",\"isFruitingStarted\": \"%s\",\"fruitingStartDate\": \"%s\",\"harvestNumber\": \"%s\",\"harvestDate\": \"%s\"}"
				.formatted(sowingDate, sowingAreaSqm, areaUomId,isFruitingStarted,fruitingStartDate,harvestNumber,harvestDate);
		System.out.println(requestBody);

		String jwtToken = Context.getAttribute("jToken");

		io.restassured.response.Response response = given().header("Authorization", "Bearer " + jwtToken)
				.pathParam("caseId", id).contentType("application/json")

				.body(requestBody)

				.when().patch(url + "/cases/{caseId}/sowing-date").then()

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

	@DataProvider(name = "sowingDate")
	Object[][] getCaseData() throws IOException {
		String path = "C:\\Users\\Kiran\\Unified\\UnifiedAPI\\src\\test\\java\\RequestData\\RequestData.xlsx";
		int rownum = XLUtils.getRowCount(path, "SowingDate");
		int colnum = XLUtils.getCellCount(path, "SowingDate", 1);

		Object sowingDateData[][] = new Object[rownum][colnum];

		for (int i = 1; i <= rownum; i++) {
			for (int j = 0; j < colnum; j++) {
				sowingDateData[i - 1][j] = XLUtils.getCellData(path, "SowingDate", i, j);
			}
		}
		return sowingDateData;
	}
}
