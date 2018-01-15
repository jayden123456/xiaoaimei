package com.yidan.xiaoaimei.model.mine;

import java.util.List;

/**
 * Created by jaydenma on 2017/8/3.
 */

public class BalanceDetailInfo {

    /**
     * status_code : 0
     * message : success
     * data : [{"payAmount":"+5800","payType":"充值","createdAt":"2017-08-01"},{"payAmount":"+800","payType":"充值","createdAt":"2017-08-01"},{"payAmount":"-1","payType":"消费(购买会员)","createdAt":"2017-08-01"},{"payAmount":"+800","payType":"充值","createdAt":"2017-08-01"},{"payAmount":"+800","payType":"充值","createdAt":"2017-08-01"},{"payAmount":"+800","payType":"充值","createdAt":"2017-08-01"},{"payAmount":"+800","payType":"充值","createdAt":"2017-08-01"},{"payAmount":"-200","payType":"消费(购买手机号)","createdAt":"2017-07-31"},{"payAmount":"-1000","payType":"消费(购买微信)","createdAt":"2017-07-31"},{"payAmount":"-100","payType":"消费(购买会员)","createdAt":"2017-07-12"}]
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
         * payAmount : +5800
         * payType : 充值
         * createdAt : 2017-08-01
         */

        private String payAmount;
        private String payType;
        private String createdAt;

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }
}
