package fr.model.traffic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Traffic {

    private @Id@GeneratedValue Long id;
    private String store;
    private int nb;
    private LocalDate date;
    private long storeId;

    public Traffic(){}

    public Traffic(String store, int nb, LocalDate date, long storeId) {
        this.store = store;
        this.nb = nb;
        this.date = date;
        this.storeId = storeId;
    }

    public Long getId() {
        return id;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public int getNb() {
        return nb;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Traffic traffic = (Traffic) o;
        return nb == traffic.nb && Objects.equals(storeId, traffic.storeId) && Objects.equals(date, traffic.date) && Objects.equals(id, traffic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, nb, date, id);
    }

    @Override
    public String toString() {
        return "Stat{" +
                "store=" + store +
                ", nb=" + nb +
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
