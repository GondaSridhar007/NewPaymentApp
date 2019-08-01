package com.testing.newapp.fragmentUI;

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
import android.widget.TextView;

import com.testing.newapp.R;

public class SplitPaymentFragment extends Fragment {
    LinearLayout laySplitViewDetails, laySplitAmountInfoDropDown, laySplitInputCard, laySplitPayOptions, laySplitPaymentProcessing;
    boolean isLayAmountInfoDropDownVisibility = false;
    ImageView imaArrow1, imaArrow2, imgPayCard1;
    Button butSplitPay, butSplitChange;
    double calculateAmount = 0.0;
    TextView txtAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_split_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        laySplitViewDetails = view.findViewById(R.id.laySplitViewDetails);
        laySplitAmountInfoDropDown = view.findViewById(R.id.laySplitAmountInfoDropDown);
        laySplitInputCard = view.findViewById(R.id.laySplitInputCard);
        laySplitPayOptions = view.findViewById(R.id.laySplitPayOptions);
        laySplitPaymentProcessing = view.findViewById(R.id.laySplitPaymentProcessing);
        butSplitPay = view.findViewById(R.id.butSplitPay);
        butSplitChange = view.findViewById(R.id.butSplitChange);
        imaArrow1 = view.findViewById(R.id.imaArrow1);
        imaArrow2 = view.findViewById(R.id.imaArrow2);
        imgPayCard1 = view.findViewById(R.id.imgPayCard1);
        txtAmount = view.findViewById(R.id.txtAmount);
        laySplitAmountInfoDropDown.setVisibility(View.GONE);


        laySplitPayOptions.setVisibility(View.VISIBLE);
        laySplitPaymentProcessing.setVisibility(View.GONE);
        try {
            Bundle bundle = this.getArguments();
            calculateAmount = bundle.getDouble("calculateAmount", 0.0);
            txtAmount.setText("" + calculateAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }

        imgPayCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                laySplitPayOptions.setVisibility(View.GONE);
                laySplitPaymentProcessing.setVisibility(View.VISIBLE);
            }
        });

        laySplitViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLayAmountInfoDropDownVisibility) {
                    laySplitAmountInfoDropDown.setVisibility(View.VISIBLE);
                    isLayAmountInfoDropDownVisibility = true;
                    imaArrow1.setRotation(90);
                    imaArrow2.setRotation(90);
                } else {
                    laySplitAmountInfoDropDown.setVisibility(View.GONE);
                    isLayAmountInfoDropDownVisibility = false;
                    imaArrow1.setRotation(270);
                    imaArrow2.setRotation(270);
                }
            }
        });
        butSplitChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLayAmountInfoDropDownVisibility) {
                    laySplitAmountInfoDropDown.setVisibility(View.GONE);
                    isLayAmountInfoDropDownVisibility = false;
                    imaArrow1.setRotation(270);
                    imaArrow2.setRotation(270);
                }
            }
        });
        butSplitPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLayAmountInfoDropDownVisibility) {
                    laySplitAmountInfoDropDown.setVisibility(View.GONE);
                    isLayAmountInfoDropDownVisibility = false;
                    imaArrow1.setRotation(270);
                    imaArrow2.setRotation(270);
                }
            }
        });
    }
}