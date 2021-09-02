package com.liujennifer.p3l03liujennifer_sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button incrementButton;
    TextView greetingDisplay;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TextView textView = new TextView(this);
        //textView.setText(R.string.greet_dad);
        //setContentView(textView);
        setContentView(R.layout.activity_main);
        incrementButton = findViewById(R.id.toprightbutton);
        greetingDisplay = findViewById(R.id.greetingtextview);
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("incrimenting: " + ++count);
                Log.i("incrementing",""+count);
                greetingDisplay.setText(""+count);
            }
        });

    }

    public void decrement(View view) {
        System.out.println("decrimenting: " + ++count);
        Log.i("decrementing", ""+count);
        greetingDisplay.setText(""+count);
    }

}