package crawler.testcase;

import com.crawljax.browser.EmbeddedBrowser;
import com.crawljax.core.configuration.BrowserConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.Form;
import com.crawljax.core.configuration.InputSpecification;
import com.crawljax.core.state.Eventable;

import java.util.concurrent.TimeUnit;

/**
 * Created by nayak on 31.07.17.
 */
public class Facebook {

    public CrawljaxConfiguration.CrawljaxConfigurationBuilder Login(CrawljaxConfiguration.CrawljaxConfigurationBuilder builder)
    {
        builder.setMaximumStates(3);
        // builder.crawlRules().setInputSpec(getInputSpecification());

        InputSpecification input = new InputSpecification();
        input.field("email").setValue("santhu_974@yahoo.co.in");
        input.field("password").setValue("gazelle2204");

        builder.crawlRules().setInputSpec(input);

        builder.crawlRules().click("u_0_r").withAttribute("value","Log In");

        builder.crawlRules().insertRandomDataInInputForms(false);









//        builder.crawlRules().addEventType(Eventable.EventType.click);
//
//        builder.crawlRules().dontClick("a").withText("Language Tools");




        // setting basic auth
//        builder.setBasicAuth("santhu_974@yahoo.co.in","gazelle2204");

        // Set timeouts
        builder.crawlRules().waitAfterReloadUrl(60, TimeUnit.MILLISECONDS);
        builder.crawlRules().waitAfterEvent(60, TimeUnit.MILLISECONDS);



        return builder;
    }


    private InputSpecification getInputSpecification() {
        InputSpecification input = new InputSpecification();
        Form contactForm = new Form();
        contactForm.field("email").setValues("santhu_974@yahoo.co.in");
        contactForm.field("password").setValues("gazelle2204");
//        contactForm.field("name").setValues("Bob", "Alice", "John");
//        contactForm.field("phone").setValues("1234567890", "1234888888", "");
//        contactForm.field("mobile").setValues("123", "3214321421");
//        contactForm.field("type").setValues("Student", "Teacher");
//        contactForm.field("active").setValues(true);
//        input.setValuesInForm(contactForm).beforeClickElement("button").withText("Save");
        return input;
    }

    }
