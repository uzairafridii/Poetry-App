package com.uzair.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class PoetryActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int REQUEST_CAMERA = 12;
    private Toolbar mToolbar;
    private ViewPager viewPager;
    private PagerAdapter myPager;
    private ImageButton next , previous , downloadBtn , shareBtn;
    private Button first , last;
    private TextView title;
    private View mView;
    private AdView adView;
    private AdRequest adRequest;
    private RelativeLayout mRelativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poetry);
        MobileAds.initialize(this, "ca-app-pub-6210048859499004~2765795142");
        initViews();


    }

    private void initViews()
    {
        myPager = new PagerAdapter(this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(myPager);

        next = findViewById(R.id.nextBtn);
        previous = findViewById(R.id.previousBtn);
        first = findViewById(R.id.btnFirstImage);
        last = findViewById(R.id.btnLastImage);

        adView = findViewById(R.id.adsView);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        mToolbar = findViewById(R.id.poetryActivityToolBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

       LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        mView = inflater.inflate(R.layout.image_buttons_on_tool_bar, null);

        getSupportActionBar().setCustomView(mView);

        downloadBtn  = mView.findViewById(R.id.downloadImageButton);
        shareBtn  = mView.findViewById(R.id.shareImageButton);
        title  = mView.findViewById(R.id.titleOfToolBar);
        title.setText("Love Poetry");


        mRelativeLayout = findViewById(R.id.relativeLayout);


        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        downloadBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
        first.setOnClickListener(this);
        last.setOnClickListener(this);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.nextBtn:
            {
                viewPager.setCurrentItem(getItem(+1), true);
                break;
            }
            case R.id.previousBtn:
            {
                viewPager.setCurrentItem(getItem(-1), true);
                break;
            }

            case R.id.downloadImageButton:
            {
                if(isStoragePermissionGranted()){
                    saveImage();
                }


                break;
            }

            case R.id.shareImageButton:
            {
                if(isStoragePermissionGranted()) {
                    int currentItem = viewPager.getCurrentItem();
                    setShareIntentForPosition(currentItem);
                }

                break;
            }

            case R.id.btnFirstImage:
            {
                viewPager.setCurrentItem(myPager.setImageTOFirst());
                break;
            }
            case R.id.btnLastImage:
            {
                viewPager.setCurrentItem(myPager.setImageTOLast());
                break;
            }

            default:
            {
                break;
            }

        }

    }


    private void saveImage()
    {
       String storagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/PoetryImages";
        boolean success = false;

        final Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        final String fname = "Poetry-" + n + ".png";

        File image = new File(storagePath);
        if (!image.exists()) {
            image.mkdir();
        }
        int currentItem =viewPager.getCurrentItem();
        Drawable drawable = getResources().getDrawable(myPager.GalImages[currentItem]);
        Bitmap bitmap =((BitmapDrawable) drawable).getBitmap();


        // Encode the file as a PNG image.
        FileOutputStream outStream;
        try {

            outStream = new FileOutputStream(image+"/"+fname);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            /* 100 to keep full quality of the image */

            outStream.flush();
            outStream.close();
            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (success) {
            Toast.makeText(getApplicationContext(), "Image saved with success at /Pictures/PoetryImage",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Error during image saving", Toast.LENGTH_LONG).show();
        }
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Tag","Permission is granted");
                return true;
            } else {

                Log.v("Tage","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Tag","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Snackbar snackbar = Snackbar.make(mRelativeLayout, "Permission Granted Successfully", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                        Snackbar snackbar = Snackbar.make(mRelativeLayout, "Please Enable Permission", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                }
                return;
            }

            case 2: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Snackbar snackbar = Snackbar.make(mRelativeLayout, "Permission Granted Successfully", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                } else {

                    Snackbar snackbar = Snackbar.make(mRelativeLayout, "Please Enable Permission", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    private void setShareIntentForPosition(int currentPosition){
        Bitmap bitmap;
        OutputStream output;
        bitmap = BitmapFactory.decodeResource(getResources(),myPager.GalImages[currentPosition]);
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() + "/PoetryPics/");
        if(!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir , "Image.png");
        try {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            output = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            output.close();

            Uri outputFileUri = FileProvider.getUriForFile(PoetryActivity.this, BuildConfig.APPLICATION_ID, file);
            share.putExtra(Intent.EXTRA_STREAM, outputFileUri);
            startActivity(Intent.createChooser(share,"Share With:"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}



