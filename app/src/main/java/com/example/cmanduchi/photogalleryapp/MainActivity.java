package com.example.cmanduchi.photogalleryapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    private static final String TAG = "WRITE";


    private ImageView imageView;
    private EditText editText;

    String captionTextSearch = null;
    String latUpSearch = null;
    String latDownSearch = null;
    String lonLeftSearch = null;
    String lonRightSearch = null;

    ArrayList<String> imagesPathArrayList = new ArrayList<String>();
    int currentPic = 0;
    private int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        isReadStoragePermissionGranted();
        isWriteStoragePermissionGranted();
        isLocationPermissionGranted();

        editText = (EditText) findViewById(R.id.caption);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                Log.d("Editor", v.getText().toString());
                changeCaption(v.getText().toString());

                return false;
            }
        });

        imageView = (ImageView) findViewById(R.id.imageView);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode)
        {
            case 2:
                isReadStoragePermissionGranted();
            case 3:
                isLocationPermissionGranted();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        showPic();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE)
        {
            captionTextSearch = data.getStringExtra("caption");
            latUpSearch = data.getStringExtra("latUp");
            latDownSearch = data.getStringExtra("latDown");
            lonRightSearch = data.getStringExtra("lonRight");
            lonLeftSearch = data.getStringExtra("lonLeft");

            showPic();
        }
    }

    public void changeCaption(String caption)
    {
        String fileName = imagesPathArrayList.get(currentPic);
        String newFileName = "";
        File imgFile = new File(fileName);

        String[] tokens = fileName.split("_");

        newFileName = tokens[0] + "_" + tokens[1] + "_" + tokens[2] + "_" + tokens[3] + "_" + tokens[4] + "_" + caption;

        File newImgFile = new File(newFileName);

        imgFile.renameTo(newImgFile);
    }

    public void showPic() {
        try {
            getImages();

            String fileName = imagesPathArrayList.get(currentPic);
            File imgFile = new File(fileName);

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);

                TextView textView = (TextView) findViewById(R.id.info);
                EditText editText = (EditText) findViewById(R.id.caption);

                String[] tokens = fileName.split("_");

                String timeStamp = tokens[1] + "_" + tokens[2];
                String lat = tokens[3].substring(0, 10);
                String lon = tokens[4].substring(0, 10);
                String captionValue = "";

                if(tokens.length > 5)
                {
                    captionValue = tokens[5];
                }


                textView.setText(timeStamp + " " + lat + " " + lon);
                editText.setText(captionValue);

            }
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

    }

    public void snap(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("Exception", ex.toString());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.cmanduchi.photogalleryapp.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

    }


    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        double longitude = location.getLongitude();
        double latitude = location.getLatitude();


        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" + longitude + "_" + latitude;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void getImages() {
        String[] filenames = new String[0];
        File path = new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_PICTURES)));// add here your fo;der name
        if (path.exists()) {
            filenames = path.list();
        }

        imagesPathArrayList = new ArrayList<String>();

        for (int i = 0; i < 10000; i++) {

            String[] tokens = filenames[i].split("_");

            if(captionTextSearch != null && !captionTextSearch.equals(""))
            {
                if(tokens.length > 5)
                {
                    if(!tokens[5].equals(captionTextSearch))
                    {
                        continue;
                    }
                }
                else
                {
                    continue;
                }
            }

            if(latDownSearch != null && !latDownSearch.equals("") &&
                    latUpSearch != null && !latUpSearch.equals("") &&
                    lonLeftSearch != null && !lonLeftSearch.equals("")
                    && lonRightSearch != null && !lonRightSearch.equals("") )
            {
                double latDownValue = Double.valueOf(latDownSearch);
                double latUpValue = Double.valueOf(latUpSearch);
                double lonLeftValue = Double.valueOf(lonLeftSearch);
                double lonRightValue = Double.valueOf(lonRightSearch);

                double lat = Double.valueOf(tokens[2]);
                double lon = Double.valueOf(tokens[3]);


                if(lat < latDownValue || lat > latUpValue
                        || lon < lonLeftValue || lon > lonRightValue)
                {
                    continue;
                }
            }

            imagesPathArrayList.add(path.getPath() + "/" + filenames[i]);
        }
    }

    public boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted1");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted1");
            return true;
        }
    }

    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted2");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2");
            return true;
        }
    }

    public boolean isLocationPermissionGranted() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int REQUEST_CODE, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 4);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
        }
        return true;
    }

    public void previousButton(View view) {

        currentPic--;

        if(currentPic < 0)
        {
            currentPic = imagesPathArrayList.size() -1;
        }

        showPic();
    }

    public void nextButton(View view) {

        currentPic++;

        if(currentPic > imagesPathArrayList.size() -1)
        {
            currentPic = 0;
        }

        showPic();
    }

    public void search(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

    }

    public void searchSpecificPic(String picToBeSearch)
    {

    }

    public void addFakePics(View view) throws IOException {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        File image = new File("");

        for(int i=1000; i<=100000 ; i++)
        {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG" + String.valueOf(i) + "_" + timeStamp + "_" + longitude + "_" + latitude;
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        }


        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
    }

    public void countPics(View view) {

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        int count = 0;
        for (File file : storageDir.listFiles()) {
            if (file.isFile()) {
                count++;
            }
        }

        Toast.makeText(this, "Number of pics is: " + String.valueOf(count), Toast.LENGTH_LONG).show();
    }
}
