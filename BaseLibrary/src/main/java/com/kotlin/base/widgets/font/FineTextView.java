package com.kotlin.base.widgets.font;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 细汉字字体
 */
@SuppressLint("AppCompatCustomView")
public class FineTextView extends TextView {

    public FineTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FineTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface newTypeface = Typeface.createFromAsset(getContext().getAssets(),
                "thin_hanzi.ttf");
        setTypeface(newTypeface);

    }
}