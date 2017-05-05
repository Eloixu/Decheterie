package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Remi on 02/05/2017.
 */
public class TypeHabitats extends ContenantBean {
    private ArrayList<TypeHabitat> listTypeHabitat;

    public TypeHabitats() {
        this.listTypeHabitat = new ArrayList<>();
    }

    public ArrayList<TypeHabitat> getListTypeHabitat() {
        return listTypeHabitat;
    }

    public void setListTypeHabitat(ArrayList<TypeHabitat> listTypeHabitat) {
        this.listTypeHabitat = listTypeHabitat;
    }

    public void addTypeHabitat(int id, String type) {
        this.listTypeHabitat.add(new TypeHabitat(id, type));
    }
}
