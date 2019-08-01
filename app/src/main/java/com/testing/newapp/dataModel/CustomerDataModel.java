package com.testing.newapp.dataModel;



public class CustomerDataModel {
    private long CustomerId;
    private String FullName;
    private long TruckId;
    private String TruckName;
    private long dailysheetid;
    private String FromState;
    private String ToState;
    private Object Bol1Url;
    private long isPaymentexception;

    public long getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(long customerId) {
        CustomerId = customerId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public long getTruckId() {
        return TruckId;
    }

    public void setTruckId(long truckId) {
        TruckId = truckId;
    }

    public String getTruckName() {
        return TruckName;
    }

    public void setTruckName(String truckName) {
        TruckName = truckName;
    }

    public long getDailysheetid() {
        return dailysheetid;
    }

    public void setDailysheetid(long dailysheetid) {
        this.dailysheetid = dailysheetid;
    }

    public String getFromState() {
        return FromState;
    }

    public void setFromState(String fromState) {
        FromState = fromState;
    }

    public String getToState() {
        return ToState;
    }

    public void setToState(String toState) {
        ToState = toState;
    }

    public Object getBol1Url() {
        return Bol1Url;
    }

    public void setBol1Url(Object bol1Url) {
        Bol1Url = bol1Url;
    }

    public long getIsPaymentexception() {
        return isPaymentexception;
    }

    public void setIsPaymentexception(long isPaymentexception) {
        this.isPaymentexception = isPaymentexception;
    }
}
