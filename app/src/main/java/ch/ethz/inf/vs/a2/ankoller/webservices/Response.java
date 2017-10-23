package ch.ethz.inf.vs.a2.ankoller.webservices;

import android.hardware.Sensor;
import android.util.Log;

import java.util.Vector;

import static android.content.ContentValues.TAG;

/**
 * Created by Qais on 21-Oct-17.
 */

public class Response {
    private static final String TAG = "#Response Class: ";

    public String responseFolder(String dir){
        Vector<String> vs = ServerThread.getPartDirectories(dir);
        if (vs != null){
            String links = "";
            for (String s : vs){
                String link = ServerThread.html_link;
                link = link.replace("|#|#|FOLDER|#|#|", s);
                link = link.replace("|#|#|SHOW|#|#|", s);
                links += link + "\t\n";
            }
            Log.i(TAG, links);
            return ServerThread.html_file.replace("|#|#|BODY|#|#|", links);
        }
        else return responseError();
    }

    public String responseSensor(String sName, String sValue){
        String body = "Value(s) of " + sName + " is/ are:<br>" + sValue;
        return ServerThread.html_file.replace("|#|#|BODY|#|#|", body);
    }

    public String responseError(){
        return ServerThread.html_error;
    }

    public String responseActuator(String aName){
        String form = "", file = "";
        Boolean state = false;
        switch (aName){
            case "vibrate":
                file = "State of actuator Vibrate:<br>";
                state = Server.is_vibrating;
                break;
            case "audio":
                file = "State of actuator Audio:<br>";
                state = Server.is_playing;
                break;
            default:
                return responseError();
        }
        form += ServerThread.html_radio.replace("|#|#|NAME|#|#|", "value").replace("|#|#|VALUE|#|#|", "on").replace("|#|#|CHECKED|#|#|", state ? "checked" : "").replace("|#|#|SHOW|#|#|", "ON");
        form += ServerThread.html_radio.replace("|#|#|NAME|#|#|", "value").replace("|#|#|VALUE|#|#|", "off").replace("|#|#|CHECKED|#|#|", !state ? "checked" : "").replace("|#|#|SHOW|#|#|", "OFF");
        form += "<br>";
        form += ServerThread.html_submit;
        file += ServerThread.html_form.replace("|#|#|NAME|#|#|", "input").replace("|#|#|ACTION|#|#|", "/actuators/" + aName).replace("|#|#|FORM|#|#|", form);
        return ServerThread.html_file.replace("|#|#|BODY|#|#|", file);
    }

    public static String getUnitString(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER: return "m/s^2";
            case Sensor.TYPE_AMBIENT_TEMPERATURE: return "°C";
            case Sensor.TYPE_DEVICE_PRIVATE_BASE: return "";
            case Sensor.TYPE_GAME_ROTATION_VECTOR: return "rad";
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR: return "rad";
            case Sensor.TYPE_GRAVITY: return "m/s^2";
            case Sensor.TYPE_GYROSCOPE: return "rad/s";
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED: return "rad/s";
            case Sensor.TYPE_HEART_BEAT: return "";
            case Sensor.TYPE_HEART_RATE: return "Hz";
            case Sensor.TYPE_LIGHT: return "lx";
            case Sensor.TYPE_LINEAR_ACCELERATION: return "m/s^2";
            case Sensor.TYPE_MAGNETIC_FIELD: return "microT";
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED: return "microT";
            case Sensor.TYPE_MOTION_DETECT: return "";
            case Sensor.TYPE_POSE_6DOF: return "";
            case Sensor.TYPE_PRESSURE: return "hPa";
            case Sensor.TYPE_PROXIMITY: return "";
            case Sensor.TYPE_RELATIVE_HUMIDITY: return "%";
            case Sensor.TYPE_ROTATION_VECTOR: return "no unit";
            case Sensor.TYPE_SIGNIFICANT_MOTION: return "";
            case Sensor.TYPE_STATIONARY_DETECT: return "";
            case Sensor.TYPE_STEP_COUNTER: return "";
            case Sensor.TYPE_STEP_DETECTOR: return "";
            default: return "";
        }
    }
}
