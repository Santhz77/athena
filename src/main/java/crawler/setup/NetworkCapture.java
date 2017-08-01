package crawler.setup; /**
 * Created by nayak on 20.06.17.
 */

import com.google.common.base.MoreObjects;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class NetworkCapture {


    public static BrowserMobProxy proxyServer;
    String info = "";

    public void startProxyServer() throws UnknownHostException {
        //Using browser mob proxy to start the proxy
        proxyServer = new BrowserMobProxyServer();
        proxyServer.start();
        saveProxyServerInfo();
    }

    public BrowserMobProxy getProxyServer() {
        return proxyServer;
    }

    public int getProxyPort()
    {
        return proxyServer.getPort();
    }

    public void saveProxyServerInfo()
    {
        info = info +" Port :" +proxyServer.getPort() + "\n";
        info = info +" ServerBindAddress :" +proxyServer.getServerBindAddress()+ "\n";
        info = info +" ClientBindAddress :" +proxyServer.getClientBindAddress();

        FileWriter fileWriter = null;
        File file = new File("LOG/proxy_server_info.txt");

        // if file doesn't exists, then create it
        if (!file.exists()) {

            try {
                file.createNewFile();
                fileWriter = new FileWriter(file);
                fileWriter.write(info);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    public void stopProxyServer()
    {
        proxyServer.stop();
    }

}
