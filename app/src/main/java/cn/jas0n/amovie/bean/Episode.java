package cn.jas0n.amovie.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Jas0n
 * Date: 2016/7/4
 * E-mail:chendong90x@gmail.com
 */
public class Episode implements Serializable {
    private String code;
    private String msg;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data implements Serializable{
        private M3U8ById.M3U8 m3u8;

        public M3U8ById.M3U8 getM3u8() {
            return m3u8;
        }

        public void setM3u8(M3U8ById.M3U8 m3u8) {
            this.m3u8 = m3u8;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "m3u8=" + m3u8 +
                    '}';
        }
    }
}
