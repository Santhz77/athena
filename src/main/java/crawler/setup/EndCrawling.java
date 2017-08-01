package crawler.setup;

import com.crawljax.core.CrawlSession;
import com.crawljax.core.ExitNotifier;
import com.crawljax.core.plugin.PostCrawlingPlugin;
import net.lightbody.bmp.BrowserMobProxy;
import org.apache.commons.lang.ObjectUtils;
import org.json.simple.JSONObject;

import java.io.FileWriter;

/**
 * Created by nayak on 20.06.17.
 */
public class EndCrawling implements PostCrawlingPlugin {
    BrowserMobProxy proxy;


    public EndCrawling(BrowserMobProxy proxy1) {
        proxy = proxy1;
    }

    @Override
    public void postCrawling(CrawlSession session, ExitNotifier.ExitStatus exitReason) {

        proxy.stop();
    }

}
