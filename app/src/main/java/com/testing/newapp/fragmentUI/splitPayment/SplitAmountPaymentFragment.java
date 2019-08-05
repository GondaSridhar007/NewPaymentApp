package com.testing.newapp.fragmentUI.splitPayment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testing.newapp.MainActivity;
import com.testing.newapp.R;

public class SplitAmountPaymentFragment extends Fragment {
    EditText etTextAmount;
    Button butPaySlitPay, butCancel;
    double totalAmount = 11770.00, calculateAmount = 11770.00;
    TextView txtTotalAmount;
    LinearLayout layViewDetails, layAmountInfoDropDown;
    boolean isLayAmountInfoDropDownVisibility = false;
    ImageView imaArrow1, imaArrow2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_split_amount_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.getInstance().setSpinnerHide(false);
        etTextAmount = view.findViewById(R.id.etTextAmount);
        butPaySlitPay = view.findViewById(R.id.butPaySlitPay);
        butCancel = view.findViewById(R.id.butCancel);
        txtTotalAmount = view.findViewById(R.id.txtTotalAmount);
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
        etTextAmount.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void afterTextChanged(Editable charSequence) {
                try {
                    if (charSequence.length() == 0) {
                        butPaySlitPay.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_confirm));
                        butPaySlitPay.setTextColor(Color.parseColor("#1C222E"));
                        etTextAmount.setBackground(getActivity().getResources().getDrawable(R.drawable.edit_bg_amount));
                        txtTotalAmount.setText("" + totalAmount);
                    } else {
                        butPaySlitPay.setBackground(getActivity().getResources().getDrawable(R.drawable.button_bg));
                        butPaySlitPay.setTextColor(Color.parseColor("#FFFFFF"));
                        etTextAmount.setBackground(getActivity().getResources().getDrawable(R.drawable.edit_bg_confirm_amount));
                        double enterAmount = Double.parseDouble(etTextAmount.getText().toString());
                        calculateAmount = totalAmount - enterAmount;
                        txtTotalAmount.setText("" + calculateAmount);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        butPaySlitPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putDouble("calculateAmount", calculateAmount);
                MainActivity.getInstance().loadFragmentUI("SplitPayment", bundle);
            }
        });
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }
}
