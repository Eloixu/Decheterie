package fr.trackoe.decheterie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import fr.trackoe.decheterie.R;

/**
 * Created by Remi on 02/12/2015.
 */
public class DecheterieDatabase extends SQLiteOpenHelper {
    private Context ctx;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String FLOAT_TYPE = " FLOAT";
    private static final String NOT_NULL = " NOT_NULL";
    private static final String COMMA_SEP = ", ";
    private static final String FOREIGN_KEY = "FOREIGN KEY";
    private static final String REFERENCES  = "REFERENCES";
    private static final String DATE_TIME  = "DATETIME";

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    /* Declaration des tables */

//    Table dch_decheterie
    public static abstract class TableDchDecheterie implements BaseColumns {
        public static final String TABLE_DCH_DECHETERIE = "dch_decheterie";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String ID_ACCOUNT = "id_account";
        public static final int NUM_ID_ACCOUNT = 1;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 2;
        public static final String CONSIGNE_COMPTAGE = "consigne_comptage";
        public static final int NUM_CONSIGNE_COMPTAGE = 3;
        public static final String CONSIGNE_AV_SIGNATURE = "consigne_av_signature";
        public static final int NUM_CONSIGNE_AV_SIGNATURE = 4;
        public static final String APPORT_FLUX= "apport_flux";
        public static final int NUM_APPORT_FLUX = 5;
        public static final String UNITE_TOTAL = "unite_total";
        public static final int NUM_UNITE_TOTAL = 6;
    }

    public static final String CREATE_TABLE_DCH_DECHETERIE = "CREATE TABLE IF NOT EXISTS " + TableDchDecheterie.TABLE_DCH_DECHETERIE + " ("
            + TableDchDecheterie.ID + INTEGER_TYPE + " PRIMARY KEY, "
            + TableDchDecheterie.ID_ACCOUNT + INTEGER_TYPE + COMMA_SEP
            + TableDchDecheterie.NOM + TEXT_TYPE + NOT_NULL + COMMA_SEP
            + TableDchDecheterie.CONSIGNE_COMPTAGE + TEXT_TYPE  + COMMA_SEP
            + TableDchDecheterie.CONSIGNE_AV_SIGNATURE + TEXT_TYPE + COMMA_SEP
            + TableDchDecheterie.APPORT_FLUX + INTEGER_TYPE + COMMA_SEP
            + TableDchDecheterie.UNITE_TOTAL + TEXT_TYPE + " )" ;

    private static final String DELETE_TABLE_DCH_DECHETERIE = "DROP TABLE IF EXISTS " + TableDchDecheterie.TABLE_DCH_DECHETERIE;


    //    Table icon
    public static abstract class TableIcon implements BaseColumns {
        public static final String TABLE_ICON = "icon";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 1;
        public static final String DOMAINE = "domaine";
        public static final int NUM_DOMAINE = 2;
        public static final String PATH = "path";
        public static final int NUM_PATH = 3;
    }

    public static final String CREATE_TABLE_ICON= "CREATE TABLE IF NOT EXISTS " + TableIcon.TABLE_ICON + " ("
            + TableIcon.ID + INTEGER_TYPE + " PRIMARY KEY, "
            + TableIcon.NOM + TEXT_TYPE + COMMA_SEP
            + TableIcon.DOMAINE + TEXT_TYPE + COMMA_SEP
            + TableIcon.PATH + TEXT_TYPE + " )" ;

    private static final String DELETE_TABLE_ICON = "DROP TABLE IF EXISTS " + TableIcon.TABLE_ICON;

    //    Table dch_flux
    public static abstract class TableDchFlux implements BaseColumns {
        public static final String TABLE_DCH_FLUX = "dch_flux";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 1;
        public static final String ICON_ID = "icon_id";
        public static final int NUM_ICON_ID = 2;
    }

    public static final String CREATE_TABLE_DCH_FLUX = "CREATE TABLE IF NOT EXISTS " + TableDchFlux.TABLE_DCH_FLUX + " ("
            + TableDchFlux.ID + INTEGER_TYPE + " PRIMARY KEY, "
            + TableDchFlux.NOM + TEXT_TYPE + COMMA_SEP
            + TableDchFlux.ICON_ID + INTEGER_TYPE + " )" ;
            //+ FOREIGN_KEY + "(" + TableDchFlux.ICON_ID + ")" + REFERENCES + TableIcon.TABLE_ICON + "(" + TableIcon.ID + "))" ;

    private static final String DELETE_TABLE_DCH_FLUX = "DROP TABLE IF EXISTS " + TableDchFlux.TABLE_DCH_FLUX;

    //    Table dch_decheterie_flux
    public static abstract class TableDchDecheterieFlux implements BaseColumns {
        public static final String TABLE_DCH_DECHETERIE_FLUX = "dch_decheterie_flux";
        public static final String DCH_DECHETERIE_ID = "dch_decheterie_id";
        public static final int NUM_DCH_DECHETERIE_ID = 0;
        public static final String DCH_FLUX_ID = "dch_flux_id";
        public static final int NUM_DCH_FLUX_ID = 1;
    }

    public static final String CREATE_TABLE_DCH_DECHETERIE_FLUX = "CREATE TABLE IF NOT EXISTS " + TableDchDecheterieFlux.TABLE_DCH_DECHETERIE_FLUX + " ("
            + TableDchDecheterieFlux.DCH_DECHETERIE_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchDecheterieFlux.DCH_FLUX_ID + INTEGER_TYPE + " )" ;

    private static final String DELETE_TABLE_DCH_DECHETERIE_FLUX = "DROP TABLE IF EXISTS " + TableDchDecheterieFlux.TABLE_DCH_DECHETERIE_FLUX;

    //    Table dch_compte_prepaye
    public static abstract class TableDchComptePrepaye implements BaseColumns {
        public static final String TABLE_DCH_COMPTE_PREPAYE = "dch_compte_prepaye";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String DCH_USAGER_ID = "dch_usager_id";
        public static final int NUM_DCH_USAGER_ID = 1;
        public static final String QTY_POINT = "qty_point";
        public static final int NUM_QTY_POINT = 2;
        public static final String NB_DEPOT_RESTANT = "nb_depot_restant";
        public static final int NUM_NB_DEPOT_RESTANT = 3;

    }

    public static final String CREATE_TABLE_DCH_COMPTE_PREPAYE = "CREATE TABLE IF NOT EXISTS " + TableDchComptePrepaye.TABLE_DCH_COMPTE_PREPAYE + " ("
            + TableDchComptePrepaye.ID + INTEGER_TYPE + " PRIMARY KEY, "
            + TableDchComptePrepaye.DCH_USAGER_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchComptePrepaye.QTY_POINT + FLOAT_TYPE + COMMA_SEP
            + TableDchComptePrepaye.NB_DEPOT_RESTANT + INTEGER_TYPE + " )" ;

    private static final String DELETE_TABLE_DCH_COMPTE_PREPAYE = "DROP TABLE IF EXISTS " +  TableDchComptePrepaye.TABLE_DCH_COMPTE_PREPAYE;

    //    Table dch_type_carte
    public static abstract class TableDchTypeCarte implements BaseColumns {
        public static final String TABLE_NAME = "dch_type_carte";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 1;

    }

    public static final String CREATE_TABLE_DCH_TYPE_CARTE = "CREATE TABLE IF NOT EXISTS " + TableDchTypeCarte.TABLE_NAME + " ("
            + TableDchTypeCarte.ID + INTEGER_TYPE + " PRIMARY KEY, "
            + TableDchTypeCarte.NOM + TEXT_TYPE + " )" ;

    private static final String DELETE_TABLE_DCH_TYPE_CARTE = "DROP TABLE IF EXISTS " + TableDchTypeCarte.TABLE_NAME;

    //    Table dch_choix_decompte_total
    public static abstract class TableDchChoixDecompteTotal implements BaseColumns {
        public static final String TABLE_NAME = "dch_choix_decompte_total";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 1;
    }

    public static final String CREATE_TABLE_DCH_CHOIX_DECOMPTE_TOTAL = "CREATE TABLE IF NOT EXISTS " + TableDchChoixDecompteTotal.TABLE_NAME + " ("
            + TableDchChoixDecompteTotal.ID + INTEGER_TYPE + " PRIMARY KEY, "
            + TableDchChoixDecompteTotal.NOM + TEXT_TYPE + " )" ;

    private static final String DELETE_TABLE_DCH_CHOIX_DECOMPTE_TOTAL = "DROP TABLE IF EXISTS " + TableDchChoixDecompteTotal.TABLE_NAME;

    //    Table dch_account_setting
    public static abstract class TableDchAccountSetting implements BaseColumns {
        public static final String TABLE_NAME = "dch_account_setting";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String DCH_ACCOUNT_ID = "dch_account_id";
        public static final int NUM_DCH_ACCOUNT_ID = 1;
        public static final String DCH_TYPE_CARTE_ID = "dch_type_carte_id";
        public static final int NUM_DCH_TYPE_CARTE_ID = 2;
        public static final String DCH_UNITE_ID = "dch_unite_id";
        public static final int NUM_DCH_UNITE_ID = 3;
        public static final String DECOMPTE_DEPOT = "decompte_depot";
        public static final int NUM_DECOMPTE_DEPOT = 4;
        public static final String DECOMPTE_UDD = "decompte_udd";
        public static final int NUM_DECOMPTE_UDD = 5;
        public static final String PAGE_SIGNATURE = "page_signature";
        public static final int NUM_PAGE_SIGNATURE = 6;
        public static final String COUT_UDD_PR_POINT = "cout_UDD_pr_point";
        public static final int NUM_COUT_UDD_PR_POINT = 7;
        public static final String COUT_POINT = "cout_point";
        public static final int NUM_COUT_POINT = 8;
        public static final String UNITE_POINT = "unite_point";
        public static final int NUM_UNITE_POINT = 9;
        public static final String DATE_DEBUT_PARAM = "date_debut_param";
        public static final int NUM_DATE_DEBUT_PARAM = 10;
        public static final String DATE_FIN_PARAM = "date_fin_param";
        public static final int NUM_DATE_FIN_PARAM = 11;
        public static final String DCH_CHOIX_DECOMPTE_TOTAL_ID = "dch_choix_decompte_total_id";
        public static final int NUM_DCH_CHOIX_DECOMPTE_TOTAL_ID = 12;
        public static final String NB_DEPOT_RESTANT = "nb_depot_restant";
        public static final int NUM_NB_DEPOT_RESTANT = 13;
    }

    public static final String CREATE_TABLE_DCH_ACCOUNT_SETTING = "CREATE TABLE IF NOT EXISTS " + TableDchAccountSetting.TABLE_NAME + " ("
            + TableDchAccountSetting.ID + INTEGER_TYPE + " PRIMARY KEY, "
            + TableDchAccountSetting.DCH_ACCOUNT_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchAccountSetting.DCH_TYPE_CARTE_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchAccountSetting.DCH_UNITE_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchAccountSetting.DECOMPTE_DEPOT + INTEGER_TYPE + COMMA_SEP
            + TableDchAccountSetting.DECOMPTE_UDD + INTEGER_TYPE + COMMA_SEP
            + TableDchAccountSetting.PAGE_SIGNATURE + INTEGER_TYPE + COMMA_SEP
            + TableDchAccountSetting.COUT_UDD_PR_POINT + TEXT_TYPE + COMMA_SEP
            + TableDchAccountSetting.COUT_POINT + TEXT_TYPE + COMMA_SEP
            + TableDchAccountSetting.UNITE_POINT + TEXT_TYPE + COMMA_SEP
            + TableDchAccountSetting.DATE_DEBUT_PARAM + TEXT_TYPE + COMMA_SEP
            + TableDchAccountSetting.DATE_FIN_PARAM + TEXT_TYPE + COMMA_SEP
            + TableDchAccountSetting.DCH_CHOIX_DECOMPTE_TOTAL_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchAccountSetting.NB_DEPOT_RESTANT + INTEGER_TYPE + " )" ;

    private static final String DELETE_TABLE_DCH_ACCOUNT_SETTING = "DROP TABLE IF EXISTS " + TableDchAccountSetting.TABLE_NAME;

    //    Table dch_account_flux_setting
    public static abstract class TableDchAccountFluxSetting implements BaseColumns {
        public static final String TABLE_NAME = "dch_account_flux_setting";
        public static final String DCH_ACCOUNT_SETTING_ID = "dch_account_setting_id";
        public static final int NUM_DCH_ACCOUNT_SETTING_ID = 0;
        public static final String DCH_FLUX_ID = "dch_flux_id";
        public static final int NUM_DCH_FLUX_ID = 1;
        public static final String CONVERT_COMPTAGE_PR_UDD = "convert_comptage_pr_UDD";
        public static final int NUM_CONVERT_COMPTAGE_PR_UDD = 2;
        public static final String COUT_UC_PR_POINT = "cout_UC_pr_point";
        public static final int NUM_COUT_UC_PR_POINT = 3;
    }

    public static final String CREATE_TABLE_DCH_ACCOUNT_FLUX_SETTING = "CREATE TABLE IF NOT EXISTS " + TableDchAccountFluxSetting.TABLE_NAME + " ("
            + TableDchAccountFluxSetting.DCH_ACCOUNT_SETTING_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchAccountFluxSetting.DCH_FLUX_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchAccountFluxSetting.CONVERT_COMPTAGE_PR_UDD + TEXT_TYPE + COMMA_SEP
            + TableDchAccountFluxSetting.COUT_UC_PR_POINT + TEXT_TYPE + " )" ;

    private static final String DELETE_TABLE_DCH_ACCOUNT_FLUX_SETTING = "DROP TABLE IF EXISTS " + TableDchAccountFluxSetting.TABLE_NAME;


    //    Table dch_carte
    public static abstract class TableDchCarte implements BaseColumns {
        public static final String TABLE_DCH_CARTE = "dch_carte";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String NUM_CARTE = "num_carte";
        public static final int NUM_NUM_CARTE = 1;
        public static final String NUM_RFID = "num_RFID";
        public static final int NUM_NUM_RFID = 2;
        public static final String DCH_TYPE_CARTE_ID = "dch_type_carte_id";
        public static final int NUM_DCH_TYPE_CARTE_ID = 3;
        public static final String DCH_ACCOUNT_ID = "dch_account_id";
        public static final int NUM_DCH_ACCOUNT_ID = 4;

    }

    public static final String CREATE_TABLE_DCH_CARTE = "CREATE TABLE IF NOT EXISTS " + TableDchCarte.TABLE_DCH_CARTE + " ("
            + TableDchCarte.ID + INTEGER_TYPE + " PRIMARY KEY, "
            + TableDchCarte.NUM_CARTE + TEXT_TYPE + COMMA_SEP
            + TableDchCarte.NUM_RFID + TEXT_TYPE + COMMA_SEP
            + TableDchCarte.DCH_TYPE_CARTE_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchCarte.DCH_ACCOUNT_ID + INTEGER_TYPE + " )" ;

    private static final String DELETE_TABLE_DCH_CARTE = "DROP TABLE IF EXISTS " +  TableDchCarte.TABLE_DCH_CARTE;

    //    Table dch_carte_active
    public static abstract class TableDchCarteActive implements BaseColumns {
        public static final String TABLE_DCH_CARTE_ACTIVE = "dch_carte_active";
        public static final String DCH_CARTE_ID = "dch_carte_id";
        public static final int NUM_DCH_CARTE_ID = 0;
        public static final String DATE_ACTIVATION = "date_activation";
        public static final int NUM_DATE_ACTIVATION = 1;
        public static final String DATE_DERNIER_MOTIF = "date_dernier_motif";
        public static final int NUM_DATE_DERNIER_MOTIF = 2;
        public static final String DCH_CARTE_ETAT_RAISON_ID = "dch_carte_etat_raison_id";
        public static final int NUM_DCH_CARTE_ETAT_RAISON_ID = 3;
        public static final String IS_ACTIVE = "is_active";
        public static final int NUM_IS_ACTIVE = 4;
        public static final String DCH_COMPTE_PREPAYE_ID = "dch_compte_prepaye_id";
        public static final int NUM_DCH_COMPTE_PREPAYE_ID = 5;

    }

    public static final String CREATE_TABLE_DCH_CARTE_ACTIVE = "CREATE TABLE IF NOT EXISTS " + TableDchCarteActive.TABLE_DCH_CARTE_ACTIVE + " ("
            + TableDchCarteActive.DCH_CARTE_ID + INTEGER_TYPE + " PRIMARY KEY, "
            + TableDchCarteActive.DATE_ACTIVATION + TEXT_TYPE + COMMA_SEP
            + TableDchCarteActive.DATE_DERNIER_MOTIF + TEXT_TYPE + COMMA_SEP
            + TableDchCarteActive.DCH_CARTE_ETAT_RAISON_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchCarteActive.IS_ACTIVE + INTEGER_TYPE + COMMA_SEP
            + TableDchCarteActive.DCH_COMPTE_PREPAYE_ID + INTEGER_TYPE + " )" ;

    private static final String DELETE_TABLE_DCH_CARTE_ACTIVE = "DROP TABLE IF EXISTS " +  TableDchCarteActive.TABLE_DCH_CARTE_ACTIVE;

    //    Table dch_carte_etat_raison
    public static abstract class TableDchCarteEtatRaison implements BaseColumns {
        public static final String TABLE_NAME = "dch_carte_etat_raison";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String RAISON = "raison";
        public static final int NUM_RAISON = 1;
    }

    public static final String CREATE_TABLE_DCH_CARTE_ETAT_RAISON = "CREATE TABLE IF NOT EXISTS " + TableDchCarteEtatRaison.TABLE_NAME + " ("
            + TableDchCarteEtatRaison.ID + INTEGER_TYPE + " PRIMARY KEY, "
            + TableDchCarteEtatRaison.RAISON + TEXT_TYPE + " )" ;

    private static final String DELETE_TABLE_DCH_CARTE_ETAT_RAISON = "DROP TABLE IF EXISTS " +  TableDchCarteEtatRaison.TABLE_NAME;


    //    Table dch_depot
    public static abstract class TableDchDepot implements BaseColumns {
        public static final String TABLE_DCH_DEPOT = "dch_depot";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String DATEHEURE = "dateheure";
        public static final int NUM_DATEHEURE = 1;
        public static final String DCH_DECHETERIE_ID = "dch_decheterie_id";
        public static final int NUM_DCH_DECHETERIE_ID = 2;
        public static final String DCH_CARTE_ACTIVE_DCH_CARTE_ID = "dch_carte_active_dch_carte_id";
        public static final int NUM_DCH_CARTE_ACTIVE_DCH_CARTE_ID = 3;
        public static final String DCH_COMPTE_PREPAYE_ID = "dch_compte_prepaye_id";
        public static final int NUM_DCH_COMPTE_PREPAYE_ID = 4;
        public static final String QTY_TOTAL_UDD = "qty_total_udd";
        public static final int NUM_QTY_TOTAL_UDD = 5;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 6;
        public static final String STATUT = "statut";
        public static final int NUM_STATUT = 7;
        public static final String IS_SENT = "is_sent";
        public static final int NUM_IS_SENT = 8;

    }

    public static final String CREATE_TABLE_DCH_DEPOT = "CREATE TABLE IF NOT EXISTS " + TableDchDepot.TABLE_DCH_DEPOT + " ("
            + TableDchDepot.ID + INTEGER_TYPE + INTEGER_TYPE + " PRIMARY KEY, "
            + TableDchDepot.DATEHEURE + TEXT_TYPE + COMMA_SEP
            + TableDchDepot.DCH_DECHETERIE_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchDepot.DCH_CARTE_ACTIVE_DCH_CARTE_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchDepot.DCH_COMPTE_PREPAYE_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchDepot.QTY_TOTAL_UDD + FLOAT_TYPE + COMMA_SEP
            + TableDchDepot.NOM + TEXT_TYPE + COMMA_SEP
            + TableDchDepot.STATUT + INTEGER_TYPE + COMMA_SEP
            + TableDchDepot.IS_SENT + INTEGER_TYPE + " )" ;

    private static final String DELETE_TABLE_DCH_DEPOT = "DROP TABLE IF EXISTS " +  TableDchDepot.TABLE_DCH_DEPOT;

    //    Table dch_apport_flux
    public static abstract class TableDchApportFlux implements BaseColumns {
        public static final String TABLE_DCH_APPORT_FLUX = "dch_apport_flux";
        public static final String DCH_DEPOT_ID = "dch_depot_id";
        public static final int NUM_DCH_DEPOT_ID = 0;
        public static final String DCH_FLUX_ID = "dch_flux_id";
        public static final int NUM_DCH_FLUX_ID = 1;
        public static final String QTY_APPORTE = "qty_apporte";
        public static final int NUM_QTY_APPORTE = 2;
        public static final String IS_SENT = "is_sent";
        public static final int NUM_IS_SENT = 3;

    }

    public static final String CREATE_TABLE_DCH_APPORT_FLUX = "CREATE TABLE IF NOT EXISTS " + TableDchApportFlux.TABLE_DCH_APPORT_FLUX + " ("
            + TableDchApportFlux.DCH_DEPOT_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchApportFlux.DCH_FLUX_ID + INTEGER_TYPE + COMMA_SEP
            + TableDchApportFlux.QTY_APPORTE + FLOAT_TYPE + COMMA_SEP
            + TableDchApportFlux.IS_SENT + INTEGER_TYPE + COMMA_SEP
            + "CONSTRAINT PK PRIMARY KEY (dch_depot_id, dch_flux_id)" + " )" ;


    private static final String DELETE_TABLE_DCH_APPORT_FLUX = "DROP TABLE IF EXISTS " + TableDchApportFlux.TABLE_DCH_APPORT_FLUX;


    //    Table users
    public static abstract class TableUsers implements BaseColumns {
        public static final String TABLE_USERS = "users";
        public static final String ID_USER = "id_user";
        public static final int NUM_ID_USER = 0;
        public static final String LOGIN = "login";
        public static final int NUM_LOGIN = 1;
        public static final String PASSWORD = "password";
        public static final int NUM_PASSWORD = 2;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 3;
        public static final String PRENOM = "prenom";
        public static final int NUM_PRENOM = 4;
        public static final String AUTORISATION_CHANGEMENT_INTER = "autorisation_chgt_inter";
        public static final int NUM_AUTORISATION_CHANGEMENT_INTER = 5;
    }

    public static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + TableUsers.TABLE_USERS + " ("
            + TableUsers.ID_USER + INTEGER_TYPE + " PRIMARY KEY, "
            + TableUsers.LOGIN + TEXT_TYPE + NOT_NULL + COMMA_SEP
            + TableUsers.PASSWORD + TEXT_TYPE + NOT_NULL + COMMA_SEP
            + TableUsers.NOM + TEXT_TYPE + COMMA_SEP
            + TableUsers.PRENOM + TEXT_TYPE + COMMA_SEP
            + TableUsers.AUTORISATION_CHANGEMENT_INTER + INTEGER_TYPE + " )" ;

    private static final String DELETE_TABLE_USERS = "DROP TABLE IF EXISTS " + TableUsers.TABLE_USERS;

    //    Table modules
    public static abstract class TableModules implements BaseColumns {
        public static final String TABLE_MODULES = "modules";
        public static final String ID_MODULE = "id_module";
        public static final int NUM_ID_MODULE = 0;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 1;
        public static final String FORMULAIRE_JSON = "formulaire_json";
        public static final int NUM_FORMULAIRE_JSON = 2;
        public static final String FORMULAIRE_VERSION = "formulaire_version";
        public static final int NUM_FORMULAIRE_VERSION = 3;
        public static final String IS_FORM = "is_form";
        public static final int NUM_IS_FORM = 4;
    }

    public static final String CREATE_TABLE_MODULES = "CREATE TABLE IF NOT EXISTS " + TableModules.TABLE_MODULES + " ("
            + TableModules.ID_MODULE + " INTEGER PRIMARY KEY, "
            + TableModules.NOM + TEXT_TYPE + COMMA_SEP
            + TableModules.FORMULAIRE_JSON + TEXT_TYPE + COMMA_SEP
            + TableModules.FORMULAIRE_VERSION + TEXT_TYPE + COMMA_SEP
            + TableModules.IS_FORM + INTEGER_TYPE + " )" ;

    public static final String DELETE_TABLE_MODULES = "DROP TABLE IF EXISTS " + TableModules.TABLE_MODULES;

    // Table bac_flux
    public static abstract class TableBacFlux implements BaseColumns {
        public static final String TABLE_BAC_FLUX = "bac_flux";
        public static final String ID_FLUX = "id_flux";
        public static final int NUM_ID_FLUX = 0;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 1;
        public static final String COULEUR = "couleur";
        public static final int NUM_COULEUR = 2;
        public static final String CODE_COULEUR = "code_couleur";
        public static final int NUM_CODE_COULEUR = 3;
        public static final String ID_CLIENT = "id_client";
        public static final int NUM_ID_CLIENT = 4;
        public static final String ACTIVATION = "activation";
        public static final int NUM_ACTIVATION = 5;
    }

    public static final String CREATE_TABLE_BAC_FLUX = "CREATE TABLE IF NOT EXISTS " + TableBacFlux.TABLE_BAC_FLUX + " ("
            + TableBacFlux.ID_FLUX + INTEGER_TYPE + " PRIMARY KEY, "
            + TableBacFlux.NOM + TEXT_TYPE + COMMA_SEP
            + TableBacFlux.COULEUR + TEXT_TYPE + COMMA_SEP
            + TableBacFlux.CODE_COULEUR + TEXT_TYPE + COMMA_SEP
            + TableBacFlux.ID_CLIENT + INTEGER_TYPE + COMMA_SEP
            + TableBacFlux.ACTIVATION + INTEGER_TYPE + " )" ;

    public static final String DELETE_TABLE_BAC_FLUX = "DROP TABLE IF EXISTS " + TableBacFlux.TABLE_BAC_FLUX;

    // Table PAV
    public static abstract class TablePav implements BaseColumns {
        public static final String TABLE_PAV = "pav";
        public static final String ID_PAV = "id";
        public static final int NUM_ID_PAV = 0;
        public static final String PAV_IDENTIFIANT = "identifiant";
        public static final int NUM_PAV_IDENTIFIANT = 1;
        public static final String PAV_ADRESSE = "adresse";
        public static final int NUM_PAV_ADRESSE = 2;
        public static final String PAV_CP = "cp";
        public static final int NUM_PAV_CP = 3;
        public static final String PAV_VILLE = "ville";
        public static final int NUM_PAV_VILLE = 4;
    }

    public static final String CREATE_TABLE_PAV = "CREATE TABLE IF NOT EXISTS " + TablePav.TABLE_PAV + " ("
            + TablePav.ID_PAV + INTEGER_TYPE + " PRIMARY KEY, "
            + TablePav.PAV_IDENTIFIANT + TEXT_TYPE + COMMA_SEP
            + TablePav.PAV_ADRESSE + TEXT_TYPE + COMMA_SEP
            + TablePav.PAV_CP + TEXT_TYPE + COMMA_SEP
            + TablePav.PAV_VILLE + TEXT_TYPE  + " )" ;

    public static final String DELETE_TABLE_PAV = "DROP TABLE IF EXISTS " + TablePav.TABLE_PAV;

    // Table releve
    public static abstract class TableReleve implements BaseColumns {
        public static final String TABLE_RELEVE = "releve";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String NOM_FORMULAIRE = "nom_formulaire";
        public static final int NUM_NOM_FORMULAIRE = 1;
        public static final String URL = "url";
        public static final int NUM_URL = 2;
        public static final String DATE_REALISATION = "date_realisation";
        public static final int NUM_DATE_REALISATION = 3;
        public static final String ID_FORMULAIRE = "id_formulaire";
        public static final int NUM_ID_FORMULAIRE = 4;
        public static final String IS_SENT = "is_sent";
        public static final int NUM_IS_SENT = 5;
        public static final String IS_URL_FORMULAIRE = "is_url_formulaire";
        public static final int NUM_IS_URL_FORMULAIRE = 6;
        public static final String IS_URL_CHAMPS = "is_url_champs";
        public static final int NUM_IS_URL_CHAMPS = 7;
        public static final String IS_URL_RELEVE= "is_url_releve";
        public static final int NUM_IS_URL_RELEVE = 8;
        public static final String STATUT= "statut";
        public static final int NUM_STATUT = 9;
        public static final String TITRE = "titre";
        public static final int NUM_TITRE = 10;
        public static final String ID_MODULE = "id_module";
        public static final int NUM_ID_MODULE = 11;
    }

    public static final String CREATE_TABLE_RELEVE = "CREATE TABLE IF NOT EXISTS " + TableReleve.TABLE_RELEVE + " ("
            + TableReleve.ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT, "
            + TableReleve.NOM_FORMULAIRE + TEXT_TYPE + COMMA_SEP
            + TableReleve.URL + TEXT_TYPE + COMMA_SEP
            + TableReleve.DATE_REALISATION + TEXT_TYPE + COMMA_SEP
            + TableReleve.ID_FORMULAIRE + INTEGER_TYPE + COMMA_SEP
            + TableReleve.IS_SENT + INTEGER_TYPE + COMMA_SEP
            + TableReleve.IS_URL_FORMULAIRE + INTEGER_TYPE + COMMA_SEP
            + TableReleve.IS_URL_CHAMPS + INTEGER_TYPE + COMMA_SEP
            + TableReleve.IS_URL_RELEVE + INTEGER_TYPE + COMMA_SEP
            + TableReleve.STATUT + INTEGER_TYPE + COMMA_SEP
            + TableReleve.TITRE + TEXT_TYPE + COMMA_SEP
            + TableReleve.ID_MODULE + INTEGER_TYPE + " )" ;

    public static final String DELETE_TABLE_RELEVE = "DROP TABLE IF EXISTS " + TableReleve.TABLE_RELEVE;

    // Table ville
    public static abstract class TableVille implements BaseColumns {
        public static final String TABLE_VILLE = "ville";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 1;
        public static final String CP = "cp";
        public static final int NUM_CP = 2;
    }

    public static final String CREATE_TABLE_VILLE = "CREATE TABLE IF NOT EXISTS " + TableVille.TABLE_VILLE + " ("
            + TableVille.ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT, "
            + TableVille.NOM + TEXT_TYPE + COMMA_SEP
            + TableVille.CP + TEXT_TYPE + " )" ;

    public static final String DELETE_TABLE_VILLE = "DROP TABLE IF EXISTS " + TableVille.TABLE_VILLE;

    // Table rue
    public static abstract class TableRue implements BaseColumns {
        public static final String TABLE_RUE = "rue";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 1;
        public static final String ID_VILLE = "id_ville";
        public static final int NUM_ID_VILLE = 2;
    }

    public static final String CREATE_TABLE_RUE = "CREATE TABLE IF NOT EXISTS " + TableRue.TABLE_RUE + " ("
            + TableRue.ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT, "
            + TableRue.NOM + TEXT_TYPE + COMMA_SEP
            + TableRue.ID_VILLE + INTEGER_TYPE + " )" ;

    public static final String DELETE_TABLE_RUE = "DROP TABLE IF EXISTS " + TableRue.TABLE_RUE;

    // Table ReponseBDD
    public static abstract class TableReponse implements BaseColumns {
        public static final String TABLE_NAME = "reponse";
        public static final String ID_REPONSE = "id_reponse";
        public static final int NUM_ID_REPONSE = 0;
        public static final String ID_RELEVE = "id_releve";
        public static final int NUM_ID_RELEVE = 1;
        public static final String ID_CHAMP = "id_champ";
        public static final int NUM_ID_CHAMP = 2;
        public static final String ID_TYPE = "id_type";
        public static final int NUM_ID_TYPE = 3;
        public static final String INFO_BOUCLE = "info_boucle";
        public static final int NUM_INFO_BOUCLE = 4;
        public static final String VALEUR = "valeur";
        public static final int NUM_VALEUR = 5;
        public static final String POSITION = "position";
        public static final int NUM_POSITION = 6;
        public static final String IS_SENT = "is_sent";
        public static final int NUM_IS_SENT = 7;
    }

    public static final String CREATE_TABLE_REPONSE = "CREATE TABLE IF NOT EXISTS " + TableReponse.TABLE_NAME + " ("
            + TableReponse.ID_REPONSE + INTEGER_TYPE + " PRIMARY KEY, "
            + TableReponse.ID_RELEVE + INTEGER_TYPE + COMMA_SEP
            + TableReponse.ID_CHAMP + INTEGER_TYPE + COMMA_SEP
            + TableReponse.ID_TYPE + INTEGER_TYPE + COMMA_SEP
            + TableReponse.INFO_BOUCLE + TEXT_TYPE + COMMA_SEP
            + TableReponse.VALEUR + TEXT_TYPE + COMMA_SEP
            + TableReponse.POSITION + INTEGER_TYPE + COMMA_SEP
            + TableReponse.IS_SENT + INTEGER_TYPE + " )" ;

    public static final String DELETE_TABLE_REPONSE = "DROP TABLE IF EXISTS " + TableReponse.TABLE_NAME;

    // Table Info_boucle
    public static abstract class TableInfoBoucle implements BaseColumns {
        public static final String TABLE_NAME = "info_boucle";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String ID_RELEVE = "id_releve";
        public static final int NUM_ID_RELEVE = 1;
        public static final String INFO_BOUCLE = "info_boucle";
        public static final int NUM_INFO_BOUCLE = 2;
        public static final String ITERATION_BOUCLE_REP = "iteration_boucle_rep";
        public static final int NUM_ITERATION_BOUCLE_REP = 3;
    }

    public static final String CREATE_TABLE_INFO_BOUCLE = "CREATE TABLE IF NOT EXISTS " + TableInfoBoucle.TABLE_NAME + " ("
            + TableInfoBoucle.ID + INTEGER_TYPE + " PRIMARY KEY, "
            + TableInfoBoucle.ID_RELEVE + INTEGER_TYPE + COMMA_SEP
            + TableInfoBoucle.INFO_BOUCLE + TEXT_TYPE + COMMA_SEP
            + TableInfoBoucle.ITERATION_BOUCLE_REP + INTEGER_TYPE + " )" ;

    public static final String DELETE_TABLE_INFO_BOUCLE = "DROP TABLE IF EXISTS " + TableInfoBoucle.TABLE_NAME;

    // Table photo
    public static abstract class TablePhoto implements BaseColumns {
        public static final String TABLE_NAME = "photo";
        public static final String ID_PHOTO = "id_photo";
        public static final int NUM_ID_PHOTO = 0;
        public static final String ID_RELEVE = "id_releve";
        public static final int NUM_ID_RELEVE = 1;
        public static final String LIEN = "lien";
        public static final int NUM_LIEN = 2;
        public static final String COMMENTAIRES = "commentaires";
        public static final int NUM_COMMENTAIRES = 3;
        public static final String DATE = "date";
        public static final int NUM_DATE = 4;
    }

    public static final String CREATE_TABLE_PHOTO = "CREATE TABLE IF NOT EXISTS " + TablePhoto.TABLE_NAME + " ("
            + TablePhoto.ID_PHOTO + INTEGER_TYPE + " PRIMARY KEY, "
            + TablePhoto.ID_RELEVE + INTEGER_TYPE + COMMA_SEP
            + TablePhoto.LIEN + TEXT_TYPE + COMMA_SEP
            + TablePhoto.COMMENTAIRES + TEXT_TYPE + COMMA_SEP
            + TablePhoto.DATE + TEXT_TYPE + " )" ;

    public static final String DELETE_TABLE_PHOTO = "DROP TABLE IF EXISTS " + TableReponse.TABLE_NAME;

    // Table usager
    public static abstract class TableUsager implements BaseColumns {
        public static final String TABLE_NAME = "usager";
        public static final String ID_USAGER = "id_usager";
        public static final int NUM_ID_USAGER = 0;
        public static final String ID_ACCOUNT = "id_account";
        public static final int NUM_ID_ACCOUNT = 1;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 2;
        public static final String DATE_MAJ = "date_maj";
        public static final int NUM_DATE_MAJ = 3;
    }

    public static final String CREATE_TABLE_USAGER = "CREATE TABLE IF NOT EXISTS " + TableUsager.TABLE_NAME + " ("
            + TableUsager.ID_USAGER + INTEGER_TYPE + " PRIMARY KEY, "
            + TableUsager.ID_ACCOUNT + INTEGER_TYPE + COMMA_SEP
            + TableUsager.NOM + TEXT_TYPE + COMMA_SEP
            + TableUsager.DATE_MAJ + TEXT_TYPE + " )" ;

    public static final String DELETE_TABLE_USAGER = "DROP TABLE IF EXISTS " + TableUsager.TABLE_NAME;

    // Table menage
    public static abstract class TableMenage implements BaseColumns {
        public static final String TABLE_NAME = "menage";
        public static final String ID_MENAGE = "id_menage";
        public static final int NUM_ID_MENAGE = 0;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 1;
        public static final String PRENOM = "prenom";
        public static final int NUM_PRENOM = 2;
        public static final String EMAIL = "email";
        public static final int NUM_EMAIL = 3;
        public static final String NB_HABITATS = "nb_habitats";
        public static final int NUM_NB_HABITATS = 4;
        public static final String REFERENCE = "reference";
        public static final int NUM_REFERENCE = 5;
        public static final String ACTIF = "actif";
        public static final int NUM_ACTIF = 6;
        public static final String TELEPHONE = "telephone";
        public static final int NUM_TELEPHONE = 7;
        public static final String CIVILITE = "civilite";
        public static final int NUM_CIVILITE = 8;
        public static final String HABITAT_ID = "habitat_id";
        public static final int NUM_HABITAT_ID = 7;
    }

    public static final String CREATE_TABLE_MENAGE = "CREATE TABLE IF NOT EXISTS " + TableMenage.TABLE_NAME + " ("
            + TableMenage.ID_MENAGE + INTEGER_TYPE + " PRIMARY KEY, "
            + TableMenage.NOM + TEXT_TYPE + COMMA_SEP
            + TableMenage.PRENOM + TEXT_TYPE + COMMA_SEP
            + TableMenage.EMAIL + TEXT_TYPE + COMMA_SEP
            + TableMenage.NB_HABITATS + INTEGER_TYPE + COMMA_SEP
            + TableMenage.REFERENCE + TEXT_TYPE + COMMA_SEP
            + TableMenage.ACTIF + INTEGER_TYPE + COMMA_SEP
            + TableMenage.TELEPHONE + TEXT_TYPE + COMMA_SEP
            + TableMenage.CIVILITE + TEXT_TYPE + COMMA_SEP
            + TableMenage.HABITAT_ID + INTEGER_TYPE + " )" ;

    public static final String DELETE_TABLE_MENAGE = "DROP TABLE IF EXISTS " + TableMenage.TABLE_NAME;

    // Table local
    public static abstract class TableLocal implements BaseColumns {
        public static final String TABLE_NAME = "local";
        public static final String ID_LOCAL = "id_local";
        public static final int NUM_ID_LOCAL = 0;
        public static final String HABITAT_ID = "habitat_id";
        public static final int NUM_HABITAT_ID = 1;
        public static final String MENAGE_ID = "menage_id";
        public static final int NUM_MENAGE_ID = 2;
        public static final String LOT = "lot";
        public static final int NUM_LOT = 3;
        public static final String INVARIANT_DFIP = "invariant_dfip";
        public static final int NUM_INVARIANT_DFIP = 4;
        public static final String IDENTIFIANT_INTERNE = "identidiant_interne";
        public static final int NUM_IDENTIFIANT_INTERNE = 5;
        public static final String BATIMENT = "batiment";
        public static final int NUM_BATIMENT = 6;
        public static final String ETAGE_PORTE = "etage_porte";
        public static final int NUM_ETAGE_PORTE = 7;
    }

    public static final String CREATE_TABLE_LOCAL = "CREATE TABLE IF NOT EXISTS " + TableLocal.TABLE_NAME + " ("
            + TableLocal.ID_LOCAL + INTEGER_TYPE + " PRIMARY KEY, "
            + TableLocal.HABITAT_ID + INTEGER_TYPE + COMMA_SEP
            + TableLocal.MENAGE_ID + INTEGER_TYPE + COMMA_SEP
            + TableLocal.LOT + TEXT_TYPE + COMMA_SEP
            + TableLocal.INVARIANT_DFIP + TEXT_TYPE + COMMA_SEP
            + TableLocal.IDENTIFIANT_INTERNE + TEXT_TYPE + COMMA_SEP
            + TableLocal.BATIMENT + TEXT_TYPE + COMMA_SEP
            + TableLocal.ETAGE_PORTE + TEXT_TYPE + " )" ;

    public static final String DELETE_TABLE_LOCAL = "DROP TABLE IF EXISTS " + TableLocal.TABLE_NAME;

    // Table habitat
    public static abstract class TableHabitat implements BaseColumns {
        public static final String TABLE_NAME = "habitat";
        public static final String ID_HABITAT = "id_habitat";
        public static final int NUM_ID_HABITAT = 0;
        public static final String ADRESSE = "adresse";
        public static final int NUM_ADRESSE = 1;
        public static final String CP = "cp";
        public static final int NUM_CP = 2;
        public static final String VILLE = "ville";
        public static final int NUM_VILLE = 3;
        public static final String NB_LGT = "nb_lgt";
        public static final int NUM_NB_LGT = 4;
        public static final String NB_HABITANT = "nb_habitat";
        public static final int NUM_NB_HABITANT = 5;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 6;
        public static final String REFERENCE = "reference";
        public static final int NUM_REFERENCE = 7;
        public static final String COORDONNEES_X = "coordonnees_x";
        public static final int NUM_COORDONNEES_X = 8;
        public static final String COORDONNEES_Y = "coordonnees_y";
        public static final int NUM_COORDONNEES_Y = 9;
        public static final String COMPLEMENT = "complement";
        public static final int NUM_COMPLEMENT = 10;
        public static final String DERNIER_MAJ = "dernier_maj";
        public static final int NUM_DERNIER_MAJ = 11;
        public static final String NUMERO = "numero";
        public static final int NUM_NUMERO = 12;
        public static final String IS_ACTIF = "is_actif";
        public static final int NUM_IS_ACTIF = 13;
        public static final String ACTIVITES = "activites";
        public static final int NUM_ACTIVITES = 14;
        public static final String ADRESSE_2 = "adresse_2";
        public static final int NUM_ADRESSE_2 = 15;
        public static final String REMARQUE = "remarque";
        public static final int NUM_REMARQUE = 16;
        public static final String ID_TYPE_HABITAT = "id_type_habitat";
        public static final int NUM_ID_TYPE_HABITAT = 17;
    }

    public static final String CREATE_TABLE_HABITAT = "CREATE TABLE IF NOT EXISTS " + TableHabitat.TABLE_NAME + " ("
            + TableHabitat.ID_HABITAT + INTEGER_TYPE + " PRIMARY KEY, "
            + TableHabitat.ADRESSE + TEXT_TYPE + COMMA_SEP
            + TableHabitat.CP + TEXT_TYPE + COMMA_SEP
            + TableHabitat.VILLE + TEXT_TYPE + COMMA_SEP
            + TableHabitat.NB_LGT + INTEGER_TYPE + COMMA_SEP
            + TableHabitat.NB_HABITANT + INTEGER_TYPE + COMMA_SEP
            + TableHabitat.NOM + TEXT_TYPE + COMMA_SEP
            + TableHabitat.REFERENCE + TEXT_TYPE + COMMA_SEP
            + TableHabitat.COORDONNEES_X + TEXT_TYPE + COMMA_SEP
            + TableHabitat.COORDONNEES_Y + TEXT_TYPE + COMMA_SEP
            + TableHabitat.COMPLEMENT + TEXT_TYPE + COMMA_SEP
            + TableHabitat.DERNIER_MAJ + TEXT_TYPE + COMMA_SEP
            + TableHabitat.NUMERO + TEXT_TYPE + COMMA_SEP
            + TableHabitat.IS_ACTIF + INTEGER_TYPE + COMMA_SEP
            + TableHabitat.ACTIVITES + TEXT_TYPE + COMMA_SEP
            + TableHabitat.ADRESSE_2 + TEXT_TYPE + COMMA_SEP
            + TableHabitat.REMARQUE + TEXT_TYPE + COMMA_SEP
            + TableHabitat.ID_TYPE_HABITAT + INTEGER_TYPE + " )" ;

    public static final String DELETE_TABLE_HABITAT = "DROP TABLE IF EXISTS " + TableHabitat.TABLE_NAME;

    // Table contact
    public static abstract class TableContact implements BaseColumns {
        public static final String TABLE_NAME = "contact";
        public static final String ID_CONTACT = "id_contact";
        public static final int NUM_ID_CONTACT = 0;
        public static final String CIVILITE = "civilite";
        public static final int NUM_CIVILITE = 1;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 2;
        public static final String PRENOM = "prenom";
        public static final int NUM_PRENOM = 3;
        public static final String TELEPHONE = "telephone";
        public static final int NUM_TELEPHONE = 4;
        public static final String ADRESSE = "adresse";
        public static final int NUM_ADRESSE = 5;
        public static final String CP = "cp";
        public static final int NUM_CP = 6;
        public static final String VILLE = "ville";
        public static final int NUM_VILLE = 7;
        public static final String EMAIL = "email";
        public static final int NUM_EMAIL = 8;
        public static final String IS_MULTI = "is_multi";
        public static final int NUM_IS_MULTI = 9;
        public static final String TYPE = "type";
        public static final int NUM_TYPE = 10;
        public static final String RAISON_SOCIALE = "raison_sociale";
        public static final int NUM_RAISON_SOCIALE = 11;
        public static final String NUMERO = "numero";
        public static final int NUM_NUMERO = 12;
        public static final String COMPLEMENT = "complement";
        public static final int NUM_COMPLEMENT = 13;
        public static final String RUE = "rue";
        public static final int NUM_RUE = 14;
        public static final String ID_TYPE_CONTACT = "id_type_contact";
        public static final int NUM_ID_TYPE_CONTACT = 15;
        public static final String ACTIVITE = "activite";
        public static final int NUM_ACTIVITE = 16;
        public static final String IS_ACTIF = "is_actif";
        public static final int NUM_IS_ACTIF = 17;
    }

    public static final String CREATE_TABLE_CONTACT = "CREATE TABLE IF NOT EXISTS " + TableContact.TABLE_NAME + " ("
            + TableContact.ID_CONTACT + INTEGER_TYPE + " PRIMARY KEY, "
            + TableContact.CIVILITE + TEXT_TYPE + COMMA_SEP
            + TableContact.NOM + TEXT_TYPE + COMMA_SEP
            + TableContact.PRENOM + TEXT_TYPE + COMMA_SEP
            + TableContact.TELEPHONE + TEXT_TYPE + COMMA_SEP
            + TableContact.ADRESSE + TEXT_TYPE + COMMA_SEP
            + TableContact.CP + TEXT_TYPE + COMMA_SEP
            + TableContact.VILLE + TEXT_TYPE + COMMA_SEP
            + TableContact.EMAIL + TEXT_TYPE + COMMA_SEP
            + TableContact.IS_MULTI + INTEGER_TYPE + COMMA_SEP
            + TableContact.TYPE + TEXT_TYPE + COMMA_SEP
            + TableContact.RAISON_SOCIALE + TEXT_TYPE + COMMA_SEP
            + TableContact.NUMERO + TEXT_TYPE + COMMA_SEP
            + TableContact.COMPLEMENT + TEXT_TYPE + COMMA_SEP
            + TableContact.RUE + TEXT_TYPE + COMMA_SEP
            + TableContact.ID_TYPE_CONTACT + INTEGER_TYPE + COMMA_SEP
            + TableContact.ACTIVITE + TEXT_TYPE + COMMA_SEP
            + TableContact.IS_ACTIF + INTEGER_TYPE + " )" ;

    public static final String DELETE_TABLE_CONTACT = "DROP TABLE IF EXISTS " + TableContact.TABLE_NAME;

    // Table type_contact
    public static abstract class TableTypeContact implements BaseColumns {
        public static final String TABLE_NAME = "type_contact";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String NOM = "nom";
        public static final int NUM_NOM = 1;
    }

    public static final String CREATE_TABLE_TYPE_CONTACT = "CREATE TABLE IF NOT EXISTS " + TableTypeContact.TABLE_NAME + " ("
            + TableTypeContact.ID + INTEGER_TYPE + " PRIMARY KEY, "
            + TableTypeContact.NOM + TEXT_TYPE + " )" ;

    public static final String DELETE_TABLE_TYPE_CONTACT = "DROP TABLE IF EXISTS " + TableTypeContact.TABLE_NAME;

    // Table type_habitat
    public static abstract class TableTypeHabitat implements BaseColumns {
        public static final String TABLE_NAME = "type_habitat";
        public static final String ID = "id";
        public static final int NUM_ID = 0;
        public static final String TYPE = "type";
        public static final int NUM_TYPE = 1;
    }

    public static final String CREATE_TABLE_TYPE_HABITAT = "CREATE TABLE IF NOT EXISTS " + TableTypeHabitat.TABLE_NAME + " ("
            + TableTypeHabitat.ID + INTEGER_TYPE + " PRIMARY KEY, "
            + TableTypeHabitat.TYPE + TEXT_TYPE + " )" ;

    public static final String DELETE_TABLE_TYPE_HABITAT = "DROP TABLE IF EXISTS " + TableTypeHabitat.TABLE_NAME;

    // Table habitat_contact
    public static abstract class TableHabitatContact implements BaseColumns {
        public static final String TABLE_NAME = "habitat_contact";
        public static final String ID_HABITAT = "id_habitat";
        public static final int NUM_ID_HABITAT = 0;
        public static final String ID_CONTACT = "id_contact";
        public static final int NUM_ID_CONTACT = 1;
        public static final String IS_PRINCIPAL = "is_principal";
        public static final int NUM_IS_PRINCIPAL = 2;
    }

    public static final String CREATE_TABLE_HABITAT_CONTACT = "CREATE TABLE IF NOT EXISTS " + TableHabitatContact.TABLE_NAME + " ("
            + TableHabitatContact.ID_HABITAT + INTEGER_TYPE + COMMA_SEP
            + TableHabitatContact.ID_CONTACT + INTEGER_TYPE + COMMA_SEP
            + TableHabitatContact.IS_PRINCIPAL + INTEGER_TYPE + " )" ;

    public static final String DELETE_TABLE_HABITAT_CONTACT = "DROP TABLE IF EXISTS " + TableHabitatContact.TABLE_NAME;

    // Table usager_habitat
    public static abstract class TableUsagerHabitat implements BaseColumns {
        public static final String TABLE_NAME = "usager_habitat";
        public static final String ID_USAGER = "id_usager";
        public static final int NUM_ID_USAGER = 0;
        public static final String ID_HABITAT = "id_habitat";
        public static final int NUM_ID_HABITAT = 1;
    }

    public static final String CREATE_TABLE_USAGER_HABITAT = "CREATE TABLE IF NOT EXISTS " + TableUsagerHabitat.TABLE_NAME + " ("
            + TableUsagerHabitat.ID_USAGER + INTEGER_TYPE + COMMA_SEP
            + TableUsagerHabitat.ID_HABITAT + INTEGER_TYPE + " )" ;

    public static final String DELETE_TABLE_USAGER_HABITAT = "DROP TABLE IF EXISTS " + TableUsagerHabitat.TABLE_NAME;

    // Table usager_menage
    public static abstract class TableUsagerMenage implements BaseColumns {
        public static final String TABLE_NAME = "usager_menage";
        public static final String ID_USAGER = "id_usager";
        public static final int NUM_ID_USAGER = 0;
        public static final String ID_MENAGE = "id_menage";
        public static final int NUM_ID_MENAGE = 1;
    }

    public static final String CREATE_TABLE_USAGER_MENAGE = "CREATE TABLE IF NOT EXISTS " + TableUsagerMenage.TABLE_NAME + " ("
            + TableUsagerMenage.ID_USAGER + INTEGER_TYPE + COMMA_SEP
            + TableUsagerMenage.ID_MENAGE + INTEGER_TYPE + " )" ;

    public static final String DELETE_TABLE_USAGER_MENAGE = "DROP TABLE IF EXISTS " + TableUsagerMenage.TABLE_NAME;


    //************************************************************************************************************************************
    public DecheterieDatabase(Context context) {
        super(context, context.getString(R.string.database_name), null, context.getResources().getInteger(R.integer.database_version));
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(CREATE_TABLE_USERS);
        //db.execSQL(CREATE_TABLE_MODULES);
        //db.execSQL(CREATE_TABLE_PAV);
        //db.execSQL(CREATE_TABLE_RELEVE);
        //db.execSQL(CREATE_TABLE_VILLE);
        //db.execSQL(CREATE_TABLE_RUE);
        //db.execSQL(CREATE_TABLE_PHOTO);
        //db.execSQL(CREATE_TABLE_REPONSE);
        //db.execSQL(CREATE_TABLE_INFO_BOUCLE);
        db.execSQL(CREATE_TABLE_DCH_DECHETERIE);
        db.execSQL(CREATE_TABLE_ICON);
        db.execSQL(CREATE_TABLE_DCH_FLUX);
        db.execSQL(CREATE_TABLE_DCH_DECHETERIE_FLUX);
        db.execSQL(CREATE_TABLE_DCH_COMPTE_PREPAYE);
        db.execSQL(CREATE_TABLE_DCH_DEPOT);
        db.execSQL(CREATE_TABLE_DCH_APPORT_FLUX);
        db.execSQL(CREATE_TABLE_USAGER);
        db.execSQL(CREATE_TABLE_DCH_COMPTE_PREPAYE);
        db.execSQL(CREATE_TABLE_DCH_CARTE_ETAT_RAISON);
        db.execSQL(CREATE_TABLE_DCH_CARTE_ACTIVE);
        db.execSQL(CREATE_TABLE_DCH_CARTE);
        db.execSQL(CREATE_TABLE_MENAGE);
        db.execSQL(CREATE_TABLE_TYPE_CONTACT);
        db.execSQL(CREATE_TABLE_TYPE_HABITAT);
        db.execSQL(CREATE_TABLE_CONTACT);
        db.execSQL(CREATE_TABLE_HABITAT);
        db.execSQL(CREATE_TABLE_HABITAT_CONTACT);
        db.execSQL(CREATE_TABLE_USAGER_HABITAT);
        db.execSQL(CREATE_TABLE_USAGER_MENAGE);
        db.execSQL(CREATE_TABLE_LOCAL);
        db.execSQL(CREATE_TABLE_DCH_CHOIX_DECOMPTE_TOTAL);
        db.execSQL(CREATE_TABLE_DCH_ACCOUNT_SETTING);
        db.execSQL(CREATE_TABLE_DCH_ACCOUNT_FLUX_SETTING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(DELETE_TABLE_USERS);
        //db.execSQL(DELETE_TABLE_MODULES);
        //db.execSQL(DELETE_TABLE_PAV);
        //db.execSQL(DELETE_TABLE_RELEVE);
        //db.execSQL(DELETE_TABLE_VILLE);
        //db.execSQL(DELETE_TABLE_RUE);
        //db.execSQL(CREATE_TABLE_INFO_BOUCLE);
        //db.execSQL(CREATE_TABLE_PHOTO);
        //db.execSQL(DELETE_TABLE_REPONSE);
        db.execSQL(DELETE_TABLE_DCH_DECHETERIE);
        db.execSQL(DELETE_TABLE_ICON);
        db.execSQL(DELETE_TABLE_DCH_FLUX);
        db.execSQL(DELETE_TABLE_DCH_DECHETERIE_FLUX);
        db.execSQL(DELETE_TABLE_DCH_COMPTE_PREPAYE);
        db.execSQL(DELETE_TABLE_DCH_DEPOT);
        db.execSQL(DELETE_TABLE_DCH_APPORT_FLUX);
        db.execSQL(DELETE_TABLE_USAGER);
        db.execSQL(DELETE_TABLE_DCH_COMPTE_PREPAYE);
        db.execSQL(DELETE_TABLE_DCH_CARTE_ETAT_RAISON);
        db.execSQL(DELETE_TABLE_DCH_CARTE_ACTIVE);
        db.execSQL(DELETE_TABLE_DCH_CARTE);
        db.execSQL(DELETE_TABLE_MENAGE);
        db.execSQL(DELETE_TABLE_HABITAT);
        db.execSQL(DELETE_TABLE_CONTACT);
        db.execSQL(DELETE_TABLE_TYPE_CONTACT);
        db.execSQL(DELETE_TABLE_TYPE_HABITAT);
        db.execSQL(DELETE_TABLE_HABITAT_CONTACT);
        db.execSQL(DELETE_TABLE_USAGER_HABITAT);
        db.execSQL(DELETE_TABLE_USAGER_MENAGE);
        db.execSQL(DELETE_TABLE_LOCAL);
        db.execSQL(DELETE_TABLE_DCH_CHOIX_DECOMPTE_TOTAL);
        db.execSQL(DELETE_TABLE_DCH_ACCOUNT_SETTING);
        db.execSQL(DELETE_TABLE_DCH_ACCOUNT_FLUX_SETTING);
        onCreate(db);
    }

}
