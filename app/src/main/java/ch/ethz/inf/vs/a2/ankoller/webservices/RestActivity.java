package ch.ethz.inf.vs.a2.ankoller.webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.AbstractSensor;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.JsonSensor;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.RawHttpSensor;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.SensorListener;
import ch.ethz.inf.vs.a2.ankoller.webservices.sensor.TextSensor;

public class RestActivity extends AppCompatActivity implements SensorListener{
    //TODO implement methods, this activity has to implement the interface sensorlistener
    // so that activity can be informed upon reception of the temperature value
    //first create a new RawHttpSensor object, register your activity as a listener
    //and invoke a request to retrieve a new temperature value
    //display the retrieved temperature value
    RawHttpSensor rawHttpSensor;
    AbstractSensor sensor;
    JsonSensor jsonSensor;
    TextView displayValue;
    String celsius;
//create a new Textsensor object to to the same steps to retrieve a temperature value
    TextSensor textSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
       displayValue=(TextView)findViewById(R.id.displayvalue_view);
        displayValue.setText("waiting for temperature:");
        celsius=" °C";

        //we do a radiobutton to switch between the different sensors
        //rawhttpsensor, textsensor or jsonsensor
        //we create a method to switch between them


        rawHttpSensor= new RawHttpSensor();
        textSensor= new TextSensor();
        jsonSensor= new JsonSensor();
       // sensor=rawHttpSensor;

        //registerlistener and get temp value
        sensor.registerListener(this);
        sensor.getTemperature();



    }

    public void onRadioButtonClick(View view){
        RadioButton radioButton= (RadioButton) view;

        sensor.unregisterListener(this);
        switch(radioButton.getId()){
            case R.id.radio_group_http:
                if(radioButton.isChecked()){
                    sensor=rawHttpSensor;
                }
                break;
            case R.id.radio_group_textsensor:
                if(radioButton.isChecked()){
                    sensor=textSensor;
                }
                break;
            case R.id.radio_group_jsonsensor:
                if(radioButton.isChecked()){
                    sensor=jsonSensor;
                }
                break;
        }
        displayValue.setText("waiting for temperature:");
        sensor.registerListener(this);
        sensor.getTemperature();
    }

    @Override
    public void onReceiveSensorValue(double value) {
        //display the sensor value

    String text= value  + celsius;
        displayValue.setText(text);

    }

    @Override
    public void onReceiveMessage(String message) {

    }
}
