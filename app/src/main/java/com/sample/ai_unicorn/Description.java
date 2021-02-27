package com.sample.ai_unicorn;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.eazegraph.lib.charts.BarChart;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Description extends AppCompatActivity implements View.OnClickListener{

    BottomNavigationView bottomNavigationView;

    private FloatingActionButton fab_main,fab_sub1,fab_sub2,fab_sub3;
    private Animation fab_open,fab_close;
    private boolean isFabOpen = false;
    BarChart mBarChart;

    FragmentManager manager;
    FragmentTransaction ft;

    Frag1 frag1;
    Frag2 frag2;
    Frag3 frag3;

    SQLiteDatabase database;

    HashMap<String, Float> hm1 = new HashMap<>();
    HashMap<String, Float> hm2 = new HashMap<>();
    HashMap<String, Float> hm3 = new HashMap<>();

    Bundle bundle,bundle2,bundle3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavi);
        bottomNavigationView.setSelectedItemId(R.id.icbase);
        Context mContext;

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.icquestion:
                        Intent i1 = new Intent(Description.this,request.class);
                        startActivity(i1);
                        break;

                    case R.id.icmap:
                        Intent i2 = new Intent(Description.this, MainActivity.class);
                        startActivity(i2);
                        break;

                    case R.id.icCustom:
                        Intent i3 = new Intent(Description.this, Option.class);
                        startActivity(i3);
                        break;
                }
            }
        });

        mContext = getApplicationContext();
        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);

        fab_main = (FloatingActionButton) findViewById(R.id.main_float);
        fab_sub1 = (FloatingActionButton) findViewById(R.id.sub_float);
        fab_sub2 = (FloatingActionButton) findViewById(R.id.sub_float2);
        fab_sub3 = (FloatingActionButton) findViewById(R.id.sub_float3);

        fab_main.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);
        fab_sub2.setOnClickListener(this);
        fab_sub3.setOnClickListener(this);

        //초깃값 설정
        fab_sub1.startAnimation(fab_close);
        fab_sub2.startAnimation(fab_close);
        fab_sub3.startAnimation(fab_close);
        fab_sub1.setClickable(false);
        fab_sub2.setClickable(false);
        fab_sub3.setClickable(false);
        isFabOpen = false;
        fab_main.setImageResource(R.drawable.ic_add);

        boolean bResult = isCheckDatabase();    // DB가 있는지?

        if (!bResult) {  // DB가 없으면 복사
            Log.d("check","database copy");
            copyDataBase();
        }

        openDatabase();

        Cursor c = database.rawQuery("select * from '2'",null);
        c.move(0);

        for(int i=0; i<63; i++) {
            c.moveToNext();
            String s = c.getString(0);

            Float f1 = c.getFloat(1);
            Float f2 = c.getFloat(43) + c.getFloat(44) + c.getFloat(45);
            Float f3 = c.getFloat(46) + c.getFloat(47) + c.getFloat(48);

            hm1.put(s,f1);
            hm2.put(s,f2);
            hm3.put(s,f3);

            Log.d("testdb",s);
        }

        Iterator it1 = sortByValue(hm1).iterator();
        Iterator it2 = sortByValue(hm2).iterator();
        Iterator it3 = sortByValue(hm3).iterator();

        int cnt =0;

        manager = getSupportFragmentManager();

        frag1 = new Frag1();
        frag2 = new Frag2();
        frag3 = new Frag3();

        bundle = new Bundle();
        bundle2 = new Bundle();
        bundle3 = new Bundle();

        while(it1.hasNext()) {
            String t = (String) it1.next();

            bundle.putString("s"+cnt,t);
            bundle.putFloat("f"+cnt,hm1.get(t));

            if(cnt != 6) cnt++;
            else break;
        }

        cnt = 0;

        while(it2.hasNext()) {
            String t = (String) it2.next();

            bundle2.putString("s"+cnt,t);
            bundle2.putFloat("f"+cnt,hm2.get(t));

            if(cnt != 6) cnt++;
            else break;
        }

        cnt = 0;

        while(it3.hasNext()) {
            String t = (String) it3.next();

            bundle3.putString("s"+cnt,t);
            bundle3.putFloat("f"+cnt,hm3.get(t));

            if(cnt != 6) cnt++;
            else break;
        }

        frag1.setArguments(bundle);
        frag2.setArguments(bundle2);
        frag3.setArguments(bundle3);

        ft = manager.beginTransaction();
        ft.add(R.id.frag_container, frag1);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        manager = getSupportFragmentManager();
        ft = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.main_float:
                toggleFab();
                break;
            case R.id.sub_float:
                toggleFab();
                ft.replace(R.id.frag_container, frag3);
                ft.commit();
                break;
            case R.id.sub_float2:
                toggleFab();
                ft.replace(R.id.frag_container, frag2);
                ft.commit();
                break;
            case R.id.sub_float3:
                toggleFab();
                ft.replace(R.id.frag_container, frag1);
                ft.commit();
                break;
        }
    }

    private void toggleFab() {
        if (isFabOpen) {
            fab_main.setImageResource(R.drawable.ic_add);

            fab_sub1.startAnimation(fab_close);
            fab_sub2.startAnimation(fab_close);
            fab_sub3.startAnimation(fab_close);
            fab_sub1.setClickable(false);
            fab_sub2.setClickable(false);
            fab_sub3.setClickable(false);
            isFabOpen = false;
        } else {
            fab_main.setImageResource(R.drawable.ic_close);
            fab_sub1.setImageResource(R.drawable.old);
            fab_sub2.setImageResource(R.drawable.young);
            fab_sub3.setImageResource(R.drawable.money);
            fab_sub1.startAnimation(fab_open);
            fab_sub2.startAnimation(fab_open);
            fab_sub3.startAnimation(fab_open);
            fab_sub1.setClickable(true);
            fab_sub2.setClickable(true);
            fab_sub3.setClickable(true);
            isFabOpen = true;
        }
    }

    public boolean isCheckDatabase() {
        String filePath = "/data/data/com.sample.ai_unicorn" + "/databases/" + "test5.db";
        File file = new File(filePath);
        return file.exists();
    }

    public void copyDataBase() {
        Context mContext = getApplicationContext();
        AssetManager manager = mContext.getAssets();
        String folderPath = "/data/data/com.sample.ai_unicorn" + "/databases";
        String filePath = "/data/data/com.sample.ai_unicorn" + "/databases/" + "test5.db";
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fileOut = null;
        BufferedOutputStream bufferOut = null;
        try {
            InputStream inputStr = manager.open("db/" + "test5.db");
            BufferedInputStream bufferStr = new BufferedInputStream(inputStr);
            if (folder.exists()) {
            } else {
                folder.mkdirs();
            }

            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }

            fileOut = new FileOutputStream(file);
            bufferOut = new BufferedOutputStream(fileOut);
            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = bufferStr.read(buffer, 0, 1024)) != -1) {
                bufferOut.write(buffer, 0, read);
            }
            bufferOut.flush();
            bufferOut.close();
            fileOut.close();
            bufferStr.close();
            inputStr.close();
        } catch (IOException e) {
            Log.e("Error : ", e.getMessage());
        }
    }

    public void openDatabase() {
        database = openOrCreateDatabase("test5.db", MODE_PRIVATE, null);
        if (database != null) {
            //      Toast.makeText(this.getApplicationContext(), "데이터베이스가 존재합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public static List sortByValue(final Map map) {

        List<String> list = new ArrayList();
        list.addAll(map.keySet());

        Collections.sort(list,new Comparator() {
            public int compare(Object o1,Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);
                return ((Comparable) v2).compareTo(v1);
            }
        });
        return list;
    }
}