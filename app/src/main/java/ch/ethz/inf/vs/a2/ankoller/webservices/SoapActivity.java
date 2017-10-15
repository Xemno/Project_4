package ch.ethz.inf.vs.a2.ankoller.webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.SensorListener;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.XmlSensor;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.AbstractSensor;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.SoapSensor;

public class SoapActivity extends AppCompatActivity implements SensorListener{


    XmlSensor xmlSensor;
    SoapSensor soapSensor;
    AbstractSensor sensor;
    TextView valueView;
    String unitStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soap);

        valueView = (TextView) findViewById(R.id.task2_value_view);
        valueView.setText(R.string.waiting);
        unitStr = " °C";

        xmlSensor = new XmlSensor();
        soapSensor = new SoapSensor();
        sensor = soapSensor;

        sensor.registerListener(this);
        sensor.getTemperature();

    }

    public void onRadioClickSoap(View view) {
        RadioButton rB = (RadioButton) view;
        boolean checked = rB.isChecked();

        sensor.unregisterListener(this);
        switch (rB.getId()) {
            case R.id.t2_radio_library:
                if (checked) {
                    sensor = soapSensor;
                }
                break;
            case R.id.t2_radio_manual:
                if (checked) {
                    sensor = xmlSensor;
                }
                break;
        }
        valueView.setText(R.string.waiting);

        sensor.registerListener(this);
        sensor.getTemperature();
    }


    @Override
    public void onReceiveSensorValue(double value){
        String string = value + unitStr;
        valueView.setText(string);
    }

    @Override
    public void onReceiveMessage(String message) {

    }
}
