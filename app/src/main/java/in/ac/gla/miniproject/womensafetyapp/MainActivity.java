package in.ac.gla.miniproject.womensafetyapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    Button btnTemplate;

    ImageView helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helpButton = findViewById(R.id.btnHelp);
        btnAdd = findViewById(R.id.btnAdd);
        btnTemplate = findViewById(R.id.btnTemplate);

    }
}
