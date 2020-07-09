package logika;

import java.time.LocalDate;
import java.util.Date;

/**
 * třída výpůjčky
 */
public class Vypujcka {
    private int id;
    private LocalDate datumPujceni;
    private LocalDate predpokladeneDatumVraceni;
    private LocalDate datumVraceni;
    private Kniha kniha;
    private Ctenar ctenar;

    /**
     * kontruktor
     * @param id
     * @param datumPujceni
     * @param predpokladeneDatumVraceni
     * @param datumVraceni
     * @param kniha
     * @param ctenar
     */
    public Vypujcka(int id, LocalDate datumPujceni, LocalDate predpokladeneDatumVraceni, LocalDate datumVraceni, Kniha kniha, Ctenar ctenar) {
        this.id = id;
        this.datumPujceni = datumPujceni;
        this.predpokladeneDatumVraceni = predpokladeneDatumVraceni;
        this.datumVraceni = datumVraceni;
        this.kniha = kniha;
        this.ctenar = ctenar;
    }

    /**
     * kontruktor
     * @param datumPujceni
     * @param predpokladeneDatumVraceni
     * @param datumVraceni
     * @param kniha
     * @param ctenar
     */
    public Vypujcka(LocalDate datumPujceni, LocalDate predpokladeneDatumVraceni, LocalDate datumVraceni, Kniha kniha, Ctenar ctenar) {
        this.datumPujceni = datumPujceni;
        this.predpokladeneDatumVraceni = predpokladeneDatumVraceni;
        this.datumVraceni = datumVraceni;
        this.kniha = kniha;
        this.ctenar = ctenar;
    }

    /**
     * konstruktor
     * @param id
     * @param datumPujceni
     * @param predpokladeneDatumVraceni
     * @param datumVraceni
     * @param kniha
     * @param ctenar
     */
    public Vypujcka(int id, Date datumPujceni, Date predpokladeneDatumVraceni, Date datumVraceni, Kniha kniha, Ctenar ctenar) {
        this.id = id;
        this.datumPujceni = toLocalDate(datumPujceni);
        this.predpokladeneDatumVraceni = toLocalDate(predpokladeneDatumVraceni);
        this.datumVraceni = toLocalDate(datumVraceni);
        this.kniha = kniha;
        this.ctenar = ctenar;
    }

    public LocalDate getDatumPujceni() {
        return datumPujceni;
    }

    public LocalDate getPredpokladeneDatumVraceni() {
        return predpokladeneDatumVraceni;
    }

    public LocalDate getDatumVraceni() {
        return datumVraceni;
    }

    public void setDatumPujceni(LocalDate datumPujceni) {
        this.datumPujceni = datumPujceni;
    }

    public void setPredpokladeneDatumVraceni(LocalDate predpokladeneDatumVraceni) {
        this.predpokladeneDatumVraceni = predpokladeneDatumVraceni;
    }

    public void setDatumVraceni(LocalDate datumVraceni) {
        this.datumVraceni = datumVraceni;
    }

    public Kniha getKniha() {
        return kniha;
    }

    public void setKniha(Kniha kniha) {
        this.kniha = kniha;
    }

    public Ctenar getCtenar() {
        return ctenar;
    }

    public void setCtenar(Ctenar ctenar) {
        this.ctenar = ctenar;
    }

    public int getIdKniha() {
        return kniha.getId();
    }

    public int getIdCtenar() {
        return ctenar.getId();
    }

    public int getId() {
        return id;
    }

    public String getIdString() {
        return String.valueOf(id);
    }

    public LocalDate toLocalDate(Date date) {
        return date == null? null : new java.sql.Date(date.getTime()).toLocalDate();
    }

    @Override
    public String toString() {
        return "Vypujcka{" +
                "id=" + id +
                ", datumPujceni=" + datumPujceni +
                ", predpokladaneDatumVraceni=" + predpokladeneDatumVraceni +
                ", SkutecneDatumVraceni=" + datumVraceni +
                ", kniha=" + kniha +
                ", ctenar=" + ctenar +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vypujcka)) return false;
        Vypujcka that = (Vypujcka) o;
        return getCtenar().equals(that.getCtenar())
                && this.getId() == (that.getId())
                && getKniha().equals(that.getKniha())
                && getDatumPujceni().equals(that.getDatumPujceni());
    }
}
