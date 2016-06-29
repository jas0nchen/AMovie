package cn.jas0n.amovie.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Jas0n
 * Date: 2016/6/29
 * E-mail:chendong90x@gmail.com
 */
public class Comment implements Serializable {

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
        return "Comment{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data implements Serializable {
        private List<Result> results;
        private int total;
        private int currentPage;

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
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
                    "results=" + results +
                    ", total=" + total +
                    ", currentPage=" + currentPage +
                    '}';
        }
    }

    public class Result implements Serializable {
        private int id;
        private Author author;
        private String content;
        private String parentContent;
        private Author parentAuthor;
        private int likeCount;
        private boolean isLiked;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getParentContent() {
            return parentContent;
        }

        public void setParentContent(String parentContent) {
            this.parentContent = parentContent;
        }

        public Author getParentAuthor() {
            return parentAuthor;
        }

        public void setParentAuthor(Author parentAuthor) {
            this.parentAuthor = parentAuthor;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public boolean isLiked() {
            return isLiked;
        }

        public void setLiked(boolean liked) {
            isLiked = liked;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "id=" + id +
                    ", author=" + author +
                    ", content='" + content + '\'' +
                    ", parentContent='" + parentContent + '\'' +
                    ", parentAuthor=" + parentAuthor +
                    ", likeCount=" + likeCount +
                    ", isLiked=" + isLiked +
                    '}';
        }
    }
}
