package com.jatin.TeleMarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class FeedBack extends AppCompatActivity {
    Button button;
    RadioButton genderradioButton;
    RadioButton genderradioButton1;
    RadioGroup radioGroup;
    RadioGroup radioGroup1;
    boolean gd1 = false;
    boolean gd2 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        radioGroup=(RadioGroup)findViewById(R.id.groupradio);
        radioGroup1=(RadioGroup)findViewById(R.id.groupradio1);
        Intent intent = getIntent();
        String Cid = intent.getStringExtra("Cid");
        String UName = intent.getStringExtra("UName");
        String UserId = intent.getStringExtra("UserId");
        String UserName = intent.getStringExtra("UserName");
        ((Button)findViewById(R.id.button4)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                int selectedId1 = radioGroup1.getCheckedRadioButtonId();
                RadioButton genderradioButton = (RadioButton) findViewById(selectedId);
                if(selectedId==-1){
//                    Toast.makeText(FeedBack.this,"Nothing selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    gd1 = true;
//                    Toast.makeText(FeedBack.this,genderradioButton.getText(), Toast.LENGTH_SHORT).show();
                }
                RadioButton genderradioButton1 = (RadioButton) findViewById(selectedId1);
                if(selectedId1==-1){
//                    Toast.makeText(FeedBack.this,"Nothing selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    gd2 = true;
//                    Toast.makeText(FeedBack.this,genderradioButton1.getText(), Toast.LENGTH_SHORT).show();
                }
                String interest = genderradioButton.getText().toString();
                String details = genderradioButton1.getText().toString();
                String urlApi = ""+Cid;
                if(gd1 && gd2){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlApi,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    System.out.println(response.trim());
                                    Toast.makeText(FeedBack.this, response.trim(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println(error.toString());
                                    Toast.makeText(FeedBack.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String,String>();
                            params.put("interest", String.valueOf(interest));
                            params.put("details", String.valueOf(details));
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(FeedBack.this);
                    requestQueue.add(stringRequest);
                    Intent intent = new Intent(FeedBack.this,MainActivity2.class);
                    intent.putExtra("UserName",UserName); //User Name
                    intent.putExtra("UserId",UserId); //User ID
                    intent.putExtra("UName",UName);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(FeedBack.this,"Fill Form", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}