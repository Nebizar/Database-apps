/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaapp1;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author student
 */
@Entity
@Table(name = "PRACOWNICY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pracownik.findAll", query = "SELECT p FROM Pracownik p")
    , @NamedQuery(name = "Pracownik.findByIdPrac", query = "SELECT p FROM Pracownik p WHERE p.idPrac = :idPrac")
    , @NamedQuery(name = "Pracownik.findByNazwisko", query = "SELECT p FROM Pracownik p WHERE p.nazwisko = :nazwisko")
    , @NamedQuery(name = "Pracownik.findByZatrudniony", query = "SELECT p FROM Pracownik p WHERE p.zatrudniony = :zatrudniony")
    , @NamedQuery(name = "Pracownik.findByPlacaPod", query = "SELECT p FROM Pracownik p WHERE p.placaPod = :placaPod")
    , @NamedQuery(name = "Pracownik.findByPlacaDod", query = "SELECT p FROM Pracownik p WHERE p.placaDod = :placaDod")})
public class Pracownik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PRAC")
    private Integer idPrac;
    @Column(name = "NAZWISKO")
    private String nazwisko;
    @Column(name = "ZATRUDNIONY")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zatrudniony;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PLACA_POD")
    private BigDecimal placaPod;
    @Column(name = "PLACA_DOD")
    private BigDecimal placaDod;
    @JoinColumn(name = "ETAT", referencedColumnName = "NAZWA")
    @ManyToOne
    private Etaty etat;
    @OneToMany(mappedBy = "idSzefa")
    private Collection<Pracownik> pracownikCollection;
    @JoinColumn(name = "ID_SZEFA", referencedColumnName = "ID_PRAC")
    @ManyToOne
    private Pracownik idSzefa;
    @JoinColumn(name = "ID_ZESP", referencedColumnName = "ID_ZESP")
    @ManyToOne
    private Zespol idZesp;

    public Pracownik() {
    }

    public Pracownik(Integer idPrac) {
        this.idPrac = idPrac;
    }

    public Integer getIdPrac() {
        return idPrac;
    }

    public void setIdPrac(Integer idPrac) {
        this.idPrac = idPrac;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Date getZatrudniony() {
        return zatrudniony;
    }

    public void setZatrudniony(Date zatrudniony) {
        this.zatrudniony = zatrudniony;
    }

    public BigDecimal getPlacaPod() {
        return placaPod;
    }

    public void setPlacaPod(BigDecimal placaPod) {
        this.placaPod = placaPod;
    }

    public BigDecimal getPlacaDod() {
        return placaDod;
    }

    public void setPlacaDod(BigDecimal placaDod) {
        this.placaDod = placaDod;
    }

    public Etaty getEtat() {
        return etat;
    }

    public void setEtat(Etaty etat) {
        this.etat = etat;
    }

    @XmlTransient
    public Collection<Pracownik> getPracownikCollection() {
        return pracownikCollection;
    }

    public void setPracownikCollection(Collection<Pracownik> pracownikCollection) {
        this.pracownikCollection = pracownikCollection;
    }

    public Pracownik getIdSzefa() {
        return idSzefa;
    }

    public void setIdSzefa(Pracownik idSzefa) {
        this.idSzefa = idSzefa;
    }

    public Zespol getIdZesp() {
        return idZesp;
    }

    public void setIdZesp(Zespol idZesp) {
        this.idZesp = idZesp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrac != null ? idPrac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pracownik)) {
            return false;
        }
        Pracownik other = (Pracownik) object;
        if ((this.idPrac == null && other.idPrac != null) || (this.idPrac != null && !this.idPrac.equals(other.idPrac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpaapp1.Pracownik[ idPrac=" + idPrac + " ]";
    }
    
}
