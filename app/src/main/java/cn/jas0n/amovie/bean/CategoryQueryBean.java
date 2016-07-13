package cn.jas0n.amovie.bean;

import java.io.Serializable;
import java.util.List;

import cn.jas0n.amovie.bean.RecBean.RecDramaItem;

/**
 * Author: Jas0n
 * Date: 2016/7/11
 * E-mail:chendong90x@gmail.com
 */
public class CategoryQueryBean implements Serializable {

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
        private int total;
        private List<RecBean.HotVideoItem> results;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RecBean.HotVideoItem> getResults() {
            return results;
        }

        public void setResults(List<RecBean.HotVideoItem> results) {
            this.results = results;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "results=" + results +
                    '}';
        }
    }
}
