package com.jts.root.esp_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jts.root.esp_android.Esp_List.spinlight;
import static com.jts.root.esp_android.Esp_List.spintime;
import static com.jts.root.esp_android.Esp_List.wifiarray;
import static com.jts.root.esp_android.MainActivity.getesps;


public class MainActivity extends AppCompatActivity {

    Button addbtn;
    ListView list;
    public int modecounter = 0;
    public String[] Modes = {"mode1", "mode2", "mode3", "mode4", "mode5"};
    public String[] modes;
    private ArrayAdapter<String> listAdapter;
    private SharedPreferences mMyPrefs;
    private SharedPreferences.Editor mMyEdit;
    public static final String myPreference = "araterPref";
    Integer arrayLength;
    static String listpos, combain;
    com.android.volley.RequestQueue sch_RequestQueue;
    static String[]  getmodes, gettimes, getpers, getesps, get_modes, posespS, postmS, posperS;
    static List<String> sharedmode = new ArrayList<String>();
    static int indexpos;
    boolean test1=false;
    static String json_array;
    String s;
    int addsize;
    static List<String> addurl = new ArrayList<String>();
    static List<String> spin2 = new ArrayList<String>();
    static List<String> spin1 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addbtn = (Button) findViewById(R.id.addbtnid);
        list = (ListView) findViewById(R.id.lstid);

        arrayLength = 0;
        getmode() ;

        listAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, modecounter);
        list.setAdapter(listAdapter);
        sharedget();
        list.setOnItemClickListener(new ListClickHandler());
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    String addmode = "Mode";
                    if (sharedmode.isEmpty()) {
                        addsize = 1;

                    } else {
                        addsize = sharedmode.size() + 1;
                    }
                    Log.d("addsize0000000 ", String.valueOf(addsize));
                    combain = addmode + addsize;
                    Log.d("combain11111 ", combain);
                    //listAdapter.add(combain);
                    sharedmode.add(combain);
                    s = String.valueOf(sharedmode);
                    Log.d("add share3333 ", s);

                    // arrayLength = (Integer) perarray.size();
                    Log.d("arraylength ", String.valueOf(sharedmode));
                    listAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, sharedmode);
                    list.setAdapter(listAdapter);
                    listAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    //e.printstacktrace();
                    Log.e("", e.getMessage());
                }

                // Put some values to shared preferences

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("netkey", String.valueOf(sharedmode));
                //editor.clear();
                editor.apply();

            }

        });


    }

    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub
            indexpos = position;
            Log.d("listpositem ", String.valueOf(indexpos));
            listpos = list.getAdapter().getItem(position).toString();
            Log.d("listpos2222 ", listpos);
            Toast.makeText(MainActivity.this, listpos, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Esp_List.class);
            intent.putExtra("intVariableName", indexpos);
            startActivity(intent);

            for (int i=0;i<2;i++) {

                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                String getespS1 = preferences1.getString(" esp" + i, "Value");
                String gettimeS1 = preferences1.getString(" tm" + i, "Value");
                String getperS1 = preferences1.getString(" per" + i, "Value");

                Log.d("main esp  ", getespS1);
                Log.d("main tm  ", gettimeS1);
                Log.d("main per  ", getperS1);

            }

        }
    }
    public void getmode() {
        // String pur_prds = "{\"username\":\"admin\",\"password\":\"admin\"}";
        String get_modes = "{\"username\":\"admin\",\"password\":\"admin\"}";
        Log.d("jsnresponse get_modes", "---" + get_modes);
        //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
        //String room_ssid_url = "http://cld003.jts-prod.in:5906/AssetTrackerApp/get_rooms_ssid/";
        String get_url = "http://cld003.jts-prod.in:5909/DimmingLightApp/get_mode_settings/";
        // String urlrs= "https://jtsha.in/service/validate_web";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(get_modes);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + get_modes);
        JSONSenderVolley(get_url, lstrmdt);
    }

    public  void get_request(JSONObject responseJSON){

        try {

            sharedmode.clear();
            //Log.d( " Array", " response ->  " + response);
            JSONArray new_array1;
            List<String> getmodeL = new ArrayList<String>();
            List<String> getespL = new ArrayList<String>();
            List<String> gettimeL = new ArrayList<String>();
            List<String> getperL = new ArrayList<String>();

            new_array1 = responseJSON.getJSONArray("get_modes");
            Log.d(" Array", " : " + new_array1);
            for (int i = 0, count = new_array1.length(); i < count; i++) {

                addurl.clear();
                spin1.clear();
                spin2.clear();

                String getespS = new_array1.getJSONObject(i).getString("esps");
                Log.d("modeS3333 ", getespS);

                getespS = getespS.substring(1, getespS.length() - 1);
                getesps = getespS.split(",");

                String  gettimeS = new_array1.getJSONObject(i).getString("times");
                Log.d("gettimeS33 ", gettimeS);

                gettimeS = gettimeS.substring(1, gettimeS.length() - 1);
                gettimes = gettimeS.split(",");

                String getperS = new_array1.getJSONObject(i).getString("lights");
                Log.d("getperS333 ", getperS);

                getperS = getperS.substring(1, getperS.length() - 1);
                getpers = getperS.split(",");

                String getmodeS = new_array1.getJSONObject(i).getString("modes");
                Log.d("getperS333 ", getmodeS);

                sharedmode.add(getmodeS);
                addurl.add(getespS);
                spin2.add(gettimeS);
                spin1.add(getperS);

                Log.d("get shredmode ", String.valueOf(sharedmode));
                Log.d("get shredmode111 ", String.valueOf(spin1));
                Log.d("get shredmode222 ", String.valueOf(addurl));
                Log.d("get shredmode333 ", String.valueOf(spin2));

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("netkey", String.valueOf(sharedmode));
                editor.putString(" esp"+i, String.valueOf(addurl));
                editor.putString(" tm"+i, String.valueOf(spin2));
                editor.putString(" per"+i, String.valueOf(spin1));
                editor.apply();
            }
            Log.d("mode size ", String.valueOf(sharedmode.size()));

            netData();

            //listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, get_modes);
            //list.setAdapter(listAdapter);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public  void  netData()
    {

        sharedmode.clear();
        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        String mode_data1 = preferences1.getString("netkey", null);

        Log.d("mode sharedpref get  ", mode_data1 + "size:" + mode_data1.length());

        mode_data1 = mode_data1.substring(1, mode_data1.length() - 1);
        String []getmodes1 = mode_data1.split(",");


        for (int l = 0; l < getmodes1.length; l++) {

            getmodes1[l] = getmodes1[l].replaceAll("^\\s+", "");
            Log.d("data mode  ", getmodes1[l]);
            sharedmode.add(getmodes1[l]);


        }
        addsize = (Integer) sharedmode.size();
        Log.d("arraylength ", String.valueOf(addsize));
        Log.d("sharedpreference esp22 ", String.valueOf(sharedmode));
        //listAdapter.add(String.valueOf(perarray));
        listAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, sharedmode);
        list.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

    }
    public void sharedget() {

        sharedmode.clear();
        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        String mode_data1 = preferences1.getString("netkey", "Mode1");

        Log.d("mode sharedpref get  ", mode_data1 + "size:" + mode_data1.length());

        String[] getmodes1 = new String[0];
        boolean check1=false;
        if(mode_data1.length()==5){
            check1=false;
        }else {
            mode_data1 = mode_data1.substring(1, mode_data1.length() - 1);
            getmodes1 = mode_data1.split(",");
            check1=true;
        }
        if(check1==true) {
            for (int l = 0; l < getmodes1.length; l++) {

                getmodes1[l] = getmodes1[l].replaceAll("\\s", "");
                Log.d("data mode  ", getmodes1[l]);
                Log.d("data size  ", String.valueOf(getmodes1[l].length()));
                sharedmode.add(getmodes1[l]);

//            if (l == 0) {
//                sharedmode.set(0, "Mode1");
//            }
            }
        }

        Log.d("sharedpreference esp22 ", String.valueOf(sharedmode));
        //listAdapter.add(String.valueOf(perarray));
        listAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, sharedmode);
        list.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();


    }


    public void JSONSenderVolley(String get_url, final JSONObject json)
    {
        Log.d("get_url-", "---"+get_url);
        Log.d("555555", "00000000"+json.toString());

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                get_url, json,

                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
/*
                        Log.d(Bitmap.Config.TAG, response.toString());
*/
                        Log.d("----get_url values-----", "---"+response.toString());
                        get_request(response);

                        try {
                            int login_code = response.getInt("error_code");
                            String er_discp=response.getString("error_desc");

                            String[] separated = er_discp.split("=");
                            if(login_code==0){
                                test1=true;
                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
                            }else
                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
                            test1=false;
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
}