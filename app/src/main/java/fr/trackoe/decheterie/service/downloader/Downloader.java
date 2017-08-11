package fr.trackoe.decheterie.service.downloader;

import java.io.Serializable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Downloader {

    public static final String MESSENGER_NAME = "MESSENGER_NAME";

    public static void callRemoteDownloader(Context ctx, DownloaderRequest dlrequest) {
        Intent intent = new Intent(ctx, DownloadService.class);
        Bundle bundle = new Bundle();
        bundle.putString(DownloadService.URL_KEY, dlrequest.getUrl());
        bundle.putString(DownloadService.PATH_KEY, dlrequest.getFilePath());
        bundle.putString(DownloadService.CALLBACK_KEY, dlrequest.getCallback());

        if (dlrequest.getMethod() != null && dlrequest.getPostData() != null) {
            bundle.putString(DownloadService.METHOD_KEY, dlrequest.getMethod());
            bundle.putString(DownloadService.POST_DATA_KEY, dlrequest.getPostData());
        }
        if (dlrequest.getTimeOut() > 0) {
            bundle.putInt(DownloadService.TIMEOUT_KEY, dlrequest.getTimeOut());
        }
        if (dlrequest.getExtraHeaders() != null) {
            bundle.putSerializable(DownloadService.EXTRA_HEADERS_KEY, (Serializable) dlrequest.getExtraHeaders());
        }

        if (dlrequest.getLastDLDate() != -1) {
            bundle.putLong(DownloadService.LASTDLDATE_KEY, dlrequest.getLastDLDate());
        }
        intent.putExtras(bundle);
        intent.putExtra(MESSENGER_NAME, dlrequest.getMessenger());
        ctx.getApplicationContext().startService(intent);
    }

    public static void removeListener(Context ctx, DownloaderRequest dlrequest) {
        Intent intent = new Intent(ctx, DownloadService.class);
        Bundle bundle = new Bundle();
        bundle.putString(DownloadService.ACTION_KEY, "actionremove");
        bundle.putString("callbackName", dlrequest.getCallback());
        intent.putExtras(bundle);
        intent.putExtra(MESSENGER_NAME, dlrequest.getMessenger());
        ctx.startService(intent);
    }

    public static void stopService(Context ctx) {
        ctx.stopService(new Intent(ctx, DownloadService.class));

    }

}
