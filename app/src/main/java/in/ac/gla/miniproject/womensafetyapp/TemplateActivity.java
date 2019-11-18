package in.ac.gla.miniproject.womensafetyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import in.ac.gla.miniproject.womensafetyapp.adapters.MyAdapterTemplate;

public class TemplateActivity extends AppCompatActivity implements MyAdapterTemplate.DeleteListener, AdapterView.OnItemClickListener {
    ListView listTemplate;
    Button btnTemplate1;
    ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);


        listTemplate = findViewById(R.id.listTemplate);
        btnTemplate1 = findViewById(R.id.btnTemplate1);
        img1 = findViewById(R.id.img1);
        listTemplate.setOnItemClickListener(this);
        btnTemplate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(TemplateActivity.this, NewTemplateActivity.class);
                startActivity(i);

            }
        });
        loadData1();

    }

    private void loadData1() {
        MyDatabase obj = new MyDatabase(this);
        ArrayList<Templates> list1 = obj.loadTemplates();
        MyAdapterTemplate adapter1 = new MyAdapterTemplate(this, R.layout.item_template, list1);
        adapter1.setDeleteListener(this);
        listTemplate.setAdapter(adapter1);


        if (list1.size() > 0) {
            img1.setVisibility(View.GONE);
        } else {

            img1.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData1();
    }


    @Override
    public void deleteItem(Templates c) {

        MyDatabase db = new MyDatabase(this);
        if (db.deleteTemplate(c.getId())) {
            Toast.makeText(this, "DELETED SUCCESSFULLY", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "SOME ERROR OCCURRED", Toast.LENGTH_SHORT).show();

        }
        loadData1();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Templates templates = (Templates) adapterView.getItemAtPosition(i);
        Intent ii = new Intent(TemplateActivity.this, NewTemplateActivity.class);
        ii.putExtra("TEMPLATE", templates);
        startActivity(ii);

    }
}