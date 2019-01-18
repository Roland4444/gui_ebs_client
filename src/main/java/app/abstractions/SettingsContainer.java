package app.abstractions;

public class SettingsContainer {
    public final String ESIAClient = "http://127.0.0.1:17778/";
    public final String AudioClient = "http://127.0.0.1:15555/";
    public final String VideoClient = "http://127.0.0.1:14444/";
    public final String Smev3addressfile = "smev3service.bin";
    public final String AudioCheckServiceAddrfile = "NetworkSettings.bin";
    public final String VideoCheckServiceAddrFile = "NetworkSettingsVid.bin";
    public final String DumpModelFile = "model.bin";
    public final String resultmerged = "result.wav";
    public final String VersionProg = "EBS GUI Client 2.1.0";
    public final String receivedOIDSave = "oid.bin";
    public final String runPhotoMake    = "java --module-path target -m uk.roland.ebs_client/app.GUIModules.Interface.GetBio.Video.PhotoMake";
    public final String runSoundRecord  = "java --module-path target -m uk.roland.ebs_client/app.GUIModules.Interface.GetBio.Audio.SoundRecord";
}
