package org.inframincer.slap.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yoon on 2017. 10. 7..
 */

public class StatusObject {

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

    @SerializedName("MosquitoStatus")
    private StatusResponse mStatusResponse;

    public StatusResponse getStatusResponse() {
        return mStatusResponse;
    }

    public void setStatusResponse(StatusResponse statusResponse) {
        mStatusResponse = statusResponse;
    }

    @Override
    public String toString() {
        return "StatusObject{" +
                "mStatusResponse=" + mStatusResponse +
                '}';
    }
}
