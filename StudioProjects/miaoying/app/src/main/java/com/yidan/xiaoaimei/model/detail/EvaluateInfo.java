package com.yidan.xiaoaimei.model.detail;

import java.util.List;

/**
 * Created by jaydenma on 2017/12/12.
 */

public class EvaluateInfo {

    /**
     * status_code : 0
     * message : success
     * data : [{"evaTagName":"声音甜美","evaId":1},{"evaTagName":"颜值高","evaId":2},{"evaTagName":"气质好","evaId":3},{"evaTagName":"舞姿热辣","evaId":4},{"evaTagName":"性感","evaId":5},{"evaTagName":"卡哇伊","evaId":6},{"evaTagName":"魔鬼身材","evaId":7},{"evaTagName":"故事多","evaId":8},{"evaTagName":"随意撩","evaId":9},{"evaTagName":"皮肤白","evaId":10},{"evaTagName":"纤手玉足","evaId":11},{"evaTagName":"角色扮演","evaId":12},{"evaTagName":"暖心","evaId":13},{"evaTagName":"小鲜肉","evaId":17},{"evaTagName":"有型","evaId":19},{"evaTagName":"逗比","evaId":20},{"evaTagName":"暖男","evaId":21},{"evaTagName":"绅士","evaId":22},{"evaTagName":"肌肉","evaId":23},{"evaTagName":"神似明星","evaId":24},{"evaTagName":"幽默","evaId":25},{"evaTagName":"才艺","evaId":26},{"evaTagName":"嗓音迷人","evaId":27},{"evaTagName":"可爱","evaId":28}]
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
         * evaTagName : 声音甜美
         * evaId : 1
         */

        private String evaTagName;
        private int evaId;
        private int status;

        public String getEvaTagName() {
            return evaTagName;
        }

        public void setEvaTagName(String evaTagName) {
            this.evaTagName = evaTagName;
        }

        public int getEvaId() {
            return evaId;
        }

        public void setEvaId(int evaId) {
            this.evaId = evaId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
