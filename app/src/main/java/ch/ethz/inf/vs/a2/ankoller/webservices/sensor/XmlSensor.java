package ch.ethz.inf.vs.a2.ankoller.webservices.sensor;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by anja on 13.10.2017.
 */



public class XmlSensor extends AbstractSensor {

    //public XmlSensor() {}

    @Override
    public String executeRequest() throws Exception {

        String server = "http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice?WSDL";
        URL url = new URL(server);
        //Casting what the openConnection() returns.
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        // http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice?Tester and then getDiscoveredSpots()
        String requestString = "<?xml version='1.0' encoding='UTF-8'?><S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'>";
        requestString += "<S:Header/>";
        requestString += "<S:Body>";
        requestString += "<ns2:getSpot xmlns:ns2='http://webservices.vslecture.vs.inf.ethz.ch/'>";
        requestString += "<id>Spot3</id>";
        requestString += "</ns2:getSpot>";
        requestString += "</S:Body>";
        requestString += "</S:Envelope>";

        byte[] reqStrBytes = requestString.getBytes("UTF-8");

        connection.setRequestProperty("Content-Type", "text/xml");
        connection.setRequestProperty("Content-Length", String.valueOf(reqStrBytes.length));
        connection.getOutputStream().write(reqStrBytes);

        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {

            BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String responseLine;
            StringBuilder response = new StringBuilder();

            while ((responseLine = responseReader.readLine()) != null) {
                response.append(responseLine);
                response.append("\n");
            }
            responseReader.close();
            return response.toString();
        }

        return null;
    }

    @Override
    //From: https://developer.android.com/reference/org/xmlpull/v1/XmlPullParser.html
    public double parseResponse(String response) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(response) );


            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("temperature")) {
                        xpp.next();
                        if (xpp.getEventType() == XmlPullParser.TEXT) {
                            return Double.valueOf(xpp.getText());
                        }
                    }
                }
                xpp.next();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }


        return Double.NaN;
    }

    /**
    @Override
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
