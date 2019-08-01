package com.testing.newapp.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("GetCustomerForPaymentNew")
    Call<ResponseBody> getCustomerForPaymentNew(@Query("date") String date, @Query("EmployeeId") int EmployeeId);
}
