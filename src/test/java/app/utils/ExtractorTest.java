package app.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExtractorTest {

    @Test
    void getFIO() {

        String name = "Rutger";
        String surname ="Hauer";
        String middle ="XCL";
        String space = " ";
        ArrayList arr = new ArrayList();
        arr.add(name);
        arr.add(surname);
        arr.add(middle);
        String input = name +space+surname+space+middle;
        assertEquals(arr, Extractor.getFIO(input));
    }


    @Test
    void getPass() {
        String seria = "1210";
        String number = "322280";
        ArrayList arr = new ArrayList();
        arr.add(seria);
        arr.add(number);
        assertEquals(Extractor.getPass(seria+number));
    }
}