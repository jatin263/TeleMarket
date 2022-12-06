package com.jatin.TeleMarket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity4 extends AppCompatActivity {
    public int time = 0;
    boolean ida = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        checkData();
        }
        public void btnLoopSt(){
            ida = true;
        }
    public void btnLoopSp(){
        ida = false;
    }

    void checkData(){
        while(ida){
            setContentView(R.layout.activity_main3);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(time);
            time++;
        }
    }
}