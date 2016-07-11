package cn.jas0n.amovie.bean;

import java.io.Serializable;
import java.util.List;

import cn.jas0n.amovie.bean.RecBean.RecDramaItem;

/**
 * Author: Jas0n
 * Date: 2016/7/11
 * E-mail:chendong90x@gmail.com
 */
public class CateBean implements Serializable {

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
        return "CateBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data implements Serializable {
        private List<RecBean.HotVideoItem> lastest;
        private List<RecBean.HotVideoItem> recommended;
        private List<RecBean.HotVideoItem> hot;

        public List<RecBean.HotVideoItem> getLastest() {
            return lastest;
        }

        public void setLastest(List<RecBean.HotVideoItem> lastUpdate) {
            this.lastest = lastUpdate;
        }

        public List<RecBean.HotVideoItem> getRecommended() {
            return recommended;
        }

        public void setRecommended(List<RecBean.HotVideoItem> recommended) {
            this.recommended = recommended;
        }

        public List<RecBean.HotVideoItem> getHot() {
            return hot;
        }

        public void setHot(List<RecBean.HotVideoItem> hot) {
            this.hot = hot;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "lastest=" + lastest +
                    ", recommended=" + recommended +
                    ", hot=" + hot +
                    '}';
        }
    }
}
