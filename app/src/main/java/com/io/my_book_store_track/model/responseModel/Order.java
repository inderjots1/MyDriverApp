
package com.io.my_book_store_track.model.responseModel;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Order {

    @SerializedName("adminId")
    private Long mAdminId;
    @SerializedName("adminPrice")
    private Long mAdminPrice;
    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("deliveryGuyId")
    private Long mDeliveryGuyId;
    @SerializedName("deliveryId")
    private Long mDeliveryId;
    @SerializedName("itemPrice")
    private Long mItemPrice;
    @SerializedName("orderId")
    private Long mOrderId;
    @SerializedName("orderStatus")
    private String mOrderStatus;
    @SerializedName("paid")
    private Boolean mPaid;
    @SerializedName("referenceId")
    private String mReferenceId;
    @SerializedName("storeId")
    private Long mStoreId;
    @SerializedName("storePrice")
    private Long mStorePrice;
    @SerializedName("totalPrice")
    private Long mTotalPrice;
    @SerializedName("userId")
    private Long mUserId;

    public Long getAdminId() {
        return mAdminId;
    }

    public void setAdminId(Long adminId) {
        mAdminId = adminId;
    }

    public Long getAdminPrice() {
        return mAdminPrice;
    }

    public void setAdminPrice(Long adminPrice) {
        mAdminPrice = adminPrice;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public Long getDeliveryGuyId() {
        return mDeliveryGuyId;
    }

    public void setDeliveryGuyId(Long deliveryGuyId) {
        mDeliveryGuyId = deliveryGuyId;
    }

    public Long getDeliveryId() {
        return mDeliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        mDeliveryId = deliveryId;
    }

    public Long getItemPrice() {
        return mItemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        mItemPrice = itemPrice;
    }

    public Long getOrderId() {
        return mOrderId;
    }

    public void setOrderId(Long orderId) {
        mOrderId = orderId;
    }

    public String getOrderStatus() {
        return mOrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        mOrderStatus = orderStatus;
    }

    public Boolean getPaid() {
        return mPaid;
    }

    public void setPaid(Boolean paid) {
        mPaid = paid;
    }

    public String getReferenceId() {
        return mReferenceId;
    }

    public void setReferenceId(String referenceId) {
        mReferenceId = referenceId;
    }

    public Long getStoreId() {
        return mStoreId;
    }

    public void setStoreId(Long storeId) {
        mStoreId = storeId;
    }

    public Long getStorePrice() {
        return mStorePrice;
    }

    public void setStorePrice(Long storePrice) {
        mStorePrice = storePrice;
    }

    public Long getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        mTotalPrice = totalPrice;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

}
