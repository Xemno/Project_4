package ch.ethz.inf.vs.a2.ankoller.webservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void onButtonClick(View v) {
        Intent intent;
        switch (v.getId()) {
            //swtich
            case R.id.btn_rest:
                intent = new Intent(this, RestActivity.class);
                break;
            case R.id.btn_soap:
                intent = new Intent(this, SoapActivity.class);
                break;
            case R.id.btn_server:
                intent = new Intent(this, ServerActivity.class);
                break;
            default:
                return;//11
        }
        startActivity(intent);
    }
}
