package com.yidan.xiaoaimei.model.mine;

import java.util.List;

/**
 * Created by jaydenma on 2017/7/21.
 */

public class MemberOptionsInfo {


    /**
     * status_code : 0
     * message : success
     * data : {"lists":[{"optionId":81,"payMoney":18,"productId":"Vip1","moneyPerDay":"1.2","duration":1},{"optionId":82,"payMoney":50,"productId":"Vip2","moneyPerDay":"1","duration":3},{"optionId":83,"payMoney":98,"productId":"Vip3","moneyPerDay":"0.9","duration":6},{"optionId":147,"payMoney":188,"productId":"Vip4","moneyPerDay":"0.8","duration":12}],"expiredAt":"","isMember":0,"showApplePay":1}
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
         * lists : [{"optionId":81,"payMoney":18,"productId":"Vip1","moneyPerDay":"1.2","duration":1},{"optionId":82,"payMoney":50,"productId":"Vip2","moneyPerDay":"1","duration":3},{"optionId":83,"payMoney":98,"productId":"Vip3","moneyPerDay":"0.9","duration":6},{"optionId":147,"payMoney":188,"productId":"Vip4","moneyPerDay":"0.8","duration":12}]
         * expiredAt :
         * isMember : 0
         * showApplePay : 1
         */

        private String expiredAt;
        private int isMember;
        private int showApplePay;
        private List<ListsBean> lists;

        public String getExpiredAt() {
            return expiredAt;
        }

        public void setExpiredAt(String expiredAt) {
            this.expiredAt = expiredAt;
        }

        public int getIsMember() {
            return isMember;
        }

        public void setIsMember(int isMember) {
            this.isMember = isMember;
        }

        public int getShowApplePay() {
            return showApplePay;
        }

        public void setShowApplePay(int showApplePay) {
            this.showApplePay = showApplePay;
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class ListsBean {
            /**
             * optionId : 81
             * payMoney : 18
             * productId : Vip1
             * moneyPerDay : 1.2
             * duration : 1
             */

            private int optionId;
            private int payMoney;
            private String productId;
            private String moneyPerDay;
            private int duration;

            public int getOptionId() {
                return optionId;
            }

            public void setOptionId(int optionId) {
                this.optionId = optionId;
            }

            public int getPayMoney() {
                return payMoney;
            }

            public void setPayMoney(int payMoney) {
                this.payMoney = payMoney;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getMoneyPerDay() {
                return moneyPerDay;
            }

            public void setMoneyPerDay(String moneyPerDay) {
                this.moneyPerDay = moneyPerDay;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }
        }
    }
}
