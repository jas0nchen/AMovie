package cn.jas0n.amovie.bean;

import java.io.Serializable;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class Author implements Serializable {

    /*{
            "headImgUrl": "http://img.rrmj.tv/head/20160423/o_1461384700136.png",
            "isConfirmed": true,
            "nickName": "呼默默",
            "roleInfo": "admin",
            "level": "8",
            "id": 4143485
    }*/

    private String headImgUrl;
    private boolean isConfirmed;
    private String nickName;
    private String roleInfo;
    private String level;
    private int id;

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

    public String getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(String roleInfo) {
        this.roleInfo = roleInfo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
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
        return "Author{" +
                "headImgUrl='" + headImgUrl + '\'' +
                ", isConfirmed=" + isConfirmed +
                ", nickName='" + nickName + '\'' +
                ", roleInfo='" + roleInfo + '\'' +
                ", level='" + level + '\'' +
                ", id=" + id +
                '}';
    }
}
