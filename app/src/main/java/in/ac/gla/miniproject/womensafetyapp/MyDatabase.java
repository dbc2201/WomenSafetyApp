package in.ac.gla.miniproject.womensafetyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import in.ac.gla.miniproject.womensafetyapp.models.Contacts;
import in.ac.gla.miniproject.womensafetyapp.models.Templates;

public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(Context context) {
        super(context, "myDb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contacts(name varchar,number varchar primary key)");
        db.execSQL("create table templates(id integer primary key autoincrement,msg longtext,active tinyint(1))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE contacts");
            db.execSQL("DROP TABLE templates");
            onCreate(db);

        }

    }

    public boolean saveContact(Contacts contact) {
        boolean status = false;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contact.getName());
        values.put("number", contact.getNumber());
        long id = db.insert("contacts", "", values);
        if (id > 0) {
            status = true;
        }
        return status;
    }

    public ArrayList<Contacts> loadContacts() {

        ArrayList<Contacts> contactList = new ArrayList<Contacts>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from contacts", null);
        while (cursor.moveToNext()) {
            Contacts obj = new Contacts();
            obj.setName(cursor.getString(cursor.getColumnIndex("name")));
            obj.setNumber(cursor.getString(cursor.getColumnIndex("number")));
            contactList.add(obj);

        }
        return contactList;
    }

    public boolean saveTemplate(Templates template) {
        boolean status = false;
        ContentValues values = new ContentValues();
        if (template.isActive()) {
            values.put("active", 1);
            clearPrimaryFlag();
        } else {
            values.put("active", 0);

        }
        SQLiteDatabase db = getWritableDatabase();
        values.put("msg", template.getMsg());
        long id = db.insert("templates", "", values);
        if (id > 0) {
            status = true;
        }
        return status;
    }

    public boolean updateTemplate(Templates template) {
        boolean status = false;
        ContentValues values = new ContentValues();
        if (template.isActive()) {
            values.put("active", 1);
            clearPrimaryFlag();
        } else {
            values.put("active", 0);

        }
        SQLiteDatabase db = getWritableDatabase();
        values.put("msg", template.getMsg());
        long id = db.update("templates", values, "id=?", new String[]{template.getId() + ""});
        if (id > 0) {
            status = true;
        }
        return status;
    }

    public boolean clearPrimaryFlag() {
        boolean status = false;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update templates set active=0");
        return status;
    }

    public ArrayList<Templates> loadTemplates() {
        ArrayList<Templates> templateList = new ArrayList<Templates>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from templates", null);
        while (cursor.moveToNext()) {
            Templates obj = new Templates();
            obj.setId(cursor.getInt(cursor.getColumnIndex("id")));
            obj.setMsg(cursor.getString(cursor.getColumnIndex("msg")));
            obj.setActive(cursor.getInt(cursor.getColumnIndex("active")) == 1);
            templateList.add(obj);

        }
        return templateList;
    }


    public Templates loadPrimaryTemplate() {
        Templates obj = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from templates where active=1", null);
        if (cursor.moveToNext()) {
            obj = new Templates();
            obj.setId(cursor.getInt(cursor.getColumnIndex("id")));
            obj.setMsg(cursor.getString(cursor.getColumnIndex("msg")));

        }
        return obj;
    }

    public boolean deleteTemplate(int id) {
        boolean status = false;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from templates where id=" + id);
        status = true;
        return status;
    }

    public boolean deleteContact(String number) {
        boolean status = false;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from contacts where number='" + number + "'");
        status = true;
        return status;
    }
}