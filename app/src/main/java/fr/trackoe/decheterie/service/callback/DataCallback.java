package fr.trackoe.decheterie.service.callback;

/**
 * User: croquette Date: 27/05/13 Time: 10:01
 */
public interface DataCallback<T> {

    /**
     * La donnée a été chargée de façon asynchrone
     *
     * @param data la donnée attendue
     */
    public void dataLoaded(T data);

}
