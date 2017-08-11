package fr.trackoe.decheterie.service.images;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * Task asynchrone pour l'affichage d'images
 *
 * @author hvaret
 */
public class DownloadVideoTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    boolean isGallery;

    public DownloadVideoTask(ImageView bmImage, boolean isGallery) {
        this.bmImage = bmImage;
        this.isGallery = isGallery;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

	/*protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
		if (!isGallery) {
			bmImage.getLayoutParams().height = bmImage.getContext().getResources().getDimensionPixelOffset(R.dimen.img_height);
			bmImage.getLayoutParams().width = bmImage.getContext().getResources().getDimensionPixelOffset(R.dimen.img_width);
			bmImage.setAdjustViewBounds(true);
		}
	}*/
}