package pdkj.zhaoshang.entity;

import java.util.List;

/**
 * 创建时间： 2018/2/5
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultNewProjectData {

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
    private ProjectData data;

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

    public ProjectData getData() {
        return data;
    }

    public void setData(ProjectData data) {
        this.data = data;
    }


    public class ProjectData{
        private String id;
        private String addTime;
        private String addUser;
        private String modifyTime;
        private String modifyUser;
        private String state;
        private String isDelete;
        private String title;
        private String header;
        private String members;
        private String industry;
        private String trade;
        private String startDate;
        private String customer;
        private String type;
        private String nodeId;
        private String stageId;
        private String currentStage;
        private String summary;
        private String state1;
        private String state2;
        private String authUsers;
        private String area;
        private String investType;

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

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getMembers() {
            return members;
        }

        public void setMembers(String members) {
            this.members = members;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getTrade() {
            return trade;
        }

        public void setTrade(String trade) {
            this.trade = trade;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
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

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getStageId() {
            return stageId;
        }

        public void setStageId(String stageId) {
            this.stageId = stageId;
        }

        public String getCurrentStage() {
            return currentStage;
        }

        public void setCurrentStage(String currentStage) {
            this.currentStage = currentStage;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getState1() {
            return state1;
        }

        public void setState1(String state1) {
            this.state1 = state1;
        }

        public String getState2() {
            return state2;
        }

        public void setState2(String state2) {
            this.state2 = state2;
        }

        public String getAuthUsers() {
            return authUsers;
        }

        public void setAuthUsers(String authUsers) {
            this.authUsers = authUsers;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getInvestType() {
            return investType;
        }

        public void setInvestType(String investType) {
            this.investType = investType;
        }
    }
}
