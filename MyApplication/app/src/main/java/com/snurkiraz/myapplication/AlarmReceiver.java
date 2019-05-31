package com.snurkiraz.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.ksoap2.SoapEnvelope.VER11;

public class AlarmReceiver extends Activity {
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://88.247.150.76/akıllıhaber.asmx";
    private static final String SOAP_ACTION = "http://tempuri.org/GetSecim";
    private static final String METHOD_NAME = "GetSecim";
    public EditText  yazaradi;
    public EditText kategoriadi;
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);


        String a  = getIntent().getExtras().getString("veri");
        String b = getIntent().getExtras().getString("veri1");
        SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
        Request.addProperty("yazaradi","");
        Request.addProperty("kategoriadi", "");
        // SoapEnvelope oluşturduk ve Soap 1.1 kullanacağımız belirttik.
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(VER11);
        envelope.dotNet = true;

        // Envelope ile requesti birbiri ile bağladık.
        envelope.setOutputSoapObject(Request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Web servisi çağırdık
            androidHttpTransport.call(SOAP_ACTION, envelope);
            // Gelen verileri değerlendirmek için objemizi oluşturuyoruz.
            SoapObject response = (SoapObject) envelope.getResponse();
            List<String> list = new ArrayList<String>();


            if (response.toString().equals("anyType{}") || response == null) {
                list = null;
            } else {
                SoapObject soapEmployeeList = (SoapObject) response.getProperty(1);


                SoapObject soapEmployee = (SoapObject) soapEmployeeList.getProperty(0);
                String mp;
                if (soapEmployee.hasProperty("Table")) {
                    int employeeCount = soapEmployee.getPropertyCount();
                    for (int i = 0; i < employeeCount; i++) {
                        long sn;
                        int say=employeeCount-1;
                        SoapObject soapEmployee1 = (SoapObject) soapEmployee.getProperty(i);


                        mp = soapEmployee1.getProperty(2).toString();
                        list.add(mp);
                        //  ContentResolver musicResolver = getContentResolver();
                        //  Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        //  Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

                        String url = mp; // your URL here
                        final MediaPlayer mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        try {
                            mediaPlayer.setDataSource(url);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            mediaPlayer.prepare(); // might take long! (for buffering, etc)
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        mediaPlayer.start();
                        sn=mediaPlayer.getDuration();

                        if(i!=say) {
                            TimeUnit.MILLISECONDS.sleep(sn);
                        }






                    }

                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}