package com.kotlin.base.widgets.font;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class RegularButton extends Button {

    public RegularButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RegularButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegularButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = getTypeface();
        int style = Typeface.NORMAL;
        if (tf != null) {
            style = tf.getStyle();
        }
        Typeface newTypeface = Typeface.createFromAsset(getContext().getAssets(), "DIN-Regular.otf");
        setTypeface(newTypeface, style);

    }
}