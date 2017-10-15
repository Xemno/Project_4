package ch.ethz.inf.vs.a2.ankoller.webservices.sensor;

/**
 * Created by anja on 13.10.2017.
 */


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Objects;

public class SoapSensor extends AbstractSensor {
    @Override
    public String executeRequest() throws Exception {

        String nameSpace = "http://webservices.vslecture.vs.inf.ethz.ch/";
        String methodName = "getSpot";
        String methodArg = "Spot3";
        String argName = "id";
        String url = "http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice?WSDL";

        SoapObject soapRequest = new SoapObject(nameSpace, methodName);

        PropertyInfo propInf1 = new PropertyInfo();
        propInf1.setName(argName);
        propInf1.setValue(methodArg);
        propInf1.setType(String.class);

        soapRequest.addProperty(propInf1);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soapRequest);

        HttpTransportSE httpTransport = new HttpTransportSE(url);

        try {
            httpTransport.call(url + "/" + methodName, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SoapObject soapResponse = (SoapObject) envelope.getResponse();

        PropertyInfo propInf2 = new PropertyInfo();
        soapResponse.getPropertyInfo(5, propInf2);

        return propInf2.getValue().toString();
    }

    @Override
    public double parseResponse(String response) {
        try {
            return Double.valueOf(response);
        } catch (Exception e) {
            e.printStackTrace();
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
