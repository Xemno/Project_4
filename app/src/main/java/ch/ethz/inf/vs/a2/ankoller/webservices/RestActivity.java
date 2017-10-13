package ch.ethz.inf.vs.a2.ankoller.webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.SensorListener;

public class RestActivity extends AppCompatActivity implements SensorListener{
    //TODO implement methods, this activity has to implement the interface sensorlistener
    // so that activity can be informed upon reception of the temperature value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
    }

    @Override
    public void onReceiveSensorValue(double value) {

    }

    @Override
    public void onReceiveMessage(String message) {

    }
}
