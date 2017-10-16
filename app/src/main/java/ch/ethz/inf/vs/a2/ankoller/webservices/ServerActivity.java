package ch.ethz.inf.vs.a2.ankoller.webservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    private static final String BUTTON_STATE = "ch.ethz.inf.vs.a2.ankoller.webservices.Button";
    private static final String IP_STATE = "ch.ethz.inf.vs.a2.ankoller.webservices.IP";
    private static final String PORT_STATE = "ch.ethz.inf.vs.a2.ankoller.webservices.PORT";
    private static final String DEFAULT_PORT = "1234";
    private static final String DEFAULT_IP = "0.0.0.0";

    private ToggleButton button_Server;
    private TextView tv_port, tv_ip;

    private localBroadcastReceiver lbr; // broadcast receiver

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        button_Server = (ToggleButton) findViewById(R.id.toggleButton);
        tv_port = (TextView) findViewById(R.id.port_address_view);
        tv_ip = (TextView) findViewById(R.id.ip_address_view);


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

    @Override
    protected void onPause() {
        super.onPause();

        // Save settings
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        editor.putBoolean(BUTTON_STATE, button_Server.isChecked());
        editor.putString(IP_STATE, tv_ip.getText().toString());
        editor.putString(PORT_STATE, tv_port.getText().toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Retrieve settings
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        button_Server.setChecked(sharedPreferences.getBoolean(BUTTON_STATE, false));
        tv_ip.setText(sharedPreferences.getString(IP_STATE, DEFAULT_IP));
        tv_port.setText(sharedPreferences.getString(PORT_STATE, DEFAULT_PORT));
    }

    public class localBroadcastReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            tv_port.setText("" + intent.getIntExtra("PORT", 1234)); // default value 1234
            tv_ip.setText(intent.getStringExtra("IP"));
        }
    }

    public void onButtonClick (View view){
        if (button_Server.isChecked()){
            startService(new Intent(this, Server.class));
        }
        else {
            stopService(new Intent(this, Server.class));
            tv_port.setText(DEFAULT_PORT);
            tv_ip.setText(DEFAULT_IP);
        }
    }
}
