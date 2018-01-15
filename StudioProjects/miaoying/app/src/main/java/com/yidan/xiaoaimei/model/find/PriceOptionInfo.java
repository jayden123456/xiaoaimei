package com.yidan.xiaoaimei.model.find;

import java.util.List;

/**
 * Created by jaydenma on 2017/12/27.
 */

public class PriceOptionInfo {

    /**
     * status_code : 0
     * message : success
     * data : [{"optionId":219,"momentPrice":"0"},{"optionId":220,"momentPrice":"100"},{"optionId":221,"momentPrice":"200"},{"optionId":222,"momentPrice":"300"},{"optionId":223,"momentPrice":"400"},{"optionId":224,"momentPrice":"500"}]
     */

    private int status_code;
    private String message;
    private List<DataBean> data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * optionId : 219
         * momentPrice : 0
         */

        private int optionId;
        private String momentPrice;
        private int status;

        public int getOptionId() {
            return optionId;
        }

        public void setOptionId(int optionId) {
            this.optionId = optionId;
        }

        public String getMomentPrice() {
            return momentPrice;
        }

        public void setMomentPrice(String momentPrice) {
            this.momentPrice = momentPrice;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
