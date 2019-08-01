package com.testing.newapp.fragmentUI;

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
import android.widget.TextView;

import com.testing.newapp.MainActivity;
import com.testing.newapp.R;

public class SplitAmountPaymentFragment extends Fragment {
    EditText etTextAmount;
    Button butPaySlitPay;
    double totalAmount = 11770.00, calculateAmount = 0.0;
    TextView txtTotalAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_split_amount_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etTextAmount = view.findViewById(R.id.etTextAmount);
        butPaySlitPay = view.findViewById(R.id.butPaySlitPay);
        txtTotalAmount = view.findViewById(R.id.txtTotalAmount);

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
                String amount = etTextAmount.getText().toString();
                if (amount.length() != 0) {
                    Bundle bundle = new Bundle();
                    bundle.putDouble("calculateAmount", calculateAmount);
                    MainActivity.getInstance().loadFragmentUI("SplitPayment",bundle);
                }
            }
        });
    }
}
