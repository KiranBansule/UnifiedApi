package Properties;
import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUrl {
   public Properties properties;

    public PropertiesUrl() {
        properties = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Kiran\\Unified\\UnifiedAPI\\src\\test\\resources\\application.properties")) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
public String getbasedUrl() {
    return properties.getProperty("baseURL");
}



}