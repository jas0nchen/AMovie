package cn.jas0n.amovie.api;

import cn.jas0n.amovie.bean.ConstantCategory;
import cn.jas0n.amovie.bean.RecBean;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
}
