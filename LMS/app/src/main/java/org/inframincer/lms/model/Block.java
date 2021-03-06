package org.inframincer.lms.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.inframincer.lms.R;

/**
 * Created by yoon on 2017. 10. 11..
 */

public class Block implements Parcelable {

    private int mHintNumber; // 주위 8방향의 지뢰의 개수
    private boolean mIsMine; // 블럭이 지뢰인지의 여부
    private boolean mIsVerified; // 사용자가 블럭을 열어보았는지 여부
    private boolean mIsDetected; // 사용자가 블럭을 지뢰로 표시하였는지 여부
    private int[] mHintColorArray = {
            R.color.colorPink100, R.color.colorPink200, R.color.colorPink300,
            R.color.colorPink400, R.color.colorPink500, R.color.colorPink600,
            R.color.colorPink700, R.color.colorPink800, R.color.colorPink900,};

    public Block() {
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mHintNumber);
        parcel.writeByte((byte) (mIsMine ? 1 : 0));
        parcel.writeByte((byte) (mIsVerified ? 1 : 0));
        parcel.writeByte((byte) (mIsDetected ? 1 : 0));
    }

    public static final Parcelable.Creator<Block> CREATOR
            = new Parcelable.Creator<Block>() {
        public Block createFromParcel(Parcel in) {
            return new Block(in);
        }

        public Block[] newArray(int size) {
            return new Block[size];
        }
    };

    private Block(Parcel in) {
        mHintNumber = in.readInt();
        mIsMine = in.readByte() != 0;
        mIsVerified = in.readByte() != 0;
        mIsDetected = in.readByte() != 0;
    }

    public int getHintNumber() {
        return mHintNumber;
    }

    public void addHintNumber() {
        mHintNumber++;
    }

    public int getHintColor() {
        return mHintColorArray[getHintNumber()];
    }

    public boolean isMine() {
        return mIsMine;
    }

    public void setMine(boolean mine) {
        mIsMine = mine;
    }

    public boolean isVerified() {
        return mIsVerified;
    }

    public void setVerified(boolean verified) {
        mIsVerified = verified;
    }

    public boolean isDetected() {
        return mIsDetected;
    }

    public void setDetected(boolean detected) {
        mIsDetected = detected;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
