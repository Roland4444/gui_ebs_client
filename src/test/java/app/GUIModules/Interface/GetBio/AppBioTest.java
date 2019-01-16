package app.GUIModules.Interface.GetBio;

import app.abstractions.SettingsContainer;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class AppBioTest {
    AppBio app = new AppBio(new SettingsContainer());

    @Test
    void initAboutFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        assertNotEquals(null, app);
        app.initAboutFrame();
        assertNotEquals(null, app.About);
        app.About.preperaGUI();
        app.About.frame.setVisible(true);
    }
}