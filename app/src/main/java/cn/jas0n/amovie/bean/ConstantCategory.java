package cn.jas0n.amovie.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class ConstantCategory implements Serializable {

    /*{
        "code":"0000",
        "msg":"",
        "data":{
            "category": [{
                "id":1,
                "name":"电影"
            },{
                "id":2,
                "name":"原创"
            },{
                "id":3,
                "name":"英剧"
            }]
        }
    }*/

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

    public class Data{
        private List<Category> category;

        public List<Category> getCategory() {
            return category;
        }

        public void setCategory(List<Category> category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "category=" + category +
                    '}';
        }
    }

    class Category{
        private int id;
        private String name;

        @Override
        public String toString() {
            return "Category{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ConstantCategory{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
