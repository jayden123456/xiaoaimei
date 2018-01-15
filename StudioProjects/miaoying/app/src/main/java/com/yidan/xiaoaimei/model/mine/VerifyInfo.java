package com.yidan.xiaoaimei.model.mine;

/**
 * Created by jaydenma on 2017/12/6.
 */

public class VerifyInfo {
    /**
     * status_code : 0
     * message : success
     * data : {"voiceUrl":"http://image.miaoying.tv/voice/1512543503782.amr","voiceTime":27}
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
         * voiceUrl : http://image.miaoying.tv/voice/1512543503782.amr
         * voiceTime : 27
         */

        private String voiceUrl;
        private int voiceTime;

        private String videoUrl;
        private int videoTime;
        private String firstImg;

        public String getVoiceUrl() {
            return voiceUrl;
        }

        public void setVoiceUrl(String voiceUrl) {
            this.voiceUrl = voiceUrl;
        }

        public int getVoiceTime() {
            return voiceTime;
        }

        public void setVoiceTime(int voiceTime) {
            this.voiceTime = voiceTime;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public int getVideoTime() {
            return videoTime;
        }

        public void setVideoTime(int videoTime) {
            this.videoTime = videoTime;
        }

        public String getFirstImg() {
            return firstImg;
        }

        public void setFirstImg(String firstImg) {
            this.firstImg = firstImg;
        }
    }


//    private int status_code;
//    private String message;
//    private DataBean dataBean;
//
//    /**
//     * data : {"voiceUrl":"http://image.miaoying.tv/voice/1512543503782.amr","voiceTime":27}
//     */
//
//
//    public int getStatus_code() {
//        return status_code;
//    }
//
//    public void setStatus_code(int status_code) {
//        this.status_code = status_code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public DataBean getDataBean() {
//        return dataBean;
//    }
//
//    public void setDataBean(DataBean dataBean) {
//        this.dataBean = dataBean;
//    }
//
//
//    public static class DataBean {
//        /**
//         * voiceUrl : http://image.miaoying.tv/voice/1512543503782.amr
//         * voiceTime : 27
//         */
//
//        private String voiceUrl;
//        private int voiceTime;
//
//        private String videoUrl;
//        private int videoTime;
//        private String firstImg;
//
//        public String getVoiceUrl() {
//            return voiceUrl;
//        }
//
//        public void setVoiceUrl(String voiceUrl) {
//            this.voiceUrl = voiceUrl;
//        }
//
//        public int getVoiceTime() {
//            return voiceTime;
//        }
//
//        public void setVoiceTime(int voiceTime) {
//            this.voiceTime = voiceTime;
//        }
//
//        public String getVideoUrl() {
//            return videoUrl;
//        }
//
//        public void setVideoUrl(String videoUrl) {
//            this.videoUrl = videoUrl;
//        }
//
//        public int getVideoTime() {
//            return videoTime;
//        }
//
//        public void setVideoTime(int videoTime) {
//            this.videoTime = videoTime;
//        }
//
//        public String getFirstImg() {
//            return firstImg;
//        }
//
//        public void setFirstImg(String firstImg) {
//            this.firstImg = firstImg;
//        }
//    }
}
