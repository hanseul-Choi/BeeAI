package com.sample.ai_unicorn;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Option extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ArrayList<String> al,al2,al3,al5;
    ArrayAdapter<String> aA,aA2,aA3,aA5;

    private Spinner spinner,spinner2,spinner3,spinner5;
    private CheckBox checkBox,checkBox2,checkBox3,checkBox4,checkBox5;
    private static int tmp,tmp2,tmp3,tmp5;

    public static String[] one = new String[3];

    Button progress_btn;

    private MqttAndroidClient mqttAndroidClient;

    String[] add = {"강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구","동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구","용산구","은평구","종로구","중구","중랑구"};
    public static String[] cat_en = {"e-commerce business","home appliances","Furniture","Flower plant","soft goods","Toys","sports/sports supplies","Cosmetics","Phrase","Medication","Clock and precious metal","Glasses","Bags",
            "Shoes","General clothing","A side dish shop","Cell phone","Sales computers and peripherals","Convenience store","Supermarket","Skin care","Nail shop","Beauty parlor","Sports Club","Dentist","General Assembly",
            "Coffee-Drink","Snack restaurant","Fast Food Shop","The bakery","Food restaurant","a Japanese restaurant","Chinese restaurants","Korean restaurant","Interior","Books","Medical equipment","Singing room",
            "Gosiwon","inn","Laundry","Car repair","PC room","Golf Training Center","a billiard room","Han Chinese Medical Center, California","a sports lesson","Hope-Ganju branch","Chicken specialty store",
            "Lighting","a hardware store","Pettle","Fruit-in-the-wool","Sales of marine products","meat sales","Rice sales","Home Appliance Repair","Art Academy","a foreign language institute","General Teaching Academy",
            "Car beauty","Real estate brokerage business","Bicycles and other transportation equipment"};

    public static String[] cat_ko = {"전자상거래업","가전제품","가구","화초","섬유제품","완구","운동/경기용품","화장품","문구","의약품","시계및귀금속","안경","가방","신발","일반의류","반찬가게","핸드폰","컴퓨터및주변장치판매","편의점",
            "슈퍼마켓","피부관리실","네일숍","미용실","스포츠클럽","치과의원","일반의원","커피-음료","분식전문점","패스트푸드점","제과점","양식음식점","일식음식점","중식음식점","한식음식점","인테리어","서적","의료기기","노래방",
            "고시원","여관","세탁소","자동차수리","PC방","골프연습장","당구장","한의원","스포츠 강습","호프-간이주점","치킨전문점","조명용품","철물점","애완동물","청과상","수산물판매","육류판매","미곡판매","가전제품수리",
            "예술학원","외국어학원","일반교습학원","자동차미용","부동산중개업","자전거 및 기타운송장비"};

    public static int cat;

    public static String[] addempty = {"Convenience store","Supermarket","Cosmetics"};
    //문자열 처리
    public String result_str;
    public String predict_str;
    public HashMap<String,Integer> hm = new HashMap<>();
    public int checktmp1=0,checktmp2=0,checktmp3=0,checktmp4=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);

        mqttAndroidClient = new MqttAndroidClient(this,  "tcp://" + "220.149.236.154" + ":1883", MqttClient.generateClientId()); //Mqtt 읽음

        try {
            Log.d("check","들어감?");
            IMqttToken token = mqttAndroidClient.connect(getMqttConnectionOption());    //mqtttoken 이라는것을 만들어 connect option을 달아줌
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions());    //연결에 성공한경우
                    Log.e("Connect_success", "Success");
                    try {
                        mqttAndroidClient.subscribe("jmlee", 0 );   //연결에 성공하면 jmlee 라는 토픽으로 subscribe함 //test
                        mqttAndroidClient.subscribe("read7", 0 );
                        mqttAndroidClient.subscribe("read17", 0 );
                        mqttAndroidClient.subscribe("read19", 0 );
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {   //연결에 실패한경우
                    Log.e("connect_fail", "Failure " + exception.toString());
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        mqttAndroidClient.setCallback(new MqttCallback() {  //클라이언트의 콜백을 처리하는부분
            @Override
            public void connectionLost(Throwable cause) {
            }
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {    //모든 메시지가 올때 Callback method
                Log.e("what",topic);
                if (topic.equals("jmlee")){     //topic 별로 분기처리하여 작업을 수행할수도있음 //test
                    String msg = new String(message.getPayload());
                    Log.d("payload",msg);
                    String test1 = msg.substring(msg.indexOf(",")+1);
                    String result1 = msg.substring(2,msg.indexOf(",")-1);
                    String test2 = test1.substring(test1.indexOf(",")+1);
                    String result2 = test1.substring(2,test1.indexOf(",")-1);
                    String result3 = test2.substring(2,test2.indexOf("]")-1);
                    one[0] = result1;
                    one[1] = result2;
                    one[2] = result3;
                    for(int i=0; i<63; i++) {
                        if(one[0].equals(cat_en[i])) {
                            one[0] = cat_ko[i];
                        }
                        if(one[1].equals(cat_en[i])) {
                            one[1] = cat_ko[i];
                        }
                        if(one[2].equals(cat_en[i])) {
                            one[2] = cat_ko[i];
                        }
                    }
                    Log.d("payload",result1 + " " + result2 + " " + result3);

                    /*predict_result(msg);
                    checktmp1++;
                    Log.d("num",Integer.toString(checktmp1));
                    if(checktmp1 == 50) {
                        checktmp1 = 0;
                        Log.e("check", "강남구");
                        if(al5.get(tmp5).equals("강남구"))
                            sortByValue(hm);
                        Toast.makeText(getApplicationContext(),"처리되었습니다.",Toast.LENGTH_SHORT).show();
                    }*/
                }
                if (topic.equals("read7")){     //topic 별로 분기처리하여 작업을 수행할수도있음
                    String msg = new String(message.getPayload());
                    Log.e("check", msg);
                    predict_result(msg);
                    checktmp2++;
                    Log.e("check","checktmp2 : " + Integer.toString(checktmp2));
                    if(checktmp2 == 40) {
                        checktmp2 = 0;
                        Log.e("check", "금천구");
                        if(al5.get(tmp5).equals("금천구"))
                            sortByValue(hm);
                    }
                }
                if (topic.equals("read17")){     //topic 별로 분기처리하여 작업을 수행할수도있음
                    String msg = new String(message.getPayload());
                    Log.e("check", msg);
                    predict_result(msg);
                    checktmp3++;
                    Log.e("check","checktmp3 : " + Integer.toString(checktmp3));
                    if(checktmp3 == 40) {
                        checktmp3 = 0;
                        Log.e("check", "송파구");
                        if(al5.get(tmp5).equals("송파구"))
                            sortByValue(hm);
                    }
                }
                if (topic.equals("read19")){     //topic 별로 분기처리하여 작업을 수행할수도있음
                    String msg = new String(message.getPayload());
                    Log.e("check", msg);
                    predict_result(msg);
                    checktmp4++;
                    Log.e("check","checktmp4 : " + Integer.toString(checktmp4));
                    if(checktmp4 == 40) {
                        checktmp4 = 0;
                        Log.e("check", "영등포구");
                        if(al5.get(tmp5).equals("영등포구"))
                            sortByValue(hm);
                    }
                }
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.d("test2","언제 들어가나요?");
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavi);
        bottomNavigationView.setSelectedItemId(R.id.icCustom);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.icmap:
                        Intent i1 = new Intent(Option.this, MainActivity.class);
                        Boolean check1 = checkBox.isChecked();
                        Boolean check2 = checkBox2.isChecked();
                        i1.putExtra("c2",check1);
                        i1.putExtra("c1",check2);
                        i1.putExtra("one0",one[0]);
                        i1.putExtra("one1",one[1]);
                        i1.putExtra("one2",one[2]);
                        i1.putExtra("add",add[tmp5]);
                        startActivity(i1);
                        break;

                    case R.id.icbase:
                        Intent i2 = new Intent(Option.this, Description.class);
                        startActivity(i2);
                        break;

                    case R.id.icquestion:
                        Intent i3 = new Intent(Option.this, request.class);
                        startActivity(i3);
                        break;
                }
            }
        });

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        Boolean check1 = pref.getBoolean("check1",false);
        Boolean spinnerIsOn = pref.getBoolean("spinner1",false);
        final Integer spinnerValue = pref.getInt("spinner1_val",0);

        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner5 = (Spinner) findViewById(R.id.spinner5);

        spinner.setEnabled(spinnerIsOn);

        //spinner 설정
        al = new ArrayList<>();
        al.add("10대 고객");
        al.add("20대 고객");
        al.add("30대 고객");
        al.add("40대 고객");
        al.add("50대 고객");
        al.add("60대 고객");

        aA = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                al);

        spinner.setAdapter(aA);
        spinner.setSelection(spinnerValue);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.setSelection(i);
                tmp = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        checkBox = (CheckBox)findViewById(R.id.checkbox1);

        checkBox.setChecked(check1);

        checkBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()) {
                    spinner.setEnabled(true);
                } else {
                    spinner.setEnabled(false);
                }
            }
        });

        //////////////////////////////////////////////////
        checkBox2 = (CheckBox)findViewById(R.id.checkbox2);

        Boolean check2 = pref.getBoolean("check2",false);
        Boolean spinnerIsOn2 = pref.getBoolean("spinner2",false);
        final Integer spinnerValue2 = pref.getInt("spinner2_val",0);

        checkBox2.setChecked(check2);
        spinner2.setEnabled(spinnerIsOn2);

        //spinner 설정
        al2 = new ArrayList<>();
        al2.add("00시 ~ 06시");
        al2.add("06시 ~ 11시");
        al2.add("11시 ~ 14시");
        al2.add("14시 ~ 17시");
        al2.add("17시 ~ 21시");
        al2.add("21시 ~ 24시");

        aA2 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                al2);

        spinner2.setAdapter(aA2);
        spinner2.setSelection(spinnerValue2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.setSelection(i);
                tmp2 = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        checkBox2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox2.isChecked()) {
                    spinner2.setEnabled(true);
                } else {
                    spinner2.setEnabled(false);
                }
            }
        });
        //////////////////////////////////////////////////
        checkBox3 = (CheckBox)findViewById(R.id.checkbox3);

        Boolean check3 = pref.getBoolean("check3",false);
        Boolean spinnerIsOn3 = pref.getBoolean("spinner3",false);
        final Integer spinnerValue3 = pref.getInt("spinner3_val",0);

        checkBox3.setChecked(check3);
        spinner3.setEnabled(spinnerIsOn3);

        //spinner 설정
        al3 = new ArrayList<>();
        al3.add("남성");
        al3.add("여성");

        aA3 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                al3);

        spinner3.setAdapter(aA3);
        spinner3.setSelection(spinnerValue3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.setSelection(i);
                tmp3 = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        checkBox3.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox3.isChecked()) {
                    spinner3.setEnabled(true);
                } else {
                    spinner3.setEnabled(false);
                }
            }
        });
        ///////////////////////////////////////////////////////////////
        checkBox4 = (CheckBox)findViewById(R.id.checkbox4);

        Boolean check4 = pref.getBoolean("check4",false);
        //Boolean spinnerIsOn4 = pref.getBoolean("spinner4",false);
        //final Integer spinnerValue4 = pref.getInt("spinner4_val",0);

        checkBox4.setChecked(check4);
        //spinner4.setEnabled(spinnerIsOn4);

        //spinner 설정
        /*al4 = new ArrayList<>();
        al4.add("남성");
        al4.add("여성");

        aA4 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                al4);

        spinner4.setAdapter(aA4);
        spinner4.setSelection(spinnerValue4);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.setSelection(i);
                tmp4 = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        /*checkBox4.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox4.isChecked()) {
                    spinner4.setEnabled(true);
                } else {
                    spinner4.setEnabled(false);
                }
            }
        });*/
        /////////////////////////////////
        checkBox5 = (CheckBox)findViewById(R.id.checkbox5);

        Boolean check5 = pref.getBoolean("check5",false);
        Boolean spinnerIsOn5 = pref.getBoolean("spinner5",false);
        final Integer spinnerValue5 = pref.getInt("spinner5_val",0);

        checkBox5.setChecked(check5);
        spinner5.setEnabled(spinnerIsOn5);

        //spinner 설정
        al5 = new ArrayList<>();
        for(int i=0; i<add.length;i++) {
            al5.add(add[i]);
        }

        aA5 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                al5);

        spinner5.setAdapter(aA5);
        spinner5.setSelection(spinnerValue5);
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.setSelection(i);
                tmp5 = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        checkBox5.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox5.isChecked()) {
                    spinner5.setEnabled(true);
                } else {
                    spinner5.setEnabled(false);
                }
            }
        });
        ///////////////////////////////////////////////////

        //처리중
        progress_btn = (Button) findViewById(R.id.btn_progress);

        //Mqtt 처리
        progress_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendChat();
                Intent i = new Intent(Option.this, Progress.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        checkBox = (CheckBox)findViewById(R.id.checkbox1);
        spinner = (Spinner)findViewById(R.id.spinner1);

        editor.putBoolean("check1",checkBox.isChecked());
        editor.putBoolean("spinner1",spinner.isEnabled());
        editor.putInt("spinner1_val",tmp);

        checkBox2 = (CheckBox)findViewById(R.id.checkbox2);
        spinner2 = (Spinner)findViewById(R.id.spinner2);

        editor.putBoolean("check2",checkBox2.isChecked());
        editor.putBoolean("spinner2",spinner2.isEnabled());
        editor.putInt("spinner2_val",tmp2);

        checkBox3 = (CheckBox)findViewById(R.id.checkbox3);
        spinner3 = (Spinner)findViewById(R.id.spinner3);

        editor.putBoolean("check3",checkBox3.isChecked());
        editor.putBoolean("spinner3",spinner3.isEnabled());
        editor.putInt("spinner3_val",tmp3);

        checkBox4 = (CheckBox)findViewById(R.id.checkbox4);
        //spinner4 = (Spinner)findViewById(R.id.spinner4);

        editor.putBoolean("check4",checkBox4.isChecked());
        //editor.putBoolean("spinner4",spinner4.isEnabled());
        //editor.putInt("spinner4_val",tmp4);

        checkBox5 = (CheckBox)findViewById(R.id.checkbox5);
        spinner5 = (Spinner)findViewById(R.id.spinner5);

        editor.putBoolean("check5",checkBox5.isChecked());
        editor.putBoolean("spinner5",spinner5.isEnabled());
        editor.putInt("spinner5_val",tmp5);

        editor.commit();
    }

    private DisconnectedBufferOptions getDisconnectedBufferOptions() {
        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
        disconnectedBufferOptions.setBufferEnabled(true);
        disconnectedBufferOptions.setBufferSize(100);
        disconnectedBufferOptions.setPersistBuffer(true);
        disconnectedBufferOptions.setDeleteOldestMessages(false);
        Log.d("check","buffer option 들어감?");
        return disconnectedBufferOptions;
    }

    private MqttConnectOptions getMqttConnectionOption() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setWill("aaa", "I am going offline".getBytes(), 1, true);
        return mqttConnectOptions;
    }

    public void sendChat() {

        JSONObject json = new JSONObject();
        try {
            if(checkBox.isChecked()) {
                if (al.get(tmp).equals("10대 고객")) {
                    json.put("연령", "y_10_m_ratio");
                    json.put("연령", "y_10_m_won");
                    json.put("연령", "y_10_m_thing");
                }
                else if (al.get(tmp).equals("20대 고객")) {
                    json.put("연령", "y_20_m_ratio");
                    json.put("연령", "y_20_m_won");
                    json.put("연령", "y_20_m_thing");
                }
                else if (al.get(tmp).equals("30대 고객")) {
                    json.put("연령", "y_30_m_ratio");
                    json.put("연령", "y_30_m_won");
                    json.put("연령", "y_30_m_thing");
                }
                else if (al.get(tmp).equals("40대 고객")) {
                    json.put("연령", "y_40_m_ratio");
                    json.put("연령", "y_40_m_won");
                    json.put("연령", "y_40_m_thing");
                }
                else if (al.get(tmp).equals("50대 고객")) {
                    json.put("연령", "y_50_m_ratio");
                    json.put("연령", "y_50_m_won");
                    json.put("연령", "y_50_m_thing");
                }
                else if (al.get(tmp).equals("60대 고객")) {
                    json.put("연령", "y_60_m_ratio");
                    json.put("연령", "y_60_m_won");
                    json.put("연령", "y_60_m_thing");
                }
            } else {
                json.put("연령", "no_check");
            }

            if(checkBox2.isChecked()) {
                if (al2.get(tmp2).equals("00시 ~ 06시")) {
                    json.put("시간대", "time_00~06_m_ratio");
                    json.put("시간대", "time_00~06_m_won");
                    json.put("시간대", "time_thing~06_m_thing");
                }
                else if (al2.get(tmp2).equals("06시 ~ 11시")) {
                    json.put("시간대", "time_06~11_m_ratio");
                    json.put("시간대", "time_06~11_m_won");
                    json.put("시간대", "time_thing~11_m_thing");
                }
                else if (al2.get(tmp2).equals("11시 ~ 14시")) {
                    json.put("시간대", "time_11~14_m_ratio");
                    json.put("시간대", "time_11~14_m_won");
                    json.put("시간대", "time_thing~14_m_thing");
                }
                else if (al2.get(tmp2).equals("14시 ~ 17시")) {
                    json.put("시간대", "time_14~17_m_ratio");
                    json.put("시간대", "time_14~17_m_won");
                    json.put("시간대", "time_thing~17_m_thing");
                }
                else if (al2.get(tmp2).equals("17시 ~ 21시")) {
                    json.put("시간대", "time_17~21_m_ratio");
                    json.put("시간대", "time_17~21_m_won");
                    json.put("시간대", "time_thing~21_m_thing");
                }
                else if (al2.get(tmp2).equals("21시 ~ 24시")) {
                    json.put("시간대", "time_21~24_m_ratio");
                    json.put("시간대", "time_21~24_m_won");
                    json.put("시간대", "time_thing~24_m_thing");
                }
            } else {
                json.put("시간대", "no_check");
            }

            if(checkBox3.isChecked()) {
                if (al3.get(tmp3).equals("남성")) {
                    json.put("성별", "man_m_ratio");
                    json.put("성별", "man_m_won");
                    json.put("성별", "man_m_thing");
                }
                else if (al3.get(tmp3).equals("여성")) {
                    json.put("성별", "woman_m_ratio");
                    json.put("성별", "woman_m_won");
                    json.put("성별", "woman_m_thing");
                }
            } else {
                json.put("성별", "no_check");
            }

            if(checkBox4.isChecked()) {
                json.put("연매출", "month_m_won");
                json.put("연매출", "month_m_thing");
            } else {
                json.put("연매출", "no_check");
            }
                //json.put("성별", al4.get(tmp4));

            if(checkBox5.isChecked()) {
                if(!al5.get(tmp5).isEmpty()) {
                    json.put("지역", tmp5);
                }
            } else {
                json.put("지역", "no_check");
            }
            //mqttAndroidClient.publish("area"+tmp5, new MqttMessage(json.toString().getBytes()));
            mqttAndroidClient.publish("jmlee2", new MqttMessage(json.toString().getBytes()));
        } catch (Exception e) {
        }
        //mqttAndroidClient.setText("");
    }

    public void predict_result(String str) {

        String str2 = str.substring(str.indexOf("new_test"),str.indexOf("\","));
        String str3 = str2.substring(11,str2.length());

        //결과 표기

        if(hm.get(str3) == null){
            hm.put(str3,1);
        } else {
            hm.put(str3,hm.get(str3)+1);
        }

    }

    public static List sortByValue(final Map map){
        Log.e("test","들어갔는지 확인");
        List<String> list = new ArrayList();
        list.addAll(map.keySet());

        Collections.sort(list,new Comparator(){
            public int compare(Object o1,Object o2){
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);
                return ((Comparable) v1).compareTo(v2);
            }
        });

        Collections.reverse(list);

        for(int i=0; i<list.size(); i++) {
            if(i==3)
                break;
            one[i]=list.get(i);
        }
        Log.e("test123","여기에 들어감?");

        if(list.size()==1) {
            one[1]=addempty[1];
            one[2]=addempty[2];
        } else if(list.size() == 2) {
            one[2]=addempty[2];
        }

        for(int i=0; i<63; i++) {
            if(one[0].equals(cat_en[i])) {
                one[0] = cat_ko[i];
            }
            if(one[1].equals(cat_en[i])) {
                one[1] = cat_ko[i];
            }
            if(one[2].equals(cat_en[i])) {
                one[2] = cat_ko[i];
            }
        }

        Log.e("one1",one[0]);
        Log.e("one2",one[1]);
        Log.e("one3",one[2]);

        return list;
    }

}