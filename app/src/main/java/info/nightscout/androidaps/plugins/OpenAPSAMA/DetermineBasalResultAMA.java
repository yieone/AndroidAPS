package info.nightscout.androidaps.plugins.OpenAPSAMA;

import android.os.Parcel;
import android.os.Parcelable;

import com.eclipsesource.v8.V8Object;

import org.json.JSONException;
import org.json.JSONObject;

import info.nightscout.androidaps.plugins.Loop.APSResult;
import info.nightscout.androidaps.data.IobTotal;

public class DetermineBasalResultAMA extends APSResult {

    public JSONObject json = new JSONObject();
    public double eventualBG;
    public double snoozeBG;
    public IobTotal iob;

    public DetermineBasalResultAMA(V8Object result, JSONObject j) {
        json = j;
        if (result.contains("error")) {
            reason = result.getString("error");
            changeRequested = false;
            rate = -1;
            duration = -1;
        } else {
            reason = result.getString("reason");
            eventualBG = result.getDouble("eventualBG");
            snoozeBG = result.getDouble("snoozeBG");
            if (result.contains("rate")) {
                rate = result.getDouble("rate");
                if (rate < 0d) rate = 0d;
                changeRequested = true;
            } else {
                rate = -1;
                changeRequested = false;
            }
            if (result.contains("duration")) {
                duration = result.getInteger("duration");
                changeRequested = changeRequested;
            } else {
                duration = -1;
                changeRequested = false;
            }
        }
        result.release();
    }

    public DetermineBasalResultAMA() {
    }

    @Override
    public DetermineBasalResultAMA clone() {
        DetermineBasalResultAMA newResult = new DetermineBasalResultAMA();
        newResult.reason = new String(reason);
        newResult.rate = rate;
        newResult.duration = duration;
        newResult.changeRequested = changeRequested;
        newResult.rate = rate;
        newResult.duration = duration;
        newResult.changeRequested = changeRequested;

        try {
            newResult.json = new JSONObject(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        newResult.eventualBG = eventualBG;
        newResult.snoozeBG = snoozeBG;
        return newResult;
    }

    @Override
    public JSONObject json() {
        try {
            JSONObject ret = new JSONObject(this.json.toString());
            return ret;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}