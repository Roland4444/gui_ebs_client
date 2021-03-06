package app.GUIModules.Interface.GetBio;

import Message.abstractions.BinaryMessage;
import Message.toSMEV.EBS.Essens.OtherInfo;
import app.abstractions.SettingsContainer;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppBioTest {
    AppBio app = new AppBio(new SettingsContainer());

    AppBioTest() throws IOException {
    }

    @Test
    void initAboutFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        assertNotEquals(null, app);
        app.initAboutFrame();
        assertNotEquals(null, app.About);
        app.About.preperaGUI();
        app.About.frame.setVisible(true);
    }

    @Test
    void main() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        AppBio app = new AppBio(new SettingsContainer());
        app.initAboutFrame();
        app.preperaGUI();
        app.initActions();
        app.initListeners();

        app.frame.setVisible(true);
        app.initwatcher();

        app.runSoundrecord.actionPerformed(new ActionEvent(app.frame, 0, ""));
        Thread.sleep(5000);
        assertTrue(new File(app.SettsContainer.lockSoundRecordfile).exists());

        app.runPhotomake.actionPerformed(new ActionEvent(app.frame, 0, ""));
        Thread.sleep(5000);
        assertTrue(new File(app.SettsContainer.lockPhotomakefile).exists());

    }

    @Test
    void createSimpleOtherInfo() throws IOException {
        OtherInfo of = new OtherInfo();
        of.OperSNILS="000-303-903 03";
        of.RegMnemonic="981601_3T";
        of.Mnemonic="ESIA";
        of.OID="1000386103";
        of.RA="1000300890";
        SettingsContainer setts = new SettingsContainer();
        var fos = new FileOutputStream(setts.SaveOtherInfoToFile);
        fos.write(BinaryMessage.savedToBLOB(of));
        fos.close();
        assertTrue(new File(setts.SaveOtherInfoToFile).exists());
    }

    @Test
    void initListeners() {

    }
}