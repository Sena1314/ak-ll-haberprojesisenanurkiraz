package com.snurkiraz.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

import static org.ksoap2.SoapEnvelope.VER11;

public class MainActivity extends AppCompatActivity {


    EditText kullaniciadi;
    EditText sifre;

    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://88.247.150.76/akıllıhaber.asmx";
    private static final String SOAP_ACTION = "http://tempuri.org/MemberDogrula";
    private static final String METHOD_NAME = "MemberDogrula";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kullaniciadi = (EditText) findViewById(R.id.editText4);
        sifre = (EditText) findViewById(R.id.editText5);
    }

    public void kisiekle(View v) {
        //Ardından Intent methodunu kullanarak nereden nereye gideceğini söylüyoruz.
        Intent intocan = new Intent(MainActivity.this, KisiEkle.class);
        startActivity(intocan);
    }
    public void button1 (View view) {
        // Bir metin içerisindeki boşluklardan kurtulmak için trim metodu kullanılır.
        // trim() metodu, metnin sonunda ve başında yer alan boşlukları yok ederken
        // kelime aralarındaki boşluklara dokunmaz.

        // Bir büyük harflere çevirmek için toUpperCase() methodu kullanılır.
        // Uygulamada verilerin isim ve soyisimin bütün harfleri büyük olması gerekiyor.

        String kullaniciadiString = kullaniciadi.getText().toString().trim();
        String sifreString = sifre.getText().toString().trim();

        new asynTask().execute(kullaniciadiString, sifreString);
    }

    private class asynTask extends AsyncTask<String, Void, Void> {

        String resultText;
        Boolean result;
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        AlertDialog alert;

        // Doğrula butonuna basıldığında ilk yapılacaklar..
        @Override
        protected void onPreExecute() {

            // ProgressDialog oluşturuyoruz.
            progressDialog.setMessage("Kontrol Ediliyor...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            // Sorgumuzu oluşturuyoruz..
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            Request.addProperty("kullaniciadi", strings[0]);
            Request.addProperty("sifre", strings[1]);

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
                SoapObject response = (SoapObject) envelope.bodyIn;
                result = Boolean.parseBoolean(response.getProperty(0).toString());
                // Gelen sonucu değerlendiriyoruz.
                if (result) {
                    resultText = "Giriş Yapıldı !";
                    Intent gecis = new Intent(MainActivity.this, Menu.class);
                    startActivity(gecis);
                } else {
                    resultText = "Giriş Yapılamadı !";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            // ProgressDialog kapatılıyor.
            progressDialog.dismiss();
            // AlertDialog'u gösteriyoruz.
            alert = builder.setMessage(resultText)
                    .setTitle("Sonuç")
                    .setCancelable(true)
                    .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).create();
            alert.show();
        }
    }
}
