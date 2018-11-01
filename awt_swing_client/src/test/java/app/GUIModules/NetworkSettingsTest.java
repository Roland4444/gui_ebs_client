package app.GUIModules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NetworkSettingsTest {

    @Test
    void getPort() {
        NetworkSettings ns = new NetworkSettings();
        String full = "http://127.0.0.1:19000/";
        assertEquals("19000", ns.getPort(full));
        assertEquals("127.0.0.1", ns.getAddress(full));
    }
}