package app.GUIModules.Interface.Blocks;
import javax.swing.*;
import java.awt.*;

public class ClientPanel extends JPanel{
    public JTextField TSNILS;
    public JTextField TFIO;
    public JList LiGender;
    public JTextField TBirthdate;
    public JTextField TPass;
    public JTextField TIssuedDatePass;
    public JTextField TIssuedPassID;
    public JTextField TIssuedBy;
    public JTextField TMobile;
    public JList LiRegion;
    public JTextField TFIAS;
    public JTextField TAddressStr;
    public JTextField TZIP;
    public JTextField TFrame;
    public JTextField TFlat;
    public JTextField THouse;
    public JTextField TBuilding;
    public JTextField TStreet;
    public JTextField TBirthPlace;

    public JLabel LSNILS;
    public JLabel LFIO;
    public JLabel LGender;
    public JLabel LBirthdate;
    public JLabel LPass;
    public JLabel LIssuedDatePass;
    public JLabel LIssuedPassID;
    public JLabel LIssuedBy;
    public JLabel LMobile;
    public JLabel LRegion;
    public JLabel LFIAS;
    public JLabel LAddressStr;
    public JLabel LZIP;
    public JLabel LFrame;
    public JLabel LFlat;
    public JLabel LHouse;
    public JLabel LBuilding;
    public JLabel LStreet;
    public JLabel LBirthPlace;

    public JPanel PSNILS;
    public JPanel PFIO;
    public JPanel PGender;
    public JPanel PBirthdate;
    public JPanel PPass;
    public JPanel PIssuedDatePass;
    public JPanel PIssuedPassID;
    public JPanel PIssuedBy;
    public JPanel PMobile;
    public JPanel PRegion;
    public JPanel PFIAS;
    public JPanel PAddressStr;
    public JPanel PZIP;
    public JPanel PFrame;
    public JPanel PFlat;
    public JPanel PHouse;
    public JPanel PBuilding;
    public JPanel PStreet;
    public JPanel PBirthPlace;

    public ClientPanel(){
        LSNILS = new JLabel("СНИЛС Клиента");
        LFIO= new JLabel("ФИО Клиента");
        LGender= new JLabel("Пол");
        LBirthdate= new JLabel("Дата рождения");
        LPass= new JLabel("Серия номер паспорта");
        LIssuedDatePass= new JLabel("Дата выдачи паспорта");
        LIssuedPassID= new JLabel("Код подразделения");
        LIssuedBy= new JLabel("Кем выдан");
        LMobile= new JLabel("Номер мобильного");
        LRegion= new JLabel("Регион");
        LFIAS= new JLabel("Код ФИАС регистрации по паспорту");
        LAddressStr= new JLabel("Адрес регистрации по паспорту");
        LZIP= new JLabel("ZIP код");
        LFrame= new JLabel("Корпус");
        LFlat= new JLabel("Квартира");
        LHouse= new JLabel("Дом");
        LBuilding= new JLabel("Строение");
        LStreet= new JLabel("Улица");
        LBirthPlace= new JLabel("Место рождения");

        TSNILS=new JTextField("",3);;
        TFIO=new JTextField("",3);;
        LiGender=new JList();;
        TBirthdate=new JTextField("",3);;
        TPass=new JTextField("",3);
        TIssuedDatePass=new JTextField("",3);
        TIssuedPassID=new JTextField("",3);
        TIssuedBy=new JTextField("",3);
        TMobile=new JTextField("",3);
        LiRegion=new JList();
        TFIAS=new JTextField("",3);
        TAddressStr=new JTextField("",3);
        TZIP=new JTextField("",3);
        TFrame=new JTextField("",3);
        TFlat=new JTextField("",3);
        THouse=new JTextField("",3);
        TBuilding=new JTextField("",3);
        TStreet=new JTextField("",3);
        TBirthPlace=new JTextField("",3);

        PSNILS=new JPanel(new GridLayout());
        PFIO=new JPanel(new GridLayout());
        PGender=new JPanel(new GridLayout());
        PBirthdate=new JPanel(new GridLayout());
        PPass=new JPanel(new GridLayout());
        PIssuedDatePass=new JPanel(new GridLayout());
        PIssuedPassID=new JPanel(new GridLayout());
        PIssuedBy=new JPanel(new GridLayout());
        PMobile=new JPanel(new GridLayout());
        PRegion=new JPanel(new GridLayout());
        PFIAS=new JPanel(new GridLayout());
        PAddressStr=new JPanel(new GridLayout());
        PZIP=new JPanel(new GridLayout());
        PFrame=new JPanel(new GridLayout());
        PFlat=new JPanel(new GridLayout());
        PHouse=new JPanel(new GridLayout());
        PBuilding=new JPanel(new GridLayout());
        PStreet=new JPanel(new GridLayout());
        PBirthPlace=new JPanel(new GridLayout());

        this.setLayout(new GridLayout(20, 1));

        prepareGUI();



    }

    public void prepareGUI() {
        PFIO.add(LFIO);
        PFIO.add(TFIO);

        PGender.add(LGender);
        PGender.add(LiGender);

        PSNILS.add(LSNILS);
        PSNILS.add(TSNILS);

        PPass.add(LPass);
        PPass.add(TPass);

        PIssuedDatePass.add(LIssuedDatePass);
        PIssuedDatePass.add(TIssuedDatePass);

        PIssuedPassID.add(LIssuedPassID);
        PIssuedPassID.add(TIssuedPassID);

        PIssuedBy.add(LIssuedBy);
        PIssuedBy.add(TIssuedBy);

        PBirthdate.add(LBirthdate);
        PBirthdate.add(TBirthdate);

        PBirthPlace.add(LBirthPlace);
        PBirthPlace.add(TBirthPlace);

        PRegion.add(LRegion);
        PRegion.add(LiRegion);

        PMobile.add(LMobile);
        PMobile.add(TMobile);

        PFIAS.add(LFIAS);
        PFIAS.add(TFIAS);

        PAddressStr.add(LAddressStr);
        PAddressStr.add(TAddressStr);

        PStreet.add(LStreet);
        PStreet.add(TStreet);

        PZIP.add(LZIP);
        PZIP.add(TZIP);

        PFrame.add(LFrame);
        PFrame.add(TFrame);

        PFlat.add(LFlat);
        PFlat.add(TFlat);

        PHouse.add(LHouse);
        PHouse.add(THouse);

        PBuilding.add(LBuilding);
        PBuilding.add(TBuilding);

        this.add( PSNILS);
        this.add( PFIO);
        this.add( PGender);
        this.add( PBirthdate);
        this.add( PPass);
        this.add( PIssuedDatePass);
        this.add( PIssuedPassID);
        this.add( PIssuedBy);
        this.add( PMobile);
        this.add( PRegion);
        this.add( PFIAS);
        this.add( PAddressStr);
        this.add( PZIP);
        this.add( PFrame);
        this.add( PFlat);
        this.add( PHouse);
        this.add( PBuilding);
        this.add( PStreet);
        this.add( PBirthPlace);




    }

    public static void main(String[] args){
        ClientPanel p = new ClientPanel();
        JFrame frame = new JFrame("Test");
        frame.setSize(500, 500);
        frame.getContentPane().setLayout(new GridLayout());
        frame.getContentPane().add(p);
        frame.pack();
        frame.setVisible(true);
    }

}
