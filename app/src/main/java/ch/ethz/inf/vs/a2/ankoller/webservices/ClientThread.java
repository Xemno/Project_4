package ch.ethz.inf.vs.a2.ankoller.webservices;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Qais on 16-Oct-17.
 */

public class ClientThread extends Thread {
    private static final String TAG = "Client Thread:";

    private Socket client;
    private Context context;

    public ClientThread(Socket socket, Context context){
        this.client = socket;
        this.context = context;
    }

    @Override
    public void run() {
        super.run();
        BufferedReader in = null;
        OutputStream os = null;
        try {
            Log.i(TAG, "Connected.");

            // read request
            RequestHandler rh = new RequestHandler();
            StateClass state = new StateClass();
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String aLine = in.readLine();
            while (aLine != null){
                //Log.d(ACTIVITY_TAG, aLine);
                state = rh.requestFSM(aLine, state);
                if (state.state == 3) break;
                aLine = in.readLine();
            }

            if (state.folder.matches(".*/")) state.folder = state.folder.substring(0, state.folder.length() - 1);

            Response response = new Response();

            // generate response
            os = client.getOutputStream();
            Log.i(TAG, "send response with folder " + state.folder);



            String response_msg = "";

            if (state.folder.matches("sensors/.+")){
                String sName = state.folder.replace("sensors/", "");
                Boolean correct = false;
                for (String s : Server.sensorNames){
                    if (s.toLowerCase().replace(" ", "").equals(sName)){
                        sName = s;
                        correct = true;
                        break;
                    }
                }
                if (correct) response_msg = response.responseSensor(sName, Server.getSensorValue(sName));
                else response_msg = response.responseError();
            }
            else if (state.folder.matches("actuators/.+")){
                String sName = state.folder.replace("actuators/", "");
                response_msg = response.responseActuator(sName);
            }
            else {
                response_msg = response.responseFolder(state.folder);
            }

            os.write("HTTP/1.1 200 OK\r\n".getBytes());
            os.write("Server: Elias/1.0\r\n".getBytes());   /////////////////////////////////////////////////////////////// Ändern
            os.write("Content-Type: text/html\r\n".getBytes()); // change!
            os.write(("Content-Length: " + response_msg.length() + "\r\n").getBytes());
            os.write("\r\n".getBytes());
            os.write(response_msg.getBytes());
            os.flush();


        }
        catch (IOException ioe){
            Log.e(TAG, ioe.toString());
        }
        finally {
            try {
                // close client connection
                client.close();
                // close in- & outputstream
                if (in != null) in.close();
                if (os != null) os.close();
                Log.i(TAG, "Client closed");
            }
            catch (IOException ioe){
                Log.e(TAG, ioe.toString());
            }
        }
    }
}
