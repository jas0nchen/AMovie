package cn.jas0n.amovie.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Jas0n
 * Date: 2016/6/28
 * E-mail:chendong90x@gmail.com
 */
public class VideoDetail implements Serializable {

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
        return "VideoDetail{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data implements Serializable{
        private int total;
        private List<Result> results;
        private int currentPage;
        private UserVideoView userVideoView;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public UserVideoView getUserVideoView() {
            return userVideoView;
        }

        public void setUserVideoView(UserVideoView userVideoView) {
            this.userVideoView = userVideoView;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "total=" + total +
                    ", results=" + results +
                    ", currentPage=" + currentPage +
                    ", userVideoView=" + userVideoView +
                    '}';
        }
    }

    public class UserVideoView implements Serializable{
        private int silverCount;
        private int commentCount;
        private String title;
        private ZimuzuView zimuzuView;
        private String playLink;
        private int playCount;
        private int danmuCount;
        private String brief;
        private String cover;
        private boolean isFavo;
        private String verticalCoverUrl;
        private String tags;
        private long createTime;
        private long updateTime;
        private String createTimeStr;
        private int id;

        public int getSilverCount() {
            return silverCount;
        }

        public void setSilverCount(int silverCount) {
            this.silverCount = silverCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ZimuzuView getZimuzuView() {
            return zimuzuView;
        }

        public void setZimuzuView(ZimuzuView zimuzuView) {
            this.zimuzuView = zimuzuView;
        }

        public String getPlayLink() {
            return playLink;
        }

        public void setPlayLink(String playLink) {
            this.playLink = playLink;
        }

        public int getPlayCount() {
            return playCount;
        }

        public void setPlayCount(int playCount) {
            this.playCount = playCount;
        }

        public int getDanmuCount() {
            return danmuCount;
        }

        public void setDanmuCount(int danmuCount) {
            this.danmuCount = danmuCount;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public boolean isFavo() {
            return isFavo;
        }

        public void setFavo(boolean favo) {
            isFavo = favo;
        }

        public String getVerticalCoverUrl() {
            return verticalCoverUrl;
        }

        public void setVerticalCoverUrl(String verticalCoverUrl) {
            this.verticalCoverUrl = verticalCoverUrl;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
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
            return "UserVideoView{" +
                    "silverCount=" + silverCount +
                    ", commentCount=" + commentCount +
                    ", title='" + title + '\'' +
                    ", zimuzuView=" + zimuzuView +
                    ", playLink='" + playLink + '\'' +
                    ", playCount=" + playCount +
                    ", danmuCount=" + danmuCount +
                    ", brief='" + brief + '\'' +
                    ", cover='" + cover + '\'' +
                    ", isFavo=" + isFavo +
                    ", verticalCoverUrl='" + verticalCoverUrl + '\'' +
                    ", tags='" + tags + '\'' +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", createTimeStr='" + createTimeStr + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

    public class ZimuzuView implements Serializable{
        private int fansCount;
        private boolean isFocus;
        private int videoCount;
        private String intro;
        private boolean isConfirmed;
        private String nickName;
        private String roleInfo;
        private String headImgUrl;
        private String level;
        private long createTime;
        private long updateTime;
        private String createTimeStr;
        private int id;

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }

        public boolean isFocus() {
            return isFocus;
        }

        public void setFocus(boolean focus) {
            isFocus = focus;
        }

        public int getVideoCount() {
            return videoCount;
        }

        public void setVideoCount(int videoCount) {
            this.videoCount = videoCount;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public boolean isConfirmed() {
            return isConfirmed;
        }

        public void setConfirmed(boolean confirmed) {
            isConfirmed = confirmed;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getRoleInfo() {
            return roleInfo;
        }

        public void setRoleInfo(String roleInfo) {
            this.roleInfo = roleInfo;
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
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
            return "ZimuzuView{" +
                    "fansCount=" + fansCount +
                    ", isFocus=" + isFocus +
                    ", videoCount=" + videoCount +
                    ", intro='" + intro + '\'' +
                    ", isConfirmed=" + isConfirmed +
                    ", nickName='" + nickName + '\'' +
                    ", roleInfo='" + roleInfo + '\'' +
                    ", headImgUrl='" + headImgUrl + '\'' +
                    ", level='" + level + '\'' +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", createTimeStr='" + createTimeStr + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

    public class Result implements Serializable{
                /*"nickName": "STAY23",
                "videoId": 51832,
                "userId": 4650587,
                "headImgUrl": "http://img.rrmj.tv/head/20160304/o_1457093582404.jpg",
                "silver": 13,
                "sign": "这个人很懒，什么都没留下"*/

        private String nickName;
        private int videoId;
        private int userId;
        private String headImgUrl;
        private int silver;
        private String sign;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getVideoId() {
            return videoId;
        }

        public void setVideoId(int videoId) {
            this.videoId = videoId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }

        public int getSilver() {
            return silver;
        }

        public void setSilver(int silver) {
            this.silver = silver;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "nickName='" + nickName + '\'' +
                    ", videoId=" + videoId +
                    ", userId=" + userId +
                    ", headImgUrl='" + headImgUrl + '\'' +
                    ", silver=" + silver +
                    ", sign='" + sign + '\'' +
                    '}';
        }
    }
}
