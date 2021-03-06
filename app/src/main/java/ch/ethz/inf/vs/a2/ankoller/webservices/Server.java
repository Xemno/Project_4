package ch.ethz.inf.vs.a2.ankoller.webservices;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 * Created by Qais on 16-Oct-17.
 */

public class Server extends Service implements SensorEventListener {

    private static final String TAG = "#Server Service: ";
    public static final String IP_KEY = "ch.ethz.inf.vs.a2.ankoller.webservices.IPKEY";
    public static final String PORT_KEY = "ch.ethz.inf.vs.a2.ankoller.webservices.IPPORT";

    private static final int PORT = 8088;
    private static String ip_addr;

    private static SensorManager sensorMngr;
    public static List<Sensor> sensorList;
    private static Vibrator vibrator;
    private static MediaPlayer mediaPlayer;
    public static Vector<String> sensorValues;
    public static Vector<String> sensorNames;

    public static boolean is_vibrating;
    public static boolean is_playing;

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(this, "Server service started!", Toast.LENGTH_SHORT).show();

        switch (ip_addr = getIP()) {
            case ":" : Log.i(TAG, "No connection!"); break;
            default  : Log.i(TAG, "IP address is: " + ip_addr);
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ServerActivity.BROADCAST).putExtra(IP_KEY, ip_addr).putExtra(PORT_KEY, PORT));

        sensorMngr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = sensorMngr.getSensorList(Sensor.TYPE_ALL);
        sensorValues = new Vector<>(sensorList.size());
        sensorNames = getSensorNames();

        int i = 0;
        for (Sensor sensor : sensorList){
            sensorMngr.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            sensorValues.add("0 " + Response.getUnitString(sensor.getType()));
            i += 1;
        }

        if (vibrator == null){
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            is_vibrating = false;
        }

        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.sound);
            mediaPlayer.setLooping(true);
            is_playing = false;
        }

        Thread thread = new Thread(new ServerThread(this, ip_addr, PORT));
        thread.start();
    }

    private String getIP(){
        try {
            Enumeration<NetworkInterface> netInts = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface nI : Collections.list(netInts)){
                Log.i(TAG, "Display name: " + nI.getDisplayName());
                Log.i(TAG, "Name: " + nI.getName());
                for (Enumeration<InetAddress> eia = nI.getInetAddresses(); eia.hasMoreElements();){
                    InetAddress ia = eia.nextElement();
                    if (!ia.isLoopbackAddress() && ia.getHostAddress().length() <= 16){
                        Log.i(TAG, "Host Address: " + ia.getHostAddress());
                        return ia.getHostAddress();
                    }
                }
            }
        } catch (SocketException se) {Log.i(TAG, se.toString());}
        return ":";
    }

    public static void vibrate(Boolean b){
        // vibrate vibrator
        if (b) {
            //vibrator.vibrate(VibrationEffect.createOneShot(10000, VibrationEffect.DEFAULT_AMPLITUDE));
            vibrator.vibrate(500);
            is_vibrating = true;
        } else {
            vibrator.cancel();
            is_vibrating = false;

        }

    }

    public static void playMedia (Boolean b){
        if (b){
            if (!mediaPlayer.isPlaying()) mediaPlayer.start();
            is_playing = true;
        }
        else {
            if (mediaPlayer.isPlaying()) mediaPlayer.pause();
            is_playing = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        String text = "";
        for (int i = 0; i < sensorEvent.values.length; i++){
            text += String.valueOf(sensorEvent.values[i]) + " " + Response.getUnitString(sensorEvent.sensor.getType()) + " ";
        }
        sensorValues.set(sensorList.indexOf(sensorEvent.sensor), text);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        for (Sensor sensor : sensorList){
            sensorMngr.unregisterListener(this, sensor);
        }

        if (ServerThread.serverSocket != null){
            try {
                ServerThread.serverSocket.close();
                ServerThread.serverSocket = null;
            }
            catch (IOException e){
                Log.i(TAG, e.toString());
            }
        }

        mediaPlayer.stop();
        if (vibrator != null) vibrator.cancel();
        if (mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = null;
        vibrator = null;
        is_playing = false;
        is_vibrating = false;
    }

    public static String getSensorValue(String sName){
        return sensorValues.get(sensorNames.indexOf(sName));
    }

    private Vector<String> getSensorNames(){
        Vector<String> vector = new Vector<>();
        for (Sensor sensor : sensorList){
            vector.add(sensor.getName());
        }
        return vector;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
