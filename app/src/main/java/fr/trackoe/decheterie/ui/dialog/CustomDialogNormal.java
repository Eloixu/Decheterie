package fr.trackoe.decheterie.ui.dialog;

/**
 * Created by Haocheng on 29/03/2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.trackoe.decheterie.R;

public class CustomDialogNormal extends Dialog {
    public CustomDialogNormal(Context context) {
        super(context);
    }

    public CustomDialogNormal(Context context, int theme) {
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

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setVisibilityLeftButton(Boolean visibility) {
            this.visibilityLeftButton = visibility;
            return this;
        }

        public Builder setVisibilityRightButton(Boolean visibility) {
            this.visibilityRightButton = visibility;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }


        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setCancelable(){
            new CustomDialogNormal(context).setCanceledOnTouchOutside(false);
            return this;
        }

        public CustomDialogFlux create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialogFlux dialog = new CustomDialogFlux(context,R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_normal_layout, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
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
            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.dialog_normal_layout_message_textView)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.dialog_normal_layout_content_linearLayout))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.dialog_normal_layout_content_linearLayout))
                        .addView(contentView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
            }
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(layout);
            return dialog;
        }
    }
}