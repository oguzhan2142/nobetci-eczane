package com.oguzhan.nobetcieczane.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.oguzhan.nobetcieczane.R;

public class SearchEdittext extends LinearLayout {


    private String text;

    private EditText editText;
    private ImageButton clearButton;


    public SearchEdittext(Context context) {
        super(context);
        init();
    }

    public SearchEdittext(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchEdittext(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SearchEdittext(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        Drawable parentBackground = ContextCompat.getDrawable(getContext(), R.drawable.button_shape);
        setBackground(parentBackground);
        editText = new EditText(getContext());
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                0,
                LayoutParams.WRAP_CONTENT,
                1.0f
        );
        editText.setLayoutParams(param);
        editText.setBackground(null);

        editText.setOnFocusChangeListener((view, b) -> {
                    clearButton.setVisibility(b ? VISIBLE : GONE);
                }
        );

        addView(editText);


        clearButton = new ImageButton(getContext());
        clearButton.setVisibility(GONE);
        clearButton.setBackground(null);

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_clear_24);
        clearButton.setImageDrawable(drawable);
        clearButton.setOnClickListener(v -> {
            clearText();
        });
        addView(clearButton);
    }


    public void clearText() {
        text = "";
        editText.setText(text);
        postInvalidate();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
