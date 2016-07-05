package cn.jas0n.amovie.bean;

import java.io.Serializable;

/**
 * Author: Jas0n
 * Date: 2016/6/29
 * E-mail:chendong90x@gmail.com
 */
public class Login implements Serializable {

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
        return "Login{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public class Data implements Serializable{
        private User user;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "user=" + user +
                    '}';
        }
    }

    public class User implements Serializable{
        private String confirmInfo;
        private int articleCount;
        private String loginFrom;
        private int continuousDay;
        private boolean isBlack;
        private int silverCount;
        private int fansCount;
        private String headImgUrl;
        private boolean isConfirmed;
        private String nickName;
        private int score;
        private String roleInfo;
        private int replyCount;
        private String mobile;
        private int focusUserCount;
        private boolean focus;
        private String birthday;
        private int sex;
        private String city;
        private int favoriteCount;
        private boolean hasSignIn;
        private boolean isSilence;
        private int seriesCount;
        private int actorCount;
        private String levelStr;
        private String wmSign;
        private String token;
        private String sign;
        private int level;
        private int id;

        public String getConfirmInfo() {
            return confirmInfo;
        }

        public void setConfirmInfo(String confirmInfo) {
            this.confirmInfo = confirmInfo;
        }

        public int getArticleCount() {
            return articleCount;
        }

        public void setArticleCount(int articleCount) {
            this.articleCount = articleCount;
        }

        public String getLoginFrom() {
            return loginFrom;
        }

        public void setLoginFrom(String loginFrom) {
            this.loginFrom = loginFrom;
        }

        public int getContinuousDay() {
            return continuousDay;
        }

        public void setContinuousDay(int continuousDay) {
            this.continuousDay = continuousDay;
        }

        public boolean isBlack() {
            return isBlack;
        }

        public void setBlack(boolean black) {
            isBlack = black;
        }

        public int getSilverCount() {
            return silverCount;
        }

        public void setSilverCount(int silverCount) {
            this.silverCount = silverCount;
        }

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
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

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getRoleInfo() {
            return roleInfo;
        }

        public void setRoleInfo(String roleInfo) {
            this.roleInfo = roleInfo;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getFocusUserCount() {
            return focusUserCount;
        }

        public void setFocusUserCount(int focusUserCount) {
            this.focusUserCount = focusUserCount;
        }

        public boolean isFocus() {
            return focus;
        }

        public void setFocus(boolean focus) {
            this.focus = focus;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(int favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public boolean isHasSignIn() {
            return hasSignIn;
        }

        public void setHasSignIn(boolean hasSignIn) {
            this.hasSignIn = hasSignIn;
        }

        public boolean isSilence() {
            return isSilence;
        }

        public void setSilence(boolean silence) {
            isSilence = silence;
        }

        public int getSeriesCount() {
            return seriesCount;
        }

        public void setSeriesCount(int seriesCount) {
            this.seriesCount = seriesCount;
        }

        public int getActorCount() {
            return actorCount;
        }

        public void setActorCount(int actorCount) {
            this.actorCount = actorCount;
        }

        public String getLevelStr() {
            return levelStr;
        }

        public void setLevelStr(String levelStr) {
            this.levelStr = levelStr;
        }

        public String getWmSign() {
            return wmSign;
        }

        public void setWmSign(String wmSign) {
            this.wmSign = wmSign;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "User{" +
                    "confirmInfo='" + confirmInfo + '\'' +
                    ", articleCount=" + articleCount +
                    ", loginFrom='" + loginFrom + '\'' +
                    ", continuousDay=" + continuousDay +
                    ", isBlack=" + isBlack +
                    ", silverCount=" + silverCount +
                    ", fansCount=" + fansCount +
                    ", headImgUrl='" + headImgUrl + '\'' +
                    ", isConfirmed=" + isConfirmed +
                    ", nickName='" + nickName + '\'' +
                    ", score=" + score +
                    ", roleInfo='" + roleInfo + '\'' +
                    ", replyCount=" + replyCount +
                    ", mobile='" + mobile + '\'' +
                    ", focusUserCount=" + focusUserCount +
                    ", focus=" + focus +
                    ", birthday='" + birthday + '\'' +
                    ", sex=" + sex +
                    ", city='" + city + '\'' +
                    ", favoriteCount=" + favoriteCount +
                    ", hasSignIn=" + hasSignIn +
                    ", isSilence=" + isSilence +
                    ", seriesCount=" + seriesCount +
                    ", actorCount=" + actorCount +
                    ", levelStr='" + levelStr + '\'' +
                    ", wmSign='" + wmSign + '\'' +
                    ", token='" + token + '\'' +
                    ", sign='" + sign + '\'' +
                    ", level=" + level +
                    ", id=" + id +
                    '}';
        }
    }
}
