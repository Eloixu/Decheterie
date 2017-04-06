package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 06/04/2017.
 */

public class Carte {
    private long id;
    private String num_carte;
    private String num_RFID;
    private int dch_type_carte_id;
    private int dch_account_id;

    public Carte() {

    }

    public Carte(long id, String num_carte, String num_RFID, int dch_type_carte_id, int dch_account_id) {
        this.id = id;
        this.num_carte = num_carte;
        this.num_RFID = num_RFID;
        this.dch_type_carte_id = dch_type_carte_id;
        this.dch_account_id = dch_account_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNum_carte() {
        return num_carte;
    }

    public void setNum_carte(String num_carte) {
        this.num_carte = num_carte;
    }

    public String getNum_RFID() {
        return num_RFID;
    }

    public void setNum_RFID(String num_RFID) {
        this.num_RFID = num_RFID;
    }

    public int getDch_type_carte_id() {
        return dch_type_carte_id;
    }

    public void setDch_type_carte_id(int dch_type_carte_id) {
        this.dch_type_carte_id = dch_type_carte_id;
    }

    public int getDch_account_id() {
        return dch_account_id;
    }

    public void setDch_account_id(int dch_account_id) {
        this.dch_account_id = dch_account_id;
    }
}
