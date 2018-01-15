package com.yidan.xiaoaimei.model.mine;

import java.util.List;

/**
 * Created by jaydenma on 2017/7/26.
 */

public class UpdatePersonInfo {

    /**
     * status_code : 0
     * message : success
     * data : {"name":"小乔","sex":"-1","signature":"小桥流水人家","height":"177","headimg":"http://oss-cn-hangzhou.aliyuncs.com/d.lana.org/d","measurements":[13,234,425],"city":"杭州"}
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
         * name : 小乔
         * sex : -1
         * signature : 小桥流水人家
         * height : 177
         * headimg : http://oss-cn-hangzhou.aliyuncs.com/d.lana.org/d
         * measurements : [13,234,425]
         * city : 杭州
         */

        private String name;
        private String sex;
        private String age;
        private String signature;
        private String height;
        private String headimg;
        private String city;
        private List<Integer> measurements;
        private String size;
//        private String cover;

        public DataBean(String name, String sex, String age, String signature, String height, String headimg, String city, String size) {
            this.name = name;
            this.sex = sex;
            this.age = age;
            this.signature = signature;
            this.height = height;
            this.headimg = headimg;
            this.city = city;
            this.size = size;
//            this.cover = cover;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<Integer> getMeasurements() {
            return measurements;
        }

        public void setMeasurements(List<Integer> measurements) {
            this.measurements = measurements;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

    }
}
