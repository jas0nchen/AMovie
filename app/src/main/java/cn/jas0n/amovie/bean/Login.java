package cn.jas0n.amovie.bean;

import java.io.Serializable;

/**
 * Author: Jas0n
 * Date: 2016/6/29
 * E-mail:chendong90x@gmail.com
 */
public class Login implements Serializable {

    private String code;
    private String msg;

    @Override
    public String toString() {
        return "Login{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
