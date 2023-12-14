package Master;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Auth.Context;
import Properties.PropertiesUrl;
import RequestData.XLUtils;
import io.restassured.module.jsv.JsonSchemaValidator;

public class PaymentInfo {
	@Test(dataProvider = "Casedata")

	void getPaymentInfo(String caseId) {

		PropertiesUrl proper = new PropertiesUrl();

		String url = proper.getbasedUrl();
		String id = caseId;

		String jwtToken = Context.getAttribute("jToken");

		// System.out.println(jwtToken);

		// public Properties properties;
		given().header("Authorization", "Bearer " + jwtToken)

				.pathParam("caseId", id)

				.when().get(url + "/cases/{caseId}/payment-details").then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("MasterJsonSchema\\Payment_info.json")).log()
				.all();
	}

	@DataProvider(name = "Casedata")
	Object[][] getCaseData() throws IOException {
		String path = "C:\\Users\\Kiran\\Unified\\UnifiedAPI\\src\\test\\java\\RequestData\\RequestData.xlsx";
		int rownum = XLUtils.getRowCount(path, "Case");
		int colnum = XLUtils.getCellCount(path, "Case", 1);

		Object caseData[][] = new Object[rownum][1]; // We only need one column for case IDs

		for (int i = 1; i <= rownum; i++) {
			caseData[i - 1][0] = XLUtils.getCellData(path, "Case", i, 0);
		}
		return caseData;
	}
}
