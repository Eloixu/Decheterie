package fr.trackoe.decheterie.ui.dialog;

/**
 * Created by Haocheng on 17/03/2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

public class CustomDialogFlux extends Dialog {

    public CustomDialogFlux(Context context) {
        super(context);
    }

    public CustomDialogFlux(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private LinearLayout dialogLinearlayoutLine1;
        private LinearLayout dialogLinearlayoutLine2;
        private LinearLayout dialogLinearlayoutLine3;
        private boolean visibilityLine1;
        private boolean visibilityLine2;
        private boolean visibilityLine3;
        private String positiveButtonText;
        private String negativeButtonText;
        private String iconName;
        private float CCPU;
        private EditText editTextQuantiteApporte;
        private String editTextQtyApporte;
        private EditText editTextQuantiteDecompte;
        private String editTextQtyDecompte;
        private TextView textViewQuantiteCalculLine3;
        private String textViewQtyCalculLine3;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;
        private String uniteApporte;
        private String uniteDecompte;

        public Builder(Context context) {
            this.context = context;
        }


        public EditText getEditTextQuantiteApporte() {
            return editTextQuantiteApporte;
        }

        public EditText getEditTextQuantiteDecompte() {
            return editTextQuantiteDecompte;
        }

        public TextView getTextViewQuantiteCalculLine3() {
            return textViewQuantiteCalculLine3;
        }


        public Builder setIconName(String iconName) {
            this.iconName = iconName;
            return this;
        }

        public Builder setEditTextQtyApporte(String editTextQtyApporte) {
            this.editTextQtyApporte = editTextQtyApporte;
            return this;
        }

        public Builder setEditTextQtyDecompte(String editTextQtyDecompte) {
            this.editTextQtyDecompte = editTextQtyDecompte;
            return this;
        }

        public Builder setTextViewQtyCalculLine3(String textViewQtyCalculLine3) {
            this.textViewQtyCalculLine3 = textViewQtyCalculLine3;
            return this;
        }

        public void setVisibilityLine1(boolean visibilityLine1) {
            this.visibilityLine1 = visibilityLine1;
        }

        public void setVisibilityLine2(boolean visibilityLine2) {
            this.visibilityLine2 = visibilityLine2;
        }

        public void setVisibilityLine3(boolean visibilityLine3) {
            this.visibilityLine3 = visibilityLine3;
        }

        public Builder setCCPU(float CCPU) {
            this.CCPU = CCPU;
            return this;
        }

        public Builder setUniteApporte(String uniteApporte) {
            this.uniteApporte = uniteApporte;
            return this;
        }

        public Builder setUniteDecompte(String uniteDecompte) {
            this.uniteDecompte = uniteDecompte;
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

        public CustomDialogFlux create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialogFlux dialog = new CustomDialogFlux(context,R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_flux_layout, null);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.dialog_flux_layout_title)).setText(title);
            editTextQuantiteApporte = ((EditText) layout.findViewById(R.id.dialog_flux_layout_quantite_apporte_editText));
            editTextQuantiteDecompte = ((EditText) layout.findViewById(R.id.dialog_flux_layout_quantite_decompte_editText));
            textViewQuantiteCalculLine3 = ((TextView) layout.findViewById(R.id.dialog_flux_layout_qty_calcul_textView));
            editTextQuantiteApporte.setText(editTextQtyApporte);
            editTextQuantiteDecompte.setText(editTextQtyDecompte);
            dialogLinearlayoutLine1 = (LinearLayout) layout.findViewById(R.id.dialog_flux_layout_line1_linearlayout);
            if(!visibilityLine1) dialogLinearlayoutLine1.setVisibility(View.GONE);
            dialogLinearlayoutLine2 = (LinearLayout) layout.findViewById(R.id.dialog_flux_layout_line2_linearlayout);
            if(!visibilityLine2) dialogLinearlayoutLine2.setVisibility(View.GONE);
            dialogLinearlayoutLine3 = (LinearLayout) layout.findViewById(R.id.dialog_flux_layout_line3_linearlayout);
            if(!visibilityLine3) dialogLinearlayoutLine3.setVisibility(View.GONE);

            // set the icon image
            if(!iconName.isEmpty() && iconName != null)
            ((ImageView) layout.findViewById(R.id.dialog_flux_layout_flux_item_imageView)).setBackgroundResource(context.getResources().getIdentifier(iconName, "drawable", context.getPackageName()));
            //set the unite apporté
            if(uniteApporte != null) {
                if(!uniteApporte.isEmpty()) {
                    ((TextView) layout.findViewById(R.id.dialog_flux_layout_unite_line1_textView)).setText(uniteApporte);
                }
            }
            //set the unite décompté
            if(uniteDecompte != null) {
                if(!uniteDecompte.isEmpty())
                    ((TextView) layout.findViewById(R.id.dialog_flux_layout_unite_line2_textView)).setText(uniteDecompte);
                    ((TextView) layout.findViewById(R.id.dialog_flux_layout_unite_line3_textView)).setText("   " + uniteDecompte);

            }
            //set textView line3
            if(textViewQtyCalculLine3 != null) ((TextView) layout.findViewById(R.id.dialog_flux_layout_qty_calcul_textView)).setText(textViewQtyCalculLine3);
            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.dialog_flux_layout_positive_button))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.dialog_flux_layout_positive_button))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.dialog_flux_layout_positive_button).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.dialog_flux_layout_negative_button))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.dialog_flux_layout_negative_button))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.dialog_flux_layout_negative_button).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.dialog_flux_layout_message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.dialog_flux_layout_content_linearLayout))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.dialog_flux_layout_content_linearLayout))
                        .addView(contentView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
            }


            TextWatcher listenerMailValide = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    float qtyApporte = 0;
                    if(editTextQuantiteApporte.getText().toString() != null && !editTextQuantiteApporte.getText().toString().isEmpty()) qtyApporte = Float.parseFloat(editTextQuantiteApporte.getText().toString());
                    float qtyCalcul = qtyApporte * CCPU;
                    textViewQuantiteCalculLine3.setText("" + qtyCalcul);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            editTextQuantiteApporte.addTextChangedListener(listenerMailValide);






            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }
    }
}


