package pdkj.zhaoshang.entity;

import java.util.List;

/**
 * 创建时间： 2018/2/9
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultDetailListData {

    /**
     * {
     "result":0,
     "message":"获取成功！",
     "data":[
     {
     "summary":"策划项目",
     "id":1,
     "title":"策划",
     "nodeState":1,
     "stageId":1
     },
     }
     */

    private String result;
    private String message;
    private List<NodeList> data;


    public class NodeList{
        private String summary;
        private String id;
        private String title;
        private String nodeState;
        private String stageId;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

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

        public String getNodeState() {
            return nodeState;
        }

        public void setNodeState(String nodeState) {
            this.nodeState = nodeState;
        }

        public String getStageId() {
            return stageId;
        }

        public void setStageId(String stageId) {
            this.stageId = stageId;
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

    public List<NodeList> getData() {
        return data;
    }

    public void setData(List<NodeList> data) {
        this.data = data;
    }
}
