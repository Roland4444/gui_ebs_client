package app.abstractions;

import java.util.HashMap;
import java.util.Map;

public class SettingsContainer {
    public final String ESIAClient = "http://127.0.0.1:17778/";
    public final String AudioClient = "http://127.0.0.1:15555/";
    public final String VideoClient = "http://127.0.0.1:14444/";
    public final String SmevClient = "http://127.0.0.1:12555/";
    public final String Smev3addressfile = "smev3service.bin";
    public final String AudioCheckServiceAddrfile = "NetworkSettings.bin";
    public final String VideoCheckServiceAddrFile = "NetworkSettingsVid.bin";

    public final String SoundSettings = "sound_settings.bin";
    public final String DumpModelFile = "model.bin";
    public final String resultmerged = "result.wav";
    public final String VersionProg = "EBS GUI Client 3.0.3";
    public final String receivedOIDSave = "oid.bin";
    public final String runPhotoMake    = "java --module-path build -m uk.roland.ebs_client/app.GUIModules.Interface.GetBio.Video.PhotoMake";
    public final String runSoundRecord  = "java --module-path build -m uk.roland.ebs_client/app.GUIModules.Interface.GetBio.Audio.SoundRecord";
    public final String runMainApp      = "java --module-path build -m uk.roland.ebs_client/app.GUIModules.Interface.GetBio.AppBio";
    public final String lockSoundRecordfile ="srecord.lock";
    public final String lockPhotomakefile ="photom.lock";
    public boolean productionMode=true;
    public final String SavePhotoToFile = "photoblob.bin";
    public final String SaveSoundDataWithtagsToFile = "soundblob.bin";
    public final String SaveOtherInfoToFile = "other.bin";
    public final String SaveClientDataToFile = "EBSMessageFUll.bin";
    public final String RegisterMnemonic="981601_3T";
    public final String IdpMnemonic="TESIA";
    public final String mergedwav = "merged.wav";

    public Map<Integer, String> SoundErrorsDict;
    public Map<Integer, String> PhotoErrorDict;

    public SettingsContainer(){
       initSoundErrorDict();
       initPhotoErrorDict();
    }

    private void initPhotoErrorDict() {

    }

    private void initSoundErrorDict() {
        SoundErrorsDict = new HashMap<>();
        String appedndix_Support = ".\n Обратитесь в техподдержку";
        String appedndix_Check = ".\n Проверьте настройки звуковой системы";
        Integer i = 101;
        SoundErrorsDict.put(i++, "Неверный вызов со стороны системы"+appedndix_Support);
        SoundErrorsDict.put(i++, "Невозможно считать конфигурационный файл"+appedndix_Support);
        SoundErrorsDict.put(i++, "Переданы не правильные данные. Нулевой указатель или размер данных равен 0"+appedndix_Support);
        SoundErrorsDict.put(i++, "Не правильный формат звуковых данных"+appedndix_Support);
        SoundErrorsDict.put(i++, "Не правильная структура переданных звуковых данных"+appedndix_Support);
        SoundErrorsDict.put(i++, "Проверка сигнал шум провалилась"+appedndix_Check);
        SoundErrorsDict.put(i++, "Количество каналов не соответствует заданным параметрами"+appedndix_Check);
        SoundErrorsDict.put(i++, "Частота дискретизации не соответствует заданному значению параметрам"+appedndix_Check);
        SoundErrorsDict.put(i++, "Значение глубины не соответствует заданному значению параметрам"+appedndix_Check);
        SoundErrorsDict.put(i++, "Размер записи (в килобайтах) не соответствует заданному значению параметрам"+appedndix_Support);
        SoundErrorsDict.put(i++, "Длина записи (в секундах) не соответствует заданному значению параметрам. Слишком быстро или слишком медленно говорите?");
        SoundErrorsDict.put(i++, "Звук записан с использованием телефонного микрофона"+appedndix_Support);
        SoundErrorsDict.put(i++, "Микрофон перегружен=>>Необходимо увеличить расстояние до микрофона");
        SoundErrorsDict.put(i++, "Говорите громче!");
    }


}
