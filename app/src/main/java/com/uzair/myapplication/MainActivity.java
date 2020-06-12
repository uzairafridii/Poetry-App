package com.uzair.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private InterstitialAd interstitialAd;
    private AdView adView;
    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Love Poetry 2020");
        MobileAds.initialize(this, "ca-app-pub-6210048859499004~2765795142");

        initViews();
        setInterstitialAd();

    }


    private void initViews()
    {
        adView = findViewById(R.id.adsView);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        mToolbar = findViewById(R.id.mainActivityToolBar);
        setSupportActionBar(mToolbar);


        interstitialAd = new InterstitialAd(this);

    }

    private void setInterstitialAd()
    {
        interstitialAd.setAdUnitId("ca-app-pub-6210048859499004/7189445253");
        interstitialAd.loadAd(new AdRequest.Builder().build());


        interstitialAd.setAdListener(new AdListener() {
                                         @Override
                                         public void onAdClosed() {
                                             startActivity(new Intent(MainActivity.this, PoetryActivity.class));
                                             interstitialAd.loadAd(new AdRequest.Builder().build());
                                         }
                                     }
        );
    }


    @Override
    public void onBackPressed() {
     showAlertDialog();
    }

    public void startSecondActivity() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            startActivity(new Intent(MainActivity.this, PoetryActivity.class));
        }
    }


    public void poetryCareView(View view)
    {
       startSecondActivity();
    }


    private void showAlertDialog()
    {
        createAlertDialog();
    }

    private void createAlertDialog()
    {
        final android.app.AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Do you want to exit?");
        alert.setTitle("Love Poetry 2020");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                MainActivity.this.finish();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alert.show();
    }

    public void clickOnShareCard(View view)
    {
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(MainActivity.this).setType("text/plain").setText(getResources().getString(R.string.share_app) +
                "\n" + "https://play.google.com/store/apps/details?id=" + getPackageName()).getIntent(), "Share App!"));
    }

    public void clickOnRateUsCard(View view)
    {
        Intent intent =new Intent(Intent.ACTION_VIEW , Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
        intent.setPackage("com.android.vending");
        startActivity(intent);
    }

    public void clickOnMoreAppsCard(View view)
    {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Utang Global Technologies Limited"));
        sendIntent.setPackage("com.android.vending");
        startActivity(sendIntent);
    }
}
