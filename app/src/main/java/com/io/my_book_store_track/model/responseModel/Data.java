
package com.io.my_book_store_track.model.responseModel;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Data {

    @SerializedName("avatarName")
    private String mAvatarName;
    @SerializedName("avatarPath")
    private String mAvatarPath;
    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("deliveryStatusId")
    private Long mDeliveryStatusId;
    @SerializedName("order")
    private Order mOrder;
    @SerializedName("orderId")
    private Long mOrderId;
    @SerializedName("status")
    private String mStatus;

    public String getAvatarName() {
        return mAvatarName;
    }

    public void setAvatarName(String avatarName) {
        mAvatarName = avatarName;
    }

    public String getAvatarPath() {
        return mAvatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        mAvatarPath = avatarPath;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public Long getDeliveryStatusId() {
        return mDeliveryStatusId;
    }

    public void setDeliveryStatusId(Long deliveryStatusId) {
        mDeliveryStatusId = deliveryStatusId;
    }

    public Order getOrder() {
        return mOrder;
    }

    public void setOrder(Order order) {
        mOrder = order;
    }

    public Long getOrderId() {
        return mOrderId;
    }

    public void setOrderId(Long orderId) {
        mOrderId = orderId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
