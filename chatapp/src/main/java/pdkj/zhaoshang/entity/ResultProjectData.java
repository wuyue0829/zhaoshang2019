package pdkj.zhaoshang.entity;

import java.util.List;

/**
 * 创建时间： 2018/2/5
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultProjectData {

    /**
     * {
     "result":0,
     "message":"获取成功！",
     "data":[
     {
     "eventTitle":"大事发生的",
     "trade":61,
     "categoryTitle":"121231",
     "header":2,
     "id":1,
     "startDate":1514736000000,
     "stageId":2,
     "customer":"12312"
     },
     {
     "eventTitle":"3",
     "trade":61,
     "categoryTitle":"123",
     "header":2,
     "id":2,
     "startDate":1514736000000,
     "stageId":0,
     "customer":"2231"
     }
     ]
     }
     */

    private String result;
    private String message;
    private List<ProjectData> data;

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

    public List<ProjectData> getData() {
        return data;
    }

    public void setData(List<ProjectData> data) {
        this.data = data;
    }


    public class ProjectData{

        private String eventTitle;
        private String trade;
        private String title;
        private String header;
        private String id;
        private String startDate;
        private String stageId;
        private String customer;
        private String type;

        public String getEventTitle() {
            return eventTitle;
        }

        public void setEventTitle(String eventTitle) {
            this.eventTitle = eventTitle;
        }

        public String getTrade() {
            return trade;
        }

        public void setTrade(String trade) {
            this.trade = trade;
        }

        public String getCategoryTitle() {
            return title;
        }

        public void setCategoryTitle(String title) {
            this.title = title;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getStageId() {
            return stageId;
        }

        public void setStageId(String stageId) {
            this.stageId = stageId;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
