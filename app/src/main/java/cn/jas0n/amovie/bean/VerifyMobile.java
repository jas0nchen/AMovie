package cn.jas0n.amovie.bean;

import java.io.Serializable;

/**
 * Author: Jas0n
 * Date: 2016/7/6
 * E-mail:chendong90x@gmail.com
 */
public class VerifyMobile implements Serializable {
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
        return "VerifyMobile{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data implements Serializable {
        private String exist;

        public String getExist() {
            return exist;
        }

        public void setExist(String exist) {
            this.exist = exist;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "exist='" + exist + '\'' +
                    '}';
        }
    }
}
