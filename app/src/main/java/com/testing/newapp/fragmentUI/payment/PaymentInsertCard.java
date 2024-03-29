package com.testing.newapp.fragmentUI.payment;

import android.os.Bundle;
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
    ImageView imaArrow1, imaArrow2, imgPayCard1, imPaymentProcessing;
    Button butPay, butPaySlitPay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_insert_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.getInstance().setSpinnerHide(true);
        layViewDetails = view.findViewById(R.id.layViewDetails);
        layAmountInfoDropDown = view.findViewById(R.id.layAmountInfoDropDown);
        imaArrow1 = view.findViewById(R.id.imaArrow1);
        imaArrow2 = view.findViewById(R.id.imaArrow2);
        layInputCard = view.findViewById(R.id.layInputCard);
        layPayOptions = view.findViewById(R.id.layPayOptions);
        layPaymentProcessing = view.findViewById(R.id.layPaymentProcessing);
        butPay = view.findViewById(R.id.butPay);
        butPaySlitPay = view.findViewById(R.id.butPaySlitPay);
        imgPayCard1 = view.findViewById(R.id.imgPayCard1);
        imPaymentProcessing = view.findViewById(R.id.imPaymentProcessing);

        layAmountInfoDropDown.setVisibility(View.GONE);
        layPayOptions.setVisibility(View.VISIBLE);
        layPaymentProcessing.setVisibility(View.GONE);
        imgPayCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layPayOptions.setVisibility(View.GONE);
                layPaymentProcessing.setVisibility(View.VISIBLE);
            }
        });

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
                MainActivity.getInstance().loadFragmentUI("SplitAmountPayment", null);
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
                MainActivity.getInstance().loadFragmentUI("CashPaymentApproval", null);
            }
        });
        imPaymentProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().loadFragmentUI("PaymentSignature", null);
            }
        });

    }
}