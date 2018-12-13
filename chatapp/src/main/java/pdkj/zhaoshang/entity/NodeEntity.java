package pdkj.zhaoshang.entity;

/**
 * 创建时间： 2018/2/10
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class NodeEntity {

    private String projectName;
    private String projectID;

    private String projectJieDuan;

    private String NodeName;
    private String NodeID;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectJieDuan() {
        return projectJieDuan;
    }

    public void setProjectJieDuan(String projectJieDuan) {
        this.projectJieDuan = projectJieDuan;
    }

    public String getNodeName() {
        return NodeName;
    }

    public void setNodeName(String nodeName) {
        NodeName = nodeName;
    }

    public String getNodeID() {
        return NodeID;
    }

    public void setNodeID(String nodeID) {
        NodeID = nodeID;
    }
}
