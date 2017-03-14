package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.Module;
import fr.trackoe.decheterie.model.bean.global.Modules;

/**
 * Created by Remi on 02/12/2015.
 */
public class ModulesDB extends MyDb {

    public ModulesDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un Module dans la bdd
     */
    public long insertModule(Module module) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableModules.ID_MODULE, Integer.parseInt(module.getIdModule()));
        values.put(DecheterieDatabase.TableModules.NOM, module.getNom());
        values.put(DecheterieDatabase.TableModules.FORMULAIRE_JSON, module.getFormulaire());
        values.put(DecheterieDatabase.TableModules.FORMULAIRE_VERSION, module.getVersion());
        values.put(DecheterieDatabase.TableModules.IS_FORM, module.isForm() ? 1 : 0);

        return db.insertOrThrow(DecheterieDatabase.TableModules.TABLE_MODULES, null, values);
    }

    /*
    Vider la table
     */
    public void clearModules() {
        db.execSQL("delete from " + DecheterieDatabase.TableModules.TABLE_MODULES);
    }

    public Modules getModules() {
        Modules modules = new Modules();
        String query = "SELECT * FROM " + DecheterieDatabase.TableModules.TABLE_MODULES + ";";
        Cursor cursor = db.rawQuery(query, null);
        modules.setListModules(cursorToModules(cursor));
        return modules;
    }

    public Modules getFormulaires() {
        Modules modules = new Modules();
        String query = "SELECT * FROM " + DecheterieDatabase.TableModules.TABLE_MODULES + " WHERE " + DecheterieDatabase.TableModules.IS_FORM + " = 1;";
        Cursor cursor = db.rawQuery(query, null);
        modules.setListModules(cursorToModules(cursor));
        return modules;
    }

    public Module getFormulaireById(int id) {
        Module m = new Module();
        String query = "SELECT * FROM " + DecheterieDatabase.TableModules.TABLE_MODULES + " WHERE " + DecheterieDatabase.TableModules.ID_MODULE + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        m = cursorToFormulaire(cursor);
        return m;
    }

    private Module cursorToFormulaire(Cursor c){
        Module m = new Module();
        if(c.moveToFirst()) {
            m.setIdModule(String.valueOf(c.getInt(DecheterieDatabase.TableModules.NUM_ID_MODULE)));
            m.setNom(c.getString(DecheterieDatabase.TableModules.NUM_NOM));
            m.setFormulaire(c.getString(DecheterieDatabase.TableModules.NUM_FORMULAIRE_JSON));
            m.setVersion(c.getString(DecheterieDatabase.TableModules.NUM_FORMULAIRE_VERSION));
            m.setIsForm(c.getInt(DecheterieDatabase.TableModules.NUM_IS_FORM) != 0);
        }
        return m;
    }

    private ArrayList<Module> cursorToModules(Cursor c){
        ArrayList<Module> lmodules = new ArrayList<Module>();

         if(c.moveToFirst()) {
             do {
                 Module m = new Module();
                 m.setIdModule(String.valueOf(c.getInt(DecheterieDatabase.TableModules.NUM_ID_MODULE)));
                 m.setNom(c.getString(DecheterieDatabase.TableModules.NUM_NOM));
                 m.setFormulaire(c.getString(DecheterieDatabase.TableModules.NUM_FORMULAIRE_JSON));
                 m.setVersion(c.getString(DecheterieDatabase.TableModules.NUM_FORMULAIRE_VERSION));
                 m.setIsForm(c.getInt(DecheterieDatabase.TableModules.NUM_IS_FORM) != 0);
                 lmodules.add(m);
             } while (c.moveToNext());
             c.close();
         }

        return lmodules;
    }

}
