package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Remi on 30/11/2015.
 */
public class Module {
    private String idModule;
    private String nom;
    private String formulaire;
    private String version;
    private boolean isForm;

    public Module() {
        this.idModule = "";
        this.nom = "";
        this.formulaire = "";
        this.version = "";
        this.isForm = false;
    }

    public Module(String idModule, String nom, String formulaire, String version, boolean isForm) {
        this.idModule = idModule;
        this.nom = nom;
        this.formulaire = formulaire;
        this.version = version;
        this.isForm = isForm;
    }

    public String getIdModule() {
        return idModule;
    }

    public void setIdModule(String idModule) {
        this.idModule = idModule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFormulaire() {
        return formulaire;
    }

    public void setFormulaire(String formulaire) {
        this.formulaire = formulaire;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isForm() {
        return isForm;
    }

    public void setIsForm(boolean isForm) {
        this.isForm = isForm;
    }
}
