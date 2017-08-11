package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Remi on 30/11/2015.
 */
public class Modules extends ContenantBean {
    private ArrayList<Module> listModules;

    public Modules() {
        this.listModules = new ArrayList<Module>();
    }

    public ArrayList<Module> getListModules() {
        return listModules;
    }

    public void setListModules(ArrayList<Module> listModules) {
        this.listModules = listModules;
    }

    public void addModule(String idModule, String nom, String formulaire, String version, boolean isForm) {
        this.listModules.add(new Module(idModule, nom, formulaire, version, isForm));
    }
}
