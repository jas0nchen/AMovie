package cn.jas0n.amovie.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class RecBean implements Serializable {

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
        private List<HotVideoItem> hotVideo;
        private List<RecDramaItem> recDrama;
        private List<Video> video;

        public List<HotVideoItem> getHotVideo() {
            return hotVideo;
        }

        public void setHotVideo(List<HotVideoItem> hotVideo) {
            this.hotVideo = hotVideo;
        }

        public List<RecDramaItem> getRecDrama() {
            return recDrama;
        }

        public void setRecDrama(List<RecDramaItem> recDrama) {
            this.recDrama = recDrama;
        }

        public List<Video> getVideo() {
            return video;
        }

        public void setVideo(List<Video> video) {
            this.video = video;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "hotVideo=" + hotVideo +
                    ", recDrama=" + recDrama +
                    ", video=" + video +
                    '}';
        }
    }

    public class HotVideoItem implements Serializable {
        private int danmuCount;
        private String title;
        private String url;
        private int viewCount;
        private Author author;
        private String verticalCoverUrl;
        private long createTime;
        private long updateTime;
        private String createTimeStr;
        private int id;

        public int getDanmuCount() {
            return danmuCount;
        }

        public void setDanmuCount(int danmuCount) {
            this.danmuCount = danmuCount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public String getVerticalCoverUrl() {
            return verticalCoverUrl;
        }

        public void setVerticalCoverUrl(String verticalCoverUrl) {
            this.verticalCoverUrl = verticalCoverUrl;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "HotVideoItem{" +
                    "danmuCount=" + danmuCount +
                    ", title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    ", viewCount=" + viewCount +
                    ", author=" + author +
                    ", verticalCoverUrl='" + verticalCoverUrl + '\'' +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", createTimeStr='" + createTimeStr + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

    public class RecDramaItem implements Serializable {
        private String title;
        private String url;
        private int upInfo;
        private String shortBrief;
        private String verticalUrl;
        private String mark;
        private String horizontalUrl;
        private int id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getUpInfo() {
            return upInfo;
        }

        public void setUpInfo(int upInfo) {
            this.upInfo = upInfo;
        }

        public String getShortBrief() {
            return shortBrief;
        }

        public void setShortBrief(String shortBrief) {
            this.shortBrief = shortBrief;
        }

        public String getVerticalUrl() {
            return verticalUrl;
        }

        public void setVerticalUrl(String verticalUrl) {
            this.verticalUrl = verticalUrl;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getHorizontalUrl() {
            return horizontalUrl;
        }

        public void setHorizontalUrl(String horizontalUrl) {
            this.horizontalUrl = horizontalUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "RecDramaItem{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    ", upInfo=" + upInfo +
                    ", shortBrief='" + shortBrief + '\'' +
                    ", verticalUrl='" + verticalUrl + '\'' +
                    ", mark='" + mark + '\'' +
                    ", horizontalUrl='" + horizontalUrl + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

    public class Video implements Serializable{
        private List<HotVideoItem> videos;
        private int categoryId;
        private String title;
        private int id;

        public List<HotVideoItem> getVideos() {
            return videos;
        }

        public void setVideos(List<HotVideoItem> videos) {
            this.videos = videos;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Video{" +
                    "videos=" + videos +
                    ", categoryId=" + categoryId +
                    ", title='" + title + '\'' +
                    ", id=" + id +
                    '}';
        }
    }
}
