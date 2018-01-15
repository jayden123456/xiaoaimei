package com.yidan.xiaoaimei.model.user;

/**
 * Created by jaydenma on 2017/10/30.
 */

public class VerifyCodeInfo {

    /**
     * status_code : 0
     * message : success
     * data : {"mobile":"15067145440","verify_code":221870}
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
         * mobile : 15067145440
         * verify_code : 221870
         */

        private String mobile;
        private int verify_code;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getVerify_code() {
            return verify_code;
        }

        public void setVerify_code(int verify_code) {
            this.verify_code = verify_code;
        }
    }
}
