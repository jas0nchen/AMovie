package cn.jas0n.amovie.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Jas0n
 * Date: 2016/7/14
 * E-mail:chendong90x@gmail.com
 */
public class VideoSearch implements Serializable {

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
        return "SeasonSearch{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data implements Serializable{
        private int total;
        private List<RecBean.HotVideoItem> results;
        private int currentPage;

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

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "total=" + total +
                    ", results=" + results +
                    ", currentPage=" + currentPage +
                    '}';
        }
    }
}
