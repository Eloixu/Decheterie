package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class UsagerMenage {
    private int dchUsagerId;
    private int menageId;

    public UsagerMenage() {

    }

    public UsagerMenage(int dchUsagerId, int menageId) {
        this.dchUsagerId = dchUsagerId;
        this.menageId = menageId;
    }

    public int getDchUsagerId() {
        return dchUsagerId;
    }

    public void setDchUsagerId(int dchUsagerId) {
        this.dchUsagerId = dchUsagerId;
    }

    public int getMenageId() {
        return menageId;
    }

    public void setMenageId(int menageId) {
        this.menageId = menageId;
    }
}
