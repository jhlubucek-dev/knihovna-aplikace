package logika;

import java.sql.*;
import java.util.ArrayList;

/**
 * třída pro připojení k databázi
 */
public class MysqlCon{
    private Connection myConn;
    public MysqlCon(){

        //parametry pro připojení k databázi
        String url = "jdbc:mysql://192.168.1.236:3306/knihovna";
        String user = "test";
        String password = "123";

//        String url = "jdbc:mysql://localhost:3306/knihovna";
//        String user = "root";
//        String password = "";

        try {
            myConn = DriverManager.getConnection(url, user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @return ArrayList včeh oblastí zaměření v databázi
     */
    public ArrayList<OblastZamereni> getOblasti(){
        ArrayList<OblastZamereni> oblasti = new ArrayList<>();

        try {
            String sql = "SELECT * FROM knihovna.oblast_zamereni";
            ResultSet rs = myConn.createStatement().executeQuery(sql);

            while (rs.next()){
                oblasti.add(new OblastZamereni(rs.getInt("oblast_id"), rs.getString("oblast_nazev")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oblasti;
    }

    /**
     *
     * @param id
     * @return oblast s daným id
     */
    public OblastZamereni getOblast(int id){
        OblastZamereni oblast = null;

        try {
            String sql = String.format("SELECT * FROM knihovna.oblast_zamereni where oblast_id = %d", id);
            ResultSet rs = myConn.createStatement().executeQuery(sql);

            if (rs.next()){
                oblast = (new OblastZamereni(rs.getInt("oblast_id"), rs.getString("oblast_nazev")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oblast;
    }

    /**
     * přída oblast do databáze
     * @param oblast
     */
    public void addOblast(OblastZamereni oblast){
        try {
            String sql = String.format("INSERT INTO knihovna.oblast_zamereni (oblast_nazev)\n" +
                                        "VALUES ('%s')", oblast.getNazev());
            myConn.createStatement().execute(sql);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * upravi oblast v databázi
     * @param oblast
     */
    public void editOblastZamereni(OblastZamereni oblast){

        try {
            String sql = String.format("UPDATE knihovna.oblast_zamereni\n" +
                    "SET oblast_zamereni.oblast_nazev = '%s'\n" +
                    "WHERE oblast_zamereni.oblast_id = %d;", oblast.getNazev(), oblast.getId());
            myConn.createStatement().execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * vrátí id příští oblasti
     * @return
     */
    public int getOblastNexId(){
        int id = 0;

        try {
            String sql = "SELECT AUTO_INCREMENT " +
                    "     FROM information_schema.TABLES " +
                    "     WHERE TABLE_SCHEMA = \"knihovna\" " +
                    "     AND TABLE_NAME = \"oblast_zamereni\";";
            ResultSet rs = myConn.createStatement().executeQuery(sql);

            while (rs.next()){
                id = rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    /**
     *
     * @return ArrayList všeh knih v databázi
     */
    public ArrayList<Kniha> getKnihy(){
        ArrayList<Kniha> knihy = new ArrayList<>();

        try {
            String sql = "select * from knihovna.kniha " +
                    "join oblast_zamereni on kniha_oblast_id = oblast_id " +
                    "join autor on kniha_autor_id = autor_id";
            ResultSet rs = myConn.createStatement().executeQuery(sql);

            while (rs.next()){
                OblastZamereni oblast = new OblastZamereni(rs.getInt("kniha_oblast_id"), rs.getString("oblast_nazev"));
                Autor autor = new Autor(rs.getInt("kniha_oblast_id"), rs.getString("autor_kod"), rs.getString("autor_jmeno"));
                knihy.add(new Kniha(rs.getInt("kniha_id"), rs.getString("kniha_nazev"), autor, oblast));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return knihy;
    }

    /**
     *
     * @param id
     * @return knihu s daným id
     */
    public Kniha getKniha(int id){
        Kniha kniha = null;

        try {
            String sql = String.format("select * from knihovna.kniha \n" +
                    "join oblast_zamereni on kniha_oblast_id = oblast_id \n" +
                    "join autor on kniha_autor_id = autor_id \n" +
                    "where kniha_id = %d", id);
            ResultSet rs = myConn.createStatement().executeQuery(sql);

            if (rs.next()){
                OblastZamereni oblast = new OblastZamereni(rs.getInt("kniha_oblast_id"), rs.getString("oblast_nazev"));
                Autor autor = new Autor(rs.getInt("kniha_oblast_id"), rs.getString("autor_kod"), rs.getString("autor_jmeno"));
                kniha = new Kniha(rs.getInt("kniha_id"), rs.getString("kniha_nazev"), autor, oblast);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kniha;
    }

    /**
     * přídá knihu do databáze
     * @param kniha
     */

    public void addKniha(Kniha kniha){
        try {
            String sql = String.format("INSERT INTO knihovna.kniha (kniha_nazev, kniha_autor_id, kniha_oblast_id) \n" +
                    "VALUES ('%s', '%s', %d)", kniha.getNazev(), kniha.getAutor().getId(), kniha.getOblastZamereni().getId());
            myConn.createStatement().execute(sql);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * vrátí id příští knihy
     * @return
     */
    public int getKnihaNexId(){
        int id = 0;

        try {
            String sql = "SELECT AUTO_INCREMENT " +
                    "     FROM information_schema.TABLES " +
                    "     WHERE TABLE_SCHEMA = \"knihovna\" " +
                    "     AND TABLE_NAME = \"kniha\";";
            ResultSet rs = myConn.createStatement().executeQuery(sql);

            while (rs.next()){
                id = rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    /**
     * upravi knihu v databázi
     * @param kniha
     */
    public void editKniha(Kniha kniha){

        try {
            String sql = String.format("UPDATE knihovna.kniha\n" +
                    "SET kniha.kniha_nazev = '%s',\n" +
                    "kniha.kniha_autor = '%s',\n" +
                    "kniha.kniha_oblast_id = %d\n" +
                    "WHERE kniha.kniha_id = %d;", kniha.getNazev(), kniha.getAutor(), kniha.getOblastZamereni().getId(), kniha.getId());
            myConn.createStatement().execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return ArrayList všeh ctenaru v databázi
     */
    public ArrayList<Ctenar> getCtenari(){
        ArrayList<Ctenar> knihy = new ArrayList<>();

        try {
            String sql = "select * from knihovna.ctenar ;";
            ResultSet rs = myConn.createStatement().executeQuery(sql);

            while (rs.next()){
                knihy.add(new Ctenar(rs.getInt("ctenar_id"), rs.getString("ctenar_jmeno"), rs.getString("ctenar_prijmeni"), rs.getString("ctenar_email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return knihy;
    }

    /**
     *
     * @param id
     * @return čtenář s daným id
     */
    public Ctenar getCtenar(int id){
        Ctenar ctenar = null;

        try {
            String sql = String.format("select * from knihovna.ctenar where ctenar_id = %d", id);
            ResultSet rs = myConn.createStatement().executeQuery(sql);

            if (rs.next()){
                ctenar = new Ctenar(rs.getInt("ctenar_id"), rs.getString("ctenar_jmeno"), rs.getString("ctenar_prijmeni"), rs.getString("ctenar_email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ctenar;
    }

    /**
     * přída čtenáře do databáze
     * @param ctenar
     */
    public void addCtenar(Ctenar ctenar){
        try {
            String sql = String.format("INSERT INTO `ctenar` (`ctenar_jmeno`, `ctenar_prijmeni`, `ctenar_email`) " +
                    "VALUES ('%s', '%s', '%s');", ctenar.getJmeno(), ctenar.getPrijmeni(), ctenar.getEmail());
            myConn.createStatement().execute(sql);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * vrátí id příštího čtenáře
     * @return
     */
    public int getCtenarNexId(){
        int id = 0;

        try {
            String sql = "SELECT AUTO_INCREMENT " +
                    "     FROM information_schema.TABLES " +
                    "     WHERE TABLE_SCHEMA = \"knihovna\" " +
                    "     AND TABLE_NAME = \"ctenar\";";
            ResultSet rs = myConn.createStatement().executeQuery(sql);

            while (rs.next()){
                id = rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    /**
     * upravi čtenáře v databázi
     * @param ctenar
     */
    public void editCtenar(Ctenar ctenar){

        try {
            String sql = String.format("UPDATE `ctenar` SET `ctenar_jmeno` = '%s'," +
                    " `ctenar_prijmeni` = '%s'," +
                    " `ctenar_email` = '%s' " +
                    "WHERE `ctenar`.`ctenar_id` = %d;", ctenar.getJmeno(), ctenar.getPrijmeni(), ctenar.getEmail(), ctenar.getId());
            myConn.createStatement().execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return ArrayList všeh výpůjček v databázi
     */
    public ArrayList<Vypujcka> getVypujcky(){
        ArrayList<Vypujcka> vypujcky = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `vypujcka` ORDER BY `vypujcka_id` ASC";
            ResultSet rs = myConn.createStatement().executeQuery(sql);

            while (rs.next()){
                vypujcky.add(new Vypujcka(rs.getInt("vypujcka_id"),rs.getDate("vypujcka_datum_pujceni"),rs.getDate("vypujcka_predpokladane_datum_vraceni"),rs.getDate("vypujcka_datum_vraceni"),getKniha(rs.getInt("vypujcka_kniha_id")),getCtenar(rs.getInt("vypujcka_ctenar_id"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vypujcky;
    }

    /**
     *
     * @param id
     * @return výpujčku s daným id
     */
    public Vypujcka getVypujcka(int id){
        Vypujcka vypujcka = null;

        try {
            String sql = String.format("SELECT * FROM `vypujcka` " +
//                    "JOIN kniha on vypujcka.vypujcka_kniha_id = kniha.kniha_id " +
//                    "JOIN ctenar on vypujcka.vypujcka_ctenar_id = ctenar.ctenar_id";
                    "WHERE `vypujcka_id` = %d", id);
            ResultSet rs = myConn.createStatement().executeQuery(sql);

            if (rs.next()){
                vypujcka = (new Vypujcka(rs.getInt("vypujcka_id"),rs.getDate("vypujcka_datum_pujceni"),rs.getDate("vypujcka_predpokladane_datum_vraceni"),rs.getDate("vypujcka_datum_vraceni"),getKniha(rs.getInt("vypujcka_kniha_id")),getCtenar(rs.getInt("vypujcka_ctenar_id"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vypujcka;
    }

    /**
     * přída výpujčku do databáze
     * @param vypujcka
     */
    public void addVypujcka(Vypujcka vypujcka){
        try {
            String datumVraceni = vypujcka.getDatumVraceni() == null ? "NULL" : "'"+vypujcka.getDatumVraceni()+"'";

            String sql = String.format("INSERT INTO `vypujcka` " +
                    "(`vypujcka_datum_pujceni`, `vypujcka_datum_vraceni`, `vypujcka_predpokladane_datum_vraceni`, `vypujcka_kniha_id`, `vypujcka_ctenar_id`) " +
                    "VALUES ('%s', %s, '%s', '%d', '%d');",
                    vypujcka.getDatumPujceni(), datumVraceni, vypujcka.getPredpokladeneDatumVraceni(), vypujcka.getIdKniha(), vypujcka.getIdCtenar());
            myConn.createStatement().execute(sql);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * vrátí id příští výpujčky
     * @return
     */
    public int getVypujckaNexId(){
        int id = 0;

        try {
            String sql = "SELECT AUTO_INCREMENT " +
                    "     FROM information_schema.TABLES " +
                    "     WHERE TABLE_SCHEMA = \"knihovna\" " +
                    "     AND TABLE_NAME = \"vypujcka\";";
            ResultSet rs = myConn.createStatement().executeQuery(sql);

            while (rs.next()){
                id = rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * upravi výpůjčku v databázi
     * @param vypujcka
     */
    public void editVypujcka(Vypujcka vypujcka){

        String datumVraceni = vypujcka.getDatumVraceni() == null ? "NULL" : "'"+vypujcka.getDatumVraceni()+"'";

        String sql = String.format("UPDATE `vypujcka` SET \n" +
                        "`vypujcka_datum_pujceni` = '%s', \n" +
                        "`vypujcka_datum_vraceni` = %s, \n" +
                        "`vypujcka_predpokladane_datum_vraceni` = '%s', \n" +
                        "`vypujcka_kniha_id` = '%d', \n" +
                        "`vypujcka_ctenar_id` = '%d' \n" +
                        "WHERE \n" +
                        "`vypujcka`.`vypujcka_id` = %d",
                vypujcka.getDatumPujceni(), datumVraceni, vypujcka.getPredpokladeneDatumVraceni(), vypujcka.getIdKniha(), vypujcka.getIdCtenar(), vypujcka.getId());

        try {

            myConn.createStatement().execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return ArrayList včeh autorů  v databázi
     */
    public ArrayList<Autor> getAutori(){
        ArrayList<Autor> autori = new ArrayList<>();

        try {
            String sql = "SELECT * FROM knihovna.autor";
            ResultSet rs = myConn.createStatement().executeQuery(sql);

            while (rs.next()){
                autori.add(new Autor(rs.getInt("autor_id"), rs.getString("autor_kod"), rs.getString("autor_jmeno")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autori;
    }

    /**
     *
     * @param id
     * @return autor s daným id
     */
    public Autor getAutor(int id){
        Autor autor = null;

        try {
            String sql = String.format("SELECT * FROM knihovna.autor where autor_id = %d", id);
            ResultSet rs = myConn.createStatement().executeQuery(sql);

            if (rs.next()){
                autor = (new Autor(rs.getInt("autor_id"), rs.getString("autor_kod"), rs.getString("autor_jmeno")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autor;
    }

    /**
     * přída autor do databáze
     * @param autor
     */
    public void addAutor(Autor autor){
        try {
            String sql = String.format("INSERT INTO knihovna.autor (`autor_kod`, `autor_jmeno`)\n" +
                    "VALUES ('%s', '%s')", autor.getKod(), autor.getJmeno());
            myConn.createStatement().execute(sql);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * upravi autor v databázi
     * @param autor
     */
    public void editAutor(Autor autor){

        try {
            String sql = String.format("UPDATE knihovna.autor\n" +
                    "SET autor.kod = '%s' ,\n" +
                    "autor.autor_jmeno = '%s'\n" +
                    "WHERE autor.autor_id = %d;", autor.getKod(), autor.getJmeno(), autor.getId());
            myConn.createStatement().execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}