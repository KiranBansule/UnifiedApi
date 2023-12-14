package Auth;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Properties.PropertiesUrl;
import RequestData.XLUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GenerateOtp {

    @Test(dataProvider = "loginPage")
    void postGenOtp(String mobile, String privacyAgreement, String languageId, String expectedStatus, String expectedMessage) {

        PropertiesUrl proper = new PropertiesUrl();
        String url = proper.getbasedUrl();
        String requestBody = "{\"mobile\": \"%s\", \"privacyAgreement\": \"%s\",\"languageId\": \"%s\"}".formatted(mobile, privacyAgreement, languageId);

        Response response = RestAssured.given()
                .baseUri(url) // Use baseUri instead of concatenating the URL manually
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/auth/generate-otp") // Use the full path starting from the base URI
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        String actualStatus = response.jsonPath().getString("status");
        String actualMessage = response.jsonPath().getString("message");

        // Assert that the actual values match the expected values
        assertEquals(actualStatus, expectedStatus);
        assertEquals(actualMessage, expectedMessage);
    }

    @DataProvider(name = "loginPage")
    Object[][] getLogin() throws IOException {
        String path = "C:\\Users\\Kiran\\Unified\\UnifiedAPI\\src\\test\\java\\RequestData\\RequestData.xlsx";
        int rownum = XLUtils.getRowCount(path, "login");
        int colnum = XLUtils.getCellCount(path, "login", 1);

        Object loginData[][] = new Object[rownum][colnum];

        for (int i = 1; i <= rownum; i++) {
            for (int j = 0; j < colnum; j++) {
                loginData[i - 1][j] = XLUtils.getCellData(path, "login", i, j);
            }
        }
        return loginData;
    }
}
