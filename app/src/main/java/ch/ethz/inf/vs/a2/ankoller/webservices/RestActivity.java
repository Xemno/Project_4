package ch.ethz.inf.vs.a2.ankoller.webservices;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.AbstractSensor;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.JsonSensor;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.RawHttpSensor;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.SensorListener;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.TextSensor;

//start
public class RestActivity extends AppCompatActivity implements SensorListener {

    RawHttpSensor httpSensor;
    TextSensor textSensor;
    JsonSensor jsonSensor;
    AbstractSensor sensor;

    TextView displayValue;
    String celsius= " °";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
        //first create a new rawhttpsensor object register your actiovity as a listener
        //and incoke a request to retrieve a new temperature value and display
        //the retrieved temperature value
        //create a new Textsensor object to the same steps to retrieve a temp value
        //also for JSON


        displayValue = (TextView) findViewById(R.id.displayvalue_view);
        displayValue.setText(R.string.waiting);


        httpSensor = new RawHttpSensor();
        textSensor = new TextSensor();
        jsonSensor = new JsonSensor();
        sensor = httpSensor;
        //create textsensor, jsonsensor and rawhttpsensor objects


        sensor.registerListener(this);
        sensor.getTemperature();
    }

    public void onRadioButtonClick(View view) {
        RadioButton radiobutton = (RadioButton) view;
        //we do a radio button to swtich between the different sensors
        //rawhttpsnesor, textsensor and jsonsensor
        //we create a method to switch between them

        sensor.unregisterListener(this);
        switch (radiobutton.getId()) {
            case R.id.radio_group_http:
                if (radiobutton.isChecked()) {
                    sensor = httpSensor;
                }
                break;
            case R.id.radio_group_textsensor:
                if (radiobutton.isChecked()) {
                    sensor = textSensor;
                }
                break;
            case R.id.radio_group_jsonsensor:
                if (radiobutton.isChecked()) {
                    sensor = jsonSensor;
                }
                break;
        }
        displayValue.setText(R.string.waiting);

        //register listener ang get temperature value
        sensor.registerListener(this);
        sensor.getTemperature();
    }

    @Override
    public void onReceiveSensorValue(double value) {
    //display the sensor value
        String text = value + celsius;
        displayValue.setText(text);
    }

    @Override
    public void onReceiveMessage(String message) {

    }
}
