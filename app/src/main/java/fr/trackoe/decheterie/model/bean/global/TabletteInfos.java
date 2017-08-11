package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Remi on 30/11/2015.
 */
public class TabletteInfos extends ContenantBean {
    private String nomTablette;
    private String nomClient;
    private int clientId;
    private int accountId;
    private int idOperateur;

    public String getNomTablette() {
        return nomTablette;
    }

    public void setNomTablette(String nomTablette) {
        this.nomTablette = nomTablette;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getIdOperateur() {
        return idOperateur;
    }

    public void setIdOperateur(int idOperateur) {
        this.idOperateur = idOperateur;
    }
}
