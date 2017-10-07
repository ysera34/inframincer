package org.inframincer.slap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yoon on 2017. 10. 7..
 */

public class StatusResponse {

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

    @SerializedName("list_total_count")
    private Integer mTotalCount;
    @SerializedName("RESULT")
    private StatusResult mStatusResult;
    @SerializedName("row")
    private List<Status> mStatuses;

    private class StatusResult {

        @SerializedName("CODE")
        private String mResultCode;
        @SerializedName("MESSAGE")
        private String mResultMessage;

        public StatusResult(String resultCode, String resultMessage) {
            mResultCode = resultCode;
            mResultMessage = resultMessage;
        }

        public String getResultCode() {
            return mResultCode;
        }

        public void setResultCode(String resultCode) {
            mResultCode = resultCode;
        }

        public String getResultMessage() {
            return mResultMessage;
        }

        public void setResultMessage(String resultMessage) {
            mResultMessage = resultMessage;
        }

        @Override
        public String toString() {
            return "StatusResult{" +
                    "mResultCode='" + mResultCode + '\'' +
                    ", mResultMessage='" + mResultMessage + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StatusResponse{" +
                "mTotalCount=" + mTotalCount +
                ", mStatusResult=" + mStatusResult +
                ", mStatuses=" + mStatuses +
                '}';
    }
}
