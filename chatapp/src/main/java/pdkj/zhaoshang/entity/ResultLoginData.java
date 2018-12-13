package pdkj.zhaoshang.entity;

/**
 * 创建时间： 2018/2/5
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultLoginData {

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

        private String loginName;
        private String password;

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
