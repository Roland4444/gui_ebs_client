package app.GUIModules.Interface;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class FindESIAFrameTest {

    @Test
    void getFIO() {
    }

    @Test
    void initActions() {
        InetAddress ip=null;
        String hostname;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);

        } catch (UnknownHostException e) {

            e.printStackTrace();
        }
        assertEquals("80.87.98.54", ip.getHostAddress());
    }
}