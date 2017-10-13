package ch.ethz.inf.vs.a2.ankoller.webservices.sensor;

/**
 * Created by anja on 13.10.2017.
 */

class TextSensor implements Sensor {
    @Override
    public String executeRequest() throws Exception {
        return null;
    }

    @Override
    public double parseResponse(String response) {
        return 0;
    }

    @Override
    public void getTemperature() {

    }

    @Override
    public void registerListener(SensorListener listener) {

    }

    @Override
    public void unregisterListener(SensorListener listener) {

    }
}
