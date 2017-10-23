package ch.ethz.inf.vs.a2.ankoller.webservices;

/**
 * Created by Qais on 21-Oct-17.
 */

public class RequestHandler {

    public RequestHandler(){

    }

    public StateClass requestFSM(String in, StateClass state){

        if (in.matches("GET.*")){
            state.state |= 1;
            state.method = 0;
            String string = in.split(" ")[1].replaceFirst("/", "");
            String[] strings = string.split("\\?");
            state.folder = strings[0];
            if (strings.length > 1){
                state.settings = strings[1];
                if (state.folder.matches(".+/audio")){
                    Server.playMedia(state.settings.contains("value=on"));
                }
                else if (state.folder.matches(".+/vibrate")){
                    Server.vibrate(state.settings.contains("value=on"));
                }
            }
        }
        else if (in.matches("POST.*")){
            state.state |= 0;
            state.method = 1;
        }
        else if (in.matches("Accept.*")){
            if (in.matches(".*text/html.*")) state.accept = 1;
            else if (in.matches(".*text/plain.*")) state.accept = 0;
            else state.accept = 0;
            state.state |= 2;
        }
        return state;
    }
}
