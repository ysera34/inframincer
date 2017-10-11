package org.inframincer.lms.model;

/**
 * Created by yoon on 2017. 10. 11..
 */

public class Block {

    private int mNumber;
    private int mHintNumber;
    private int mHintColor;
    private boolean mIsMine;

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public int getHintNumber() {
        return mHintNumber;
    }

    public void setHintNumber(int hintNumber) {
        mHintNumber = hintNumber;
    }

    public void addHintNumber() {
        mHintNumber++;
    }

    public int getHintColor() {
        return mHintColor;
    }

    public void setHintColor(int hintColor) {
        mHintColor = hintColor;
    }

    public boolean isMine() {
        return mIsMine;
    }

    public void setMine(boolean mine) {
        mIsMine = mine;
    }
}
