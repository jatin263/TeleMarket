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

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private String Username = null;
    private RequestQueue mQueue;
    private String Passwordd = null;
    private String Namee = null;
    private String idd = null;
    private Button button;
    private EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
        button=(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uName = null;
                String uPass = null;
                ed = (EditText) findViewById(R.id.userName);
                String u = ed.getText().toString();
                ed = (EditText) findViewById(R.id.passWord);
                String p = ed.getText().toString();
                String urlApi = "https://mocki.io/v1/a2311b1e-8052-4c7b-9d22-b95b17623821";
                String url = urlApi;
                System.out.println(url);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println("Response: " + response.toString());
                                try {
                                    String Name = response.getString("name");
                                    String Password = response.getString("password");
                                    String Username = response.getString("username");
//                                    String IdU = response.getString("id");
                                    if(u.equals(Username) && p.equals(Password)){
                                        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                                        intent.putExtra("uName",Username);
//                                        intent.putExtra("uID",IdU);
                                        intent.putExtra("NameS",Name);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Wrong Username and Password",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error

                            }
                        });
//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        System.out.println("here");
//                        try {
//                            String Name = response.getString("name");
//                            String Password = response.getString("password");
//                            String Username = response.getString("username");
//                            String IdU = response.getString("id");
//                            setData(Name,Password,Username,IdU);
//                        } catch (JSONException e) {
//                            System.out.println(e.toString());
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        System.out.println("Error");
//                    }
//                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);
//                Toast.makeText(getApplicationContext(),Username+Passwordd,Toast.LENGTH_SHORT).show();

            }
        });

    }



    public void login(String u,String p) {
        if(u.equals(Username) && p.equals(Passwordd)){
            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
            intent.putExtra("uName",Username);
            intent.putExtra("uID",idd);
            intent.putExtra("NameS",Namee);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"Wrong Username and Password",Toast.LENGTH_SHORT).show();
        }
//        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
//        startActivity(intent);
    }
    public void setData(String u,String p,String n ,String l){
        System.out.println(u+p+n+l);
        this.Username = u;
        this.Passwordd = p;
        this.Namee = n;
        this.idd = l;
    }
}