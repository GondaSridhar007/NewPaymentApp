package com.testing.newapp.fragmentUI;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.testing.newapp.MainActivity;
import com.testing.newapp.R;

public class PaymentInsertCard extends Fragment {
    LinearLayout layViewDetails, layAmountInfoDropDown, layInputCard, layPayOptions, layPaymentProcessing;
    boolean isLayAmountInfoDropDownVisibility = false;
    ImageView imaArrow1, imaArrow2;
    Button butPay, butPaySlitPay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_insert_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layViewDetails = view.findViewById(R.id.layViewDetails);
        layAmountInfoDropDown = view.findViewById(R.id.layAmountInfoDropDown);
        imaArrow1 = view.findViewById(R.id.imaArrow1);
        imaArrow2 = view.findViewById(R.id.imaArrow2);
        layInputCard = view.findViewById(R.id.layInputCard);
        layPayOptions = view.findViewById(R.id.layPayOptions);
        layPaymentProcessing = view.findViewById(R.id.layPaymentProcessing);
        butPay = view.findViewById(R.id.butPay);
        butPaySlitPay = view.findViewById(R.id.butPaySlitPay);

        layAmountInfoDropDown.setVisibility(View.GONE);
        layPayOptions.setVisibility(View.GONE);
        layPaymentProcessing.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layPayOptions.setVisibility(View.VISIBLE);
                layPaymentProcessing.setVisibility(View.GONE);
            }
        }, MainActivity.getInstance().SPLASH_DELAY);
        layViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLayAmountInfoDropDownVisibility) {
                    layAmountInfoDropDown.setVisibility(View.VISIBLE);
                    isLayAmountInfoDropDownVisibility = true;
                    imaArrow1.setRotation(90);
                    imaArrow2.setRotation(90);
                } else {
                    layAmountInfoDropDown.setVisibility(View.GONE);
                    isLayAmountInfoDropDownVisibility = false;
                    imaArrow1.setRotation(270);
                    imaArrow2.setRotation(270);
                }
            }
        });
        butPaySlitPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLayAmountInfoDropDownVisibility) {
                    layAmountInfoDropDown.setVisibility(View.GONE);
                    isLayAmountInfoDropDownVisibility = false;
                    imaArrow1.setRotation(270);
                    imaArrow2.setRotation(270);
                }
                MainActivity.getInstance().loadFragmentUI("SplitPayment");
            }
        });
        butPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLayAmountInfoDropDownVisibility) {
                    layAmountInfoDropDown.setVisibility(View.GONE);
                    isLayAmountInfoDropDownVisibility = false;
                    imaArrow1.setRotation(270);
                    imaArrow2.setRotation(270);
                }
            }
        });
    }
}