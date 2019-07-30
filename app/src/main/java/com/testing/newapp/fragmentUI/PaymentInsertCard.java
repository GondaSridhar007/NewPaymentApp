package com.testing.newapp.fragmentUI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.testing.newapp.R;

public class PaymentInsertCard extends Fragment {
    LinearLayout layViewDetails, layAmountInfoDropDown, layInputCard;
    boolean isLayAmountInfoDropDownVisibility = false;
    ImageView imaArrow1, imaArrow2, imgPayCard1, imgPayCard2;

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
        imgPayCard1 = view.findViewById(R.id.imgPayCard1);
        imgPayCard2 = view.findViewById(R.id.imgPayCard2);
        layInputCard = view.findViewById(R.id.layInputCard);
        layAmountInfoDropDown.setVisibility(View.GONE);
        imgPayCard1.setVisibility(View.VISIBLE);
        imgPayCard2.setVisibility(View.GONE);
        layViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLayAmountInfoDropDownVisibility) {
                    layAmountInfoDropDown.setVisibility(View.VISIBLE);
                    isLayAmountInfoDropDownVisibility = true;
                    imaArrow1.setRotation(90);
                    imaArrow2.setRotation(90);
                    imgPayCard1.setVisibility(View.GONE);
                    imgPayCard2.setVisibility(View.VISIBLE);
                } else {
                    layAmountInfoDropDown.setVisibility(View.GONE);
                    isLayAmountInfoDropDownVisibility = false;
                    imaArrow1.setRotation(270);
                    imaArrow2.setRotation(270);
                    imgPayCard1.setVisibility(View.VISIBLE);
                    imgPayCard2.setVisibility(View.GONE);
                }
            }
        });
    }
}