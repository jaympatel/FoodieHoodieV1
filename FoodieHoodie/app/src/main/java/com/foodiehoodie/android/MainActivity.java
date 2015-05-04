package com.foodiehoodie.android;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.beardedhen.androidbootstrap.FontAwesomeText;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FontAwesomeText loader = (FontAwesomeText)findViewById(R.id.loader);
        loader.startFlashing(this,true, FontAwesomeText.AnimationSpeed.MEDIUM);
        new UserMobileDetail().execute();

    }
    //Class for fetching user mobile information
    private class UserMobileDetail extends AsyncTask<Void,Void,Void> implements  LocationListener{
        protected LocationManager locationManager;
        protected LocationListener locationListener;
        @Override
        protected Void doInBackground(Void... params) {
            TelephonyManager tMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            String mPhoneNumber = tMgr.getLine1Number();
            String mIMEINumber = tMgr.getDeviceId();
            Log.d("Phone # ", mPhoneNumber);
            Log.d("IMEI # ",mIMEINumber);
            //Todo - Save data in database
            //Todo - Configure GoogleAnalytics
            //Todo - Get LastLocation
            //locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            /*Error : java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()*/
           // Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //Log.d("Latitude ",location.getLatitude()+"");
            //Log.d("Longitude ",location.getLongitude()+"");
            //Todo Remove this loop once location is been found
            Log.d("Loop Started","Loop Started");
            for(int i=0;i<60000000;i++);
            Log.d("Loop Ended","Loop Ends");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(MainActivity.this,FillIngredientsActivity.class);
            startActivity(intent);
            finish();//finish this activity
        }

        @Override
        public void onLocationChanged(Location location) {

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
}
