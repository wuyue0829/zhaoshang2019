package pdkj.zhaoshang.entity;

/**
 * 创建时间： 2018/2/9
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultCategoryData {


    private String result;
    private String message;
    private Category data;


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

    public Category getData() {
        return data;
    }

    public void setData(Category data) {
        this.data = data;
    }
}
