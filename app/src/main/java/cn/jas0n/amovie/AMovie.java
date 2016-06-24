package cn.jas0n.amovie;

import android.app.Application;

import com.orhanobut.logger.Logger;


/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class AMovie extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.init();
    }
}
