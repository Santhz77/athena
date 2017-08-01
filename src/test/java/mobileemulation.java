import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nayak on 18.07.17.
 */
public class mobileemulation {

    static DesiredCapabilities capabilities;
    static String DeviceName;

    public static void main(String[] args) throws IOException {
        //some Sample Devices.
        // Complete list can be found here: https://code.google.com/p/chromium/codesearch#chromium/src/chrome/test/chromedriver/chrome/mobile_device_list.cc
        //pick any of the device

        DeviceName = "Google Nexus 5";
        //  DeviceName = "Samsung Galaxy S4";
        //  DeviceName = "Samsung Galaxy Note 3";
        //  DeviceName = "Samsung Galaxy Note II";
        //  DeviceName = "Apple iPhone 4";
        //  DeviceName = "Apple iPhone 5";
        //  DeviceName = "Apple iPad 3 / 4";


        //Start the proxy server here!
        BrowserMobProxyServer bmproxy = new BrowserMobProxyServer();
        bmproxy.start(8081, InetAddress.getByName("134.96.225.88"));

        // TO set the Selenium Proxy
        Proxy seleniumproxy = new Proxy();
        seleniumproxy.setProxyType(Proxy.ProxyType.MANUAL);
        String proxyStr = "134.96.225.88:8081";
        seleniumproxy.setHttpProxy(proxyStr);
        seleniumproxy.setSslProxy(proxyStr);



        // To enable the mobile emulation!
        Map<String, String> mobileEmulation = new HashMap<String, String>();
        mobileEmulation.put("deviceName", DeviceName);

        Map<String, Object> chromeOptions = new HashMap<String, Object>();
        chromeOptions.put("mobileEmulation", mobileEmulation);

        capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        //set the browser proxy
        capabilities.setCapability(CapabilityType.PROXY, seleniumproxy);



        // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
        bmproxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        bmproxy.newHar("trypp");

        // open ebay.de
        WebDriver driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
        driver.get("https://en.wikipedia.org/wiki/Main_Page");

        // get the HAR data
        Har har = bmproxy.getHar();
        File harFile = new File("HAR/wikiHar_test_.har" );
        har.writeTo(harFile);

        //bmproxy.stop();

        //driver.quit();
    }
}
