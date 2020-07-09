package logika;

/**
 * třída kniha
 */
public class Kniha {
    private int id;
    private String kod;
    private String nazev;
    private Autor autor;
    private OblastZamereni oblastZamereni;

    /**
     * konstruktor
     * @param id
     * @param nazev
     * @param autor
     * @param oblastZamereni
     */
    public Kniha(int id, String nazev, Autor autor, OblastZamereni oblastZamereni) {
        this.id = id;
        this.nazev = nazev;
        this.autor = autor;
        this.oblastZamereni = oblastZamereni;
    }

    /**
     * konstruktor
     * @param nazev
     * @param autor
     * @param oblastZamereni
     */
    public Kniha(String nazev, Autor autor, OblastZamereni oblastZamereni) {
        this.nazev = nazev;
        this.autor = autor;
        this.oblastZamereni = oblastZamereni;
    }

//    @Override
//    public String toString() {
//        return String.format("%d %s %s %d %s", id, nazev, autor, oblastZamereni.getId(), oblastZamereni.getNazev());
//    }

    public int getId() {
        return id;
    }

    public String getIdString() {
        return String.valueOf(id);
    }

    public String getNazev() {
        return nazev;
    }

    public Autor getAutor() {
        return autor;
    }

    public OblastZamereni getOblastZamereni() {
        return oblastZamereni;
    }

    public String getOblastNazev() {
        return oblastZamereni.getNazev();
    }


    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setOblastZamereni(OblastZamereni oblastZamereni) {
        this.oblastZamereni = oblastZamereni;
    }

    @Override
    public String toString() {
        return String.format("%s :: %s (%d)", nazev, autor, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kniha)) return false;
        Kniha that = (Kniha) o;
        return getNazev().equals(that.getNazev())
                && this.getId() == (that.getId());
    }
}
