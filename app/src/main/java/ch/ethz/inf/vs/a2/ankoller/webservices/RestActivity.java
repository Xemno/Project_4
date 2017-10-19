package ch.ethz.inf.vs.a2.ankoller.webservices;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.AbstractSensor;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.JsonSensor;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.RawHttpSensor;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.SensorListener;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.TextSensor;


public class RestActivity extends AppCompatActivity implements SensorListener {

    private static final String TAG = "RestActivity > Log";

    RawHttpSensor httpSensor;
    TextSensor textSensor;
    JsonSensor jsonSensor;
    AbstractSensor sensor;

    TextView valueView;
    String unitString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);

        Log.d(TAG, "Rest activity started");

        valueView = (TextView) findViewById(R.id.displayvalue_view);
        valueView.setText(R.string.waiting);
        unitString = " °C";

        httpSensor = new RawHttpSensor();
        textSensor = new TextSensor();
        jsonSensor = new JsonSensor();
        sensor = httpSensor;

        sensor.registerListener(this);
        sensor.getTemperature();
    }

    public void onRadioButtonClick(View view) {
        RadioButton radiobutton = (RadioButton) view;
        boolean checked = radiobutton.isChecked();

        sensor.unregisterListener(this);
        switch (radiobutton.getId()) {
            case R.id.radio_group_http:
                if (checked) {
                    sensor = httpSensor;
                }
                break;
            case R.id.radio_group_textsensor:
                if (checked) {
                    sensor = textSensor;
                }
                break;
            case R.id.radio_group_jsonsensor:
                if (checked) {
                    sensor = jsonSensor;
                }
                break;
        }
        valueView.setText(R.string.waiting);

        sensor.registerListener(this);
        sensor.getTemperature();
    }

    @Override
    public void onReceiveSensorValue(double value) {
        Log.d(TAG, "received sensor value: " + value);

        String text = value + unitString;
        valueView.setText(text);
    }

    @Override
    public void onReceiveMessage(String message) {
        Log.d(TAG, "received sensor message: " + message);
    }
}
