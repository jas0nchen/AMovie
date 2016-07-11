package cn.jas0n.amovie.api;

import cn.jas0n.amovie.bean.CateBean;
import cn.jas0n.amovie.bean.Comment;
import cn.jas0n.amovie.bean.ConstantCategory;
import cn.jas0n.amovie.bean.DramaBean;
import cn.jas0n.amovie.bean.Episode;
import cn.jas0n.amovie.bean.Login;
import cn.jas0n.amovie.bean.M3U8ById;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.bean.RecDramaBean;
import cn.jas0n.amovie.bean.SeasonDetail;
import cn.jas0n.amovie.bean.ValidInfo;
import cn.jas0n.amovie.bean.VerifyMobile;
import cn.jas0n.amovie.bean.VideoDetail;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public interface AMovieApi {

    @GET("constant/category")
    Observable<ConstantCategory> getConstantCategory();

    @GET("video/index")
    Observable<RecBean> getRecVideos(@Header("clientVersion") String clientVersion);

    @FormUrlEncoded
    @POST("video/categoryIndex")
    Observable<CateBean> getCategoryIndex(@Field("categoryId") int categoryId);

    @GET("season/index")
    Observable<DramaBean> getSeasonIndex(@Header("clientVersion") String clientVersion);

    @GET("season/recommended")
    Observable<RecDramaBean> getRecommendSeasons(@Query("page") int page, @Query("rows") int rows);

    @FormUrlEncoded
    @POST("video/findM3u8ByVideoId")
    Observable<M3U8ById> getM3U8ByVideoId(@Field("videoId") String videoId, @Field("quality") String quality);

    @FormUrlEncoded
    @POST("video/detail")
    Observable<VideoDetail> getVideoDetail(@Field("videoId") String videoId, @Field("token")
    String token);

    @FormUrlEncoded
    @POST("comment/list")
    Observable<Comment> getCommentList(@Query("page") int page, @Query("rows") int
            rows, @Field("videoId") String videoId, @Field("infoId") String infoId,
                                       @Field("activeId") String activeId,
                                       @Field("reportId") String reportId,
                                       @Field("seasonId") String seasonId,
                                       @Field("userId") String userId);

    @FormUrlEncoded
    @POST("user/mobileLogin")
    Observable<Login> loginByMobile(@Field("mobile") String mobile, @Field("password")
    String password);

    @FormUrlEncoded
    @POST("season/detail")
    Observable<SeasonDetail> getSeasonDetail(@Field("seasonId") String seasonId);

    @FormUrlEncoded
    @POST("video/findM3u8ByEpisodeSid")
    Observable<Episode> getEpisode(@Field("quality") String quality, @Field("seasonId") String
            seasonId, @Field("episodeSid") String episodeSid);

    @FormUrlEncoded
    @POST("user/verifyMobile")
    Observable<VerifyMobile> verifyMobile(@Field("mobile") String mobile);

    @GET("user/sendCaptcha ")
    Observable<ValidInfo> sendCaptcha(@Query("mobile") String mobile);

    @FormUrlEncoded
    @POST("user/verifyCaptcha")
    Observable<ValidInfo> verifyCaptcha(@Field("mobile") String mobile, @Field("code") String
            code);

    @FormUrlEncoded
    @POST("user/mobileReg")
    Observable<Login> mobileRegister(@Field("mobile") String mobile, @Field("code") String
            code, @Field("nickName") String nickName, @Field("pwd") String pwd);

    @FormUrlEncoded
    @POST("user/resetPwdMobile")
    Observable<ValidInfo> resetPwdMobile(@Field("mobile") String mobile, @Field("password")
    String password, @Field("code") String code);

    @FormUrlEncoded
    @POST("user/updateUser")
    Observable<Login> updateUser(@Query("token") String token, @Field("sex") String sex,
                                 @Field("city") String city, @Field("sign") String sign);

    @GET("user/profile ")
    Observable<Login> getProfile(@Query("token") String token);
}
