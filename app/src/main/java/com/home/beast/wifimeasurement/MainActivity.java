package com.home.beast.wifimeasurement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private Handler handler = new Handler();
    private int signalLevel;
    TextView signalStrength;
    TextView signalIndicator;
    TextView ipAddress;
    TextView linkSpeed;
    TextView is5GHzBandSupported;
    TextView frequency;
    TextView channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler.postDelayed(r,500);
        signalIndicator=findViewById(R.id.signalIndicator);
        ipAddress=findViewById(R.id.ipAddress);
        linkSpeed=findViewById(R.id.linkSpeed);
        is5GHzBandSupported=findViewById(R.id.is5GHzSupported);
        frequency=findViewById(R.id.frequency);
        channel=findViewById(R.id.channel);
    }

    private Runnable r=new Runnable() {
        @Override
        public void run() {
            wifiScan();
            if(signalLevel>-50){
                signalIndicator.setTextColor(getResources().getColor(R.color.lightGreen));
                signalIndicator.setText("Excellent");
            }
            else if(signalLevel<-50 && signalLevel>-60){
                signalIndicator.setTextColor(getResources().getColor(R.color.orange));
                signalIndicator.setText("Good");
            }
            else if(signalLevel<-60 && signalLevel>-70){
                signalIndicator.setTextColor(getResources().getColor(R.color.yellow));
                signalIndicator.setText("Fair");
            }
            else if(signalLevel<-70 && signalLevel>-100){
                signalIndicator.setTextColor(getResources().getColor(R.color.red));
                signalIndicator.setText("Bad");
            }
            else if(signalLevel<-100){
                signalIndicator.setTextColor(getResources().getColor(R.color.white));
                signalIndicator.setText("No signal");
            }
            handler.postDelayed(r,500);
        }
    };

    private void wifiScan(){
        WifiManager wifiManager=(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        signalLevel=wifiInfo.getRssi();
        signalStrength=findViewById(R.id.signalStrength);
        signalStrength.setText(signalLevel+" dbm");
        String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        ipAddress.setText(ip);
        linkSpeed.setText(String.valueOf(wifiInfo.getLinkSpeed())+" Mbps");
        supports5GHzBand(wifiManager);
        getLinkSpeed(wifiInfo);
    }

    private void supports5GHzBand(WifiManager wifiManager){
        if(wifiManager.isWifiEnabled()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(wifiManager.is5GHzBandSupported()){
                    is5GHzBandSupported.setText("True");
                    is5GHzBandSupported.setTextColor(getResources().getColor(R.color.lightGreen));
                }
                else{
                    is5GHzBandSupported.setText("False");
                    is5GHzBandSupported.setTextColor(getResources().getColor(R.color.red));
                }
            }
        }
        else{
            is5GHzBandSupported.setText("-");
        }

    }

    private void getLinkSpeed(WifiInfo wifiInfo){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            int f0=wifiInfo.getFrequency();
            frequency.setText(String.valueOf(f0)+" Hz");
            switch(f0){
                case 2412:
                  channel.setText("1");
                  break;
                case 2417:
                    channel.setText("2");
                    break;
                case 2422:
                    channel.setText("3");
                    break;
                case 2427:
                    channel.setText("4");
                    break;
                case 2432:
                    channel.setText("5");
                    break;
                case 2437:
                    channel.setText("6");
                    break;
                case 2442:
                    channel.setText("7");
                    break;
                case 2447:
                    channel.setText("8");
                    break;
                case 2452:
                    channel.setText("9");
                    break;
                case 2457:
                    channel.setText("10");
                    break;
                case 2462:
                    channel.setText("11");
                    break;
                case 2467:
                    channel.setText("12");
                    break;
                case 2472:
                    channel.setText("13");
                    break;
                case 2484:
                    channel.setText("14");
                    break;
                 default:
                     channel.setText("-");
            }
        }
    }

}
