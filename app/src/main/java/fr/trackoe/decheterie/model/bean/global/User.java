package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Remi on 30/11/2015.
 */
public class User {
    private String idUser;
    private String login;
    private String password;
    private String nom;
    private String prenom;
    private boolean isAutorisationChangementInter ;

    public User() {
        this.idUser = "";
        this.login = "";
        this.password = "";
        this.nom = "";
        this.prenom = "";
        this.isAutorisationChangementInter = false;
    }

    public User(String idUser, String login, String password, String nom, String prenom, boolean isAutorisationChangementInter) {
        this.idUser = idUser;
        this.login = login;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.isAutorisationChangementInter = isAutorisationChangementInter;
    }

    public String toString() {
        return "Id: " + this.idUser + "\nLogin: " + login + "\nPassword: " + password + "\nNom: " + nom + "\nPrenom: " + prenom;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isAutorisationChangementInter() {
        return isAutorisationChangementInter;
    }

    public void setIsAutorisationChangementInter(boolean isAutorisationChangementInter) {
        this.isAutorisationChangementInter = isAutorisationChangementInter;
    }
}
