package pdkj.zhaoshang.entity;

/**
 * 创建时间： 2018/2/5
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultLibAppData {

    /**
     * {
     "result": 0,
     "message": "登录成功！",
     "data": {
     "password": "1517648312955",
     "loginName": "15389209832"
     }
     }
     */

    private String result;
    private String message;
    private DataMessage data;

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

    public DataMessage getData() {
        return data;
    }

    public void setData(DataMessage data) {
        this.data = data;
    }

    public class DataMessage{

        private String type;
        private String uploadUri ;
        private String increment ;
        private String code ;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUploadUri() {
            return uploadUri;
        }

        public void setUploadUri(String uploadUri) {
            this.uploadUri = uploadUri;
        }

        public String getIncrement() {
            return increment;
        }

        public void setIncrement(String increment) {
            this.increment = increment;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
