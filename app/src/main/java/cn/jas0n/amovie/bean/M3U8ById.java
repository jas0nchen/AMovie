package cn.jas0n.amovie.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Author: Jas0n
 * Date: 16/6/26
 * E-mail:chendong90x@gmail.com
 */
public class M3U8ById implements Serializable {

    /*{
            "code": "0000",
            "msg": "",
            "data": {
                "m3u8": {
                    "currentQuality": "super",
                    "url": "http://play.g3proxy.lecloud.com/vod/v2/MjAyLzE0LzExMy9iY2xvdWQvODI5NjQzL3Zlcl8wMF8yMi0xMDUxNzQ4OTE5LWF2Yy04MDE5MzMtYWFjLTY0MDA3LTExOTc0NS0xMzA5MzYwNi03NmNlNGYzNmFhNDBiOTFiMGFlN2E4MGM1Y2VhMjJiNC0xNDY2NTg0OTcyOTQ5Lm1wNA==?b=873&mmsid=59210011&tm=1466912791&key=c68dbd66a36936a0b54fd5e61ad61fbd&platid=2&splatid=206&playid=0&tss=no&vtype=22&cvid=703397531943&payff=0&pip=105519432d894c250fd31c7931f9b337&tag=mobile&sign=bcloud_829643&termid=2&pay=0&ostype=android&hwtype=un",
                    "qualityArr": [
                    "low",
                    "high",
                    "super"
                    ]
                }
            }
    }*/

    private String code;
    private String msg;
    private Data data;

    public class Data implements Serializable{
        private M3U8 m3u8;

        public M3U8 getM3u8() {
            return m3u8;
        }

        public void setM3u8(M3U8 m3u8) {
            this.m3u8 = m3u8;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "m3u8=" + m3u8 +
                    '}';
        }
    }

    public class M3U8 implements Serializable{
        private String currentQuality;
        private String url;
        private String[] qualityArr;

        public int getQualitySize(){
            return qualityArr.length;
        }

        public String getCurrentQuality() {
            return currentQuality;
        }

        public String getUrl() {
            return url;
        }

        public String[] getQualityArr() {
            return qualityArr;
        }

        @Override
        public String toString() {
            return "M3U8{" +
                    "qualityArr=" + Arrays.toString(qualityArr) +
                    ", url='" + url + '\'' +
                    ", currentQuality='" + currentQuality + '\'' +
                    '}';
        }
    }

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
        return "M3U8ById{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
