
package com.io.my_book_store_track.model.dataModel;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class LoginDataModel {

    @SerializedName("avatarHeight")
    private Long mAvatarHeight;
    @SerializedName("avatarName")
    private String mAvatarName;
    @SerializedName("avatarPath")
    private String mAvatarPath;
    @SerializedName("avatarWidth")
    private Long mAvatarWidth;
    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("deliveryGuyId")
    private Long mDeliveryGuyId;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private Object mId;
    @SerializedName("key")
    private String mKey;
    @SerializedName("modifiedDate")
    private Object mModifiedDate;
    @SerializedName("name")
    private String mName;


    public Long getAvatarHeight() {
        return mAvatarHeight;
    }

    public void setAvatarHeight(Long avatarHeight) {
        mAvatarHeight = avatarHeight;
    }

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

    public Long getAvatarWidth() {
        return mAvatarWidth;
    }

    public void setAvatarWidth(Long avatarWidth) {
        mAvatarWidth = avatarWidth;
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

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Object getId() {
        return mId;
    }

    public void setId(Object id) {
        mId = id;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public Object getModifiedDate() {
        return mModifiedDate;
    }

    public void setModifiedDate(Object modifiedDate) {
        mModifiedDate = modifiedDate;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }


}
