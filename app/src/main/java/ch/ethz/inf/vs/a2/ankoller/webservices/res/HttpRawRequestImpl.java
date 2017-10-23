package ch.ethz.inf.vs.a2.ankoller.webservices.res;

import ch.ethz.inf.vs.a2.ankoller.webservices.http.HttpRawRequest;

/**
 * Created by anja on 13.10.2017.
 */
//should generate a raw HTTP GET request that implements the interface HttpRawRequest

public class HttpRawRequestImpl implements HttpRawRequest {
    private final String HTTP_VERSION="1.1";
    private final String ENDL="\r\n";

    @Override
    public String generateRequest(String host, int port, String path) {

        String request= generateRequestExtended(host,port, path, "text/plain");
       //TODO HTTp a) set ACCEPT header in request set to text/plain -> DONE
        //in test we have to do text/html, that is why i changed it
        return request;

    }

    public String generateRequestExtended(String host, int port, String path, String accept) {
        //3 required headers Host, Accept and Connection
        String request =
                "GET " + path + " HTTP/" + HTTP_VERSION + ENDL +
                        "User-Agent: ME" + ENDL +
                        "Host: " + host + ":" + port + ENDL +
                        "Accept: " + accept + ENDL +
                        "Keep-Alive: 300" + ENDL +
                        "Connection: close" + ENDL +
                        ENDL;
        return request;
    }
}
