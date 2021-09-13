package com.liujennifer.p3l03liujennifer_sharedpreference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    String TAG = "com.liujennifer.p3l03liujennifer_sharedpreferences";
    Button bRight, bLeft;
    TextView tLeft, tRight;
    SeekBar seekBar;
    TextView[] views;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ConstraintLayout layout;
    long startTime, clicks;
    float cPS;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bRight = findViewById(R.id.bottomright_button);
        bLeft = findViewById(R.id.bottomleft_button);
        tLeft = findViewById(R.id.topleft_textview);
        tRight = findViewById(R.id.topright_textview);
        seekBar = findViewById(R.id.seekbar);
        views = new TextView[]{bRight, bLeft, tRight, tLeft};
        layout = findViewById(R.id.activity_main_Layout);
        bRight.setOnClickListener(this); 
        bLeft.setOnClickListener(this); 
        tLeft.setOnClickListener(this); 
        tRight.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(TAG, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int lastProgress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                for(TextView x:views) {
                    x.setTextSize(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { //record state
                lastProgress = seekBar.getProgress();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { //pop snackbar
                Snackbar snackbar = Snackbar.make(layout, "Font Size Changed To " + seekBar.getProgress() + "sp", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        seekBar.setProgress(lastProgress);
                        for (TextView x:views) {x.setTextSize(lastProgress);}
                        Snackbar.make(layout, "Font Size Reversed Back To " + lastProgress + "sp", Snackbar.LENGTH_LONG);
                    }
                });
                snackbar.setActionTextColor(Color.BLUE);
                View snackBarView = snackbar.getView();
                TextView textView = snackBarView.findViewById(R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);

                snackbar.show();

            }
        });
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                editor.clear().apply();
                setInitialValues();
                return false;
            }
        });
        setInitialValues();
        startTime = System.currentTimeMillis();


    }

    private void setInitialValues() {
        for(TextView x:views) {
            x.setText(sharedPreferences.getString(x.getTag().toString(), "0"));
        }
        seekBar.setProgress(30);

    }

    @Override
    public void onClick(View view) {
        /*if(view instanceof Button) {  you don't actually need this because button is under textview
            Button x = (Button) view;
            x.setText(""+(Integer.parseInt(x.getText().toString())+1));
        }
        else {*/
            TextView x = (TextView)view;
            x.setText(""+(Integer.parseInt(x.getText().toString())+1));
            editor.putString(x.getTag().toString(), x.getText().toString()).apply();
            cPS = ++clicks/((System.currentTimeMillis()-startTime)/1000f);
            Toast.makeText(this, ""+cPS, Toast.LENGTH_SHORT).show();



        //}
    }

    @Override
    protected void onResume() {
        super.onResume();
        setInitialValues();
    }
}