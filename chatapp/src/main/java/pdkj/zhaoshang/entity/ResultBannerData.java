package pdkj.zhaoshang.entity;

import java.util.List;

/**
 * 创建时间： 2018/2/7
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultBannerData {

    private String result;
    private String message;
    private List<BannerList> data;

    public class BannerList{

        private String icon;
        private String title;
        private String link;
        private String remark;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

    public List<BannerList> getData() {
        return data;
    }

    public void setData(List<BannerList> data) {
        this.data = data;
    }
}
