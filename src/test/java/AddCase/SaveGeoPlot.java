package AddCase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Auth.Context;
import Properties.PropertiesUrl;
import RequestData.XLUtils;

public class SaveGeoPlot {
	@Test(dataProvider = "GeoPlot")
	void PatchSaveGeoPlot(String caseId, String polygon, String plottedAreaSqm,String expectedStatus,String expectedMessage,String Output) {

		PropertiesUrl proper = new PropertiesUrl();
		String url = proper.getbasedUrl();
		String id = caseId;
		String requestBody = "{\"polygon\": \"%s\",\"plottedAreaSqm\": %s}".formatted(polygon, plottedAreaSqm);
		System.out.println(requestBody);

		String jwtToken = Context.getAttribute("jToken");

		io.restassured.response.Response response = given().header("Authorization", "Bearer " + jwtToken)
				.pathParam("caseId", id).contentType("application/json")

				.body(requestBody)

				.when().patch(url + "/cases/{caseId}/geo-plot").then()

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

	@DataProvider(name = "GeoPlot")
	Object[][] getCaseData() throws IOException {
		String path = "C:\\Users\\Kiran\\Unified\\UnifiedAPI\\src\\test\\java\\RequestData\\RequestData.xlsx";
		int rownum = XLUtils.getRowCount(path, "GeoPlot");
		int colnum = XLUtils.getCellCount(path, "GeoPlot", 1);

		Object geoPlotData[][] = new Object[rownum][colnum];

		for (int i = 1; i <= rownum; i++) {
			for (int j = 0; j < colnum; j++) {
				geoPlotData[i - 1][j] = XLUtils.getCellData(path, "GeoPlot", i, j);
			}
		}
		return geoPlotData;
	}
}
