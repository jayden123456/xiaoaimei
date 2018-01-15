package com.yidan.xiaoaimei.model.mine;

import java.util.List;

/**
 * Created by jaydenma on 2017/11/3.
 */

public class WechatPayInfo {

    /**
     * status_code : 0
     * message : success
     * data : {"options":[{"optionId":204,"wechatPay":"9900","chance":"80%"},{"optionId":205,"wechatPay":"12800","chance":"60%"},{"optionId":206,"wechatPay":"16800","chance":"40%"},{"optionId":207,"wechatPay":"19800","chance":"30%"},{"optionId":208,"wechatPay":"26800","chance":"15%"}],"wechatNum":"","wechatPay":0}
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
         * options : [{"optionId":204,"wechatPay":"9900","chance":"80%"},{"optionId":205,"wechatPay":"12800","chance":"60%"},{"optionId":206,"wechatPay":"16800","chance":"40%"},{"optionId":207,"wechatPay":"19800","chance":"30%"},{"optionId":208,"wechatPay":"26800","chance":"15%"}]
         * wechatNum :
         * wechatPay : 0
         */

        private String wechatNum;
        private int wechatPay;
        private List<OptionsBean> options;

        public String getWechatNum() {
            return wechatNum;
        }

        public void setWechatNum(String wechatNum) {
            this.wechatNum = wechatNum;
        }

        public int getWechatPay() {
            return wechatPay;
        }

        public void setWechatPay(int wechatPay) {
            this.wechatPay = wechatPay;
        }

        public List<OptionsBean> getOptions() {
            return options;
        }

        public void setOptions(List<OptionsBean> options) {
            this.options = options;
        }

        public static class OptionsBean {
            /**
             * optionId : 204
             * wechatPay : 9900
             * chance : 80%
             */

            private int optionId;
            private String wechatPay;
            private String chance;

            public int getOptionId() {
                return optionId;
            }

            public void setOptionId(int optionId) {
                this.optionId = optionId;
            }

            public String getWechatPay() {
                return wechatPay;
            }

            public void setWechatPay(String wechatPay) {
                this.wechatPay = wechatPay;
            }

            public String getChance() {
                return chance;
            }

            public void setChance(String chance) {
                this.chance = chance;
            }
        }
    }
}
