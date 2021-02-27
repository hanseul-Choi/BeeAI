package com.sample.ai_unicorn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.daimajia.numberprogressbar.OnProgressBarListener;

import java.util.Timer;
import java.util.TimerTask;

public class Progress extends AppCompatActivity implements OnProgressBarListener {

    private NumberProgressBar bnp;
    private Timer timer;

    private boolean noreset = true;

    @Override
    public void onProgressChange(int current, int max) {
        if(current == max && noreset) {
            Toast.makeText(getApplicationContext(), "완료했습니다. ", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Progress.this, Option.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            bnp.setProgress(0);
            noreset = false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);

        bnp = (NumberProgressBar)findViewById(R.id.numberbar1);
        bnp.setOnProgressBarListener(this);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bnp.incrementProgressBy(1);
                    }
                });
            }
        }, 100, 100);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
