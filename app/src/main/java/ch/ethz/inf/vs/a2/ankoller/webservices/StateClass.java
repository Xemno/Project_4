package ch.ethz.inf.vs.a2.ankoller.webservices;

/**
 * Created by Qais on 21-Oct-17.
 */

public class StateClass {
    public int state;
    public String folder;
    public int method;      // 0 = not set; 1 = GET; 2 = POST
    public int accept;      // 0 = not set; 1 = text/plain; 2 = text/html
    public String settings; // for POST and GET

    public StateClass(){
        this.state = 0;
        this.folder = "";
        this.method = 0;
        this.accept = 0;
        this.settings = "";
    }
}
