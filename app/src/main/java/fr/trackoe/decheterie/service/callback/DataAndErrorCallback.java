package fr.trackoe.decheterie.service.callback;

/**
 * Created with IntelliJ IDEA. User: Vincent Date: 09/07/13 Time: 09:37
 */
public interface DataAndErrorCallback<T> extends DataCallback<T> {

    public void dataLoadingFailed(boolean isInternetConnected, String errorMessage);
}
