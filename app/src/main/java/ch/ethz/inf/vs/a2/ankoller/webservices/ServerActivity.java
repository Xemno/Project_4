package ch.ethz.inf.vs.a2.ankoller.webservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;

public class ServerActivity extends AppCompatActivity {

    private static final String TAG = "Server Activity: ";

    private ToggleButton button_Server;
    private TextView tv_port, tv_server;

    private localBroadcastReceiver lbr; // broadcast receiver

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        button_Server = (ToggleButton) findViewById(R.id.toggleButton);
        tv_port = (TextView) findViewById(R.id.port_address_view);
        tv_server = (TextView) findViewById(R.id.ip_address_view);


        /* Now we show the Network Interfaces */
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface nI : Collections.list(nets)){
                Log.i(TAG, "Display name: " + nI.getDisplayName());
                Log.i(TAG, "Name: " + nI.getName());
                for (Enumeration<InetAddress> eia = nI.getInetAddresses(); eia.hasMoreElements();){
                    InetAddress ia = eia.nextElement();
                    if (!ia.isLoopbackAddress() && ia.getHostAddress().length() <= 16){
                        Log.i(TAG, ia.getHostAddress());
                    }
                }
            }
        } catch (Exception e) {}

    }

    public class localBroadcastReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            tv_port.setText("" + intent.getIntExtra("PORT", 1234)); // default value 1234
            tv_server.setText(intent.getStringExtra("IP"));
        }
    }

    public void clicked_tbServer(View view){
        if (button_Server.isChecked()){
            startService(new Intent(this, Server.class));
        }
        else {
            stopService(new Intent(this, Server.class));
            tv_port.setText("0.0.0.0");
            tv_server.setText("1234");
        }
    }
}
