package pdkj.zhaoshang.entity;

import java.util.List;

/**
 * 创建时间： 2018/2/7
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultMatterListData {

    private String result;
    private String message;
    private Matter data;

    public class Matter{
        private String pageNum;
        private String pageSize;

        private List<MatterEntye> pageData;


        public class MatterEntye{
            private String id;
            private String addTime;
            private String addUser;
            private String modifyTime;
            private String modifyUser;
            private String state;
            private String isDelete;
            private String user;
            private String plannedTime;
            private String itemName;
            private String itemContent;
            private String categoryName;

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

            public String getUser() {
                return user;
            }

            public void setUser(String user) {
                this.user = user;
            }

            public String getPlannedTime() {
                return plannedTime;
            }

            public void setPlannedTime(String plannedTime) {
                this.plannedTime = plannedTime;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getItemContent() {
                return itemContent;
            }

            public void setItemContent(String itemContent) {
                this.itemContent = itemContent;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }
        }


        public String getPageNum() {
            return pageNum;
        }

        public void setPageNum(String pageNum) {
            this.pageNum = pageNum;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public List<MatterEntye> getPageData() {
            return pageData;
        }

        public void setPageData(List<MatterEntye> pageData) {
            this.pageData = pageData;
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

    public Matter getData() {
        return data;
    }

    public void setData(Matter data) {
        this.data = data;
    }
}
