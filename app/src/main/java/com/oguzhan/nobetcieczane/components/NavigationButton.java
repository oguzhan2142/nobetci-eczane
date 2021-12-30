package com.oguzhan.nobetcieczane.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import com.oguzhan.nobetcieczane.R;

public class NavigationButton extends AppCompatButton {

    private TextView distanceTextview;


    public NavigationButton(@NonNull Context context) {
        super(context);
        init();
    }

    public NavigationButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    void init() {

        Drawable d = ContextCompat.getDrawable(getContext(), R.drawable.button_shape);
        setBackground(d);


        Drawable icon = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_navigation_24);
        int color = ContextCompat.getColor(getContext(),R.color.grey);
        if (icon != null) {
            icon.setTint(color);
        }

        setCompoundDrawables(null, icon, null, null);







        setText("asd");


    }
}
