package com.example.islamiapp.TabsFragment.qabla;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.islamiapp.Base.BaseFragment;

import com.example.islamiapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;
import static android.view.View.INVISIBLE;


public class QablaFragment extends BaseFragment implements SensorEventListener, LocationListener {
    private static final String TAG = "CompassActivity";
    private static final int MY_PERMISSIONS_REQUEST_LOCATION_CODE = 500;

    private Compass compass;
    private ImageView arrowViewQiblat;
    private ImageView imageDial;
    private TextView text_atas;
    private TextView text_bawah;


    private SensorManager sensor;
    double latitude,longitude;
    double meccaLatitude = 21.422483;
    double meccaLongitude = 39.826181;
    float qiblaAngle;
    float compDegree,qiblaDegree;
    private float compCurrentDegree=0f;
    private float qiblaCurrentDegree = 0f;
    MyLocationProvider myLocationProvider;
    Location currentLocation;

    View view;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_qabla, container, false);


        //////////////////////////////////////////
        arrowViewQiblat = (ImageView) view.findViewById(R.id.main_image_qiblat);
        imageDial = (ImageView) view.findViewById(R.id.main_image_dial);
        text_atas = (TextView) view.findViewById(R.id.teks_atas);
        text_bawah = (TextView) view.findViewById(R.id.teks_bawah);


        sensor = (SensorManager)activity.getSystemService(SENSOR_SERVICE);

        myLocationProvider=new MyLocationProvider(activity);
        if (isLocationPermessionGranted()){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
            alertDialogBuilder.setTitle("رسالة تأكيد");
            alertDialogBuilder.setMessage("من فضلك قم بتشغيل ال GPS الخاص بالهاتف");
            alertDialogBuilder.setCancelable(true);

            alertDialogBuilder.setPositiveButton("حسنا", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int arg1) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    dialog.cancel();
                    Log.e("log","you  are here");

                }
            });
            alertDialogBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();



            if (myLocationProvider.isGpsEnabled()){
                Log.e("stateDilog" , "enabeld");
                alertDialog.cancel();
            }else {
                Log.e("stateDilog" , "desibled");
                alertDialog.show();
            }



            //call function
            try {
                //Toast.makeText(this, "opened", Toast.LENGTH_SHORT).show();

                currentLocation=myLocationProvider.getUserLocation(this);
                latitude = currentLocation.getLatitude();
                longitude = currentLocation.getLongitude();
                qiblaAngle = getQiblaAngle(latitude, longitude, meccaLatitude, meccaLongitude);
                Log.e(TAG, "onCreateView: "+longitude );

                Geocoder geocoder;
                List<Address> addresses = new ArrayList();


                try {
                    Locale mLocale = new Locale("ar");
                    Locale.setDefault(mLocale);
                    geocoder = new Geocoder(activity, Locale.getDefault());
                    addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String city = addresses.get(0).getLocality();
                String country = addresses.get(0).getCountryName();
                if (country == null || city == null){

                    text_atas.setText("غير متوفر");
                }
                else {
                    text_atas.setText(country+" - "+city);
                    //UserLocation.setText(session.getLocationCode());
                }
            }catch (Exception e) {
                //Toast.makeText(this, "wrong", Toast.LENGTH_SHORT).show();
                text_atas.setText("غير متوفر");
            }

        }else {
            requestLocationPersmission();
        }



        Log.e(TAG, "onCreateView: " );





        return view;
    }

    private void requestLocationPersmission() {
        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.+
            showConfirmationMessage(R.string.warning,
                    R.string.message_request_location_permission,
                    R.string.ok, new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION_CODE);
                        }
                    });

        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION_CODE);

        }
    }

    public boolean isLocationPermessionGranted(){
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        sensor.registerListener((SensorEventListener) this, sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        Log.e(TAG, "onStart: " );

    }

    @Override
    public void onPause() {
        super.onPause();
        sensor.unregisterListener((SensorEventListener) this);
        Log.e(TAG, "onPause: " );

    }

    @Override
    public void onResume() {
        super.onResume();
        sensor.registerListener((SensorEventListener) this, sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        Log.e(TAG, "onResume: " );

        try {
            if(isLocationPermessionGranted()){
                //call function
                //Toast.makeText(this, "onRusuem", Toast.LENGTH_SHORT).show();
                //  showUserLocation();
                if (myLocationProvider.isGpsEnabled()){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                    alertDialogBuilder.setTitle("رسالة تأكيد");
                    alertDialogBuilder.setMessage("من فضلك قم بتشغيل ال GPS الخاص بالهاتف");
                    alertDialogBuilder.setCancelable(true);

                    alertDialogBuilder.setPositiveButton("حسنا", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int arg1) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            dialog.cancel();
                            Log.e("log","you  are here");

                        }
                    });
                    alertDialogBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();



                    if (myLocationProvider.isGpsEnabled()){
                        Log.e("stateDilog" , "enabeld");
                        alertDialog.cancel();
                    }else {
                        Log.e("stateDilog" , "desibled");
                        alertDialog.show();
                    }
                    myLocationProvider=new MyLocationProvider(activity);
                    currentLocation=myLocationProvider.getUserLocation(this);
                    latitude = currentLocation.getLatitude();
                    longitude = currentLocation.getLongitude();
                    qiblaAngle = getQiblaAngle(latitude, longitude, meccaLatitude, meccaLongitude);
                    Log.e(TAG, "onCreateView: "+longitude );

                    Geocoder geocoder;
                    List<Address> addresses = new ArrayList();


                    try {
                        Locale mLocale = new Locale("ar");
                        Locale.setDefault(mLocale);
                        geocoder = new Geocoder(activity, Locale.getDefault());
                        addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String city = addresses.get(0).getLocality();
                    String country = addresses.get(0).getCountryName();
                    text_atas.setText(country+" - "+city);
                }else {
                    text_atas.setText("غير متوفر");

                }



            }else {
                requestLocationPersmission();
            }


        }catch (Exception e){
            Log.e("ResumeLocPermissions","isLocationPermissionsNotGranted");
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        sensor.unregisterListener((SensorEventListener) this);

        Log.e(TAG, "onStop: " );



    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    myLocationProvider=new MyLocationProvider(activity);
                    latitude = currentLocation.getLatitude();
                    longitude = currentLocation.getLongitude();
                    qiblaAngle = getQiblaAngle(latitude, longitude, meccaLatitude, meccaLongitude);
                    Log.e(TAG, "onCreateView: "+longitude );

                    Geocoder geocoder;
                    List<Address> addresses = new ArrayList();


                    try {
                        Locale mLocale = new Locale("ar");
                        Locale.setDefault(mLocale);
                        geocoder = new Geocoder(activity, Locale.getDefault());
                        addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String city = addresses.get(0).getLocality();
                    String country = addresses.get(0).getCountryName();
                    if (country == null || city == null){

                        text_atas.setText("غير متوفر");
                    }
                    else {
                        text_atas.setText(country+" - "+city);
                        //UserLocation.setText(session.getLocationCode());
                    }
                } else {

                    Toast.makeText(activity,"لا استطيع العثور على موقعك", Toast.LENGTH_LONG).show();

                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        compDegree = Math.round(event.values[0]);
        RotateAnimation compRotate = new RotateAnimation(compCurrentDegree ,-compDegree,
                Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        compRotate.setDuration(210);
        compRotate.setFillAfter(true);
        imageDial.startAnimation(compRotate);
        compCurrentDegree= -compDegree;

        qiblaDegree = Math.round(event.values[0])+qiblaAngle-90;
        RotateAnimation qiblaRotate = new RotateAnimation(qiblaCurrentDegree,-qiblaDegree,
                Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        qiblaRotate.setDuration(210);
        qiblaRotate.setFillAfter(true);
        arrowViewQiblat.startAnimation(qiblaRotate);
        qiblaCurrentDegree = -qiblaDegree;
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public float getQiblaAngle(double lat1,double long1,double lat2,double long2){
        double angle,dy,dx;
        dy = lat2 - lat1;
        dx = Math.cos(Math.PI/ 180 * lat1) * (long2 - long1);
        angle = Math.atan2(dy, dx);
        angle = Math.toDegrees(angle);
        return (float)angle;
    }


    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        latitude = currentLocation.getLatitude();
        longitude = currentLocation.getLongitude();
        qiblaAngle = getQiblaAngle(latitude, longitude, meccaLatitude, meccaLongitude);
        Log.e(TAG, "onCreateView: "+longitude );

        Geocoder geocoder;
        List<Address> addresses = new ArrayList();


        try {
            Locale mLocale = new Locale("ar");
            Locale.setDefault(mLocale);
            geocoder = new Geocoder(activity, Locale.getDefault());
            addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String city = addresses.get(0).getLocality();
        String country = addresses.get(0).getCountryName();
        if (country == null || city == null){

            text_atas.setText("غير متوفر");
        }
        else {
            text_atas.setText(country+" - "+city);
            //UserLocation.setText(session.getLocationCode());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}