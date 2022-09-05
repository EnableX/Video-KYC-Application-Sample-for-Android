package com.enablex.remitonline.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.enablex.remitonline.R;
import com.enablex.remitonline.webcommunication.WebCall;
import com.enablex.remitonline.webcommunication.WebConstants;
import com.enablex.remitonline.webcommunication.WebResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import enx_rtc_android.Controller.EnxPlayerView;
import enx_rtc_android.Controller.EnxReconnectObserver;
import enx_rtc_android.Controller.EnxRoom;
import enx_rtc_android.Controller.EnxRoomObserver;
import enx_rtc_android.Controller.EnxRtc;
import enx_rtc_android.Controller.EnxScreenShotObserver;
import enx_rtc_android.Controller.EnxStream;
import enx_rtc_android.Controller.EnxStreamObserver;
import enx_rtc_android.Controller.EnxUtils;

public class VideoConfActivity extends AppCompatActivity implements EnxRoomObserver, EnxStreamObserver, View.OnClickListener, EnxScreenShotObserver, WebResponse, EnxReconnectObserver {
    String roomId;
    String[] PERMISSIONS = {
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.RECORD_AUDIO
    };
    private String token;
    private String name;
    private FrameLayout selfFL;
    private TextView quesTV;
    private TextView nextTV;
    private EnxRtc enxRtc;
    private EnxRoom mEnxRoom;
    private EnxStream localStream;
    private final int PERMISSION_ALL = 1;
    private EnxPlayerView enxPlayerView;
    private int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_conf);
        getPreviousIntent();
        getSupportActionBar().setTitle(name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            } else {
                initialize();
            }
        }
    }

    @Override
    public void onRoomConnected(EnxRoom enxRoom, JSONObject jsonObject) {
        //received when user connected with Enablex room
        Log.e("onRoomConnected", jsonObject.toString());
        this.mEnxRoom = enxRoom;
        roomId = jsonObject.optJSONObject("room").optString("_id");
        Log.e("roomId", roomId);
        mEnxRoom.publish(localStream);
        mEnxRoom.setReconnectObserver(this);
    }

    @Override
    public void onRoomError(JSONObject jsonObject) {
        //received when any error occurred while connecting to the Enablex room
        Log.e("onRoomError", jsonObject.toString());
        Toast.makeText(VideoConfActivity.this, "Room Error", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void onUserConnected(JSONObject jsonObject) {
        // received when a new remote participant joins the call
    }

    @Override
    public void onUserDisConnected(JSONObject jsonObject) {
        // received when a remote participant left the call
    }

    @Override
    public void onPublishedStream(EnxStream enxStream) {
        //received when audio video published successfully to the other remote users
        Log.e("onPublishedStream", enxStream.toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                enxPlayerView = new EnxPlayerView(VideoConfActivity.this, EnxPlayerView.ScalingType.SCALE_ASPECT_BALANCED, true);
                enxPlayerView.setZOrderMediaOverlay(true);
                enxStream.attachRenderer(enxPlayerView);
                selfFL.setVisibility(View.VISIBLE);
                selfFL.addView(enxPlayerView);
                quesTV.setText(getResources().getString(R.string.ques));
                nextTV.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onUnPublishedStream(EnxStream enxStream) {
        //received when audio video unpublished successfully to the other remote users
    }

    @Override
    public void onStreamAdded(EnxStream enxStream) {
        //received when a new stream added
    }

    @Override
    public void onSubscribedStream(EnxStream enxStream) {
        //received when a remote stream subscribed successfully
    }

    @Override
    public void onUnSubscribedStream(EnxStream enxStream) {
        //received when a remote stream unsubscribed successfully
    }

    @Override
    public void onRoomDisConnected(JSONObject jsonObject) {
        //received when Enablex room successfully disconnected
        Log.e("onRoomDisConnected", jsonObject.toString());
        Intent intent = new Intent(VideoConfActivity.this, EndKYCActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onEventError(JSONObject jsonObject) {
        //received when any error occurred for any room event
    }

    @Override
    public void onEventInfo(JSONObject jsonObject) {
        // received for different events update
    }

    @Override
    public void onNotifyDeviceUpdate(String s) {
        // received when when new media device changed
    }

    @Override
    public void onAcknowledgedSendData(JSONObject jsonObject) {
// received your chat data successfully sent to the other end
    }

    @Override
    public void onMessageReceived(JSONObject jsonObject) {
// received when chat data received at room
    }

    @Override
    public void onUserDataReceived(JSONObject jsonObject) {

    }

    @Override
    public void onUserStartTyping(boolean b) {

    }

    @Override
    public void onSwitchedUserRole(JSONObject jsonObject) {
        // received when user switch their role (from moderator  to participant)
    }

    @Override
    public void onUserRoleChanged(JSONObject jsonObject) {
// received when user role changed successfully
    }

    @Override
    public void onConferencessExtended(JSONObject jsonObject) {

    }

    @Override
    public void onConferenceRemainingDuration(JSONObject jsonObject) {

    }

    @Override
    public void onAckDropUser(JSONObject jsonObject) {

    }

    @Override
    public void onAckDestroy(JSONObject jsonObject) {

    }

    @Override
    public void onAudioEvent(JSONObject jsonObject) {
        //received when audio mute/unmute happens
    }

    @Override
    public void onVideoEvent(JSONObject jsonObject) {
        //received when video mute/unmute happens
    }

    @Override
    public void onReceivedData(JSONObject jsonObject) {
        //received when chat data received at room level
    }

    @Override
    public void onRemoteStreamAudioMute(JSONObject jsonObject) {
        //received when any remote stream mute audio
    }

    @Override
    public void onRemoteStreamAudioUnMute(JSONObject jsonObject) {
        //received when any remote stream unmute audio
    }

    @Override
    public void onRemoteStreamVideoMute(JSONObject jsonObject) {
        //received when any remote stream mute video
    }

    @Override
    public void onRemoteStreamVideoUnMute(JSONObject jsonObject) {
        //received when any remote stream unmute video
    }

    @Override
    public void onAckPinUsers(JSONObject jsonObject) {

    }

    @Override
    public void onAckUnpinUsers(JSONObject jsonObject) {

    }

    @Override
    public void onPinnedUsers(JSONObject jsonObject) {

    }

    @Override
    public void onRoomAwaited(EnxRoom enxRoom, JSONObject jsonObject) {

    }

    @Override
    public void onUserAwaited(JSONObject jsonObject) {

    }

    @Override
    public void onAckForApproveAwaitedUser(JSONObject jsonObject) {

    }

    @Override
    public void onAckForDenyAwaitedUser(JSONObject jsonObject) {

    }

    @Override
    public void onAckAddSpotlightUsers(JSONObject jsonObject) {

    }

    @Override
    public void onAckRemoveSpotlightUsers(JSONObject jsonObject) {

    }

    @Override
    public void onUpdateSpotlightUsers(JSONObject jsonObject) {

    }

    @Override
    public void onRoomBandwidthAlert(JSONObject jsonObject) {

    }

    @Override
    public void onStopAllSharingACK(JSONObject jsonObject) {

    }

    @Override
    public void onACKStartLiveTranscription(JSONObject jsonObject) {

    }

    @Override
    public void onACKStopLiveTranscription(JSONObject jsonObject) {

    }

    @Override
    public void onTranscriptionEvents(JSONObject jsonObject) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                    initialize();
                } else {
                    Toast.makeText(this, "Please enable permissions to further proceed.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextTV:
                count++;
                onNextClick(count);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.video_actions, menu);
        return true;
    }

    private void initialize() {
        setView();
        enxRtc = new EnxRtc(this, this, this);
        localStream = enxRtc.joinRoom(token, getPublisherInfo(), getReconnectInfo(), new JSONArray());
        quesTV.setText("Please wait.....");
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private JSONObject getPublisherInfo() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("audio", true);
            jsonObject.put("video", true);
            jsonObject.put("data", true);
            JSONObject videoSize = new JSONObject();
            videoSize.put("minWidth", 320);
            videoSize.put("minHeight", 180);
            videoSize.put("maxWidth", 1280);
            videoSize.put("maxHeight", 720);
            jsonObject.put("videoSize", videoSize);
            jsonObject.put("audioMuted", false);
            jsonObject.put("videoMuted", false);
            jsonObject.put("name", "Remit Online");
            jsonObject.put("maxVideoLayers", 3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void setView() {
        selfFL = (FrameLayout) findViewById(R.id.selfFL);
        quesTV = (TextView) findViewById(R.id.quesTV);
        nextTV = (TextView) findViewById(R.id.nextTV);
        nextTV.setOnClickListener(this);
    }

    private void getPreviousIntent() {
        if (getIntent() != null) {
            token = getIntent().getStringExtra("token");
            name = getIntent().getStringExtra("name");
        }
    }


    private void onNextClick(int count) {
        quesTV.setTextSize(22);
        switch (count) {
            case 1:
                if (enxPlayerView != null) {
                    enxPlayerView.captureScreenShot(this);
                }
                setQues(getResources().getString(R.string.ques1));
                break;
            case 2:
                setQues(getResources().getString(R.string.ques2));
                break;
            case 3:
                setQues(getResources().getString(R.string.ques3));
                break;
            case 4:
                setQues(getResources().getString(R.string.ques4));
                break;
            case 5:
                if (mEnxRoom != null) {
                    mEnxRoom.disconnect();
                }
                break;
        }
        showNextBtn();
    }

    private void setQues(String string) {
        JSONObject object = new JSONObject();
        try {
            object.put("message", string);
            object.put("from", name);
            object.put("type", "public");
            object.put("timestamp", EnxUtils.getCurrentTimeInMillSecond());
            localStream.sendData(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        quesTV.setText(string);
        nextTV.setVisibility(View.INVISIBLE);
    }

    private void showNextBtn() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (count == 4) {
                    nextTV.setText("End KYC");
                } else {
                    nextTV.setText("Next");
                }
                nextTV.setVisibility(View.VISIBLE);
            }
        }, 10000);
    }

    public JSONObject getReconnectInfo() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("allow_reconnect", true);
            jsonObject.put("number_of_attempts", 3);
            jsonObject.put("timeout_interval", 15);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void OnCapturedView(Bitmap bitmap) {
        //received when any screenshot capture of any stream
        Log.e("OnCapturedView", bitmap.toString());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        JSONObject jsonObject = new JSONObject();
        Log.e("Filename", roomId + "_snapshot.png");
        try {
            jsonObject.put("data", Base64.encodeToString(byteArray, Base64.DEFAULT));
            jsonObject.put("filename", roomId + "_snapshot.png");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebCall(this, this, jsonObject, WebConstants.uploadImageURL, WebConstants.uploadImageCode, false).execute();
    }

    @Override
    public void onWebResponse(String response, int callCode) {
        Log.e("response", response);
        switch (callCode) {
            case WebConstants.uploadImageCode:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("success").equalsIgnoreCase("true")) {
                        Log.e("Snapshot", "done");
                    } else {
                        Log.e("Snapshot", "Error occurred");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onWebResponseError(String error, int callCode) {

    }

    @Override
    public void onReconnect(String s) {
// received when room tries to reconnect due to low bandwidth or any connection interruption
    }

    @Override
    public void onUserReconnectSuccess(EnxRoom enxRoom, JSONObject jsonObject) {
// received when reconnect successfully completed
    }
}
