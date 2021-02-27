package com.sample.ai_unicorn;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class request extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavi);
        bottomNavigationView.setSelectedItemId(R.id.icquestion);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.icmap:
                        Intent i1 = new Intent(request.this, MainActivity.class);
                        startActivity(i1);
                        break;

                    case R.id.icbase:
                        Intent i2 = new Intent(request.this, Description.class);
                        startActivity(i2);
                        break;

                    case R.id.icCustom:
                        Intent i3 = new Intent(request.this, Option.class);
                        startActivity(i3);
                        break;
                }
            }
        });

        btn = (Button)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"email@address.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "hschoi5542@gmail.com");
                email.putExtra(Intent.EXTRA_TEXT, "문의사항을 작성해주세요");
                startActivity(email);
            }
        });
    }
}
