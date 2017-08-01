package crawler.setup;

import com.sun.jndi.toolkit.url.Uri;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by nayak on 19.06.17.
 */
public class Configuration {
    InputStream inputStream;
    private static String testURL ;
    private static String testPlatform = "";


    /**
     *
     * @return
     * @throws IOException
     */
    Properties  readConfigFile() throws IOException {

        Properties prop = null;
        try {

            prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            testURL = prop.getProperty("testurl");
            testPlatform = prop.getProperty("testplatform");

        } catch (Exception e)
        {
            System.out.println("Exception: " + e);
        }finally {
            inputStream.close();
        }

        return prop;
    }


    public String getTestURL() throws IOException {
        return testURL;
    }

    public String getTestPlatform() {
        return testPlatform;
    }

    public String getFolderName() throws IOException {
        Uri uri = new Uri(getTestURL());
        String host = uri.getHost();
        String FILENAME_PREFIX = host.startsWith("www.") ? host.substring(4) : host;
        FILENAME_PREFIX = FILENAME_PREFIX + "_" + testPlatform;
        return FILENAME_PREFIX;
    }


}
