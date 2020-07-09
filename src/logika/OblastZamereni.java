package logika;

/**
 * třída oblasti zaměření
 */
public class OblastZamereni {
    private int id;
    private String nazev;

    /**
     * konstruktor
     * @param id
     * @param nazev
     */
    public OblastZamereni(int id, String nazev) {
        this.id = id;
        this.nazev = nazev;
    }

    /**
     * konstruktor
     * @param nazev
     */
    public OblastZamereni(String nazev) {
        this.nazev = nazev;
    }

    public int getId() {
        return id;
    }

    public String getNazev() {
        return nazev;
    }

    public String getNazevSid(){
        return String.format("%s (%d)",nazev , id);
    }

    @Override
    public String toString() {
        return String.format("%s (%d)",nazev , id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OblastZamereni)) return false;
        OblastZamereni that = (OblastZamereni) o;
        return getNazev().equals(that.getNazev()) &&
                this.getId() == (that.getId());
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }
}
