package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 30/05/2017.
 */

public class ModePaiement {
    private int id;
    private String mode;

    public ModePaiement() {

    }

    public ModePaiement(int id, String mode) {
        this.id = id;
        this.mode = mode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
