package com.testing.newapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.testing.newapp.fragmentUI.PaymentChargingFragment;
import com.testing.newapp.fragmentUI.PaymentInsertCard;

public class MainActivity extends AppCompatActivity {
    private static MainActivity mainActivity;

    public static MainActivity getInstance() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
//        loadFragmentUI("PaymentCharging");
        loadFragmentUI("PaymentInsert");
    }

    public void loadFragmentUI(String type) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (type) {
            case "PaymentCharging":
                Fragment paymentChargingFragment = new PaymentChargingFragment();
                transaction.replace(R.id.frameLoader, paymentChargingFragment);
                transaction.commit();
                break;
            case "PaymentInsert":
                Fragment paymentInsertFragment = new PaymentInsertCard();
                transaction.replace(R.id.frameLoader, paymentInsertFragment);
                transaction.commit();
                break;
        }

    }
}
