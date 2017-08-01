import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.InetAddress;

/**
 * Created by nayak on 19.06.17.
 */
public class SampleTestCase_desktop {

    public static void main(String[] args) throws Exception {

        //System.out.println(Inet4Address.getLocalHost().getHostAddress());
        //System.out.println(Inet4Address.getLocalHost().getHostName());

        //start the proxy
        //BrowserMobProxy bmproxy = new BrowserMobProxyServer();
        //bmproxy.start(8081,InetAddress.getByName("134.96.225.93"));
        //bmproxy.start();


        // get the Selenium proxy object
        //Proxy seleniumProxy = ClientUtil.createSeleniumProxy(bmproxy);

        // configure it as a desired capability
        DesiredCapabilities capabilities = new DesiredCapabilities();
//
        ///Proxy seleniumProxy = ClientUtil.createSeleniumProxy(bmproxy);

        //capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

        capabilities.setCapability("browserName",  "chrome");
        //capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

        //System.out.println(bmproxy.getHarCaptureTypes());
        //System.out.println(bmproxy.notifyAll(););


        // start the browser up
        WebDriver driver = new ChromeDriver(capabilities);

        // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
        //bmproxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

        // create a new HAR with the label "yahoo.com"
        //bmproxy.newHar("trypp");

        // open ebay.de
        driver.get("https://www.myagedcare.gov.au/service-finder");

        // get the HAR data
        //Har har = bmproxy.getHar();
        //File harFile = new File("HAR/ebayHar_test_desk.har" );


        //har.writeTo(harFile);



 

       // bmproxy.stop();

        //driver.quit();

    }
}
