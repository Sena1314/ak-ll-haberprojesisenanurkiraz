package com.snurkiraz.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import static org.ksoap2.SoapEnvelope.VER11;

public class Ana extends AppCompatActivity {
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://88.247.150.76/akıllıhaber.asmx";
    private static final String SOAP_ACTION = "http://tempuri.org/GetSecim";
    private static final String METHOD_NAME = "GetSecim";
    ListView listView;
    EditText kategori;
    EditText yazar;
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.okuma);
        listView = (ListView) findViewById(R.id.listview);

        // Sorgumuzu oluşturuyoruz..
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

SoapObject soapEmp=(SoapObject) soapEmployeeList.getProperty(0);


                String baslik;
                String icerik;
                if(soapEmp.hasProperty("Table")) {
                    int employeeCount = soapEmp.getPropertyCount();
                    for (int i = 0; i < employeeCount; i++) {
                        // yazarAdi = soapEmployee.getProperty(i).toString();
                        SoapObject soapEmployee1 = (SoapObject) soapEmp.getProperty(i);
                        baslik = soapEmployee1.getProperty(0).toString();
                        icerik = soapEmployee1.getProperty(1).toString();
                        list.add(baslik);
                        list.add(icerik);
                    }


                }

            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
            //  dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            listView.setAdapter(dataAdapter);
            // Gelen sonucu değerlendiriyoruz.
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
