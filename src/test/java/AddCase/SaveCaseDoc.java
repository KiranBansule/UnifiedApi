package AddCase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Auth.Context;
import Properties.PropertiesUrl;
import RequestData.XLUtils;

public class SaveCaseDoc {
	@Test(dataProvider = "UploadDocument")
    void PatchSaveCaseDoc(String caseId, String entityId, String widgetId, String deleted, String file,String files1,String files2,String Output) {
        PropertiesUrl proper = new PropertiesUrl();
        String url = proper.getbasedUrl();
        String id = caseId;
        String imagePath = file;
        String imagePath1 = files1;
        String imagePath2 = files2;
        String jwtToken = Context.getAttribute("jToken");
        System.out.println(jwtToken);

       // String[] filePaths = files.split(",");
        io.restassured.response.Response response = null;

       // for (String filePath : filePaths) {
          //  File file = new File(filePath.trim());
         //   if (file.exists()) {
        File fileA = new File(imagePath);
        File fileB = new File(imagePath1);
        File fileC = new File(imagePath2);
                response = given().header("Authorization", "Bearer " + jwtToken).pathParam("caseId", id)
                        .multiPart("entityId", entityId).multiPart("widgetId", widgetId).multiPart("deleted", deleted)
                        .multiPart("files", fileA)
                        .multiPart("files", fileB) 
                        .multiPart("files", fileC) // Add each file as a multi-part parameter
                        .when().patch(url + "/cases/{caseId}/upload-docs").then().log().all().extract().response();
                
                
                int statusCode = response.getStatusCode();
                String actualOutput = response.asString();
                assertEquals(actualOutput, Output);

	}
        
			// ... existing code ...
	//}
	@DataProvider(name = "UploadDocument")
	Object[][] getCaseData() throws IOException {
		String path = "C:\\Users\\Kiran\\Unified\\UnifiedAPI\\src\\test\\java\\RequestData\\RequestData.xlsx";
		int rownum = XLUtils.getRowCount(path, "UploadDoc");
		int colnum = XLUtils.getCellCount(path, "UploadDoc", 1);

		Object UploadDocData[][] = new Object[rownum][colnum];

		for (int i = 1; i <= rownum; i++) {
			for (int j = 0; j < colnum; j++) {
				UploadDocData[i - 1][j] = XLUtils.getCellData(path, "UploadDoc", i, j);
			}
		}
		return UploadDocData;
	}
}
