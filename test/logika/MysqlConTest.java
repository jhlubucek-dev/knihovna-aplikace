package logika;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MysqlConTest {

    @org.junit.jupiter.api.Test
    void oblast() {
        MysqlCon databaze = new MysqlCon();
        int id = databaze.getOblastNexId();
        OblastZamereni oblast = new OblastZamereni(id, "test");


        databaze.addOblast(oblast);
        assertTrue(oblast.equals(databaze.getOblast(id)));

        oblast.setNazev("testTest");
        databaze.editOblastZamereni(oblast);
        assertTrue(oblast.equals(databaze.getOblast(id)));

        assertTrue(databaze.getOblasti().contains(oblast));
    }


    @org.junit.jupiter.api.Test
    void ctenar() {
        MysqlCon databaze = new MysqlCon();
        int id = databaze.getCtenarNexId();
        Ctenar ctenar = new Ctenar(id, "jmeno", "prijmeni", "jmenoprijmeni@seznam.cz");

        databaze.addCtenar(ctenar);
        assertTrue(ctenar.equals(databaze.getCtenar(id)));

        ctenar.setJmeno("jmenoo");
        ctenar.setPrijmeni("prijmenii");
        ctenar.setEmail("jmenoprijmenii@seznam.cz");

        databaze.editCtenar(ctenar);
        assertTrue(ctenar.equals(databaze.getCtenar(id)));

        assertTrue(databaze.getCtenari().contains(ctenar));
    }


    @org.junit.jupiter.api.Test
    void kniha() {
        MysqlCon databaze = new MysqlCon();
        int id = databaze.getKnihaNexId();
        OblastZamereni oblast = new OblastZamereni(1,"jmeno");
        Autor autor = new Autor(1, "te", "test");
        Kniha kniha = new Kniha(id,"nazev",autor, oblast);

        databaze.addKniha(kniha);
        assertTrue(kniha.equals(databaze.getKniha(id)));

        kniha.setNazev("nazevv");
        autor.setJmeno("autorr");
        kniha.setAutor(autor);
        kniha.setOblastZamereni(new OblastZamereni(2, "jmenoo"));

        databaze.editKniha(kniha);
        assertTrue(kniha.equals(databaze.getKniha(id)));

        assertTrue(databaze.getKnihy().contains(kniha));
    }


    @org.junit.jupiter.api.Test
    void vypujcka() {

        MysqlCon databaze = new MysqlCon();
        OblastZamereni oblast = new OblastZamereni(1,"jmeno");
        Autor autor = new Autor(1, "te", "test");
        Kniha kniha = new Kniha(databaze.getKnihaNexId(),"nazev", autor, oblast);
        databaze.addKniha(kniha);

        Ctenar ctenar = new Ctenar(databaze.getCtenarNexId(),"jmeno","prijmeni","jmenoprijmeni@seznam.cz");
        databaze.addCtenar(ctenar);

        int id = databaze.getVypujckaNexId();
        Vypujcka vypujcka =
                new Vypujcka(id, LocalDate.now(),
                    LocalDate.of(2020,5,15),
                    LocalDate.of(2020,6,15), kniha,ctenar);


        databaze.addVypujcka(vypujcka);
        assertTrue(vypujcka.equals(databaze.getVypujcka(id)));

        vypujcka.setDatumPujceni(LocalDate.now());
        vypujcka.setPredpokladeneDatumVraceni(LocalDate.of(2020,5,15));
        vypujcka.setDatumVraceni(LocalDate.of(2020,6,15));
        vypujcka.setKniha(kniha);
        vypujcka.setCtenar(ctenar);

        databaze.editVypujcka(vypujcka);
        assertTrue(vypujcka.equals(databaze.getVypujcka(id)));

        assertTrue(databaze.getVypujcky().contains(vypujcka));
    }

}