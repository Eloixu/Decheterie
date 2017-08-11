package fr.trackoe.decheterie.service.cache;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Vincent
 * Date: 28/05/13
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */

/**
 * Chaque element du URCache est lié à une instance de ElementDownloadAndExpirationDelay qui enregistre la date de son dernier telechargement ainsi que sa duree de validite
 */
public class ElementDownloadAndExpirationDelay implements Serializable {

    private long _downloadDate = 0;
    private long _expirationDelay = 0;

    /**
     * Chaque element du cache est lié à une instance de ElementDownloadAndExpirationDelay qui enregistre la date de son dernier telechargement ainsi que sa duree de validite
     *
     * @param downloadDate    la date de la derniere mise à jour de l'element exprime en temps Unix
     * @param expirationDelay la duree de l'expiration de l'element exprime en ms
     */
    public ElementDownloadAndExpirationDelay(long downloadDate, long expirationDelay) {
        this._downloadDate = downloadDate;
        this._expirationDelay = expirationDelay;
    }

    /**
     * Renvoi la date de la derniere mise à jour de l'element
     *
     * @return la date de la derniere mise à jour exprime en temps Unix
     */
    public long getDownloadDate() {
        return _downloadDate;
    }

    /**
     * Renvoi la duree de l'expiration de l'element
     *
     * @return la duree de l'expiration exprime en ms
     */
    public long getExpirationDelay() {
        return _expirationDelay;
    }

    /**
     * Modifie la date de la derniere mise à jour de l'element
     *
     * @param date la date de la derniere mise à jour exprime en temps Unix
     */
    public void setDownloadDate(Long date) {
        _downloadDate = date;
    }

    /**
     * Modifie le delai d'expiration de l'element
     *
     * @param delay la duree de l'expiration exprime en ms
     */
    public void setExpirationDelay(long delay) {
        _expirationDelay = delay;
    }
}
