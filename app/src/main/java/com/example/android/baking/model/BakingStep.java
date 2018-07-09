package com.example.android.baking.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BakingStep implements Parcelable{

    private int id;
    private String short_description;
    private String description;
    private String video_url;
    private String thumbnail_url;

    public BakingStep() {
        super();
    }

    protected BakingStep(Parcel in) {
        id = in.readInt();
        short_description = in.readString();
        description = in.readString();
        video_url = in.readString();
        thumbnail_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(short_description);
        dest.writeString(description);
        dest.writeString(video_url);
        dest.writeString(thumbnail_url);
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
        return short_description;
    }

    public void setShortDescription(String aShortDescription) {
        short_description = aShortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String aDescription) {
        description = aDescription;
    }

    public String getVideoUrl() {
        return video_url;
    }

    public void setVideoUrl(String aVideoUrl) {
        video_url = aVideoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnail_url;
    }

    public void setThumbnailUrl(String aThumbnailUrl) {
        thumbnail_url = aThumbnailUrl;
    }
}
