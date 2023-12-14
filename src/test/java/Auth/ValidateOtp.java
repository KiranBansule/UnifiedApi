package Auth;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.IAttributes;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Properties.PropertiesUrl;
import RequestData.XLUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

public class ValidateOtp {
	
	String jwtToken;

	@Test(dataProvider = "otp")
	    void postValOtp(String mobile, String otp) {
	    
	        PropertiesUrl proper = new PropertiesUrl();

	        String url = proper.getbasedUrl();

	        String requestBody = "{\"mobile\": \"%s\", \"otp\": \"%s\"}".formatted(mobile, otp);

	        jwtToken = RestAssured.given()

					.contentType(ContentType.JSON).body(requestBody)

					.when()

					.post(url + "/auth/validate-otp")

					.jsonPath().getString("data.jwt");

			
			Context.setAttribute("jToken", jwtToken);

	        System.out.print(jwtToken);
	        
	      
			
	       
	       
	    }
	


		@DataProvider(name = "otp")
	    Object[][] getLogin() throws IOException {
	        String path = "C:\\Users\\Kiran\\Unified\\UnifiedAPI\\src\\test\\java\\RequestData\\RequestData.xlsx";
	        int rownum = XLUtils.getRowCount(path, "ValidateOtp");
	        int colnum = XLUtils.getCellCount(path, "ValidateOtp", 1);

	        Object otpData[][] = new Object[rownum][colnum];

	        for (int i = 1; i <= rownum; i++) {
	            for (int j = 0; j < colnum; j++) {
	                otpData[i - 1][j] = XLUtils.getCellData(path, "ValidateOtp", i, j);
	            }
	        }
	        return otpData;
	    }
	}
