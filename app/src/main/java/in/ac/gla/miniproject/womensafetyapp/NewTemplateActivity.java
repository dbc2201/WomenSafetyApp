package in.ac.gla.miniproject.womensafetyapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewTemplateActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et1;
    Button btn1;
    CheckBox cbDefault;
    Templates templates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_template);
        et1 = findViewById(R.id.et1);
        btn1 = findViewById(R.id.btn1);
        cbDefault = findViewById(R.id.cbDefault);
        btn1.setOnClickListener(this);

        templates = (Templates) getIntent().getSerializableExtra("TEMPLATE");
        if (templates != null) {
            et1.setText(templates.getMsg() + templates.getId());
            cbDefault.setChecked(templates.isActive());
        }
    }

    @Override
    public void onClick(View view) {
        if (templates == null) {
            templates = new Templates();
        }
//        Templates obj = new Templates();
//obj.setActive();
        templates.setMsg(et1.getText().toString());
        templates.setActive(cbDefault.isChecked());
        MyDatabase db = new MyDatabase(this);

        if (templates.getId() <= 0 ? db.saveTemplate(templates) : db.updateTemplate(templates)) {
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Not added", Toast.LENGTH_SHORT).show();
        }
    }
}