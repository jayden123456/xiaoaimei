package com.yidan.xiaoaimei.http.base;


import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.CommonInfo;
import com.yidan.xiaoaimei.model.detail.AlbumShowInfo;
import com.yidan.xiaoaimei.model.detail.EvaluateInfo;
import com.yidan.xiaoaimei.model.detail.UserDetailInfo;
import com.yidan.xiaoaimei.model.find.MomentDetailInfo;
import com.yidan.xiaoaimei.model.find.MomentListInfo;
import com.yidan.xiaoaimei.model.find.PriceOptionInfo;
import com.yidan.xiaoaimei.model.find.TopicInfo;
import com.yidan.xiaoaimei.model.home.HomeCommonInfo;
import com.yidan.xiaoaimei.model.mine.AlbumAndVideoInfo;
import com.yidan.xiaoaimei.model.mine.AlbumInfo;
import com.yidan.xiaoaimei.model.mine.AttentionInfo;
import com.yidan.xiaoaimei.model.mine.BalanceDetailInfo;
import com.yidan.xiaoaimei.model.mine.BalanceInfo;
import com.yidan.xiaoaimei.model.mine.GiftInfo;
import com.yidan.xiaoaimei.model.mine.GiftListInfo;
import com.yidan.xiaoaimei.model.mine.IncomeDetailInfo;
import com.yidan.xiaoaimei.model.mine.MemberOptionsInfo;
import com.yidan.xiaoaimei.model.mine.PayOptionsInfo;
import com.yidan.xiaoaimei.model.mine.PersonInfo;
import com.yidan.xiaoaimei.model.mine.UpdatePersonInfo;
import com.yidan.xiaoaimei.model.mine.VerifyInfo;
import com.yidan.xiaoaimei.model.mine.VisitorInfo;
import com.yidan.xiaoaimei.model.mine.WechatPayInfo;
import com.yidan.xiaoaimei.model.mine.WithdrawInfo;
import com.yidan.xiaoaimei.model.user.AdverInfo;
import com.yidan.xiaoaimei.model.user.CityInfo;
import com.yidan.xiaoaimei.model.user.LoginQuickInfo;
import com.yidan.xiaoaimei.model.user.SetPersonInfo;
import com.yidan.xiaoaimei.model.user.TagInfo;
import com.yidan.xiaoaimei.model.user.VerifyCodeInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * API--接口  服务[这里处理的是同一的返回格式 resultCode  resultInfo Data<T> --->这里的data才是返回的结果,T就是泛型 具体返回的been对象或集合]
 * Created by HDL on 2016/8/3.
 */
public interface APIService {


    /**
     * 获取验证码
     *
     * @param mobile
     * @param type
     * @return
     */
    @POST("sendcode")
    @FormUrlEncoded
    Observable<VerifyCodeInfo> getCode(@Field("mobile") String mobile,
                                       @Field("type") String type); //0正常发送 1注册 2忘记密码


    /**
     * 快捷登录
     *
     * @param mobile
     * @param verifyCode
     * @param location
     * @return
     */
    @POST("auth/newLogin")
    @FormUrlEncoded
    Observable<LoginQuickInfo> loginQuick(@Field("mobile") String mobile,
                                          @Field("verifyCode") String verifyCode,
                                          @Field("location") String location);


    /**
     * 刷新token
     *
     * @param authorization
     * @return
     */
    @GET("auth/refreshTokenNew")
    Observable<LoginQuickInfo> refreshToken(@Header("Authorization") String authorization);


    /**
     * 获取城市
     *
     * @return
     */
    @GET("get_city")
    Observable<CityInfo> getCity();


    /**
     * 启动页
     *
     * @return
     */
    @GET("advertisement")
    Observable<AdverInfo> getAdverPage();


    /**
     * 设置个人资料
     *
     * @param authorization
     * @param nickName
     * @param sex
     * @param agentId
     * @param headImg
     * @return
     */
    @POST("user/setPerson")
    @FormUrlEncoded
    Observable<SetPersonInfo> setPerson(@Header("Authorization") String authorization,
                                        @Field("nickName") String nickName,
                                        @Field("sex") String sex,
                                        @Field("agentId") String agentId,
                                        @Field("headImg") String headImg,
                                        @Field("cityId") String cityId,
                                        @Field("location") String location);


    /**
     * 获取所有标签
     *
     * @param authorization
     * @return
     */
    @GET("getAllTags")
    Observable<TagInfo> getAllTags(@Header("Authorization") String authorization);


    /**
     * 选择标签
     *
     * @param authorization
     * @param tags
     * @param serviceTags
     * @return
     */
    @POST("user/selectTags")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> selectTags(@Header("Authorization") String authorization,
                                           @Field("tags") String tags,
                                           @Field("serviceTags") String serviceTags);


    /**
     * 获取用户信息
     *
     * @param authorization
     * @return
     */
    @GET("user/userShow")
    Observable<PersonInfo> userShow(@Header("Authorization") String authorization);


    /**
     * 获取个人资料
     *
     * @param authorization
     * @return
     */
    @GET("user/getPersonInfo")
    Observable<PersonInfo> getPersonInfo(@Header("Authorization") String authorization);


    /**
     * 更改个人资料
     *
     * @param authorization
     * @return
     */
    @POST("user/updatePerson")
    @FormUrlEncoded
    Observable<UpdatePersonInfo> updatePerson(@Header("Authorization") String authorization,
                                              @Field("headImg") String headImg,
                                              @Field("name") String name,
                                              @Field("sex") String sex,
                                              @Field("signature") String signature,
                                              @Field("age") String age,
                                              @Field("height") String height,
                                              @Field("measurements") String measurements,
                                              @Field("city") String city);

    /**
     * 关注列表，粉丝列表
     *
     * @param authorization
     * @param type
     * @return
     */
    @GET("user/fanList")
    Observable<AttentionInfo> getAttentionList(@Header("Authorization") String authorization,
                                               @Query("type") String type,
                                               @Query("page") String page);


    /**
     * 访客列表
     *
     * @param authorization
     * @param page
     * @return
     */
    @GET("user/visitRecord")
    Observable<VisitorInfo> getVisitors(@Header("Authorization") String authorization,
                                        @Query("page") String page);

    /**
     * 获取微信出售选项
     *
     * @param authorization
     * @return
     */
    @GET("getWechatPayOptions")
    Observable<WechatPayInfo> getWechatPay(@Header("Authorization") String authorization);


    /**
     * 设置微信
     *
     * @param authorization
     * @param optionId
     * @param wechatNum
     * @return
     */
    @POST("user/sellWechat")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> settingWechat(@Header("Authorization") String authorization,
                                              @Field("optionId") String optionId,
                                              @Field("wechatNum") String wechatNum);


    /**
     * 获取充值选项
     *
     * @return
     */
    @GET("getPayOptions")
    Observable<PayOptionsInfo> getPayOptions();


    /**
     * 获取开通会员选项
     *
     * @return
     */
    @GET("getMemberPayOptions")
    Observable<MemberOptionsInfo> getMemberPayOptions(@Header("Authorization") String authorization);

    /**
     * 获取余额
     *
     * @return
     */
    @GET("user/balance")
    Observable<BalanceInfo> getBalance(@Header("Authorization") String authorization);


    /**
     * 获取购买记录
     *
     * @param authorization
     * @return
     */
    @GET("user/accountDetail")
    Observable<BalanceDetailInfo> getBalanceDetail(@Header("Authorization") String authorization);


    /**
     * 账户充值
     *
     * @param authorization
     * @param payType
     * @param payOptionId
     * @return
     */
    @POST("accountRecharge")
    @FormUrlEncoded
    Observable<CommonInfo> accountRecharge(@Header("Authorization") String authorization,
                                           @Field("payType") String payType,
                                           @Field("payOptionId") String payOptionId);


    /**
     * 会员充值
     *
     * @param authorization
     * @param payType
     * @param payOptionId
     * @return
     */
    @POST("memberRecharge")
    @FormUrlEncoded
    Observable<CommonInfo> memberRecharge(@Header("Authorization") String authorization,
                                          @Field("payType") String payType,
                                          @Field("payOptionId") String payOptionId);


    /**
     * 提现认证
     *
     * @param authorization
     * @param identityName
     * @param identityNum
     * @param alipayAccount
     * @param mobile
     * @param verifyCode
     * @return
     */
    @POST("user/authWithdraw")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> authWithdraw(@Header("Authorization") String authorization,
                                             @Field("identityName") String identityName,
                                             @Field("identityNum") String identityNum,
                                             @Field("alipayAccount") String alipayAccount,
                                             @Field("mobile") String mobile,
                                             @Field("verifyCode") String verifyCode);

    /**
     * 获取提现状态(支付宝账号，可提现金额等)
     *
     * @param authorization
     * @param type
     * @return
     */
    @GET("user/submitCash")
    Observable<WithdrawInfo> getWithdrawInfo(@Header("Authorization") String authorization,
                                             @Query("type") String type);


    /**
     * 提交提现请求
     *
     * @param authorization
     * @param type
     * @return
     */
    @GET("user/submitCash")
    Observable<CommonEmptyInfo> withdraw(@Header("Authorization") String authorization,
                                         @Query("type") String type);


    /**
     * 获取收益详情
     *
     * @param authorization
     * @return
     */
    @GET("user/incomeDetails")
    Observable<IncomeDetailInfo> getIncomeDetail(@Header("Authorization") String authorization);


    /**
     * 礼物列表
     *
     * @return
     */
    @GET("gifts")
    Observable<GiftInfo> getGifts();


    /**
     * 获取收到的礼物
     *
     * @param uid
     * @return
     */
    @POST("giftList")
    @FormUrlEncoded
    Observable<GiftListInfo> getMyGifts(@Field("uid") String uid);


    /**
     * 获取用户相册和视频
     *
     * @param authorization
     * @return
     */
    @GET("user/getUserAlbum")
    Observable<AlbumAndVideoInfo> getAlbumAndVideo(@Header("Authorization") String authorization);


    /**
     * 获取相册或视频
     *
     * @param authorization
     * @param albumType
     * @return
     */
    @POST("user/myAlbum")
    @FormUrlEncoded
    Observable<AlbumInfo> getAlbum(@Header("Authorization") String authorization,
                                   @Field("albumType") String albumType);

    /**
     * 添加图片
     *
     * @param authorization
     * @param img
     * @return
     */
    @POST("user/addImg")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> addImage(@Header("Authorization") String authorization,
                                         @Field("img") String img);

    /**
     * 添加视频
     *
     * @param authorization
     * @param videoInfo
     * @return
     */
    @POST("user/addVideo")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> addVideo(@Header("Authorization") String authorization,
                                         @Field("videoInfo") String videoInfo);


    /**
     * 删除图片/视频
     *
     * @param authorization
     * @param imgId
     * @return
     */
    @POST("user/deleteImg")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> deleteImage(@Header("Authorization") String authorization,
                                            @Field("imgId") String imgId);


    /**
     * 认证记录
     *
     * @param authorization
     * @param type
     * @return
     */
    @POST("user/userAuthen")
    @FormUrlEncoded
    Observable<VerifyInfo> getUserVerify(@Header("Authorization") String authorization,
                                         @Field("type") String type);


    /**
     * 提交录音
     *
     * @param authorization
     * @param voiceUrl
     * @param voiceTime
     * @return
     */
    @POST("user/submitVoice")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> submitVoice(@Header("Authorization") String authorization,
                                            @Field("voiceUrl") String voiceUrl,
                                            @Field("voiceTime") String voiceTime);


    /**
     * 提交认证视频
     *
     * @param authorization
     * @param videoUrl
     * @param videoTime
     * @param firstImg
     * @return
     */
    @POST("user/submitVideo")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> submitVideo(@Header("Authorization") String authorization,
                                            @Field("videoUrl") String videoUrl,
                                            @Field("videoTime") String videoTime,
                                            @Field("firstImg") String firstImg);


    /**
     * 获取首页数据
     *
     * @param authorization
     * @param type
     * @return
     */
    @POST("home")
    @FormUrlEncoded
    Observable<HomeCommonInfo> getHomeData(@Header("Authorization") String authorization,
                                           @Field("type") String type,
                                           @Field("page") String page);

    /**
     * 关注
     *
     * @param authorization
     * @param type
     * @param uid
     * @return
     */
    @POST("user/focusOthers")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> focus(@Header("Authorization") String authorization,
                                      @Field("type") String type,
                                      @Field("uid") String uid);

    /**
     * 获取个人详情
     *
     * @param authorization
     * @param uid
     * @return
     */
    @POST("user/userDetail")
    @FormUrlEncoded
    Observable<UserDetailInfo> getUserDetail(@Header("Authorization") String authorization,
                                             @Field("uid") String uid);

    /**
     * 获取相册或视频
     *
     * @param authorization
     * @param uid
     * @param albumType
     * @return
     */
    @POST("user/showAlbum")
    @FormUrlEncoded
    Observable<AlbumShowInfo> getDetailAlbums(@Header("Authorization") String authorization,
                                              @Field("uid") String uid,
                                              @Field("albumType") String albumType);

    /**
     * 获取评价选项
     *
     * @return
     */
    @GET("getEvaOptions")
    Observable<EvaluateInfo> getEvaOptions();


    /**
     * 评价用户
     *
     * @param authorization
     * @param evaId
     * @param uid
     * @return
     */
    @POST("user/evaluate")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> evaluate(@Header("Authorization") String authorization,
                                         @Field("evaId") String evaId,
                                         @Field("uid") String uid);


    /**
     * 动态列表
     *
     * @param authorization
     * @param type
     * @param page
     * @return
     */
    @POST("moment/momentList")
    @FormUrlEncoded
    Observable<MomentListInfo> getMomentList(@Header("Authorization") String authorization,
                                             @Field("type") String type,
                                             @Field("page") String page);

    /**
     * 点赞
     *
     * @param authorization
     * @param momentId
     * @return
     */
    @POST("moment/praise")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> praise(@Header("Authorization") String authorization,
                                       @Field("momentId") String momentId);


    /**
     * 动态详情
     *
     * @param authorization
     * @param momentId
     * @return
     */
    @POST("moment/momentDetail")
    @FormUrlEncoded
    Observable<MomentDetailInfo> getMomentDetail(@Header("Authorization") String authorization,
                                                 @Field("momentId") String momentId);


    /**
     * 评论
     *
     * @param authorization
     * @param momentId
     * @param content
     * @return
     */
    @POST("moment/comment")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> comment(@Header("Authorization") String authorization,
                                        @Field("momentId") String momentId,
                                        @Field("content") String content);

    /**
     * 回复
     *
     * @param authorization
     * @param momentId
     * @param content
     * @param commentId
     * @return
     */
    @POST("moment/reply")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> reply(@Header("Authorization") String authorization,
                                      @Field("momentId") String momentId,
                                      @Field("content") String content,
                                      @Field("commentId") String commentId);


    /**
     * 删除评论
     *
     * @param authorization
     * @param commentId
     * @return
     */
    @POST("moment/deleteComment")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> deleteComment(@Header("Authorization") String authorization,
                                              @Field("commentId") String commentId);


    /**
     * 获取动态话题
     *
     * @return
     */
    @GET("getTopicTags")
    Observable<TopicInfo> getTopic();


    /**
     * 获取动态出售价格
     *
     * @return
     */
    @GET("getMomentPriceOptions")
    Observable<PriceOptionInfo> getMomentPriceOptions();


    /**
     * 发动态
     *
     * @param authorization
     * @param momentType
     * @param tagId
     * @param content
     * @param city
     * @param voice
     * @param voiceTime
     * @param video
     * @param firstImg
     * @param videoTime
     * @param imgs
     * @param optionId
     * @return
     */
    @POST("moment/publishMoment")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> publishMoment(@Header("Authorization") String authorization,
                                              @Field("momentType") String momentType,
                                              @Field("tagId") String tagId,
                                              @Field("content") String content,
                                              @Field("city") String city,
                                              @Field("voice") String voice,
                                              @Field("voiceTime") String voiceTime,
                                              @Field("video") String video,
                                              @Field("firstImg") String firstImg,
                                              @Field("videoTime") String videoTime,
                                              @Field("imgs") String imgs,
                                              @Field("optionId") String optionId);


    /**
     * 购买动态
     *
     * @param authorization
     * @param momentId
     * @return
     */
    @POST("user/buyMoment")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> buyMoment(@Header("Authorization") String authorization,
                                          @Field("momentId") String momentId);


    /**
     * 获取礼物
     *
     * @return
     */
    @GET("gifts")
    Observable<com.netease.nim.uikit.api.model.gift.GiftListInfo> getAllGifts();


    /**
     * 赠送礼物
     *
     * @param authorization
     * @param giftId
     * @param giftNum
     * @param uid
     * @return
     */
    @POST("giveGift")
    @FormUrlEncoded
    Observable<CommonEmptyInfo> giveGift(@Header("Authorization") String authorization,
                                         @Field("giftId") String giftId,
                                         @Field("giftNum") String giftNum,
                                         @Field("uid") String uid);
}