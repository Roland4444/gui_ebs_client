package app.GUIModules.Interface.Blocks;
import Message.abstractions.Gender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.showMessageDialog;

public class ClientPanel extends JPanel{
    Gender[] genders = new Gender[]{Gender.F, Gender.M};
    String[] regions = new String[]{
            "01	Республика Адыгея",
            "02	Республика Башкортостан",
            "03	Республика Бурятия",
            "04	Республика Алтай",
            "05	Республика Дагестан",
            "06	Республика Ингушетия",
            "07	Кабардино-Балкарская Республика",
            "08	Республика Калмыкия",
            "09	Карачаево-Черкесская Республика",
            "10	Республика Карелия",
            "11	Республика Коми",
            "12	Республика Марий Эл",
            "13	Республика Мордовия",
            "14	Республика Саха (Якутия)",
            "15	Республика Северная Осетия - Алания",
            "16	Республика Татарстан (Татарстан)",
            "17	Республика Тыва",
            "18	Удмуртская Республика",
            "19	Республика Хакасия",
            "20	Чеченская Республика",
            "21	Чувашская Республика - Чувашия",
            "22	Алтайский край",
            "23	Краснодарский край",
            "24	Красноярский край",
            "25	Приморский край",
            "26	Ставропольский край",
            "27	Хабаровский край",
            "28	Амурская область",
            "29	Архангельская область",
            "30	Астраханская область",
            "31	Белгородская область",
            "32	Брянская область",
            "33	Владимирская область",
            "34	Волгоградская область",
            "35	Вологодская область",
            "36	Воронежская область",
            "37	Ивановская область",
            "38	Иркутская область",
            "39	Калининградская область",
            "40	Калужская область",
            "41	Камчатский край",
            "42	Кемеровская область",
            "43	Кировская область",
            "44	Костромская область",
            "45	Курганская область",
            "46	Курская область",
            "47	Ленинградская область",
            "48	Липецкая область",
            "49	Магаданская область",
            "50	Московская область",
            "51	Мурманская область",
            "52	Нижегородская область",
            "53	Новгородская область",
            "54	Новосибирская область",
            "55	Омская область",
            "56	Оренбургская область",
            "57	Орловская область",
            "58	Пензенская область",
            "59	Пермский край",
            "60	Псковская область",
            "61	Ростовская область",
            "62	Рязанская область",
            "63	Самарская область",
            "64	Саратовская область",
            "65	Сахалинская область",
            "66	Свердловская область",
            "67	Смоленская область",
            "68	Тамбовская область",
            "69	Тверская область",
            "70	Томская область",
            "71	Тульская область",
            "72	Тюменская область",
            "73	Ульяновская область",
            "74	Челябинская область",
            "75	Забайкальский край",
            "76	Ярославская область",
            "77	г. Москва",
            "78	Санкт-Петербург",
            "79	Еврейская автономная область",
            "83	Ненецкий автономный округ",
            "86	Ханты-Мансийский автономный округ - Югра",
            "87	Чукотский автономный округ",
            "89	Ямало-Ненецкий автономный округ",
            "91	Республика Крым",
            "92	Севастополь",
            "99	Иные территории, включая город и космодром Байконур"
    };

    public JTextField TSNILS;
    public JTextField TFIO;
    public JComboBox LiGender;
    public JTextField TBirthdate;
    public JTextField TPass;
    public JTextField TIssuedDatePass;
    public JTextField TIssuedPassID;
    public JTextField TIssuedBy;
    public JTextField TMobile;
    public JComboBox LiRegion;
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

    public  AbstractAction simple;


    public ClientPanel(){
        LSNILS = new JLabel("СНИЛС Клиента*\n(*обязательно)");
        LFIO= new JLabel("ФИО Клиента*");
        LGender= new JLabel("Пол*");
        LBirthdate= new JLabel("Дата рождения(по паспорту)*");
        LPass= new JLabel("Серия номер паспорта(10 значащих цифр)*");
        LIssuedDatePass= new JLabel("Дата выдачи паспорта*");
        LIssuedPassID= new JLabel("Код подразделения выдавшего паспорт*");
        LIssuedBy= new JLabel("Кем выдан*");
        LMobile= new JLabel("Номер мобильного*");
        LRegion= new JLabel("Регион*");
        LFIAS= new JLabel("Код ФИАС регистрации по паспорту*");
        LAddressStr= new JLabel("Адрес регистрации по паспорту*");
        LZIP= new JLabel("ZIP код(необязательно)?\n(? могут быть получены автоматически)");
        LFrame= new JLabel("Корпус?");
        LFlat= new JLabel("Квартира?");
        LHouse= new JLabel("Дом?");
        LBuilding= new JLabel("Строение?");
        LStreet= new JLabel("Улица?");
        LBirthPlace= new JLabel("Место рождения");

        TSNILS=new JTextField("",3);;
        TFIO=new JTextField("",3);;
        LiGender=new JComboBox<Gender>(genders);;
        TBirthdate=new JTextField("",3);;
        TPass=new JTextField("",3);
        TIssuedDatePass=new JTextField("",3);
        TIssuedPassID=new JTextField("",3);
        TIssuedBy=new JTextField("",3);
        TMobile=new JTextField("",3);
        LiRegion=new JComboBox<String>(regions);
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
        initListeners();
    }
    public void initActions(){
        simple = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showMessageDialog(null, "changed"+ LiRegion.getSelectedItem());
            }
        };//    LiRegion.addActionListener(simple);
    }

    public void initListeners (){
        initActions();
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
      //  this.remove(PStreet);
      //  this.remove(PBirthPlace);
      //  PStreet.setVisible(false);
      //  PBirthPlace.setVisible(false);
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
