package com.yidan.xiaoaimei.model.mine;

import java.util.List;

/**
 * Created by jaydenma on 2017/7/21.
 */

public class PayOptionsInfo {

    /**
     * status_code : 0
     * message : success
     * data : [{"id":209,"payCoin":"1200","payMoney":12},{"id":210,"payCoin":"6800","payMoney":68},{"id":211,"payCoin":"9800","payMoney":98},{"id":212,"payCoin":"19800","payMoney":198},{"id":213,"payCoin":"51800","payMoney":518},{"id":214,"payCoin":"199800","payMoney":1998}]
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
         * id : 209
         * payCoin : 1200
         * payMoney : 12
         */

        private int id;
        private String payCoin;
        private int payMoney;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPayCoin() {
            return payCoin;
        }

        public void setPayCoin(String payCoin) {
            this.payCoin = payCoin;
        }

        public int getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(int payMoney) {
            this.payMoney = payMoney;
        }
    }
}
