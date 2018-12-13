package pdkj.zhaoshang.entity;

import java.util.List;

/**
 * Created by wuyue on 2018/5/25.
 */

public class ResultMyProjectData {
    private String result;
    private String message;
    private Data data;


    public class Data{
        private List<ProjectData> scategorys;
        private List<ProjectData> mcategorys;
        private List<ProjectData> hcategorys;

        public List<ProjectData> getScategorys() {
            return scategorys;
        }

        public void setScategorys(List<ProjectData> scategorys) {
            this.scategorys = scategorys;
        }

        public List<ProjectData> getMcategorys() {
            return mcategorys;
        }

        public void setMcategorys(List<ProjectData> mcategorys) {
            this.mcategorys = mcategorys;
        }

        public List<ProjectData> getHcategorys() {
            return hcategorys;
        }

        public void setHcategorys(List<ProjectData> hcategorys) {
            this.hcategorys = hcategorys;
        }
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
