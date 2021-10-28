package com.soft.fbhackprank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Random;

public class HackActivity extends AppCompatActivity {

    TextView txt_hack_details;
    Runnable runnable;
    Handler handler;
    int cnt = 0, flag = 1;
    ArrayList<String> DataString;
    String saltStr;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Hack " + this.getIntent().getStringExtra("UserName"));


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        txt_hack_details = (TextView) findViewById(R.id.txt_hack_details);

        DataString = new ArrayList<>();
        DataString.add("\nScanning " + this.getIntent().getStringExtra("UserName") + "...");
        DataString.add("\nintro: " + this.getIntent().getStringExtra("UserName") + " is an 802.11 and WPA-PSK keys cracking program that can recover keys once enough data packets have been captured.");
        DataString.add("\nPreparing...");
        DataString.add("\nTrying password from list...");
        DataString.add("\nAnalysing Data...");
        DataString.add("\nSpoofing Packets...");
        DataString.add("\nOpen password packets...");
        DataString.add("\nDecrypting password data...");
        DataString.add("\nGetting Password...");
        DataString.add("\nPreparing Result...");

        handler = new Handler();

        runnable = new Runnable() {

            public void run() {

                if (cnt >= DataString.size()) {
                    handler.removeCallbacks(runnable);
                    if (flag == 1) {
                        flag = 2;
                        background_process back = new background_process();
                        back.execute();
                    }
                } else {
                    String str = txt_hack_details.getText().toString();
                    String temp = str + DataString.get(cnt);
                    txt_hack_details.setText(temp);
                    cnt++;
                }

                handler.postDelayed(this, 3000);
            }
        };

        runnable.run();

    }

    private class background_process extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            StringBuilder salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() < 8) {
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            saltStr = salt.toString();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub

            AlertDialog.Builder alert = new AlertDialog.Builder(HackActivity.this);
            alert.setTitle("Password");
            alert.setMessage("Password is:-" + saltStr);
            alert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            dialog.cancel();
                        }
                    });

            alert.setNegativeButton("Copy",
                    new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                        @SuppressLint("NewApi")
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("label", saltStr);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(getApplicationContext(), "Password copy", Toast.LENGTH_LONG).show();

                        }
                    });

            alert.show();
        }
    }
}