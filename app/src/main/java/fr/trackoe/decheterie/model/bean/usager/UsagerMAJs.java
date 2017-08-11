package fr.trackoe.decheterie.model.bean.usager;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.CarteActive;
import fr.trackoe.decheterie.model.bean.global.CarteActives;
import fr.trackoe.decheterie.model.bean.global.ComptePrepayes;
import fr.trackoe.decheterie.model.bean.global.ContenantBean;

/**
 * Created by Haocheng on 27/07/2017.
 */

public class UsagerMAJs extends ContenantBean {
    private Usagers usagers;
    private UsagerHabitats usagerHabitats;
    private UsagerMenages  usagerMenages;
    private Menages menages;
    private Locaux locaux;
    private Habitats habitats;
    private ComptePrepayes comptePrepayes;
    private CarteActives carteActives;

    public UsagerMAJs(){

    }

    public Usagers getUsagers() {
        return usagers;
    }

    public void setUsagers(Usagers usagers) {
        this.usagers = usagers;
    }

    public UsagerHabitats getUsagerHabitats() {
        return usagerHabitats;
    }

    public void setUsagerHabitats(UsagerHabitats usagerHabitats) {
        this.usagerHabitats = usagerHabitats;
    }

    public UsagerMenages getUsagerMenages() {
        return usagerMenages;
    }

    public void setUsagerMenages(UsagerMenages usagerMenages) {
        this.usagerMenages = usagerMenages;
    }

    public Menages getMenages() {
        return menages;
    }

    public void setMenages(Menages menages) {
        this.menages = menages;
    }

    public Locaux getLocaux() {
        return locaux;
    }

    public void setLocaux(Locaux locaux) {
        this.locaux = locaux;
    }

    public Habitats getHabitats() {
        return habitats;
    }

    public void setHabitats(Habitats habitats) {
        this.habitats = habitats;
    }

    public ComptePrepayes getComptePrepayes() {
        return comptePrepayes;
    }

    public void setComptePrepayes(ComptePrepayes comptePrepayes) {
        this.comptePrepayes = comptePrepayes;
    }

    public CarteActives getCarteActives() {
        return carteActives;
    }

    public void setCarteActives(CarteActives carteActives) {
        this.carteActives = carteActives;
    }
}

