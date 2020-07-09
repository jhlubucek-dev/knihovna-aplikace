package logika;

/**
 * třída čtenář
 */
public class Ctenar {
    private int id;
    private String jmeno;
    private String prijmeni;
    private String email;

    /**
     * konstruktor
     * @param id
     * @param jmeno
     * @param prijmeni
     * @param email
     */
    public Ctenar(int id, String jmeno, String prijmeni, String email) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.email = email.toLowerCase();
    }

    /**
     * konstruktor
     * @param jmeno
     * @param prijmeni
     * @param email
     */
    public Ctenar(String jmeno, String prijmeni, String email) {
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.email = email.toLowerCase();
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getCeleJmeno() {
        return jmeno + " " + prijmeni;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%d)", jmeno, prijmeni, id);
    }

    /**
     *
     * @return id jako string
     */
    public String getIdString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ctenar)) return false;
        Ctenar that = (Ctenar) o;
        return getCeleJmeno().equals(that.getCeleJmeno()) &&
                this.getId() == (that.getId());
    }
}
