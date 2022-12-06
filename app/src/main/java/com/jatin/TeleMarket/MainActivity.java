package com.jatin.TeleMarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private String Username = null;
    private RequestQueue mQueue;
    private String Passwordd = null;
    private String Namee = null;
    private int idd = 0;
    private Button button;
    private EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().hide();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
        button=(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed = (EditText) findViewById(R.id.userName);
                String u = ed.getText().toString();
                ed = (EditText) findViewById(R.id.passWord);
                String p = ed.getText().toString();
                String urlApi = "http://api.jatinkumawat.rf.gd/upData/login.php?unames=";
                jsonPrase(urlApi+u);
//                Toast.makeText(getApplicationContext(),urlApi+u+"-"+Username+Passwordd,Toast.LENGTH_SHORT).show();
                if(u.equals(Username) && p.equals(Passwordd)){
                    login();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Wrong Username and Password",Toast.LENGTH_SHORT).show();
                }
//                login();
            }
        });

    }

    private  void jsonPrase(String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        System.out.println("here");
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
//                            System.out.println("here");
                            for(int i = 0;i<=jsonArray.length();i++){
                                JSONObject user = jsonArray.getJSONObject(i);
                                String Username = user.getString("username").toString();
//                                System.out.println(Username);
                                String Passwordd = user.getString("password").toString();
                                String Namee = user.getString("name").toString();
                                int idd = user.getInt("id");
                                setData(Username,Passwordd,Namee,idd);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                System.out.println("Errorrrr");
                    error.printStackTrace();
            }
        });
        mQueue.add(request);

    }

    public void login() {
        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
        startActivity(intent);
    }
    public void setData(String u,String p,String n ,int l){
//        System.out.println(u+p+n+l);
        this.Username = u;
        this.Passwordd = p;
        this.Namee = n;
        this.idd = l;
    }
}