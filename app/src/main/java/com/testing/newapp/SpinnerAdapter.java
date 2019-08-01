package com.testing.newapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.testing.newapp.dataModel.CustomerDataModel;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<CustomerDataModel> dataList;

    public SpinnerAdapter(Context context, ArrayList<CustomerDataModel> list) {
        mContext = context;
        dataList = list;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.adapter_spinner, parent, false);
        TextView txtName = view.findViewById(R.id.txtName);
        String[] items = dataList.get(position).getFullName().split("-");
        txtName.setText(items[1]);
        TextView txtId = view.findViewById(R.id.txtId);
        if (dataList.get(position).getCustomerId() == 0) {
            txtId.setVisibility(View.GONE);
        } else {
            txtId.setVisibility(View.VISIBLE);
            txtId.setText("CUSTOMER ID: " + dataList.get(position).getCustomerId());
        }
        return view;
    }
}
