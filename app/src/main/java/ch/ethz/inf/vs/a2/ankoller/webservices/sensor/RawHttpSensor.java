package ch.ethz.inf.vs.a2.ankoller.webservices.sensor;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import ch.ethz.inf.vs.a2.ankoller.webservices.http.RemoteServerConfiguration;
import ch.ethz.inf.vs.a2.ankoller.webservices.res.HttpRawRequestImpl;

/**
 * Created by anja on 13.10.2017.
 */

public class RawHttpSensor extends AbstractSensor implements RemoteServerConfiguration {
    //public static final String SENSOR_PATH = "http://vslab.inf.ethz.ch:8081/sunspots/Spot1/sensors/temperature";
    //8081 is the REST_PORT, so we need the rest as the path
    public static final String SENSOR_PATH="sunspots/Spot1/sensors/temperature";

    public static final String TAG="HttpRawSensor > Log";
    //use request from a) and send it over a tcp connection use socket class and flush when using printwrier

    @Override
    public String executeRequest() throws Exception {

        HttpRawRequestImpl requestMaker= new HttpRawRequestImpl();
        Socket socket = new Socket(HOST, REST_PORT);
        //create a socket with string host and int rest_port
        OutputStream outputStream= socket.getOutputStream();
        InputStream inputStream= socket.getInputStream();
        PrintWriter output= new PrintWriter(outputStream);
        //will buffer the PrintWriter's output to the file
        //closing the socket also closes input and outputstream

        output.println(requestMaker.generateRequest(HOST, REST_PORT, SENSOR_PATH));
        output.flush();
        //Flushes this buffered output stream.
        //this forces any buffered output bytes to be written out to the underlying outputstream

        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
        //Reads text from a character-input stream

        String response = "";
        String temp;
        while((temp = bufferedReader.readLine())!=null){
            response += "\n" + temp;
            //that is because headers require to have carriage return and nwline at end of each line

        }
        bufferedReader.close();
        //Closes the stream and releases any system resources associated with it.
        return response;

    }

    @Override
    public double parseResponse(String response) {
        Log.d(TAG, "HtmlSensor response gotten: " + response);

        double result;
        try{
            result = Double.valueOf(response.substring(response.lastIndexOf('\n') + 1));
        }catch(Exception e){
            result = Double.NaN;
        }
        return result;
    }

    /**@Override
    public void getTemperature() {

    }

    @Override
    public void registerListener(SensorListener listener) {

    }

    @Override
    public void unregisterListener(SensorListener listener) {

    }
    */

}
