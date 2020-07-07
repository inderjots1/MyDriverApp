
package com.io.my_book_store_track.model.responseModel;

import com.io.my_book_store_track.model.dataModel.LoginDataModel;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class LoginAndOrderResponseModel {

    @SerializedName("data")
    private LoginDataModel mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Boolean mStatus;

    public LoginDataModel getData() {
        return mData;
    }

    public void setData(LoginDataModel data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
