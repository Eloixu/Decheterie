package fr.trackoe.decheterie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import fr.trackoe.decheterie.R;

/**
 * Created by Remi on 02/12/2015.
 */
public class FormulairesDatabase extends SQLiteOpenHelper {
    private Context ctx;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
//    private static final String NUMERIC_TYPE = " NUMERIC";
    private static final String NOT_NULL = " NOT_NULL";
    private static final String COMMA_SEP = ", ";

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

    public FormulairesDatabase(Context context) {
        super(context, context.getString(R.string.database_name), null, context.getResources().getInteger(R.integer.database_version));
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_MODULES);
        db.execSQL(CREATE_TABLE_PAV);
        db.execSQL(CREATE_TABLE_RELEVE);
        db.execSQL(CREATE_TABLE_VILLE);
        db.execSQL(CREATE_TABLE_RUE);
        db.execSQL(CREATE_TABLE_PHOTO);
        db.execSQL(CREATE_TABLE_REPONSE);
        db.execSQL(CREATE_TABLE_INFO_BOUCLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_USERS);
        db.execSQL(DELETE_TABLE_MODULES);
        db.execSQL(DELETE_TABLE_PAV);
        //db.execSQL(DELETE_TABLE_RELEVE);
        db.execSQL(DELETE_TABLE_VILLE);
        db.execSQL(DELETE_TABLE_RUE);
        db.execSQL(CREATE_TABLE_INFO_BOUCLE);
        db.execSQL(CREATE_TABLE_PHOTO);
        db.execSQL(DELETE_TABLE_REPONSE);
        onCreate(db);
    }

}
