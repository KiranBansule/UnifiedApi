package AddCase;

import org.apache.http.entity.ContentType;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matcher.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import com.github.scribejava.core.model.Response;

import Auth.Context;
import Properties.PropertiesUrl;
import RequestData.XLUtils;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponseOptions;
import io.restassured.specification.RequestSpecification;

public class CreateCase {
	// public static String caseId;
	@Test(dataProvider = "TenderData")

	public void postCase(String tenderTypeId, String expectedStatus, String expectedMessage) throws Exception {
		PropertiesUrl proper = new PropertiesUrl();
		String url = proper.getbasedUrl();

		String requestBody = "{\"tenderTypeId\": \"%s\"}".formatted(tenderTypeId);

		String jwtToken = Context.getAttribute("jToken");

		io.restassured.response.Response response = given().header("Authorization", "Bearer " + jwtToken)
				.contentType("application/json")

				.body(requestBody)

				.when().post(url + "/cases").then()

				.log().all().extract().response();

		String actualStatus = response.jsonPath().getString("status");
		String actualMessage = response.jsonPath().getString("message");

		// Assert that the actual values match the expected values
		assertEquals(actualStatus, expectedStatus);
		assertEquals(actualMessage, expectedMessage);
	}

	@DataProvider(name = "TenderData")
	Object[][] getCaseData() throws IOException {
		String path = "C:\\Users\\Kiran\\Unified\\UnifiedAPI\\src\\test\\java\\RequestData\\RequestData.xlsx";
		int rownum = XLUtils.getRowCount(path, "TenderType");
		int colnum = XLUtils.getCellCount(path, "TenderType", 1);

		Object tenderData[][] = new Object[rownum][colnum];

		for (int i = 1; i <= rownum; i++) {
			for (int j = 0; j < colnum; j++) {
				tenderData[i - 1][j] = XLUtils.getCellData(path, "TenderType", i, j);
			}
		}
		return tenderData;
	}
}
