package com.example.kikuchio.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_SUSPECT = "suspect";

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;

    @Override
    public String toString() {
        return mTitle;
    }

    public Crime() {
        this.mId = UUID.randomUUID();
        this.mDate = new Date();
    }

    public Crime(JSONObject jsonCrime) throws JSONException {
        mId = UUID.fromString(jsonCrime.getString(JSON_ID));
        if(jsonCrime.has(JSON_TITLE)) {
            mTitle = jsonCrime.getString(JSON_TITLE);
        }
        mSolved = jsonCrime.getBoolean(JSON_SOLVED);
        mSuspect = jsonCrime.getString(JSON_SUSPECT);
        mDate = new Date(jsonCrime.getLong(JSON_DATE));
    }

    public UUID getId() {
        return mId;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public void setSolved(boolean solved) {
        this.mSolved = solved;
    }

    public Date getDate() {
        return this.mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSuspect(String suspect) {
        this.mSuspect = suspect;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_SOLVED, mSolved);
        json.put(JSON_DATE, mDate.getTime());
        json.put(JSON_SUSPECT, mSuspect);
        return json;
    }
}
