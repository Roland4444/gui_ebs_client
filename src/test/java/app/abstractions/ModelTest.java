package app.abstractions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void saveModel() {
        var model = new Model("12345678-00", "8988989", "Roman", "1211111111", "", "");
        var restored = Model.restoredModel(Model.saveModel(model));
        assertEquals(model.FIO, restored.FIO);
        assertEquals(model.Pass, restored.Pass);
        assertEquals(model.RA, restored.RA);
        assertEquals(model.SNILSoper, restored.SNILSoper);

    }
}