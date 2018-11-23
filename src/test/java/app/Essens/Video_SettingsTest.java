package app.Essens;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Video_SettingsTest {

    @Test
    void restoreBytesToSetiings() {
        var vs = new Video_Settings(640, 480, false);
        vs.width=640;
        vs.heigth=480;
        vs.CheckFaces=false;
        var restored = Video_Settings.restoreBytesToSetiings(Video_Settings.saveSetiingsToBytes(vs));
        assertEquals(vs.heigth, restored.heigth);
        assertEquals(vs.width, restored.width);
        assertEquals(vs.CheckFaces, restored.CheckFaces);

    }


}