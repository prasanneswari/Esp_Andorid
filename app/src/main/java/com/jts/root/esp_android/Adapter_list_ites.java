package com.jts.root.esp_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.jts.root.esp_android.Esp_List.stringList;
import static com.jts.root.esp_android.Esp_List.wifiarray;
import static com.jts.root.esp_android.Esp_List.wifis;

public class Adapter_list_ites extends ArrayAdapter<String> {
    private Context context;
    private String[] txtlist;
    TextView textidT;
    public static String WIFI;
    static List<String> addurl=new ArrayList<>();
    static boolean state;
    static CheckBox chkbox;
    public static int listPosition = -1;

    public Adapter_list_ites(Context context, String[] txtlistS) {
        super(context, R.layout.activity_main, txtlistS);
        this.context = context;
        this.txtlist = txtlistS;
    }

    @Override
    public View getView(final int positionv, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_list_items, parent, false);
        textidT = (TextView) rowView.findViewById(R.id.txtid);
        chkbox = (CheckBox) rowView.findViewById(R.id.checkedTextView1);
        try {
            final int index1 = positionv;
            textidT.setText(txtlist[positionv]);
            for (int i = 0; i < wifiarray.size(); i++) {
                if (wifis[index1].trim().equals(wifiarray.get(i))) {
                    chkbox.setChecked(true);
                    //break;
                }
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.d(Config.TAG,"doubtttttttttt"+para.aKey);
                listPosition = positionv;
                WIFI = txtlist[positionv];
            }
        });

        chkbox.setTag(positionv);

        chkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = ((CheckBox) view).isChecked();
                if (state) {
                    listPosition = positionv;
                    WIFI= String.valueOf(stringList);
                    Log.d("clickable items", String.valueOf(listPosition));
                    Log.d("clickable items513", wifis[positionv]);
                    //addurl.add(wifis[positionv]);
                    //Log.d("clickable items99999", String.valueOf(addurl));
                    wifiarray.add(wifis[positionv]);

                    Toast.makeText(getContext(),"checked item_"+wifis[positionv],Toast.LENGTH_LONG).show();

                }
                else {

                    Toast.makeText(getContext(),"uncheked item_"+wifis[positionv],Toast.LENGTH_LONG).show();

                }

            }
        });
        return rowView;

    }
}







