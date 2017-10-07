package org.inframincer.slap.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yoon on 2017. 10. 7..
 */

public class Status {

    /*
    {
        "MosquitoStatus": {
            "list_total_count": 1,
            "RESULT": {
                "CODE": "INFO-000",
                "MESSAGE": "정상 처리되었습니다"
            },
            "row": [
                {
                    "MOSQUITO_DATE": "2017-10-04",
                    "MOSQUITO_VALUE": 221.9
                }
            ]
        }
    }
    */


    @SerializedName("MOSQUITO_DATE")
    private String mDate;
    @SerializedName("MOSQUITO_VALUE")
    private Double mValue;

    public Status(String date, Double value) {
        mDate = date;
        mValue = value;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public Double getValue() {
        return mValue;
    }

    public void setValue(Double value) {
        mValue = value;
    }

    @Override
    public String toString() {
        return "Status{" +
                "mDate='" + mDate + '\'' +
                ", mValue=" + mValue +
                '}';
    }
}


