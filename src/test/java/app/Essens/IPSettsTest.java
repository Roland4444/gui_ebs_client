package app.Essens;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IPSettsTest {

    @Test
    void restoreBytesToIPSetts() {
        IPSetts ipsetts = new IPSetts();
        ipsetts.address="http://127.0.0.1:15555/";
        IPSetts restored = IPSetts.restoreBytesToIPSetts(IPSetts.saveIPSettsToBytes(ipsetts));
        assertEquals(restored.address, ipsetts.address);
    }
}