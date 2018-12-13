package pdkj.zhaoshang.entity;

import java.util.List;

/**
 * 创建时间： 2018/2/9
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultEventData {

    private String result;
    private String message;
    private List<Event> data;


    public static class Event{
        private String images;
        private String attachment;
        private String happenDate;
        private String id;
        private String title;
        private String nodeId;
        private String categoryId;
        private String content;
        private String stageId;

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

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

    public List<Event> getData() {
        return data;
    }

    public void setData(List<Event> data) {
        this.data = data;
    }
}
