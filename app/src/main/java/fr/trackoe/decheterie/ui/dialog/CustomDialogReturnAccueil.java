package fr.trackoe.decheterie.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.trackoe.decheterie.R;

/**
 * Created by Haocheng on 15/06/2017.
 */

public class CustomDialogReturnAccueil extends Dialog {
    public CustomDialogReturnAccueil(Context context) {
        super(context);
    }

    public CustomDialogReturnAccueil(Context context, int theme) {
        super(context, theme);
    }


    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;
        private boolean visibilityLeftButton = true;
        private boolean visibilityRightButton = true;
        private Button btnLeft;
        private Button btnRight;
        private int widthScale;
        private int heightScale;
        private int width;
        private int height;
        private boolean isCancelableTouchOutside = true;
        private boolean isCancelable =true;

        public Builder(Context context) {
            this.context = context;
        }

        public CustomDialogReturnAccueil.Builder setVisibilityLeftButton(Boolean visibility) {
            this.visibilityLeftButton = visibility;
            return this;
        }

        public CustomDialogReturnAccueil.Builder setVisibilityRightButton(Boolean visibility) {
            this.visibilityRightButton = visibility;
            return this;
        }

        public CustomDialogReturnAccueil.Builder setWidthScale(int widthScale) {
            this.widthScale = widthScale;
            return this;
        }

        public CustomDialogReturnAccueil.Builder setHeightScale(int heightScale) {
            this.heightScale = heightScale;
            return this;
        }

        public CustomDialogReturnAccueil.Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public CustomDialogReturnAccueil.Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public CustomDialogReturnAccueil.Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        public CustomDialogReturnAccueil.Builder setCancelableTouchOutside(boolean isCancelableTouchOutside) {
            this.isCancelableTouchOutside = isCancelableTouchOutside;
            return this;
        }
        public CustomDialogReturnAccueil.Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public CustomDialogReturnAccueil.Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public CustomDialogReturnAccueil.Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public CustomDialogReturnAccueil.Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public CustomDialogReturnAccueil.Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public CustomDialogReturnAccueil.Builder setPositiveButton(int positiveButtonText,
                                                            DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public CustomDialogReturnAccueil.Builder setPositiveButton(String positiveButtonText,
                                                            DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public CustomDialogReturnAccueil.Builder setNegativeButton(int negativeButtonText,
                                                            DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public CustomDialogReturnAccueil.Builder setNegativeButton(String negativeButtonText,
                                                            DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public CustomDialogFlux create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialogFlux dialog = new CustomDialogFlux(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_normal_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            //set the width/width scale and height/height scale
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            int windowWidth = outMetrics.widthPixels;
            int windowHeight = outMetrics.heightPixels;
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            if(width != 0) params.width = (int) (windowWidth/100 * width);
            if(height != 0) params.height = (int) (windowHeight/100 * height);
            if(widthScale != 0){
                params.x = (int) (windowWidth/100 * widthScale);
            }
            if (heightScale == 0) {
                params.gravity = Gravity.CENTER;
            }
            else {
                params.gravity = Gravity.TOP;
                params.y = (int) (windowHeight/100 * heightScale);
            }
            dialog.getWindow().setAttributes(params);
            // set the dialog title
            ((TextView) layout.findViewById(R.id.dialog_normal_layout_title_textView)).setText(title);
            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.dialog_normal_layout_positive_button))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.dialog_normal_layout_positive_button))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.dialog_normal_layout_positive_button).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.dialog_normal_layout_negative_button))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.dialog_normal_layout_negative_button))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.dialog_normal_layout_negative_button).setVisibility(
                        View.GONE);
            }
            //set isCancelableTouchOutside and isCancelable
            dialog.setCanceledOnTouchOutside(isCancelableTouchOutside);
            dialog.setCancelable(isCancelable);
            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.dialog_normal_layout_message_textView)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.dialog_normal_layout_content_linearLayout))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.dialog_normal_layout_content_linearLayout))
                        .addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
