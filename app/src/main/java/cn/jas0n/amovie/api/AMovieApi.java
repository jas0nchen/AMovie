package cn.jas0n.amovie.api;

import cn.jas0n.amovie.bean.ConstantCategory;
import cn.jas0n.amovie.bean.M3U8ById;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.bean.VideoDetail;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public interface AMovieApi {

    @GET("constant/category")
    public Observable<ConstantCategory> getConstantCategory();

    @GET("video/index")
    public Observable<RecBean> getRecVideos(@Header("clientVersion") String clientVersion);

    @FormUrlEncoded
    @POST("video/findM3u8ByVideoId")
    public Observable<M3U8ById> getM3U8ByVideoId(@Field("videoId") String videoId, @Field("quality") String quality);

    @FormUrlEncoded
    @POST("video/detail")
    public Observable<VideoDetail> getVideoDetail(@Field("videoId") String videoId, @Field("token")
                                                  String token);
}
