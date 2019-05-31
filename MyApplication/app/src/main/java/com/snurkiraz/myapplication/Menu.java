package com.snurkiraz.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.getProperty;
import static org.ksoap2.SoapEnvelope.VER11;

public class Menu extends AppCompatActivity {
    Spinner yazar;
    Spinner kategori;

    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://88.247.150.76/akıllıhaber.asmx";
    private static final String SOAP_ACTION = "http://tempuri.org/Yazar";
    private static final String METHOD_NAME = "Yazar";
    private static final String NAMESPACE1 = "http://tempuri.org/";
    private static final String URL1 = "http://88.247.150.76/akıllıhaber.asmx";
    private static final String SOAP_ACTION1 = "http://tempuri.org/Kategori";
    private static final String METHOD_NAME1 = "Kategori";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        yazar = (Spinner) findViewById(R.id.spinner);
        kategori = (Spinner) findViewById(R.id.spinner2);


        // Sorgumuzu oluşturuyoruz..
        SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

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
                String yazarAdi;
if(soapEmployee.hasProperty("Table")) {
    int employeeCount = soapEmployee.getPropertyCount();
    for (int i = 0; i < employeeCount; i++) {
        // yazarAdi = soapEmployee.getProperty(i).toString();
        SoapObject soapEmployee1 = (SoapObject) soapEmployee.getProperty(i);
        yazarAdi = soapEmployee1.getProperty(1).toString();
        list.add(yazarAdi);
    }


}

            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            yazar.setAdapter(dataAdapter);
            // Gelen sonucu değerlendiriyoruz.
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Sorgumuzu oluşturuyoruz..
        SoapObject Request1 = new SoapObject(NAMESPACE1, METHOD_NAME1);

        // SoapEnvelope oluşturduk ve Soap 1.1 kullanacağımız belirttik.
        SoapSerializationEnvelope envelope1 = new SoapSerializationEnvelope(VER11);
        envelope1.dotNet = true;

        // Envelope ile requesti birbiri ile bağladık.
        envelope1.setOutputSoapObject(Request1);
        HttpTransportSE androidHttpTransport1 = new HttpTransportSE(URL1);

        try {
            // Web servisi çağırdık
            androidHttpTransport1.call(SOAP_ACTION1, envelope1);
            // Gelen verileri değerlendirmek için objemizi oluşturuyoruz.
            SoapObject response2 = (SoapObject) envelope1.getResponse();
            List<String> list2 = new ArrayList<String>();


            if (response2.toString().equals("anyType{}") || response2 == null) {
                list2 = null;
            } else {
                SoapObject soapEmployeeList1 = (SoapObject) response2.getProperty(1);


                SoapObject soapEmployee1 = (SoapObject) soapEmployeeList1.getProperty(0);
                String kategoriAdi;
if (soapEmployee1.hasProperty("Table")) {
    int employeeCount1 = soapEmployee1.getPropertyCount();
    for (int i = 0; i < employeeCount1; i++) {

        SoapObject soapEmployee2 = (SoapObject) soapEmployee1.getProperty(i);
        kategoriAdi = soapEmployee2.getProperty(1).toString();

        list2.add(kategoriAdi);
    }

}


            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list2);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            kategori.setAdapter(dataAdapter);
            // Gelen sonucu değerlendiriyoruz.
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void button2(View v) {
        //Ardından Intent methodunu kullanarak nereden nereye gideceğini söylüyoruz.
        Intent intocan = new Intent(Menu.this, AnaMenu.class);
        startActivity(intocan);
    }
    public void button5(View v) {
        //Ardından Intent methodunu kullanarak nereden nereye gideceğini söylüyoruz.
        Intent intocan = new Intent(Menu.this, Ana.class);
        startActivity(intocan);
    }
}


