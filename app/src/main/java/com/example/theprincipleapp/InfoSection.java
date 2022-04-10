package com.example.theprincipleapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;


public class InfoSection extends ConstraintLayout {
    TextView section, value;
    ImageView icon;

    public InfoSection(Context context) {
        super(context);
        init(context, null);
    }
    public InfoSection(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public InfoSection(Context context, AttributeSet attrs, int dsa) {
        super(context, attrs, dsa);
        init(context, attrs);
    }

    public void init (Context context, AttributeSet attrs) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(
                R.layout.card_infosection,
                this, false
        );
        v.setId(getChildCount());
        addView(v);
        section = findViewById(R.id.info_section);
        value = findViewById(R.id.info_value);
        icon = findViewById(R.id.info_icon);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.InfoSection, 0, 0
        );
        section.setText(a.getString(R.styleable.InfoSection_sectionName));
        value.setText(a.getString(R.styleable.InfoSection_sectionValue));
        icon.setForeground(a.getDrawable(R.styleable.InfoSection_sectionIcon));
        a.recycle();
    }

    public void setValue (String s) {
        value.setText(s);
    }

    public String getValue () {
        return value.getText().toString();
    }
}
