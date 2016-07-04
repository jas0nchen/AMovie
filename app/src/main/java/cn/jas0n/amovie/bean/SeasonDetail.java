package cn.jas0n.amovie.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Jas0n
 * Date: 2016/7/4
 * E-mail:chendong90x@gmail.com
 */
public class SeasonDetail implements Serializable {
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
        return "SeasonDetail{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data implements Serializable {
        private Season season;

        public Season getSeason() {
            return season;
        }

        public void setSeason(Season season) {
            this.season = season;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "season=" + season +
                    '}';
        }
    }

    public class Season implements Serializable {
        private String title;
        private List<PlayUrl> playUrlList;
        private String sid;
        private String cat;
        private float score;
        private boolean isFocus;
        private int viewCount;
        private String verticalUrl;
        private String cover;
        private String playStatus;
        private String enTitle;
        private Author author;
        private String brief;
        private String horizontalUrl;
        private List<LikeSeason> likeSeasons;
        private List<EpisodeBrief> episode_brief;
        private List<LikeSeason> recommend;
        private int updateInfo;
        private int total;
        private long createTime;
        private long updateTime;
        private String createTimeStr;
        private int id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<PlayUrl> getPlayUrlList() {
            return playUrlList;
        }

        public void setPlayUrlList(List<PlayUrl> playUrlList) {
            this.playUrlList = playUrlList;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getCat() {
            return cat;
        }

        public void setCat(String cat) {
            this.cat = cat;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public boolean isFocus() {
            return isFocus;
        }

        public void setFocus(boolean focus) {
            isFocus = focus;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public String getVerticalUrl() {
            return verticalUrl;
        }

        public void setVerticalUrl(String verticalUrl) {
            this.verticalUrl = verticalUrl;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getPlayStatus() {
            return playStatus;
        }

        public void setPlayStatus(String playStatus) {
            this.playStatus = playStatus;
        }

        public String getEnTitle() {
            return enTitle;
        }

        public void setEnTitle(String enTitle) {
            this.enTitle = enTitle;
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getHorizontalUrl() {
            return horizontalUrl;
        }

        public void setHorizontalUrl(String horizontalUrl) {
            this.horizontalUrl = horizontalUrl;
        }

        public List<LikeSeason> getLikeSeasons() {
            return likeSeasons;
        }

        public void setLikeSeasons(List<LikeSeason> likeSeasons) {
            this.likeSeasons = likeSeasons;
        }

        public List<EpisodeBrief> getEpisode_brief() {
            return episode_brief;
        }

        public void setEpisode_brief(List<EpisodeBrief> episode_brief) {
            this.episode_brief = episode_brief;
        }

        public List<LikeSeason> getRecommend() {
            return recommend;
        }

        public void setRecommend(List<LikeSeason> recommend) {
            this.recommend = recommend;
        }

        public int getUpdateInfo() {
            return updateInfo;
        }

        public void setUpdateInfo(int updateInfo) {
            this.updateInfo = updateInfo;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
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
            return "Season{" +
                    "title='" + title + '\'' +
                    ", playUrlList=" + playUrlList +
                    ", sid='" + sid + '\'' +
                    ", cat='" + cat + '\'' +
                    ", score=" + score +
                    ", isFocus=" + isFocus +
                    ", viewCount=" + viewCount +
                    ", verticalUrl='" + verticalUrl + '\'' +
                    ", cover='" + cover + '\'' +
                    ", playStatus='" + playStatus + '\'' +
                    ", enTitle='" + enTitle + '\'' +
                    ", author=" + author +
                    ", brief='" + brief + '\'' +
                    ", horizontalUrl='" + horizontalUrl + '\'' +
                    ", likeSeasons=" + likeSeasons +
                    ", episode_brief=" + episode_brief +
                    ", recommend=" + recommend +
                    ", updateInfo=" + updateInfo +
                    ", total=" + total +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", createTimeStr='" + createTimeStr + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

    public class EpisodeBrief implements Serializable{
        private String sid;
        private String text;
        private String episode;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getEpisode() {
            return episode;
        }

        public void setEpisode(String episode) {
            this.episode = episode;
        }

        @Override
        public String toString() {
            return "EpisodeBrief{" +
                    "sid='" + sid + '\'' +
                    ", text='" + text + '\'' +
                    ", episode='" + episode + '\'' +
                    '}';
        }
    }

    public class PlayUrl implements Serializable {
        private String playLink;
        private String site;
        private String episondeSid;
        private int id;

        public String getPlayLink() {
            return playLink;
        }

        public void setPlayLink(String playLink) {
            this.playLink = playLink;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getEpisondeSid() {
            return episondeSid;
        }

        public void setEpisondeSid(String episondeSid) {
            this.episondeSid = episondeSid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "PlayUrl{" +
                    "playLink='" + playLink + '\'' +
                    ", site='" + site + '\'' +
                    ", episondeSid='" + episondeSid + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

    public class LikeSeason implements Serializable{
        private String title;
        private String sid;
        private String cat;
        private float score;
        private String verticalUrl;
        private int upInfo;
        private String cover;
        private boolean finish;
        private String enTitle;
        private int seasonNo;
        private String brief;
        private String horizontalUrl;
        private int id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getCat() {
            return cat;
        }

        public void setCat(String cat) {
            this.cat = cat;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public String getVerticalUrl() {
            return verticalUrl;
        }

        public void setVerticalUrl(String verticalUrl) {
            this.verticalUrl = verticalUrl;
        }

        public int getUpInfo() {
            return upInfo;
        }

        public void setUpInfo(int upInfo) {
            this.upInfo = upInfo;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public boolean isFinish() {
            return finish;
        }

        public void setFinish(boolean finish) {
            this.finish = finish;
        }

        public String getEnTitle() {
            return enTitle;
        }

        public void setEnTitle(String enTitle) {
            this.enTitle = enTitle;
        }

        public int getSeasonNo() {
            return seasonNo;
        }

        public void setSeasonNo(int seasonNo) {
            this.seasonNo = seasonNo;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
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
            return "LikeSeason{" +
                    "title='" + title + '\'' +
                    ", sid='" + sid + '\'' +
                    ", cat='" + cat + '\'' +
                    ", score=" + score +
                    ", verticalUrl='" + verticalUrl + '\'' +
                    ", upInfo=" + upInfo +
                    ", cover='" + cover + '\'' +
                    ", finish=" + finish +
                    ", enTitle='" + enTitle + '\'' +
                    ", seasonNo=" + seasonNo +
                    ", brief='" + brief + '\'' +
                    ", horizontalUrl='" + horizontalUrl + '\'' +
                    ", id=" + id +
                    '}';
        }
    }
}
