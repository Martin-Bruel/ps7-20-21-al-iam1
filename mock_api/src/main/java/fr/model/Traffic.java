package fr.model;
import java.util.Objects;

public class Traffic {

    private long id;
    private String store;
    private int nb;
    private String date;
    private long storeId;

    public Traffic(){}

    public Traffic(long id, String store, int nb, String date, long storeId) {
        this.id = id;
        this.store = store;
        this.nb = nb;
        this.date = date;
        this.storeId = storeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setNb(int nb) {
        this.nb = nb;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Traffic traffic = (Traffic) o;
        return id == traffic.id && nb == traffic.nb && storeId == traffic.storeId && Objects.equals(store, traffic.store) && Objects.equals(date, traffic.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, store, nb, date, storeId);
    }

    @Override
    public String toString() {
        return "Traffic{" +
                "id=" + id +
                ", store='" + store + '\'' +
                ", nb=" + nb +
                ", date=" + date +
                ", storeId=" + storeId +
                '}';
    }
}
