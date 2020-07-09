package logika;

/**
 * třída oblasti zaměření
 */
public class Autor {
    private int id;
    private String jmeno;
    private String kod;

    /**
     * konstruktor
     * @param id
     * @param jmeno
     */
    public Autor(int id, String kod, String jmeno) {
        this.id = id;
        this.jmeno = jmeno;
        this.kod = kod;
    }

    /**
     * konstruktor
     * @param jmeno
     */
    public Autor(String kod, String jmeno) {
        this.id = id;
        this.jmeno = jmeno;
        this.kod = kod;
    }

    public int getId() {
        return id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getNazevSid(){
        return String.format("%s (%d)", jmeno, id);
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", jmeno, kod);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Autor)) return false;
        Autor that = (Autor) o;
        return getJmeno().equals(that.getJmeno()) &&
                this.getId() == (that.getId());
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }
}
