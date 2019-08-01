package com.testing.newapp;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.testing.newapp.api.ApiClient;
import com.testing.newapp.api.ApiInterface;
import com.testing.newapp.dataModel.CustomerDataModel;
import com.testing.newapp.fragmentUI.PaymentChargingFragment;
import com.testing.newapp.fragmentUI.PaymentDisconnected;
import com.testing.newapp.fragmentUI.PaymentInsertCard;
import com.testing.newapp.fragmentUI.SplitAmountPaymentFragment;
import com.testing.newapp.fragmentUI.SplitPaymentFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static MainActivity mainActivity;
    public long SPLASH_DELAY = 3000;
    public static String URL = "http://appservicestesting.allmysons.com/TimeTrackerTesting.asmx/";
    Spinner spinnerDropDown;

    public static MainActivity getInstance() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        spinnerDropDown = findViewById(R.id.spinnerDropDown);
        GetCustomerForPaymentNew();
        loadFragmentUI("PaymentCharging");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFragmentUI("PaymentInsert");
//                loadDisconnected();
            }
        }, SPLASH_DELAY);
    }

    private void loadDisconnected() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFragmentUI("Disconnected");
            }
        }, SPLASH_DELAY);
    }

    public void loadFragmentUI(String type) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (type) {
            case "PaymentCharging":
                spinnerDropDown.setVisibility(View.GONE);
                Fragment paymentChargingFragment = new PaymentChargingFragment();
                transaction.replace(R.id.frameLoader, paymentChargingFragment);
                transaction.commit();
                break;
            case "PaymentInsert":
                spinnerDropDown.setEnabled(true);
                spinnerDropDown.setClickable(true);
                spinnerDropDown.setVisibility(View.VISIBLE);
                Fragment paymentInsertFragment = new PaymentInsertCard();
                transaction.replace(R.id.frameLoader, paymentInsertFragment);
                transaction.commit();
                break;
            case "Disconnected":
                spinnerDropDown.setEnabled(false);
                spinnerDropDown.setClickable(false);
                Fragment disconnectedFragment = new PaymentDisconnected();
                transaction.replace(R.id.frameLoader, disconnectedFragment);
                transaction.commit();
                break;
            case "SplitPayment":
                spinnerDropDown.setEnabled(true);
                spinnerDropDown.setClickable(true);
                Fragment splitPaymentFragment = new SplitPaymentFragment();
                transaction.replace(R.id.frameLoader, splitPaymentFragment);
                transaction.commit();
                break;
            case "SplitAmountPayment":
                spinnerDropDown.setEnabled(true);
                spinnerDropDown.setClickable(true);
                Fragment splitAmountPaymentFragment = new SplitAmountPaymentFragment();
                transaction.replace(R.id.frameLoader, splitAmountPaymentFragment);
                transaction.commit();
                break;
        }
    }

    private void GetCustomerForPaymentNew() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Fetching customer details....");
        progressDialog.show();
        int empid = 69489;
        String date = "07/29/2019";
        Log.i("Current Date:", date);
        ApiInterface apiInterface = ApiClient.getAMSClient(URL).create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getCustomerForPaymentNew(date, empid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response != null) {
                    try {
                        if (response.body() != null) {
                            String strResp = response.body().string();
                            String jsonStr = getJson(strResp);
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            JSONArray customers = (JSONArray) jsonObject.get("Table");
                            CustomerDataModel dataModel;
                            ArrayList<CustomerDataModel> listData = new ArrayList<>();
                            for (int i = 0; i < customers.length(); i++) {
                                dataModel = new Gson().fromJson(customers.getJSONObject(i).toString(), new TypeToken<CustomerDataModel>() {
                                }.getType());
                                listData.add(dataModel);
                            }
                            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(MainActivity.this, listData);
                            spinnerDropDown.setAdapter(spinnerAdapter);
                            /*JSONObject customersRow = customers.getJSONObject(0);
                            String CustomerId = customersRow.getString("CustomerId");
                            String dailysheetid = customersRow.getString("dailysheetid");
                            String FromState = customersRow.getString("FromState");
                            String ToState = customersRow.getString("ToState");
                            String Bol1Url = customersRow.getString("Bol1Url");
                            String isPaymentexception = customersRow.getString("isPaymentexception");
                            String FullName = customersRow.getString("FullName");*/
                        } else {
                            String err = response.message();
                            showToast(err, Toast.LENGTH_LONG);
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    String err = "Response is null";
                    showToast(err, Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // if error occurs in network transaction then we can get the error in this method.
                showToast(t.toString(), Toast.LENGTH_LONG);
                progressDialog.dismiss(); //dismiss progress dialog
                t.printStackTrace();
            }
        });
    }

    private String getJson(String strResp) {
        return strResp.substring(76, strResp.length() - 9);
    }


    public void showToast(String msg, int lengthLong) {
        Toast.makeText(MainActivity.this, msg, lengthLong).show();
    }
}
