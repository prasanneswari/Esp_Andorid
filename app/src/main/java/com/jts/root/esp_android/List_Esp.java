package com.jts.root.esp_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import static android.R.layout.simple_spinner_item;

import static com.jts.root.esp_android.Esp_List.dummy;
import static com.jts.root.esp_android.Esp_List.spinlight;
import static com.jts.root.esp_android.Esp_List.spintime;

public class List_Esp extends ArrayAdapter<String> {

    private Context context;
    private List<String> indx;
    private List<String> edit;
    String selectedperspinner;

    int pos_get = 0;

    //public static String[] time_spinner = new String[]{"5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60"};
    public static String[] percentage_spinner = new String[]{"5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100"};
    public List_Esp(Context context, List<String> array,List<String> editarray) {
        super(context, R.layout.activity_main, array);
        this.context = context;
        this.indx = array;
        this.edit = editarray;
    }

    @Override
    public View getView(final int positionv, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_list__esp, parent, false);

        //RecyclerView.ViewHolder holder;
        //final Spinner timespnr = (Spinner) rowView.findViewById(R.id.timeid);
        final Spinner prcntgspn = (Spinner) rowView.findViewById(R.id.percentageid);
        final EditText timespnr = (EditText) rowView.findViewById(R.id.timeid);
        timespnr.setEnabled(true);
        final int index1 = positionv;

        timespnr.requestFocus();
        timespnr.setFocusableInTouchMode(true);


        timespnr.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                //detailsID = id[position];
                String enteredValue  = s.toString();
                Log.d("enteredValue ", enteredValue);
                spintime.set(index1, enteredValue);
                Log.d("addTextChangedListener ", spintime.get(index1));

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // qIssAnew[position]="nodata";
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //qIssAnew[position] = qIssT.getText().toString();
            }
        });

        try {
            //spin2.set(index1,selectedperspinner);

            /*ArrayAdapter aa = new ArrayAdapter(context, simple_spinner_item, time_spinner);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            timespnr.setAdapter(aa);*/

            timespnr.setText(spintime.get(index1));
            Log.d("editttttt", "--------------" + spintime.get(index1));


            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String lName = spintime.get(index1);

                    Log.d("Location" ," Id1111 :" + lName);

                    String timestring =  timespnr.getText().toString();
                    Log.d("get editvalueee", "--------------" + timestring);

                    spintime.add(String.valueOf(timestring));
                    Log.d("get array value", "--------------" + spintime);

                }
            });

            ArrayAdapter switcharry1 = new ArrayAdapter(context, simple_spinner_item, percentage_spinner);
            switcharry1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            prcntgspn.setAdapter(switcharry1);

            /*for (int i = 0; i < time_spinner.length; i++) {
                if (spintime.get(index1).trim().equals(time_spinner[i])) {
                    timespnr.setSelection(i);
                    break;
                }
            }*/
            for (int i = 0; i < percentage_spinner.length; i++) {
                if (dummy.get(index1).trim().equals(percentage_spinner[i])) {
                    prcntgspn.setSelection(i);
                    Log.d(" data 34343434", "--------------" + dummy.get(index1));
                    break;
                }
                Log.d(" data 67676767", "--------------" + dummy.get(index1));

            }

            Log.d("spin1 value@@@@@", "--------------" + spinlight);

            Log.d("spin2 value@@@@@", "--------------" + spintime);

            Log.d("dummy value@@@@@", "--------------" + dummy);


            /*timespnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String selectedtmspinner = parent.getItemAtPosition(position).toString();

                    Log.d("selectedtmspinner vallu", "--------------" + String.valueOf(index1) + selectedtmspinner);
                    spintime.set(index1, selectedtmspinner);

                    Log.d("spin2 value", "--------------" + spintime);


                } // to close the onItemSelected

                public void onNothingSelected(AdapterView<?> parent) {
                    //poaddnw.set(positionv, String.valueOf("0"));
                }
            });*/
            prcntgspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    selectedperspinner = parent.getItemAtPosition(position).toString();

                    Log.d("selectedperspinner", "--------------" + String.valueOf(index1) + selectedperspinner);
                    dummy.set(index1, selectedperspinner);

                    Log.d("spin1 value", "--------------" + dummy);


                } // to close the onItemSelected

                public void onNothingSelected(AdapterView<?> parent) {
                    //poaddnw.set(positionv, String.valueOf("0"));
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        return rowView;
    }

}


