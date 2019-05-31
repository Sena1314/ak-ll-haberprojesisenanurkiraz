package com.snurkiraz.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static org.ksoap2.SoapEnvelope.VER11;

public class KisiEkle extends AppCompatActivity {
    EditText ad;
    EditText soyad;
    EditText e_mail;
    EditText kullaniciadi;
    EditText sifre;

    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://88.247.150.76/akıllıhaber.asmx";
    private static final String SOAP_ACTION = "http://tempuri.org/AddMember";
    private static final String METHOD_NAME = "AddMember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kisiekle);
        ad = (EditText) findViewById(R.id.editText);
        soyad = (EditText) findViewById(R.id.editText2);
        e_mail = (EditText) findViewById(R.id.editText3);
        kullaniciadi = (EditText) findViewById(R.id.editText6);
        sifre = (EditText) findViewById(R.id.editText8);
    }


    public void button (View view) {
        // Bir metin içerisindeki boşluklardan kurtulmak için trim metodu kullanılır.
        // trim() metodu, metnin sonunda ve başında yer alan boşlukları yok ederken
        // kelime aralarındaki boşluklara dokunmaz.
        String adString = ad.getText().toString().trim();
        // Bir büyük harflere çevirmek için toUpperCase() methodu kullanılır.
        // Uygulamada verilerin isim ve soyisimin bütün harfleri büyük olması gerekiyor.
        String soyadString = soyad.getText().toString().trim();
        String e_mailString = e_mail.getText().toString().trim();
        String kullaniciadiString = kullaniciadi.getText().toString().trim();
        String sifreString = sifre.getText().toString().trim();

        new KisiEkle.asynTask().execute(adString, soyadString, e_mailString, kullaniciadiString, sifreString);
    }

    private class asynTask extends AsyncTask<String, Void, Void> {

        String resultText;
        Boolean result;
        ProgressDialog progressDialog = new ProgressDialog(KisiEkle.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(KisiEkle.this);
        AlertDialog alert;

        // Doğrula butonuna basıldığında ilk yapılacaklar..
        @Override
        protected void onPreExecute() {

            // ProgressDialog oluşturuyoruz.
            progressDialog.setMessage("Sisteme Ekleniyor...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            // Sorgumuzu oluşturuyoruz..
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("ad", strings[0]);
            Request.addProperty("soyad", strings[1]);
            Request.addProperty("email", strings[2]);
            Request.addProperty("kullaniciadi", strings[3]);
            Request.addProperty("sifre", strings[4]);

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
                    resultText = "Eklendi !";

                } else {
                    resultText = "Bilgileri Eksiksiz Girin !";
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
