package in.ac.gla.miniproject.womensafetyapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity implements Runnable {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread x = new Thread(this);
        x.start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            Toast.makeText(this, "ERROR OCCURED", Toast.LENGTH_SHORT).show();
        }
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}


