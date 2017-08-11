package fr.trackoe.decheterie.model.bean.global;

import java.io.Serializable;

/**
 * Created by Remi on 30/11/2015.
 */
public class ContenantBean implements Serializable {

    protected boolean mSuccess;
    protected String mError;

    public boolean ismSuccess() {
        return mSuccess;
    }

    public void setmSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public String getmError() {
        return mError;
    }

    public void setmError(String mError) {
        this.mError = mError;
    }
}
