package ch.ethz.inf.vs.a2.ankoller.webservices;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Qais on 16-Oct-17.
 */

public class Server extends Service implements SensorEventListener {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
