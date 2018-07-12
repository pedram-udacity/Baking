package com.example.android.baking.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BakingStep implements Parcelable{

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public BakingStep() {
        super();
    }

    protected BakingStep(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BakingStep> CREATOR = new Creator<BakingStep>() {
        @Override
        public BakingStep createFromParcel(Parcel in) {
            return new BakingStep(in);
        }

        @Override
        public BakingStep[] newArray(int size) {
            return new BakingStep[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int aId) {
        id = aId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String aShortDescription) {
        shortDescription = aShortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String aDescription) {
        description = aDescription;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String aVideoURL) {
        videoURL = aVideoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String aThumbnailURL) {
        thumbnailURL = aThumbnailURL;
    }
}
