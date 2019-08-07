package com.testing.newapp.fragmentUI.splitPayment;

import android.graphics.Color;
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
import com.testing.newapp.signatureView.SignatureView;

public class SplitPaymentSignatureFragment extends Fragment {
    LinearLayout layViewDetails, layAmountInfoDropDown, layPaymentSignature;
    boolean isLayAmountInfoDropDownVisibility = false;
    ImageView imaArrow1, imaArrow2;
    Button butSignatureSubmit, butSignatureClearSignature;
    SignatureView mSignature;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_split_payment_signature, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.getInstance().setSpinnerHide(false);
        layViewDetails = view.findViewById(R.id.layViewDetails);
        View includeLayAmountInfo = view.findViewById(R.id.includeLayAmountInfo);
        layAmountInfoDropDown = includeLayAmountInfo.findViewById(R.id.layAmountInfoDropDown);
        layPaymentSignature = view.findViewById(R.id.layPaymentSignature);
        butSignatureSubmit = view.findViewById(R.id.butSignatureSubmit);
        butSignatureClearSignature = view.findViewById(R.id.butSignatureClearSignature);
        imaArrow1 = view.findViewById(R.id.imaArrow1);
        imaArrow2 = view.findViewById(R.id.imaArrow2);
        layPaymentSignature.setVisibility(View.VISIBLE);
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
        butSignatureSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLayAmountInfoDropDownVisibility) {
                    layAmountInfoDropDown.setVisibility(View.GONE);
                    isLayAmountInfoDropDownVisibility = false;
                    imaArrow1.setRotation(270);
                    imaArrow2.setRotation(270);
                }
                MainActivity.getInstance().loadFragmentUI("SplitPaymentSuccess", null);
            }
        });

        LinearLayout laySignature = view.findViewById(R.id.laySignature);
        mSignature = new SignatureView(getActivity(), null);
        mSignature.setBackgroundColor(Color.WHITE);
        laySignature.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        butSignatureClearSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignature.clear();
            }
        });
    }
}