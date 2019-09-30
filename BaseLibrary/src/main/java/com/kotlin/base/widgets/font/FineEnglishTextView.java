package com.kotlin.base.widgets.font;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 细英文字体
 */
@SuppressLint("AppCompatCustomView")
public class FineEnglishTextView extends TextView {

    public FineEnglishTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FineEnglishTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FineEnglishTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface newTypeface = Typeface.createFromAsset(getContext().getAssets(),
                "thin_pingyin.otf");
        setTypeface(newTypeface);

    }
}