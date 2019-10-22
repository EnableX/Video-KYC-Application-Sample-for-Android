package com.enablex.remitonline.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enablex.remitonline.R;
import com.enablex.remitonline.webcommunication.WebCall;
import com.enablex.remitonline.webcommunication.WebConstants;
import com.enablex.remitonline.webcommunication.WebResponse;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, WebResponse {

    private TextView startTV;
    private String name;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Remit Online");
        setView();
    }

    private void setView() {
        startTV = (TextView) findViewById(R.id.startTV);
        startTV.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startTV:
                getRoomWebCall();
                break;
        }
    }

    private void getRoomWebCall() {
        if (dialog != null) {
            dialog.show();
        }
        new WebCall(MainActivity.this, this, null, WebConstants.createRoomURL, WebConstants.createRoomCode, false).execute();
    }

    @Override
    public void onWebResponse(String response, int callCode) {
        Log.e("onWebResponse", response);
        switch (callCode) {
            case WebConstants.createRoomCode:
                onCreateRoomSuccess(response);
                break;
            case WebConstants.createTokenCode:
                onCreateTokenSuccess(response);
                break;
        }
    }

    private void onCreateTokenSuccess(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (dialog != null) {
                dialog.dismiss();
            }
            if (jsonObject.optString("result").equalsIgnoreCase("0")) {
                Intent intent = new Intent(MainActivity.this, VideoConfActivity.class);
                intent.putExtra("token", jsonObject.optString("token"));
                intent.putExtra("name", name);
                startActivity(intent);
            } else {
                Toast.makeText(this, jsonObject.optString("error"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onCreateRoomSuccess(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("result").equalsIgnoreCase("0")) {
                new WebCall(this, this, createTokenJSON(jsonObject), WebConstants.createTokenURL, WebConstants.createTokenCode, false).execute();
            }else{
                if(dialog!=null){
                    dialog.dismiss();
                }
                Toast.makeText(this, jsonObject.optString("error"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject createTokenJSON(JSONObject response) {
        JSONObject object = new JSONObject();
        try {
            name = response.optJSONObject("room").optString("name");

            object.put("name", response.optJSONObject("room").optString("name"));
            object.put("role", "participant");
            object.put("roomId", response.optJSONObject("room").optString("room_id"));
            object.put("user_ref", response.optJSONObject("room").optString("owner_ref"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    @Override
    public void onWebResponseError(String error, int callCode) {
        Log.e("onWebResponseError", error);
    }
}
