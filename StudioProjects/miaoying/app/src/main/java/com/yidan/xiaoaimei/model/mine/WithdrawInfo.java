package com.yidan.xiaoaimei.model.mine;

/**
 * Created by jaydenma on 2017/8/3.
 */

public class WithdrawInfo {

    /**
     * status_code : 0
     * message : success
     * data : {"canCash":0,"aliPayAccount":"","serviceWechat":"sdaasdasd"}
     */

    private int status_code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * canCash : 0
         * aliPayAccount :
         * serviceWechat : sdaasdasd
         */

        private int canCash;
        private String aliPayAccount;
        private String serviceWechat;

        public int getCanCash() {
            return canCash;
        }

        public void setCanCash(int canCash) {
            this.canCash = canCash;
        }

        public String getAliPayAccount() {
            return aliPayAccount;
        }

        public void setAliPayAccount(String aliPayAccount) {
            this.aliPayAccount = aliPayAccount;
        }

        public String getServiceWechat() {
            return serviceWechat;
        }

        public void setServiceWechat(String serviceWechat) {
            this.serviceWechat = serviceWechat;
        }
    }
}
