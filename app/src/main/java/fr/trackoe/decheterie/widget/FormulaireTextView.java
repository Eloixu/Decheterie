package fr.trackoe.decheterie.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Remi on 22/12/2016.
 */
public class FormulaireTextView extends TextView {
    public FormulaireTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FormulaireTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FormulaireTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans_Regular.ttf");
            setTypeface(tf);
        }
    }
}
