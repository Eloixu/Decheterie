package fr.trackoe.decheterie.service.callback;

/**
 * Created with IntelliJ IDEA. User: Vincent Date: 09/07/13 Time: 09:37
 */
public interface DataAndProcessAfterFailCallback<T> extends DataCallback<T> {

    public void dataProcessAfterFailed(boolean isInternetConnected, String errorMessage);

    public void dataLoadingFailed(boolean isInternetConnected, String errorMessage);
}
