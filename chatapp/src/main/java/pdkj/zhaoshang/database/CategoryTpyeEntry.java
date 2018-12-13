package pdkj.zhaoshang.database;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 创建时间： 2018/2/6
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */

@Table(name = "categoryTpyeEntry")
public class CategoryTpyeEntry {

    @Column(name = "id", isId = true, autoGen = false)
    public int id;
    @Column(name = "name")
    public String name;
    @Column(name = "model")
    public int model;
    @Column(name = "parentId")
    public int parentId;

    @Override
    public String toString() {
        return "person [id=" + id + ", name=" + name + ", model=" + model
                + ", parentId=" + parentId + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
