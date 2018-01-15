package com.yidan.xiaoaimei.model.user;

import java.util.List;

/**
 * Created by jaydenma on 2017/7/26.
 */

public class CityInfo {

    /**
     * status_code : 0
     * message : success
     * data : [{"id":2,"pid":1,"area":"北京","son":[{"id":52,"pid":2,"area":"北京"}]},{"id":31,"pid":1,"area":"浙江","son":[{"id":383,"pid":31,"area":"杭州"},{"id":384,"pid":31,"area":"湖州"},{"id":385,"pid":31,"area":"嘉兴"},{"id":386,"pid":31,"area":"金华"},{"id":387,"pid":31,"area":"丽水"},{"id":388,"pid":31,"area":"宁波"},{"id":389,"pid":31,"area":"绍兴"},{"id":390,"pid":31,"area":"台州"},{"id":391,"pid":31,"area":"温州"},{"id":392,"pid":31,"area":"舟山"},{"id":393,"pid":31,"area":"衢州"}]}]
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
         * id : 2
         * pid : 1
         * area : 北京
         * son : [{"id":52,"pid":2,"area":"北京"}]
         */

        private int id;
        private int pid;
        private String area;
        private List<SonBean> son;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public List<SonBean> getSon() {
            return son;
        }

        public void setSon(List<SonBean> son) {
            this.son = son;
        }

        public static class SonBean {
            /**
             * id : 52
             * pid : 2
             * area : 北京
             */

            private int id;
            private int pid;
            private String area;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }
        }
    }
}
