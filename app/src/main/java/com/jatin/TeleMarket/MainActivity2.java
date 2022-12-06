package com.jatin.TeleMarket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class MainActivity2 extends AppCompatActivity {
    private TextView textView;
//    private int IdleTime = 0;
//    private AudioManager mA ;
//    int serverResponseCode = 0;
    private MediaRecorder rec = new MediaRecorder();
//    private boolean recStart;
    Customer[] customers = new Customer[100];
//    File file;
    String upLoadServerUri = null;
    String filePath = null;

    private Button button;
    public static int idd =0;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        String[] Names = new String[]{"Jatin Kumawat","Karan Kumawat","Saurabh Kumawat","Ankit Kumawat"};
        String[] Numbers = new String[]{"6375842013","7793815719","7611921283","6375842013"};
        for (int i = 0 ; i< Names.length;i++){
            customers[i] = new Customer(Names[i],new BigInteger(Numbers[i]));
        }
        setContentView(R.layout.activity_main2);
        DisplayOnPage(customers[idd]);
        button=(Button) findViewById(R.id.btnCall1);
        upLoadServerUri = "http://api.jatinkumawat.rf.gd/upData/fileUpload.php";

        ((Button)findViewById(R.id.btnCall)).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v) {
                TelephonyManager manager = (TelephonyManager) getApplicationContext().getSystemService(TELEPHONY_SERVICE);
                if(manager.getCallState()==TelephonyManager.CALL_STATE_IDLE){
                    Toast.makeText(getApplicationContext(),"Making Call Wait",Toast.LENGTH_SHORT).show();
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    String num = customers[idd].GetMobileNumber();
                    callIntent.setData(Uri.parse("tel:"+customers[idd].GetMobileNumber()));
                    startActivity(callIntent);
                    Toast.makeText(getApplicationContext(),"Rec Start",Toast.LENGTH_SHORT).show();
                    while(manager.getCallState()!=TelephonyManager.CALL_STATE_OFFHOOK){

                    }
                    String filePath=getRecordingFilePath(customers[idd]);
                    Date date = new Date();
                    CharSequence startTime = DateFormat.format("yy-MM-dd hh:mm:ss",date.getTime());
                    AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); //turn on speaker
                    if (mAudioManager != null) {
                        mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION); //MODE_IN_COMMUNICATION | MODE_IN_CALL
                        mAudioManager.setSpeakerphoneOn(true);
                        mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL), 0); // increase Volume
                        //hasWiredHeadset(mAudioManager);
                    }

                    //android.permission.RECORD_AUDIO
                    String manufacturer = Build.MANUFACTURER;
                    //Log.d(TAG, manufacturer);
                    try{
                        rec.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
                        rec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        rec.setOutputFile(filePath);
                        rec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        rec.prepare();
                       // Thread.sleep(1000);
                        rec.start();
                        Toast.makeText(getApplicationContext(),"Recording Started",Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        System.out.println(e.toString());
                    }

                    System.out.println("Rec Start");
                    Intent iIntent = new Intent(Intent.ACTION_MAIN);
                    callIntent.setData(Uri.parse("tel:"+customers[idd].GetMobileNumber()));
                    startActivity(callIntent);
                    while(manager.getCallState()!=TelephonyManager.CALL_STATE_IDLE )
                    {
                        //Toast.makeText(getApplicationContext(),"Rec Start",Toast.LENGTH_SHORT).show();
                    }
//                    TelephonyManager.CellInfoCallback()
                    rec.stop();
                    rec.release();

                    Toast.makeText(getApplicationContext(),"Now Idle",Toast.LENGTH_SHORT).show();
                    idd++;
                    DisplayOnPage(customers[idd]);
                    CharSequence endTime = DateFormat.format("yy-MM-dd hh:mm:ss",date.getTime());
                    String datee = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        datee = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
                    }
                    else{
                        datee = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
                    }
                    String urlApi = "http://api.jatinkumawat.rf.gd/upData/index.php";
                    String finalDatee = datee;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlApi,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    System.out.println(response.trim());
                                    Toast.makeText(MainActivity2.this, response.trim(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println(error.toString());
                                    Toast.makeText(MainActivity2.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String,String>();
                            params.put("CallStart", String.valueOf(startTime));
                            params.put("CallEnd", finalDatee);
                            params.put("teleNum",num);
//                            params.put("recfile",filePath);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
                    requestQueue.add(stringRequest);
                    File file22 = new File(filePath);
                    if(filePath!=null){
                        Toast.makeText(getApplicationContext(),filePath,Toast.LENGTH_SHORT).show();
                        UploadFileRec(filePath);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"File not found",Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(MainActivity2.this,FeedBack.class);
                    startActivity(intent);

                }
                else if(manager.getCallState()==TelephonyManager.CALL_STATE_OFFHOOK){
                    Toast.makeText(getApplicationContext(),"phone is busy",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void UploadFileRec(String filePath) {
        UploadTask uploadTask = new UploadTask();
        uploadTask.execute(new String[]{filePath});
    }

    public class UploadTask extends AsyncTask<String,String,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("true")){
                Toast.makeText(getApplicationContext(),"File Uploaded",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Failed Uploaded",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            if(uploadFile(strings[0])){
                return "true";
            }
            else{
                return "false";
            }
        }
        private boolean uploadFile(String path){
//            Toast.makeText(getApplicationContext(),path,Toast.LENGTH_SHORT).show();
            System.out.println(path);
            File file = new File(path);
            try{
                RequestBody requestBody;
                requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("files",file.getName(),RequestBody.create(MediaType.parse("audio/*"),file))
                        .addFormDataPart("some_key","some_value")
                        .build();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("http://api.jatinkumawat.rf.gd/upData/fileUpload.php")
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {

                    }
                });
                return true;
            }
            catch (Exception ex){
                return false;
            }
        }
    }


    void DisplayOnPage(Customer c){
        textView = (TextView) findViewById(R.id.CustomerName);
        textView.setText(c.GetName());
        textView = (TextView) findViewById(R.id.CustomerNumber);
        textView.setText(c.GetMobileNumber());
    }
    private String getRecordingFilePath(Customer customer){
        Date date = new Date();
        CharSequence sdf = DateFormat.format(customer.GetMobileNumber()+"MM-dd-yy-hh-mm-ss",date.getTime());
        ContextWrapper contextWrapper = new ContextWrapper(getApplication());
        File musicDir = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDir,sdf+".mp3");
        return file.getPath();
    }

}



class Customer{
    String name;
    BigInteger number;
    Customer(String name,BigInteger number){
        this.name = name;
        this.number=number;
    }
    public String GetMobileNumber(){
        String numStr = String.valueOf(this.number);
        //System.out.println(this.number);
        return numStr;
    }
    public String GetName(){
        return  this.name;
    }
}
