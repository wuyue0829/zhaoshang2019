package pdkj.zhaoshang.entity;

import java.util.List;

import pdkj.zhaoshang.database.CategoryTpyeEntry;

/**
 * 创建时间： 2018/2/6
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultCategoryTpyeData {
    private String result;
    private String message;
    private List<CategoryTpyeEntry> data;

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

    public List<CategoryTpyeEntry> getData() {
        return data;
    }

    public void setData(List<CategoryTpyeEntry> data) {
        this.data = data;
    }
}
