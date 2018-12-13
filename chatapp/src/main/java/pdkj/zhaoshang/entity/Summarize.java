package pdkj.zhaoshang.entity;

import java.util.List;

/**
 * 创建时间： 2018/2/7
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class Summarize {

    private String result;
    private String message;
    private List<SummarizeData> data;


    public class SummarizeData{
        private String addTime;
        private String id;
        private String addUser;
        private String content;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddUser() {
            return addUser;
        }

        public void setAddUser(String addUser) {
            this.addUser = addUser;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SummarizeData> getData() {
        return data;
    }

    public void setData(List<SummarizeData> data) {
        this.data = data;
    }
}
