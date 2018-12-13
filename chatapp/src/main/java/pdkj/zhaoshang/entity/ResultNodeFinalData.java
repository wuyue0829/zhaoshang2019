package pdkj.zhaoshang.entity;

/**
 * 创建时间： 2018/2/10
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultNodeFinalData {

    private String result;
    private String message;
    private NodeFinal data;

    public class NodeFinal{
        private String id;
        private String addTime;
        private String addUser;
        private String modifyTime;
        private String modifyUser;
        private String state;
        private String isDelete;
        private String title;
        private String categoryId;
        private String stageId;
        private String nodeId;
        private String content;
        private String images;
        private String attachment;
        private String happenDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getAddUser() {
            return addUser;
        }

        public void setAddUser(String addUser) {
            this.addUser = addUser;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getModifyUser() {
            return modifyUser;
        }

        public void setModifyUser(String modifyUser) {
            this.modifyUser = modifyUser;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getStageId() {
            return stageId;
        }

        public void setStageId(String stageId) {
            this.stageId = stageId;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getHappenDate() {
            return happenDate;
        }

        public void setHappenDate(String happenDate) {
            this.happenDate = happenDate;
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

    public NodeFinal getData() {
        return data;
    }

    public void setData(NodeFinal data) {
        this.data = data;
    }
}
