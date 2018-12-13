package pdkj.zhaoshang.entity;

import java.util.List;

/**
 * 创建时间： 2018/2/7
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultRecordData {

    private String result;
    private String message;
    private List<Record> data;


    public class Record{

        private String id;
        private String addTime;
        private String modifyTime;
        private String state;
        private String isDelete;
        private String model;
        private String modelName;
        private String operationContent;
        private String success;
        private String resultContent;
        private String operator;
        private String user;
        private String show4App;


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

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
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

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getOperationContent() {
            return operationContent;
        }

        public void setOperationContent(String operationContent) {
            this.operationContent = operationContent;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getResultContent() {
            return resultContent;
        }

        public void setResultContent(String resultContent) {
            this.resultContent = resultContent;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getShow4App() {
            return show4App;
        }

        public void setShow4App(String show4App) {
            this.show4App = show4App;
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

    public List<Record> getData() {
        return data;
    }

    public void setData(List<Record> data) {
        this.data = data;
    }
}
