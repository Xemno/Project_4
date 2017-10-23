package ch.ethz.inf.vs.a2.ankoller.webservices;

import android.content.Context;
import android.hardware.Sensor;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by Qais on 16-Oct-17.
 */

public class ServerThread extends Thread {
    private static final String TAG = "#Server Thread:";

    public static Vector<String> html_dir;
    public static String html_link;
    public static String html_radio;
    public static String html_submit;
    public static String html_file;
    public static String html_form;
    public static String html_error;

    public static ServerSocket serverSocket;
    private int port;
    private String ipAddr;
    private Context context;



    public ServerThread(Context context, String ipAddr, int port){
        this.port = port;
        this.ipAddr = ipAddr;
        this.context = context;



        html_link = context.getString(R.string.html_link);
        html_link = html_link.replace("|#|#|PORT|#|#|", String.valueOf(port));
        html_link = html_link.replace("|#|#|IP|#|#|", ipAddr);
        html_file = context.getString(R.string.html_file);
        html_dir = getAllDirectories();
        html_radio = context.getString(R.string.html_radio);
        html_submit = context.getString(R.string.html_submit);
        html_form = context.getString(R.string.html_form);
        html_error = context.getString(R.string.html_error);

    }

    @Override
    public void run() {
        try {   // Here we start the server socket
            if (serverSocket == null) serverSocket = new ServerSocket(port); // create bound server socket to given port
            Log.d(TAG, "Server listening on IP (" + ipAddr + ") and PORT (" + port + ")");

            while (true){ // here we start/accept the connection
                Socket client = serverSocket.accept();  // listens to a connection and accepts it
                Thread thread = new Thread(new ClientThread(client, context));
                thread.start();
            }

        }
        catch (IOException ioe){
            Log.e(TAG, ioe.toString());
        }
        finally {
            try {
                if (serverSocket != null) serverSocket.close();  // close this socket
                serverSocket = null;
                Log.d(TAG, "Socket closed");
            }
            catch (IOException ioe){
                Log.e(TAG, ioe.toString());
            }

        }
    }

    private Vector<String> getAllDirectories(){
        Vector<String> vector = new Vector<>();
        vector.add("");
        vector.add("sensors");
        vector.add("actuators");
        vector.add("actuators/vibrate");
        vector.add("actuators/audio");
        for (Sensor sensor : Server.sensorList){
            vector.add("sensors/" + sensor.getName().toLowerCase().replace(" ", ""));
        }
        return vector;
    }

    public static Vector<String> getPartDirectories(String dir){
        Vector<String> vector = new Vector<>();
        if (dir.equals("")){
            for (String s : html_dir){
                if (s.indexOf("/") < 0) vector.add(s);
            }
        }
        else {
            for (String s : html_dir){
                if (s.indexOf("/") >= 0 && s.replace(dir + "/", "").indexOf("/") < 0) vector.add(s);
            }
            if (vector.size() == 0) return null;
        }
        return vector;
    }
}
