package crawler.setup;

import com.crawljax.browser.EmbeddedBrowser;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.BrowserConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.InputSpecification;
import com.crawljax.plugins.crawloverview.CrawlOverview;
import crawler.testcase.Facebook;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *
 * Main entry point of the program
 * Created by nayak on 13.06.17.
 */
public class CrawlWeb {


    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //Reading the config and setting it.
        Configuration config = new Configuration();
        config.readConfigFile();

        NetworkCapture networkCapture = new NetworkCapture();
        networkCapture.startProxyServer();

        // Configuring Crawljax
        CrawljaxConfiguration.CrawljaxConfigurationBuilder builder = CrawljaxConfiguration.builderFor(config.getTestURL());
        builder.setBrowserConfig(new BrowserConfiguration(EmbeddedBrowser.BrowserType.CHROME, 1, new MyBrowserProvider(networkCapture.getProxyServer())));




        // Location to save the data files of the crawlOverview plugin
        String fileName = config.getFolderName();
        File file = new File("Data/" + fileName + "/" );
        builder.setOutputDirectory(file);

        //CrawlOverview Plugin which extracts all the information.
        builder.addPlugin(new CrawlOverview());

        //adding a plugin to capture the HAR!
        //builder.addPlugin(new ExtractHar(networkCapture.getProxyServer()));

        //implements the postcrawling plugin and closed the browser and proxy server instance.
        //builder.addPlugin(new EndCrawling(networkCapture.getProxyServer()));


        // Add builder rulses dependent on the testcase!
        Facebook fb =  new Facebook();
        //builder = fb.Login(builder);



        InputSpecification input = new InputSpecification();
        input.field("email").setValue("santhu_974@yahoo.co.in");
        input.field("password").setValue("gazelle2204");
        input.getCrawlElements();
        builder.crawlRules().setInputSpec(input);

        builder.crawlRules().dontCrawlFrame("//*[@id=\"content\"]/div/div/div/div");



        builder.crawlRules().insertRandomDataInInputForms(false);


        try {
            CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
            crawljax.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
