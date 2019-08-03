package com.testing.newapp;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.testing.newapp.api.ApiClient;
import com.testing.newapp.api.ApiInterface;
import com.testing.newapp.dataModel.CustomerDataModel;
import com.testing.newapp.fragmentUI.payment.PaymentChargingFragment;
import com.testing.newapp.fragmentUI.payment.PaymentDisconnected;
import com.testing.newapp.fragmentUI.payment.PaymentInsertCard;
import com.testing.newapp.fragmentUI.payment.PaymentSignatureFragment;
import com.testing.newapp.fragmentUI.splitPayment.SplitAmountPaymentFragment;
import com.testing.newapp.fragmentUI.splitPayment.SplitPaymentFragment;
import com.testing.newapp.fragmentUI.splitPayment.SplitPaymentSignatureFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        Toolbar mTopToolbar = findViewById(R.id.toolbarPayment);
        setSupportActionBar(mTopToolbar);
        GetCustomerForPaymentNew();
        loadFragmentUI("PaymentCharging", null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFragmentUI("PaymentInsert", null);
//                loadDisconnected();
            }
        }, SPLASH_DELAY);
    }

    private void loadDisconnected() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFragmentUI("Disconnected", null);
            }
        }, SPLASH_DELAY);
    }

    public void loadFragmentUI(String type, Bundle bundle) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (type) {
            case "PaymentCharging":
                spinnerDropDown.setVisibility(View.GONE);
                Fragment paymentChargingFragment = new PaymentChargingFragment();
                transaction.add(R.id.frameLoader, paymentChargingFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "PaymentInsert":
                spinnerDropDown.setEnabled(true);
                spinnerDropDown.setClickable(true);
                spinnerDropDown.setVisibility(View.VISIBLE);
                Fragment paymentInsertFragment = new PaymentInsertCard();
                transaction.replace(R.id.frameLoader, paymentInsertFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "Disconnected":
                spinnerDropDown.setEnabled(false);
                spinnerDropDown.setClickable(false);
                Fragment disconnectedFragment = new PaymentDisconnected();
                transaction.replace(R.id.frameLoader, disconnectedFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "SplitPayment":
                spinnerDropDown.setEnabled(true);
                spinnerDropDown.setClickable(true);
                Fragment splitPaymentFragment = new SplitPaymentFragment();
                splitPaymentFragment.setArguments(bundle);
                transaction.replace(R.id.frameLoader, splitPaymentFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "SplitAmountPayment":
                spinnerDropDown.setEnabled(false);
                spinnerDropDown.setClickable(false);
                Fragment splitAmountPaymentFragment = new SplitAmountPaymentFragment();
                transaction.replace(R.id.frameLoader, splitAmountPaymentFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "PaymentSignature":
                spinnerDropDown.setEnabled(false);
                spinnerDropDown.setClickable(false);
                Fragment paymentSignatureFragment = new PaymentSignatureFragment();
                transaction.replace(R.id.frameLoader, paymentSignatureFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "SplitPaymentSignature":
                spinnerDropDown.setEnabled(false);
                spinnerDropDown.setClickable(false);
                Fragment splitPaymentSignatureFragment = new SplitPaymentSignatureFragment();
                transaction.replace(R.id.frameLoader, splitPaymentSignatureFragment);
                transaction.commitAllowingStateLoss();
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
                            CustomerDataModel customerDataModel = new CustomerDataModel();
                            customerDataModel.setFullName("0-Select Customer");
                            customerDataModel.setCustomerId(0);
                            final ArrayList<CustomerDataModel> listData = new ArrayList<>();
                            listData.add(customerDataModel);
                            for (int i = 0; i < customers.length(); i++) {
                                CustomerDataModel dataModel = new Gson().fromJson(customers.getJSONObject(i).toString(), new TypeToken<CustomerDataModel>() {
                                }.getType());
                                listData.add(dataModel);
                            }
                            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(MainActivity.this, listData);
                            spinnerDropDown.setAdapter(spinnerAdapter);
                            spinnerDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != 0) {
                                        CustomerDataModel dataModel = listData.get(position);
                                        long dailysheetid = dataModel.getDailysheetid();
                                        long CustomerId = dataModel.getCustomerId();
                                        Toast.makeText(MainActivity.this, "dailysheetid :- " + dailysheetid + " CustomerId :-" + CustomerId, Toast.LENGTH_LONG).show();

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


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
