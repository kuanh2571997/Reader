package com.example.tablayout.Model;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.loopj.android.http.HttpGet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class getToken extends AsyncTask<String, Void, String> {

    ProgressDialog mDialog;

    //Ctrl+O


    @Override
    protected String doInBackground(String... strings) {
        InputStream inputStream = null;
        HttpClient httpClient = new DefaultHttpClient();
        String result = "";
        try {
            HttpResponse httpResponse = httpClient.execute(new HttpGet("http://ocp-api-v2.gdcvn.com/v1/publishers/get-items?id=120&limit=0&offset=20&publisher_key=zw5yfhcygiruH81M"));
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else {
                result = "Did not work!";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        //Khởi tạo dialog
//        mDialog = new ProgressDialog(MainActivity.this,android.R.style.Theme_DeviceDefault_Dialog);
//        mDialog.setCancelable(false);
//        mDialog.setMessage("Please wait");
//        mDialog.show();
//    }

    private static String convertInputStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(inputStream));
        String line = "", result = "";

        try {
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
