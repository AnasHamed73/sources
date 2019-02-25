package com.example.kikuchio.criminalintent;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class CriminalIntentJsonSerializer {
    private Context mContext;
    private String mFileName;

    public CriminalIntentJsonSerializer(Context c, String fileName) {
        mContext = c;
        mFileName = fileName;
    }

    public void saveCrimes(List<Crime> crimes) throws IOException, JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Crime c : crimes)
            jsonArray.put(c.toJson());
        try (Writer writer = getOutputStreamWriter()) {
            writer.write(jsonArray.toString());
        }
    }

    public List<Crime> loadCrimes() throws IOException, JSONException {
        List<Crime> crimes = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(mContext.openFileInput(mFileName)))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONArray jsonArray = (JSONArray) new JSONTokener(builder.toString()).nextValue();
            for(int i = 0; i < jsonArray.length(); i++) {
                crimes.add(new Crime(jsonArray.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            //ignored
        }
        return crimes;
    }

    @NonNull
    private OutputStreamWriter getOutputStreamWriter() throws FileNotFoundException {
        return new OutputStreamWriter(mContext.openFileOutput(mFileName, Context.MODE_PRIVATE));
    }
}
