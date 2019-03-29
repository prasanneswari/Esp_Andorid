package com.jts.root.esp_android;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.jts.root.esp_android.MainActivity.listpos;

public class Esp_List extends AppCompatActivity {
    Button savebtn, btnadd,savebesp;
    ListView esp_lst, spinner_list;
    WifiManager mainWifiObj;
    WifiScanReceiver wifiReciever;
    static String[] wifis;
    static  List<String> dummy = new ArrayList<>();
    static List<String> spinlight = new ArrayList<>();
    static List<String> spintime = new ArrayList<>();
    RequestQueue sch_RequestQueue;
    private String TAG = "Shulamithe";
    static List<String> stringList = new ArrayList<>();
    List<ScanResult> wifiScanList;
    static List<String> wifiarray = new ArrayList<String>();
    int intValue;
    String[] time_spinnerS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esp__list);

        mainWifiObj = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiReciever = new WifiScanReceiver();
        mainWifiObj.startScan();
        savebtn = findViewById(R.id.btnsave);
        btnadd = findViewById(R.id.btnadd);
        savebesp=findViewById(R.id.btnback);
        esp_lst = findViewById(R.id.esp_lstid);
        spinner_list = findViewById(R.id.spinner_lst);
        Intent mIntent = getIntent();
        intValue = mIntent.getIntExtra("intVariableName", 0);
        Log.d("..positionnnn.. ", String.valueOf(intValue));

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //httpRequestsave();
                save_values();
                shredsave();
            }
        });
        savebesp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                httpRequestsave();
                shredsave();

            }
        });
        spintime.clear();
        spinlight.clear();
        dummy.clear();
        wifiarray.clear();
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //httpRequestsave();
                save_values();
                shredsave();

            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dummy.add("5");
                spintime.add("");
                spinlight.add("5");

                final List_Esp adapter = new List_Esp(getApplicationContext(), dummy,spintime);
                spinner_list.setAdapter(adapter);

            }
        });
    }

    protected void onPause() {
        unregisterReceiver(wifiReciever);
        super.onPause();
    }
    protected void onResume() {
        registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }
    class WifiScanReceiver extends BroadcastReceiver {
        @SuppressLint("UseValueOf")
        public void onReceive(Context c, Intent intent) {
            wifiScanList = mainWifiObj.getScanResults();

            wifis = new String[wifiScanList.size()];
            Log.d("wifis items", String.valueOf(wifis));

            for (int i = 0; i < wifiScanList.size(); i++) {
                wifis[i] = (wifiScanList.get(i)).SSID.toString();

                Log.d("wifilist items1111", wifis[i].toString());

            }

            for (String k : wifis) {
                System.out.println("data:" + k);
                stringList.add(String.valueOf(k));

            }

           /* Adapter_list_ites adapter_items = new Adapter_list_ites(getApplicationContext(), wifis);
            esp_lst.setAdapter(adapter_items);
            //esp_lst.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_list_items,R.id.txtid, stringList));
            esp_lst.setItemsCanFocus(false);
            // we want multiple clicks
            esp_lst.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
*/
            //jsonarray();
            sharedget();
        }
    }
    public void shredsave() {
        Log.d("..json_array.. ", String.valueOf(wifiarray));
        Log.d("..array spinlight.. ", String.valueOf(dummy));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Esp_List.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(" esp" + intValue, wifiarray.toString());
        editor.putString(" tm" + intValue, spintime.toString());
        editor.putString(" per" + intValue, dummy.toString());
        editor.apply();
    }
    public void sharedget() {

        spintime.clear();
        spinlight.clear();
        dummy.clear();
        wifiarray.clear();

        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(Esp_List.this);
        String getespS1 = preferences1.getString(" esp" + intValue, "Value");
        String gettimeS1 = preferences1.getString(" tm" + intValue, "Value");
        String getperS1 = preferences1.getString(" per" + intValue, "Value");

        Log.d("sharedpreference esp  ", getespS1);
        Log.d("sharedpreference tm  ", gettimeS1);
        Log.d("sharedpreference per  ", getperS1);

        String[] espposS = new String[0];
        boolean check1=false;
        if(getespS1.length()==5){
            check1=false;
        }else {
            getespS1 = getespS1.substring(1, getespS1.length() - 1);
            espposS = getespS1.split(",");
            check1=true;
        }
        String[] tmposS = new String[0];
        boolean check2=false;
        if(gettimeS1.length()==5){
            check2=false;
        }else {
            gettimeS1 = gettimeS1.substring(1, gettimeS1.length() - 1);
            tmposS = gettimeS1.split(",");
            check2=true;
        }
        String[] perposS = new String[0];
        boolean check3=false;
        if(getperS1.length()==5){
            check3=false;
        }else {
            getperS1 = getperS1.substring(1, getperS1.length() - 1);
            perposS = getperS1.split(",");
            check3=true;
        }
        if(check1==true) {
            for (int i = 0; i < espposS.length; i++) {
                System.out.println(espposS[i]);
                espposS[i] = espposS[i].replaceAll("^\\s+", "");
                wifiarray.add(espposS[i]);
            }
        }
        if(check2==true) {
            for (int i = 0; i < tmposS.length; i++) {
                System.out.println("time value " + tmposS[i]);
                tmposS[i] = tmposS[i].replaceAll("^\\s+", "");
                spintime.add(tmposS[i]);
                System.out.println("array timevalue " + spintime);
            }
        }
        if(check3==true) {
            for (int i = 0; i < perposS.length; i++) {
                System.out.println("percnete value " + perposS[i]);
                perposS[i] = perposS[i].replaceAll("^\\s+", "");
                spinlight.add(perposS[i]);
                dummy.add(perposS[i]);
            }
        }
        Log.d(" data 11111", "---" + dummy);
        final List_Esp adapter = new List_Esp(getApplicationContext(), dummy,spintime);
        spinner_list.setAdapter(adapter);

        Adapter_list_ites adapter_items = new Adapter_list_ites(getApplicationContext(),wifis);
        esp_lst.setAdapter(adapter_items);
        //esp_lst.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_list_items,R.id.txtid, stringList));
        esp_lst.setItemsCanFocus(false);
        // we want multiple clicks
        esp_lst.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

    }
    public void save_values(){
        String saves = "{\"username\":\"admin\",\"password\":\"admin\",\"Modes\":\"" + listpos + "\",\"Esps\":\"" + wifiarray + "\",\"Time_Dim\":\"" + spintime + "\",\"Light_Dim\":\"" + dummy + "\"}";
        //String add_posts = "{\"orderid\":\"" + lID + "\"}";
        Log.d("jsnresponse add_posts", "---" + saves);
        //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
        //String room_ssid_url = "http://cld003.jts-prod.in:5906/AssetTrackerApp/get_rooms_ssid/";
        String save_url = "\n" + "http://cld003.jts-prod.in:5909/DimmingLightApp/add_modes/";
        // String urlrs= "https://jtsha.in/service/validate_web";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(saves);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + saves);
        JSONSenderVolley(save_url, lstrmdt);
    }
    public void JSONSenderVolley(String save_url, final JSONObject json)
    {
        Log.d("save_url-", "---"+save_url);
        Log.d("555555", "00000000"+json.toString());
        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                save_url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
/*
                        Log.d(Bitmap.Config.TAG, response.toString());
*/
                        Log.d("----pur_url values-----", "---"+response.toString());
                        //getval_request(response);
                        //pur_request(response);
                        try {
                            int login_code = response.getInt("error_code");
                            String er_discp=response.getString("error_desc");

                            String[] separated = er_discp.split("=");
                            if(login_code==0){
                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
                            }else
                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" ", "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
            }
        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Adding request to request queue
        // jsonObjReq.setTag("");
        addToRequestQueue(jsonObjReq);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }
    public void httpRequestsave() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://192.168.2.80/message?esplist=\""+wifiarray+"\"&&time=\""+spintime +"\"&&lightpercentage=\""+spinlight+"\"";
        Log.d("sending string is :", url.toString());
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello response :", response.toString());

                        JSONObject responseJSON = null;
                        JSONObject jsonReq;
                        try {
                            responseJSON = new JSONObject(response);
                            try {
                                int login_code = responseJSON.getInt("error_code");
                                String er_discp=responseJSON.getString("error_desc");

                                String[] separated = er_discp.split("=");
                                if(login_code==0){
                                    Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
                                }else
                                    Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Log.d("hello1 ","error.......");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}