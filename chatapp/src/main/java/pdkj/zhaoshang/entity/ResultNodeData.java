package pdkj.zhaoshang.entity;

import java.util.List;

/**
 * 创建时间： 2018/2/7
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultNodeData {

    private String result;
    private String message;
    private List<NodeData> data;

    public class NodeData{
        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

    public List<NodeData> getData() {
        return data;
    }

    public void setData(List<NodeData> data) {
        this.data = data;
    }
}
