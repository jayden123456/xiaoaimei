package com.yidan.xiaoaimei.model.mine;

import java.util.List;

/**
 * Created by jaydenma on 2017/8/3.
 */

public class IncomeDetailInfo {


    /**
     * status_code : 0
     * message : success
     * data : {"withdrawalsSum":2400,"lists":[{"payType":"收益(购买手机号)","payAmount":200,"createdAt":"2017-07-31"},{"payType":"收益(购买手机号)","payAmount":200,"createdAt":"2017-07-31"},{"payType":"收益(购买微信)","payAmount":1000,"createdAt":"2017-07-31"},{"payType":"收益(购买微信)","payAmount":1000,"createdAt":"2017-07-31"}]}
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
         * withdrawalsSum : 2400
         * lists : [{"payType":"收益(购买手机号)","payAmount":200,"createdAt":"2017-07-31"},{"payType":"收益(购买手机号)","payAmount":200,"createdAt":"2017-07-31"},{"payType":"收益(购买微信)","payAmount":1000,"createdAt":"2017-07-31"},{"payType":"收益(购买微信)","payAmount":1000,"createdAt":"2017-07-31"}]
         */

        private int withdrawalsSum;
        private List<ListsBean> lists;

        public int getWithdrawalsSum() {
            return withdrawalsSum;
        }

        public void setWithdrawalsSum(int withdrawalsSum) {
            this.withdrawalsSum = withdrawalsSum;
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class ListsBean {
            /**
             * payType : 收益(购买手机号)
             * payAmount : 200
             * createdAt : 2017-07-31
             */

            private String payType;
            private int payAmount;
            private String createdAt;

            public String getPayType() {
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            public int getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(int payAmount) {
                this.payAmount = payAmount;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }
        }
    }
}
