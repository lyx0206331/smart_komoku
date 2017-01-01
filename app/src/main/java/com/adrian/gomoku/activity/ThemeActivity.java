package com.adrian.gomoku.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.adrian.gomoku.R;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

import static com.adrian.gomoku.R.id.opacitybar;
import static com.adrian.gomoku.R.id.valuebar;

public class ThemeActivity extends BaseActivity implements ColorPicker.OnColorChangedListener {

    private Button mConfirmBtn;

    private ColorPicker picker;
    private SVBar svBar;
    private OpacityBar opacityBar;
    private SaturationBar saturationBar;
    private ValueBar valueBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_theme);
        initToolbar();
        mConfirmBtn = (Button) findViewById(R.id.btn_confirm);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(MainActivity.RES_THEME);
                finish();
            }
        });

        picker = (ColorPicker) findViewById(R.id.picker);
        svBar = (SVBar) findViewById(R.id.svbar);
        opacityBar = (OpacityBar) findViewById(opacitybar);
        saturationBar = (SaturationBar) findViewById(R.id.saturationbar);
        valueBar = (ValueBar) findViewById(valuebar);

        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);

//To get the color
        picker.getColor();

//To set the old selected color u can do it like this
        picker.setOldCenterColor(picker.getColor());
// adds listener to the colorpicker which is implemented
//in the activity
        picker.setOnColorChangedListener(this);

//to turn of showing the old color
        picker.setShowOldCenterColor(false);

//adding onChangeListeners to bars
        opacityBar.setOnOpacityChangedListener(new OpacityBar.OnOpacityChangedListener() {
            @Override
            public void onOpacityChanged(int opacity) {

            }
        });
        valueBar.setOnValueChangedListener(new ValueBar.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {

            }
        });
        saturationBar.setOnSaturationChangedListener(new SaturationBar.OnSaturationChangedListener() {
            @Override
            public void onSaturationChanged(int saturation) {

            }
        });
    }

    @Override
    protected void loadData() {

    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.mipmap.btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolBarTextView.setText(R.string.theme);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_transparent, menu);
        return true;
    }

    @Override
    public void onColorChanged(int color) {

    }
}
