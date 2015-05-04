package com.foodiehoodie.android.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

public class UserMobileDetail extends AsyncTask<Void,Void,Void> {
    @Override
    protected Void doInBackground(Void... params) {
        Activity tempActivity = new Activity();
        TelephonyManager tMgr = (TelephonyManager) tempActivity.getSystemService(tempActivity.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        String mIMEINumber = tMgr.getDeviceId();
        Log.d("Phone # ",mPhoneNumber);
        Log.d("IMEI # ",mIMEINumber);
        //Todo - Save data in database

    return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
}
