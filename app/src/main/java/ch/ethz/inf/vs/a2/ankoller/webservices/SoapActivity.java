package ch.ethz.inf.vs.a2.ankoller.webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.SensorListener;

public class SoapActivity extends AppCompatActivity implements SensorListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soap2);
    }


    @Override
    public void onReceiveSensorValue(double value){

    }

    @Override
    public void onReceiveMessage(String message) {

    }
}
