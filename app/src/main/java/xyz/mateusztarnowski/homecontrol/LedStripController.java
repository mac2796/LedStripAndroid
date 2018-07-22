package xyz.mateusztarnowski.homecontrol;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by mac2796 on 02.01.18.
 */

public class LedStripController {
    private final String address;
    private RequestQueue requestQueue;

    public LedStripController(Context context, String address) {
        this.address = address;
        requestQueue = Volley.newRequestQueue(context);
    }

    public int getColor() {
        //HTTP GET to LedStrip
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, address + "/light/color", new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
        return Color.rgb(60, 60, 60);
    }

    public int getBrightness() {
        //HTTP GET to LedStrip
        // TODO Implement
        return 5;
    }

    public void turnOff() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("on", false);
        } catch (Exception e) {
            Log.e("JsonObjectError", e.toString());
        }

        JsonObjectRequest request = getJsonObjectRequest(Request.Method.POST, "/light", jsonObject);
        requestQueue.add(request);
    }

    public void changeBrightness(int to) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("brightness", to + 1);
        } catch (Exception e) {

        }

        JsonObjectRequest request = getJsonObjectRequest(Request.Method.POST, "/light/brightness", jsonObject);
        requestQueue.add(request);
    }

    public void changeColor(int r, int g, int b) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("r", r);
            jsonObject.put("g", g);
            jsonObject.put("b", b);
        } catch (Exception e) {

        }

        JsonObjectRequest request = getJsonObjectRequest(Request.Method.POST, "/light/color", jsonObject);
        requestQueue.add(request);
    }

    public void changeColor(int led, int r, int g, int b) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("r", r);
            jsonObject.put("g", g);
            jsonObject.put("b", b);
            jsonObject.put("l", led);
        } catch (Exception e) {

        }

        JsonObjectRequest request = getJsonObjectRequest(Request.Method.POST, "/light/color/single", jsonObject);
        requestQueue.add(request);
    }

    private JsonObjectRequest getJsonObjectRequest(int method, String relativePath, JSONObject jsonObject) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, address + relativePath, jsonObject, new Response.Listener<JSONObject>() {
            @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", error.toString());
                }
            });
        return jsonObjectRequest;
    }
}
