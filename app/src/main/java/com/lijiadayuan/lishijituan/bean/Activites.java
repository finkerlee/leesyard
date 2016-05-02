package com.lijiadayuan.lishijituan.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhaoyi on 16/5/2.
 */
public class Activites implements Parcelable{

    private int actId;                      // 主键,自动增长
    private String actName;                 // 活动名称
    private String actLocation;             // 活动地点
    private String actScale;                // 活动规模
    private long actDate;                   // 活动日期
    private String actIntro;                // 活动说明
    private int actShow;                    // 是否显示,0:不显示,1:显示 默认1
    private int actPosition;                // 排列顺序
    private int actStatus;                  // 判断当前用户是否已经报名的标识,申请状态，0:未报名, 1:未审核，2:审核通过，-1:审核失败，3:报名成功。默认1
    private int pic;

    public Activites() {

    }

    protected Activites(Parcel in) {
        actId = in.readInt();
        actName = in.readString();
        actLocation = in.readString();
        actScale = in.readString();
        actDate = in.readLong();
        actIntro = in.readString();
        actShow = in.readInt();
        actPosition = in.readInt();
        actStatus = in.readInt();
        pic = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Activites> CREATOR = new Creator<Activites>() {
        @Override
        public Activites createFromParcel(Parcel in) {
            return new Activites(in);
        }

        @Override
        public Activites[] newArray(int size) {
            return new Activites[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(actId);
        dest.writeString(actName);
        dest.writeString(actLocation);
        dest.writeString(actScale);
        dest.writeLong(actDate);
        dest.writeString(actIntro);
        dest.writeInt(actShow);
        dest.writeInt(actPosition);
        dest.writeInt(actStatus);
        dest.writeInt(pic);
    }

    public int getActId() {
        return actId;
    }

    public void setActId(int actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActLocation() {
        return actLocation;
    }

    public void setActLocation(String actLocation) {
        this.actLocation = actLocation;
    }

    public String getActScale() {
        return actScale;
    }

    public void setActScale(String actScale) {
        this.actScale = actScale;
    }

    public long getActDate() {
        return actDate;
    }

    public void setActDate(long actDate) {
        this.actDate = actDate;
    }

    public String getActIntro() {
        return actIntro;
    }

    public void setActIntro(String actIntro) {
        this.actIntro = actIntro;
    }

    public int getActShow() {
        return actShow;
    }

    public void setActShow(int actShow) {
        this.actShow = actShow;
    }

    public int getActPosition() {
        return actPosition;
    }

    public void setActPosition(int actPosition) {
        this.actPosition = actPosition;
    }

    public int getActStatus() {
        return actStatus;
    }

    public void setActStatus(int actStatus) {
        this.actStatus = actStatus;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
