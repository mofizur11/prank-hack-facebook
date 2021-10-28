package com.soft.fbhackprank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    Button btn_getuserdata;
    EditText txt_username;

    private InterstitialAd mInterstitialAd;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdRequest adRequest1 = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest1,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }
                });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        btn_getuserdata = (Button) findViewById(R.id.btn_getuserdata);
        txt_username = (EditText) findViewById(R.id.txt_username);

        if (txt_username.getText().toString().isEmpty()) {
            // editText is empty
            txt_username.setError("You did not enter a fb URL");
        } else {
            // editText is not empty
            btn_getuserdata.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    Intent i = new Intent(MainActivity.this, HackActivity.class);
                    i.putExtra("UserName", txt_username.getText().toString());
                    startActivity(i);
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(MainActivity.this);
                    }
                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}