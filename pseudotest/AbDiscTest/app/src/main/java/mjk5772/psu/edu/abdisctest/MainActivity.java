package mjk5772.psu.edu.abdisctest;

import android.support.v7.app.AppCompatActivity;
import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.mbientlab.metawear.android.BtleService;

public class MainActivity extends AppCompatActivity {

    Integer connectcheck;
    private BtleService.LocalBinder serviceBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///< Bind the service when the activity is created
        getApplicationContext().bindService(new Intent(this, BtleService.class),
                this, Context.BIND_AUTO_CREATE);


        //Create a button for connecting
        /*
        Button connectBtn = (Button)findViewById(R.id.CntBtn);
        connectBtn.setOnClickListener(new View.OnClickListener()){
            public void onClick(View v) {
                onServiceConnected();
            }
        };

        //Create a button for disconnecting
        Button disconnectBtn = (Button)findViewById(R.id.DisCntBtn);
        disconnectBtn.setOnClickListener(new View.OnClickListener()){
            public void onClick(View v) {
                onServiceDisconnected();
            }
        };*/

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        ///< Unbind the service when the activity is destroyed
        getApplicationContext().unbindService(this);
    }


    public void onServiceConnected(Button connectBtn, IBinder service) {

        if(connectcheck == 1){
            return;
        }

        ///< Typecast the binder to the service's LocalBinder class
        serviceBinder = (BtleService.LocalBinder) service;
        connectcheck = 1;
    }


    public void onServiceDisconnected(Button disconnectBtn) {
        connectcheck = 0;
    }

    //crunchsession sudocode:

    /*public void crunchsession() {
        if(//stepcounter != increasing){

            double pressureavg;
            //read pressure
            //start timer

            if (//timer > 10min) {

                double sum;
                double freshpressure;
                freshpressure = pressureavg;


                for(//run for 10seconds) {
                    freshpressure = //read pressure
                    sum =+ freshpressure;
                    pressureavg = sum / count
                    //wait 3seconds;
                }

                if (//pressureavg > freshpressure){
                    //send vibrate

                }

            }

        }
    }*/
}
