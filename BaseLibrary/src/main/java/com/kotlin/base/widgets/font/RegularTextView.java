package com.kotlin.base.widgets.font;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class RegularTextView extends TextView {

    public RegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegularTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
//        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "DIN-Regular.otf");
//        setTypeface(tf);
        Typeface tf = getTypeface();
        int style = Typeface.NORMAL;
        if (tf != null) {
            style = tf.getStyle();
        }
        Typeface newTypeface = Typeface.createFromAsset(getContext().getAssets(), "DIN-Regular.otf");
        setTypeface(newTypeface, style);

    }
}