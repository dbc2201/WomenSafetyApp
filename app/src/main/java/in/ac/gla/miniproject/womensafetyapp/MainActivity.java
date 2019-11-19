package in.ac.gla.miniproject.womensafetyapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

import in.ac.gla.miniproject.womensafetyapp.models.Contacts;
import in.ac.gla.miniproject.womensafetyapp.models.Templates;

public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    Button btnTemplate;
    private static final String LOCATION_URL = "http://maps.google.com/?q=LATITUDE,LONGITUDE";
    ImageView btnHelp;
    Location location;
    LocationManager mlocManager;
    private String myLocationUrl;
    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            MainActivity.this.location = location;
            String url = LOCATION_URL.replaceAll("LATITUDE", location.getLatitude() + "");
            url = LOCATION_URL.replaceAll("LONGITUDE", location.getLongitude() + "");
            setMyLocationUrl(url);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public static String getLocationUrl() {
        return LOCATION_URL;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        btnTemplate = findViewById(R.id.btnTemplate);
        btnHelp = findViewById(R.id.btnHelp);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(i);

            }
        });
        btnTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(MainActivity.this, TemplateActivity.class);
                startActivity(ii);

            }
        });
        demandPermission();
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getStringExtra("SEND_AUTO") != null) {
            sendSMS();
            finish();
        }
    }

    private void sendSMS() {
        SmsManager sms = SmsManager.getDefault();

        MyDatabase db = new MyDatabase(this);
        Templates template = db.loadPrimaryTemplate();
        ArrayList<Contacts> contactList = db.loadContacts();
        if (template != null && contactList.size() > 0) {
            String message = template.getMsg();
            message = message + "\n" + getLocationUrl();
            for (Contacts c : contactList) {

                sms.sendTextMessage(c.getNumber(), null, message, null, null);
            }
            Toast.makeText(this, "Message sent successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Either there is no contact or default template is not selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int menuid = item.getItemId();
        if (menuid == R.id.CommonHelplineNumbers) {


            Intent browserIntent = new Intent(this, HelplineNumbers.class);
            startActivity(browserIntent);
        }

        if (menuid == R.id.Help) {
            Intent browserIntent = new Intent(this, HelpActivity.class);
            startActivity(browserIntent);


        }
        if (menuid == R.id.About) {
            Intent browserIntent = new Intent(this, HelpActivity.class);
            startActivity(browserIntent);


        }




        if (menuid == R.id.Exit) {
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }

    void demandPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != 0 || ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != 0 || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != 0 || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != 0 || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != 0) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 7657);
            }
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != 0) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 7658);
//            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean granted = false;
        if (requestCode == 7657) {
            for (int i = 0; i < permissions.length; i++) {
                if ((permissions[i].equals(Manifest.permission.READ_CONTACTS) && grantResults[i] != 0) || (permissions[i].equals(Manifest.permission.READ_CONTACTS) && grantResults[i] != 0) || (permissions[i].equals(Manifest.permission.READ_PHONE_STATE) && grantResults[i] != 0) || (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION) && grantResults[i] != 0) || (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[i] != 0)) {
                    Toast.makeText(this, "sorry permission not granted", Toast.LENGTH_SHORT).show();
                    demandPermission();

                }
            }
            if (granted) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();

            }

        }
//        if (requestCode == 7658) {
//            for (int i = 0; i < permissions.length; i++) {
//                if (permissions[i].equals(Manifest.permission.SEND_SMS) && grantResults[i] != 0) {
//                    Toast.makeText(this, "sorry permission not granted", Toast.LENGTH_SHORT).show();
//                    demandPermission();
//
//                } else {
//                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializeLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mlocManager != null) {
            mlocManager.removeUpdates(listener);
        }
    }

    private void initializeLocation() {

//        LocationListener mlocListener = new YourLocationListener(getApplicationContext(), mobileNo, deviceId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean checkGPS = mlocManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // get network provider status
        boolean checkNetwork = mlocManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!checkGPS && !checkNetwork) {
            Toast.makeText(this, "No Service Provider is available", Toast.LENGTH_SHORT).show();
        } else {
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, listener);
        }
    }

    public void setMyLocationUrl(String myLocationUrl) {
        this.myLocationUrl = myLocationUrl;
    }
}
