package fr.trackoe.decheterie.service.callback;

/**
 * Created with IntelliJ IDEA. User: Vincent Date: 09/07/13 Time: 09:35
 */
public interface DataAndCacheCallback<T> extends DataCallback<T> {

    public void notInCache();

}
