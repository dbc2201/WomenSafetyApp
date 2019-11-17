package in.ac.gla.miniproject.womensafetyapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    int counter = 0;

    @Override
    public void onReceive(final Context context, Intent intent) {
        counter++;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (counter >= 3) {
                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("SEND_AUTO", "YES");
                    counter = 0;
                    return;
                }
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    Toast.makeText(context, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                }
                counter = 0;
            }

        }).start();
    }


}
