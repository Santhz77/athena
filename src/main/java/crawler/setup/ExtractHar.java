package crawler.setup;

import com.crawljax.core.CrawlerContext;
import com.crawljax.core.plugin.OnNewStatePlugin;
import com.crawljax.core.state.StateVertex;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.Har;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by nayak on 19.06.17.
 */
public class ExtractHar implements OnNewStatePlugin {

    Configuration config = new Configuration();
    BrowserMobProxy proxy;
    JSONObject obj= new JSONObject();



    /**
     * Constructor that sets the proxy
     * @param proxy1
     */
    public ExtractHar(BrowserMobProxy proxy1) {

        proxy = proxy1;

    }



    /**
     * This extends the OnNewStatePlugin where we write our plugin
     * to handle the HAR capture for every new state reached.
     * @param context
     *            the current context.
     * @param newState
     */
    @Override
    public void onNewState(CrawlerContext context, StateVertex newState) {
        String folderName = "HAR"; // This is a default name we use to save our HAr files.
        try {

            //read the configuration file ot get the configuration details.
            config.readConfigFile();

            // get the HAR data from the proxy
            Har har = proxy.getHar();

            folderName = folderName + "/" + config.getFolderName() + "/";

            if(createFolder(folderName))
            {
                File harFile = new File(folderName + newState.getName()+".har" );


                System.out.println(">>>>>>>>>>STD OUT NOTIFICATION>>>>>>>>>>>>>>>>>>>");
                System.out.println("NEW STATE : " + newState.getName());

                har.writeTo(harFile);

                //start a new har capture after writing the old one!
                proxy.newHar();

                JSONObject aray = new JSONObject();


                JSONObject serialized_element_data = new JSONObject();
                serialized_element_data.put("element_id",context.getCrawlPath().last().getElement().getElementId());
                serialized_element_data.put("node",context.getCrawlPath().last().getElement().getNode().getNodeValue());
                serialized_element_data.put("tag",context.getCrawlPath().last().getElement().getTag());
                serialized_element_data.put("text",context.getCrawlPath().last().getElement().getText());
                serialized_element_data.put("attributes",context.getCrawlPath().last().getElement().getAttributes());
                serialized_element_data.put("event_type",context.getCrawlPath().last().getEventType());

                aray.put("action_element" ,serialized_element_data);
                String harfile_name = newState.getName() + ".har";
                aray.put("harfile", harfile_name );

                // load the json data into the object.
                obj.put(newState.getName(),aray);



                // To write the object into a file
                //write the json Object to a file!
                try {
                    FileWriter file = new FileWriter(folderName + "action_mapping.json");
                    obj.writeJSONString(file);
                    file.flush();
                    file.close();
                }
                catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }



            }
            else
            {
                System.out.println("No folder was created");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public boolean createFolder(String folderName)
    {

        // To look for folder called HAR, if not create HAR
        File theDir = new File(folderName);
        boolean flag = false;


        // if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("No directory called " + folderName + " exists.");
            System.out.println("Creating directory: " + theDir.getName());

            try{
                theDir.mkdirs();
                flag = true;
            }
            catch(SecurityException se){
                System.out.println(se.getMessage());
            }
            if(flag) {
                System.out.println("Directory " + folderName + " created.");
            }
        }
        else
        {
            System.out.println("directory " + folderName + " already exists. ");
            flag = true;
        }
        return flag;
    }

}
