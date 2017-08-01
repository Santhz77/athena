package crawler.setup;

import com.crawljax.browser.EmbeddedBrowser;
import com.crawljax.browser.WebDriverBackedEmbeddedBrowser;
import com.google.inject.Provider;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nayak on 19.06.17.
 */
public class MyBrowserProvider implements Provider<EmbeddedBrowser> {

    BrowserMobProxy proxy;

    public MyBrowserProvider(BrowserMobProxy proxy1)
    {
        proxy = proxy1;

    }

    @Override
    public EmbeddedBrowser get(){
        EmbeddedBrowser browser = null;

        //Reading the config and setting it.
        Configuration config = new Configuration();


        browser = newDesktopBrowser();

        //System.out.print(config.getTestPlatform());

//        if (config.getTestPlatform() == "desktop")
//        {
//            browser = newDesktopBrowser();
//        }
//        else if(config.getTestPlatform() == "mobile")
//        {
//            browser = newMobileBrowser();
//        }
        return browser;
    }

    private EmbeddedBrowser newMobileBrowser() {


        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("browser", "chrome");

        //Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);


        //For mobile devices
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("androidPackage", "com.android.chrome");
        chromeOptions.setExperimentalOption("androidDeviceSerial", "SH45TWR03175");
        capabilities.setCapability("chromeOptions",chromeOptions);

        WebDriver driver = new ChromeDriver(capabilities);

        proxy.newHar();

        return WebDriverBackedEmbeddedBrowser.withDriver(driver);

    }


    private EmbeddedBrowser newMobileEmulator(){


        String DeviceName = "Google Nexus 5";

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();

        // To enable the mobile emulation!
        Map<String, String> mobileEmulation = new HashMap<String, String>();
        mobileEmulation.put("deviceName", DeviceName);

        Map<String, Object> chromeOptions = new HashMap<String, Object>();
        chromeOptions.put("mobileEmulation", mobileEmulation);

        capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        //set the browser proxy
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);


        // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        proxy.newHar();

        WebDriver driver = new ChromeDriver(capabilities);

        // open ebay.de
        return WebDriverBackedEmbeddedBrowser.withDriver(driver);
    }


    /**
     * A method to provide an instance of desktop browser
     * @return embedded browser
     */
    public EmbeddedBrowser newDesktopBrowser() {

        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

        // configure it as a desired capability
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("browserName",  "chrome");
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

        // start the browser up
        WebDriver driver = new ChromeDriver(capabilities);

        driver.manage().window().maximize();
        proxy.newHar();

        System.out.println("Proxy server started at port :" + proxy.getPort());

        return WebDriverBackedEmbeddedBrowser.withDriver(driver);

    }

}
