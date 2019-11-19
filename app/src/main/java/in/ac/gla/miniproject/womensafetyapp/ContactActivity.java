package in.ac.gla.miniproject.womensafetyapp;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import in.ac.gla.miniproject.womensafetyapp.adapters.MyAdapter;
import in.ac.gla.miniproject.womensafetyapp.models.Contacts;

public class ContactActivity extends Activity implements View.OnClickListener, MyAdapter.DeleteListener {
    private static final int RQS_PICK_CONTACT = 1131;
    ListView listNumbers;
    Button btnAdd1;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        listNumbers = findViewById(R.id.listNumbers);
        btnAdd1 = findViewById(R.id.btnAdd1);
        img = findViewById(R.id.img);
        btnAdd1.setOnClickListener(this);

        loadData();

    }

    private void loadData() {
        MyDatabase obj = new MyDatabase(this);
        ArrayList<Contacts> list = obj.loadContacts();
        MyAdapter adapter = new MyAdapter(this, R.layout.item_contact, list);
        adapter.setDeleteListener(this);
        listNumbers.setAdapter(adapter);


        if (list.size() > 0) {
            img.setVisibility(View.GONE);
        } else {

            img.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        if (view == btnAdd1) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            startActivityForResult(intent, RQS_PICK_CONTACT);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQS_PICK_CONTACT) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor cursor = managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();

                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Contacts obj = new Contacts();
                obj.setName(name);
                obj.setNumber(number);

                MyDatabase db = new MyDatabase(this);
                if (db.saveContact(obj)) {

                    Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Not added", Toast.LENGTH_SHORT).show();
                }
                loadData();
                //contactName.setText(name);
//                contactNumber.setText(number);
                //contactEmail.setText(email);
            }
        }
    }

    @Override
    public void deleteItem(Contacts c) {
        MyDatabase db = new MyDatabase(this);
        if (db.deleteContact(c.getNumber())) {
            Toast.makeText(this, "DELETED SUCCESSFULLY", Toast.LENGTH_SHORT).show();

            loadData();
        } else {
            Toast.makeText(this, "SOME ERROR OCCURED", Toast.LENGTH_SHORT).show();

        }
    }


}
