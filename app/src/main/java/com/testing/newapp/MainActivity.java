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
import com.testing.newapp.fragmentUI.cashPayment.CashPaymentApprovalFragment;
import com.testing.newapp.fragmentUI.cashPayment.CashPaymentSignatureFragment;
import com.testing.newapp.fragmentUI.disconnect.CashDisconnectedFragment;
import com.testing.newapp.fragmentUI.payment.PaymentChargingFragment;
import com.testing.newapp.fragmentUI.disconnect.CardDisconnectedFragment;
import com.testing.newapp.fragmentUI.payment.PaymentInsertCard;
import com.testing.newapp.fragmentUI.payment.PaymentSignatureFragment;
import com.testing.newapp.fragmentUI.splitPayment.SplitAmountPaymentFragment;
import com.testing.newapp.fragmentUI.splitPayment.SplitPaymentFragment;
import com.testing.newapp.fragmentUI.splitPayment.SplitPaymentSignatureFragment;
import com.testing.newapp.fragmentUI.splitPayment.SplitPaymentSuccessFragment;

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
                Fragment paymentChargingFragment = new PaymentChargingFragment();
                transaction.add(R.id.frameLoader, paymentChargingFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "PaymentInsert":
                Fragment paymentInsertFragment = new PaymentInsertCard();
                transaction.addToBackStack(paymentInsertFragment.getTag());
                transaction.replace(R.id.frameLoader, paymentInsertFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "SplitPayment":
                Fragment splitPaymentFragment = new SplitPaymentFragment();
                splitPaymentFragment.setArguments(bundle);
                transaction.addToBackStack(splitPaymentFragment.getTag());
                transaction.replace(R.id.frameLoader, splitPaymentFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "SplitAmountPayment":
                Fragment splitAmountPaymentFragment = new SplitAmountPaymentFragment();
                transaction.addToBackStack(splitAmountPaymentFragment.getTag());
                transaction.replace(R.id.frameLoader, splitAmountPaymentFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "PaymentSignature":
                Fragment paymentSignatureFragment = new PaymentSignatureFragment();
                transaction.addToBackStack(paymentSignatureFragment.getTag());
                transaction.replace(R.id.frameLoader, paymentSignatureFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "SplitPaymentSignature":
                Fragment splitPaymentSignatureFragment = new SplitPaymentSignatureFragment();
                transaction.addToBackStack(splitPaymentSignatureFragment.getTag());
                transaction.replace(R.id.frameLoader, splitPaymentSignatureFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "SplitPaymentSuccess":
                Fragment splitPaymentSuccessFragment = new SplitPaymentSuccessFragment();
                transaction.addToBackStack(splitPaymentSuccessFragment.getTag());
                transaction.replace(R.id.frameLoader, splitPaymentSuccessFragment);
                transaction.commitAllowingStateLoss();
                break;

            case "CashPaymentApproval":
                Fragment cashPaymentApprovalFragment = new CashPaymentApprovalFragment();
                transaction.addToBackStack(cashPaymentApprovalFragment.getTag());
                transaction.replace(R.id.frameLoader, cashPaymentApprovalFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "CashPaymentSignature":
                Fragment cashPaymentSignatureFragment = new CashPaymentSignatureFragment();
                transaction.addToBackStack(cashPaymentSignatureFragment.getTag());
                transaction.replace(R.id.frameLoader, cashPaymentSignatureFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "Disconnected":
                Fragment disconnectedFragment = new CardDisconnectedFragment();
                transaction.addToBackStack(disconnectedFragment.getTag());
                transaction.replace(R.id.frameLoader, disconnectedFragment);
                transaction.commitAllowingStateLoss();
                break;
            case "CashDisconnected":
                Fragment cashDisconnectedFragment = new CashDisconnectedFragment();
                transaction.addToBackStack(cashDisconnectedFragment.getTag());
                transaction.replace(R.id.frameLoader, cashDisconnectedFragment);
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
                showToast(t.toString(), Toast.LENGTH_LONG);
                progressDialog.dismiss(); //dismiss progress dialog
                t.printStackTrace();
            }
        });
    }

    public void setSpinnerGone() {
        spinnerDropDown.setVisibility(View.GONE);
    }

    public void setSpinnerHide(boolean isClick) {
        spinnerDropDown.setVisibility(View.VISIBLE);
        if (isClick) {
            spinnerDropDown.setEnabled(true);
            spinnerDropDown.setClickable(true);
        } else {
            spinnerDropDown.setEnabled(false);
            spinnerDropDown.setClickable(false);
        }
    }

    private String getJson(String strResp) {
        return strResp.substring(76, strResp.length() - 9);
    }


    public void showToast(String msg, int lengthLong) {
        Toast.makeText(MainActivity.this, msg, lengthLong).show();
    }
}
