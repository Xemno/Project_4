package ch.ethz.inf.vs.a2.ankoller.webservices.res;

import ch.ethz.inf.vs.a2.ankoller.webservices.http.HttpRawRequest;

/**
 * Created by anja on 13.10.2017.
 */

public class HttpRawRequestImpl implements HttpRawRequest {
    private final String HTTP_VERSION="1.1";
    private final String ENDL="\r\n";

    @Override
    public String generateRequest(String host, int port, String path) {

        //TODO implement this
        //GENERATE A RAW http get REQUEST
    return null;
    }
}
