package com.example.kikuchio.criminalintent;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

    private CriminalIntentJsonSerializer mSerializer;

    private List<Crime> mCrimes;
    private static CrimeLab sCrimeLab;

    private Context mAppContext;

    private CrimeLab(Context context) {
        this.mAppContext = context;
        mSerializer = new CriminalIntentJsonSerializer(context, FILENAME);
        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (IOException|JSONException e) {
            mCrimes = new ArrayList<>();
            Log.e(TAG, "error loading crimes", e);
        }
    }

    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "Crimes saved to file");
            return true;
        } catch (IOException|JSONException e) {
            Log.e(TAG, "Error saving crimes: " + e);
            return false;
        }
    }

    public void add(Crime crime) {
        mCrimes.add(crime);
    }

    public void delete(Crime crime) {
        mCrimes.remove(crime);
    }

    public static CrimeLab get(Context context) {
        if(sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context.getApplicationContext());
        }
        return sCrimeLab;
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for(Crime c : mCrimes) {
            if(c.getId().equals(id))
                return c;
        }
        return null;
    }
}
