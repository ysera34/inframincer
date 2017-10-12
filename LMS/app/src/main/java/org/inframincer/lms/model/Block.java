package org.inframincer.lms.model;

import org.inframincer.lms.R;

/**
 * Created by yoon on 2017. 10. 11..
 */

public class Block {

//    private int mNumber;
    private int mHintNumber;
    private boolean mIsMine;
    private int[] mHintColorArray = {
            R.color.colorPink100, R.color.colorPink200, R.color.colorPink300,
            R.color.colorPink400, R.color.colorPink500, R.color.colorPink600,
            R.color.colorPink700, R.color.colorPink800, R.color.colorPink900,};

//    public int getNumber() {
//        return mNumber;
//    }
//
//    public void setNumber(int number) {
//        mNumber = number;
//    }

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
        return mHintColorArray[getHintNumber()];
    }

    public boolean isMine() {
        return mIsMine;
    }

    public void setMine(boolean mine) {
        mIsMine = mine;
    }
}
