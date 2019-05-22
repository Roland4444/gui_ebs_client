package app.GUIModules.Interface.GetBio.Video;

import app.abstractions.SettingsContainer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PhotoMakeTest {

    @Test
    void savePhotoBlob() throws IOException {
        PhotoMake pm = new PhotoMake(new SettingsContainer());
        pm.savePhotoBlob("tested.png");
        assertTrue(new File(pm.SettsContainer.SavePhotoToFile).exists());
    }
}