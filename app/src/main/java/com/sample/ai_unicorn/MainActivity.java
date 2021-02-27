package com.sample.ai_unicorn;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener {

    private GoogleMap gmap;
    private Geocoder geocoder;
    public String pos_add;
    public String rank;
    public String first;
    public String second;
    public String third;
    MenuItem mSearch;
    FusedLocationProviderClient mFusedLocationClient;

    private BottomNavigationView bottomNavigationView;

    public Boolean b1;
    public Boolean b2;
    public Boolean b3;
    public Boolean b4;

    public static String f,s,t;
    public static String adr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("LifeCycle", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("A.I Unicorn");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 102);
            }
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavi);
        bottomNavigationView.setSelectedItemId(R.id.icmap);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.icquestion:
                        Intent i1 = new Intent(MainActivity.this, request.class);
                        startActivity(i1);
                        break;

                    case R.id.icbase:
                        Intent i2 = new Intent(MainActivity.this, Description.class);
                        startActivity(i2);
                        break;

                    case R.id.icCustom:
                        Intent i3 = new Intent(MainActivity.this, Option.class);
                        startActivity(i3);
                        break;
                }
            }
        });

        Intent intent = getIntent();

        try {
            b1 = intent.getExtras().getBoolean("c1");
            b2 = intent.getExtras().getBoolean("c2");
            f = intent.getExtras().getString("one0");
            s = intent.getExtras().getString("one1");
            t = intent.getExtras().getString("one2");
            adr = intent.getExtras().getString("add");
        } catch (NullPointerException e) {
            b1 = true;
            b2 = true;
            f = "no";
            s = "no";
            t = "no";
            adr = "no";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //상단바를 이용한 검색 기능
        super.onCreateOptionsMenu(menu);
        Log.d("LifeCycle", "onCreateOptionsMenu");
        //search_menu.xml 등록
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        mSearch = menu.findItem(R.id.search);
        SearchView sv = (SearchView) mSearch.getActionView();

        //SearchView의 검색 이벤트
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //검색버튼을 눌렀을 경우
            @Override
            public boolean onQueryTextSubmit(String query) {

                String latitude;
                String longitude;
                String str = query;
                List<Address> addressList = null;
                try {
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            10); // 최대 검색 결과 개수
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 콤마를 기준으로 split
                try {
                    Log.d("address", addressList.get(0).toString());
                    String[] splitStr = addressList.get(0).toString().split(",");
                    String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소
                    String[] splitadd = address.split(" ");
                    pos_add = splitadd[2];
                    Log.d("pos",pos_add);
                    System.out.println(address);

                    latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                    longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                    System.out.println(latitude);
                    System.out.println(longitude);

                    // 좌표(위도, 경도) 생성
                    LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                    // 해당 좌표로 화면 줌
                    gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));

                    //검색된 곳으로 좌표를 찍고 좌표 메모 추가 및 정보창 이동
                    MarkerOptions mOptions2 = new MarkerOptions();
                    setrank(pos_add);

                    mOptions2.title(pos_add);

                    if (!f.equals("no") && pos_add.equals(adr)) {
                        rank = "";
                        rank += "1순위 " + f + "\n" + "2순위 " + s + "\n" + "3순위 " + t;
                    }
                    mOptions2.snippet(rank);
                    mOptions2.position(point);
                    gmap.addMarker(mOptions2);

                    gmap.setOnInfoWindowClickListener(infoWindowClickListener);

                    gmap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                        @Override
                        public View getInfoWindow(Marker marker) {
                            return null;
                        }

                        @Override
                        public View getInfoContents(Marker marker) {
                            LinearLayout info = new LinearLayout(getApplicationContext());
                            info.setOrientation(LinearLayout.VERTICAL);

                            TextView title = new TextView(getApplicationContext());
                            title.setTextColor(Color.BLACK);
                            title.setGravity(Gravity.CENTER);
                            title.setTypeface(null, Typeface.BOLD);
                            title.setText(marker.getTitle());

                            TextView snippet = new TextView(getApplicationContext());
                            snippet.setTextColor(Color.GRAY);
                            snippet.setGravity(Gravity.LEFT);
                            snippet.setText(marker.getSnippet());

                            info.addView(title);
                            info.addView(snippet);

                            return info;
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "없는 주소입니다.", Toast.LENGTH_LONG).show();
                }

                return true;
            }

            GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(MainActivity.this, test.class);
                    if(f.equals("no")) {
                        intent.putExtra("position",pos_add);
                        intent.putExtra("first", first);
                        intent.putExtra("second", second);
                        intent.putExtra("third", third);
                        Log.e("dd",pos_add + " " + first + " " + second + " " + third);
                    } else {
                        intent.putExtra("position",adr);
                        intent.putExtra("first",f);
                        intent.putExtra("second", s);
                        intent.putExtra("third", t);
                        Log.e("dd",adr + " " + f + " " + s + " " + t);
                    }
                    intent.putExtra("graph1",b1);
                    intent.putExtra("graph2",b2);
                    startActivity(intent);
                }
            };

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("check", s);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("LifeCycle", "onMapReady");
        gmap = googleMap;
        geocoder = new Geocoder(this);

        setDefaultLocation(); // 초기 맵을 서울로 설정

        gmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {//키보드 숨김
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //키보드 관련 method
                if (getCurrentFocus() instanceof EditText) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //EditText가 아니면 키보드 숨김
                    getCurrentFocus().clearFocus();
                }
            }
        });
    }

    private void setDefaultLocation() {
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        gmap.moveCamera(cameraUpdate);

        //현재 위치 버튼 생성
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);//현재 위치 설정을 위한 클라이언트 생성
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //가끔 null error가 뜸.
                double d1;
                double d2;

                if(location == null) {
                    d1 = 0.0;
                    d2 = 0.0;
                } else {
                    d1 = location.getLatitude();
                    d2 = location.getLongitude();
                }

                List<Address> list = null;
                try {
                    list = geocoder.getFromLocation(
                            d1, // 위도
                            d2, // 경도
                            10); // 얻어올 값의 개수
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (list != null) {
                    if (list.size()==0) {
                    } else {
                        String[] s = list.get(0).toString().split(",");
                        String[] s2 = s[0].split(" ");
                        pos_add = s2[2];
                        Log.d("pos",pos_add);
                    }
                }
                //현재 위치로 마커를 찍고 좌표 메모 추가 및 정보창 이동
                LatLng point = new LatLng(d1, d2);
                MarkerOptions mOptions2 = new MarkerOptions();
                setrank(pos_add);

                mOptions2.title(pos_add);

                try{
                    if (!f.equals("no")&& pos_add.equals(adr)) {
                    rank = "";
                    rank += "1순위 " + f + "\n" + "2순위 " + s + "\n" + "3순위 " + t;
                    }
                } catch (java.lang.NullPointerException e) {
                    e.printStackTrace();
                }

                mOptions2.snippet(rank);
                mOptions2.position(point);
                gmap.addMarker(mOptions2);

                gmap.setOnInfoWindowClickListener(infoWindowClickListener);

                gmap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        LinearLayout info = new LinearLayout(getApplicationContext());
                        info.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(getApplicationContext());
                        title.setTextColor(Color.BLACK);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.BOLD);
                        title.setText(marker.getTitle());

                        TextView snippet = new TextView(getApplicationContext());
                        snippet.setTextColor(Color.GRAY);
                        snippet.setGravity(Gravity.LEFT);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);

                        return info;
                    }
                });

            }

            GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(MainActivity.this, test.class);
                    intent.putExtra("position",pos_add);
                    intent.putExtra("first",first);
                    intent.putExtra("second",second);
                    intent.putExtra("third",third);

                    intent.putExtra("graph1",b1);
                    intent.putExtra("graph2",b2);

                    startActivity(intent);
                }
            };

        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gmap.setMyLocationEnabled(true);
    }

    public void onMyLocationChange(Location location) {
        double d1=location.getLatitude();
        double d2=location.getLongitude();
        String s = location.toString();
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(d1, d2), 18));
    }

    public void setrank(String pos_add) {

        //json 파일 읽어와서 분석하기

        //assets폴더의 파일을 가져오기 위해
        //창고관리자(AssetManager) 얻어오기
        AssetManager assetManager = getAssets();

        //assets/ test.json 파일 읽기 위한 InputStream
        try {
            InputStream is = assetManager.open("jsons/ksb.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }

            String jsonData = buffer.toString();

            //읽어온 json문자열 확인
            //tv.setText(jsonData);

            //json 분석
            //json 객체 생성
//            JSONObject jsonObject= new JSONObject(jsonData);
//            String name= jsonObject.getString("name");
//            String msg= jsonObject.getString("msg");
//
//            tv.setText("이름 : "+name+"\n"+"메세지 : "+msg);

            //json 데이터가 []로 시작하는 배열일때..
            JSONArray jsonArray = new JSONArray(jsonData);

            rank = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                String name = jo.getString("구");
                if(name.equals(pos_add)) {
                    JSONObject flag = jo.getJSONObject("순위");
                    first = flag.getString("1");
                    second = flag.getString("2");
                    third = flag.getString("3");
                    rank += "1순위 " + first + "\n" + "2순위 " + second + "\n" + "3순위 " + third;
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //back button으로 task 죽이기
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finishAndRemoveTask();
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onBackPressed();
    }
}