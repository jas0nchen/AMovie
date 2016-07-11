package cn.jas0n.amovie.bean;

import java.io.Serializable;
import java.util.List;

import cn.jas0n.amovie.bean.RecBean.RecDramaItem;

/**
 * Author: Jas0n
 * Date: 2016/7/11
 * E-mail:chendong90x@gmail.com
 */
public class RecDramaBean implements Serializable {

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
        return "RecBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data implements Serializable {
        private List<RecDramaItem> recommended;

        public List<RecDramaItem> getRecommended() {
            return recommended;
        }

        public void setRecommended(List<RecDramaItem> recommended) {
            this.recommended = recommended;
        }

        @Override
        public String toString() {
            return "Data{" +
                    ", recommended=" + recommended +
                    '}';
        }
    }
}
