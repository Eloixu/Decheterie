package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 20/04/2017.
 */

public class TypeHabitat {
    private int id;
    private String type;

    public TypeHabitat() {

    }

    public TypeHabitat(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
