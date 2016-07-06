package cn.jas0n.amovie.bean;

import java.io.Serializable;

/**
 * Author: Jas0n
 * Date: 2016/7/6
 * E-mail:chendong90x@gmail.com
 */
public class ValidInfo implements Serializable {

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ValidInfo{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
