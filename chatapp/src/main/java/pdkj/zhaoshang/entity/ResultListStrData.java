package pdkj.zhaoshang.entity;

import java.util.List;

/**
 * 创建时间： 2018/2/5
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultListStrData {


    private String result;
    private String message;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
