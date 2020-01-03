package com.enablex.remitonline.webcommunication;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


public class WebCall extends AsyncTask<Void, Void, String> {

    private static final String TAG = "WebCall";
    private Context context;
    private WebResponse callback;
    private HashMap<String, String> paramsMap;
    private String urlPath;
    private int callCode;
    private Boolean isShowDialog = true;
    private JSONObject object = null;
    ProgressDialog dialog;

    public WebCall(Context context, WebResponse callback, JSONObject object,
                   String urlPath, int callCode, boolean isShowDialog) {
        this.context = context;
        this.callback = callback;
        this.object = object;
        this.urlPath = urlPath;
        this.callCode = callCode;
        this.isShowDialog=isShowDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!checkInternetConnection()) {

            return;
        }
        if (isShowDialog) {
            dialog = new ProgressDialog(context);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        return callPostCallWithJSON();
    }

    private String callPostCallWithJSON() {
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(WebConstants.baseURL + urlPath);
            Log.e("WebURL", String.valueOf(url));
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(60000);
            httpURLConnection.setConnectTimeout(60000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();
            OutputStream os = httpURLConnection.getOutputStream();
            if (object != null) {
                Log.e("jsonObject", object.toString());
                os.write(object.toString().getBytes());
                os.flush();
                os.close();
            }
            Log.e("responseCode", String.valueOf(httpURLConnection.getResponseCode()));
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK ||
                    httpURLConnection.getResponseCode() == 422 ||
                    httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED ||
                    httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                InputStream is = null;
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND ||
                        httpURLConnection.getResponseCode() == 422 || httpURLConnection.getResponseCode() == 401) {
                    is = httpURLConnection.getErrorStream();
                } else {
                    is = httpURLConnection.getInputStream();
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder builder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null) {
                    builder.append(line + "\n");
                    line = bufferedReader.readLine();
                }
                is.close();
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return builder.toString();
            } else {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return "Invalid response from the server";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            if (response == null) {
                Toast.makeText(context, "Unable to connect to internet", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, response);
                if (!response.isEmpty()) {
                    callback.onWebResponse(response, callCode);
                } else {
                    callback.onWebResponseError(response, callCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo activeNetInfoWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting() || activeNetInfoWifi != null && activeNetInfoWifi.isConnectedOrConnecting();
        if (isConnected) {
            Log.i("NET", "connected" + isConnected);
            return true;
        } else {
            Log.i("NET", "disconnected" + isConnected);
            return false;
        }
    }

}

