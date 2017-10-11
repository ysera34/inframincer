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
            R.color.colorAmber100, R.color.colorAmber200, R.color.colorAmber300,
            R.color.colorAmber400, R.color.colorAmber500, R.color.colorAmber600,
            R.color.colorAmber700, R.color.colorAmber800, R.color.colorAmber900,};

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
