package com.yidan.xiaoaimei.model.find;

/**
 * Created by jaydenma on 2017/12/27.
 */

public class MomentPublishInfo {

    private String content;
    private String city;
    private String voice;
    private String voiceTime;
    private String video;
    private String firstImg;
    private String videoTime;
    private String imgs;


    public MomentPublishInfo(String content, String city, String voice, String voiceTime, String video, String firstImg, String videoTime, String imgs) {
        this.content = content;
        this.city = city;
        this.voice = voice;
        this.voiceTime = voiceTime;
        this.video = video;
        this.firstImg = firstImg;
        this.videoTime = videoTime;
        this.imgs = imgs;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(String voiceTime) {
        this.voiceTime = voiceTime;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
}
