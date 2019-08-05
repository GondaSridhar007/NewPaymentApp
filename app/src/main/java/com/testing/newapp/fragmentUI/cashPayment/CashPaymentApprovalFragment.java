package com.testing.newapp.fragmentUI.cashPayment;

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

public class CashPaymentApprovalFragment extends Fragment {
    LinearLayout layViewDetails, layAmountInfoDropDown;
    boolean isLayAmountInfoDropDownVisibility = false;
    ImageView imaArrow1, imaArrow2, imgCashPayment;
    Button butCancelRequest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cash_payment_approval, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.getInstance().setSpinnerHide(false);
        butCancelRequest = view.findViewById(R.id.butCancelRequest);
        imgCashPayment = view.findViewById(R.id.imgCashPayment);
        layViewDetails = view.findViewById(R.id.layViewDetails);
        View includeLayAmountInfo = view.findViewById(R.id.includeLayAmountInfo);
        layAmountInfoDropDown = includeLayAmountInfo.findViewById(R.id.layAmountInfoDropDown);
        imaArrow1 = view.findViewById(R.id.imaArrow1);
        imaArrow2 = view.findViewById(R.id.imaArrow2);
        layAmountInfoDropDown.setVisibility(View.GONE);

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
       butCancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().loadFragmentUI("PaymentInsert", null);
            }
        });
        imgCashPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().loadFragmentUI("CashPaymentSignature", null);
            }
        });
    }
}
