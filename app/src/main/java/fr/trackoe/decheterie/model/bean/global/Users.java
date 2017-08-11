package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Remi on 30/11/2015.
 */
public class Users extends ContenantBean {
    private ArrayList<User> listUsers;

    public Users() {
        this.listUsers = new ArrayList<User>();
    }

    public ArrayList<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(ArrayList<User> listUsers) {
        this.listUsers = listUsers;
    }

    public void addUser(String idUser, String login, String password, String nom, String prenom, boolean isAutorisationChgtInter) {
        this.listUsers.add(new User(idUser, login, password, nom, prenom, isAutorisationChgtInter));
    }
}
