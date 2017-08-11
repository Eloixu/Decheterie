package fr.trackoe.decheterie.model.bean.usager;

/**
 * Created by Haocheng on 07/04/2017.
 */

public class UsagerHabitat {
    private int dchUsagerId;
    private int habitatId;

    public UsagerHabitat() {

    }

    public UsagerHabitat(int dchUsagerId, int habitatId) {
        this.dchUsagerId = dchUsagerId;
        this.habitatId = habitatId;
    }

    public int getDchUsagerId() {
        return dchUsagerId;
    }

    public void setDchUsagerId(int dchUsagerId) {
        this.dchUsagerId = dchUsagerId;
    }

    public int getHabitatId() {
        return habitatId;
    }

    public void setHabitatId(int habitatId) {
        this.habitatId = habitatId;
    }
}
