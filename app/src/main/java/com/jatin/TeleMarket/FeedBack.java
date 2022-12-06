package com.jatin.TeleMarket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FeedBack extends AppCompatActivity {
    Button button;
    RadioButton genderradioButton;
    RadioButton genderradioButton1;
    RadioGroup radioGroup;
    RadioGroup radioGroup1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        radioGroup=(RadioGroup)findViewById(R.id.groupradio);
        radioGroup1=(RadioGroup)findViewById(R.id.groupradio1);

        ((Button)findViewById(R.id.button4)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                int selectedId1 = radioGroup1.getCheckedRadioButtonId();
                RadioButton genderradioButton = (RadioButton) findViewById(selectedId);
                if(selectedId==-1){
                    Toast.makeText(FeedBack.this,"Nothing selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(FeedBack.this,genderradioButton.getText(), Toast.LENGTH_SHORT).show();
                }
                RadioButton genderradioButton1 = (RadioButton) findViewById(selectedId1);
                if(selectedId1==-1){
                    Toast.makeText(FeedBack.this,"Nothing selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(FeedBack.this,genderradioButton1.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}