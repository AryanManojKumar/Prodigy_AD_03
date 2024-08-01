package com.example.stop_watch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stop_watch.R;

public class MainActivity extends AppCompatActivity {

    private TextView tvTimer;
    private Button btnStart, btnStop, btnReset;
    private Handler handler = new Handler();
    private long startTime = 0;
    private boolean isRunning = false;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            tvTimer.setText(String.format("%02d:%02d:%02d", minutes, seconds, millis % 1000 / 10));
            handler.postDelayed(this, 10);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tvTimer);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnReset = findViewById(R.id.btnReset);

        Button closeButton = findViewById(R.id.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close this activity
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                handler.postDelayed(runnable, 0);
                isRunning = true;
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
                btnReset.setEnabled(true);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                isRunning = false;
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                isRunning = false;
                tvTimer.setText("00:00:00");
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                btnReset.setEnabled(false);
            }
        });
    }
}
