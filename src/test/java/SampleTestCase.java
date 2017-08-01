import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.net.NetworkInterface;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nayak on 19.06.17.
 */
public class SampleTestCase {

    public static void main(String[] args) throws Exception {


        //start the proxy
        BrowserMobProxy bmproxy = new BrowserMobProxyServer();
        bmproxy.start(8082,InetAddress.getByName("134.96.225.88"));


        // get the Selenium proxy object
        //Proxy seleniumProxy = ClientUtil.createSeleniumProxy(bmproxy);


        Proxy proxy = new Proxy();
        proxy.setProxyType(Proxy.ProxyType.MANUAL);

        String proxyStr = "134.96.225.88:8082";
        proxy.setHttpProxy(proxyStr);
        proxy.setSslProxy(proxyStr);


        // configure it as a desired capability
       DesiredCapabilities capabilities = new DesiredCapabilities();



        capabilities.setCapability(CapabilityType.PROXY, proxy);

       //For real mobile devices
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("androidPackage", "com.android.chrome");
        chromeOptions.setExperimentalOption("androidDeviceSerial", "SH45TWR03175");
        capabilities.setCapability("chromeOptions",chromeOptions);




        // start the browser up
        WebDriver driver = new ChromeDriver(capabilities);

        // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
        bmproxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

        // create a new HAR with the label "yahoo.com"
        bmproxy.newHar("trypp");

        // open ebay.de
        driver.get("https://en.wikipedia.org/wiki/Main_Page");

        // get the HAR data
        Har har = bmproxy.getHar();
        File harFile = new File("HAR/wikiHar_test_.har" );
        har.writeTo(harFile);

        //bmproxy.stop();

        //driver.quit();

    }
}
