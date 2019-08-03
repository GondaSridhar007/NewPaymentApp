package com.testing.newapp.fragmentUI.cashPayment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.testing.newapp.MainActivity;
import com.testing.newapp.R;

public class CashPaymentApprovalFragment extends Fragment {
    Button butCancelRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cash_payment_approval, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        butCancelRequest = view.findViewById(R.id.butCancelRequest);
        butCancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().loadFragmentUI("PaymentInsert", null);
            }
        });
    }
}
