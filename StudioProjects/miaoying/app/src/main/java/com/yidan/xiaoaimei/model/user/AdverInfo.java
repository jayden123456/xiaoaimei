package com.yidan.xiaoaimei.model.user;

/**
 * Created by jaydenma on 2017/8/25.
 */

public class AdverInfo {

    /**
     * status_code : 0
     * message : success
     * data : {"url":"http://api.miaokong.tv/miaokong/miaoGirlRecruit/pages/enroll.html","startpage":"http://img.miaokong.tv/image/598d87618dcf3.png"}
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
         * url : http://api.miaokong.tv/miaokong/miaoGirlRecruit/pages/enroll.html
         * startpage : http://img.miaokong.tv/image/598d87618dcf3.png
         */

        private String url;
        private String startpage;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getStartpage() {
            return startpage;
        }

        public void setStartpage(String startpage) {
            this.startpage = startpage;
        }
    }
}
