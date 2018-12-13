package pdkj.zhaoshang.entity;

/**
 * 创建时间： 2018/2/11
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ResultEvaluateData {

    private String result;
    private String message;
    private EvaluateEnty data;


    public class EvaluateEnty{

        private String id;
        private String categoryId;
        private String year;
        private String totalAmount;
        private String investment;
        private String landArea;
        private String increase;
        private String rank;
        private String energyLand;
        private String energyTotal;
        private String jobLand;
        private String jobTotal;
        private String profitLand;
        private String profitTotal;
        private String outPutLand;
        private String outPutTotal;
        private String taxesLand;
        private String taxesTotal;
        private String score;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getInvestment() {
            return investment;
        }

        public void setInvestment(String investment) {
            this.investment = investment;
        }

        public String getLandArea() {
            return landArea;
        }

        public void setLandArea(String landArea) {
            this.landArea = landArea;
        }

        public String getIncrease() {
            return increase;
        }

        public void setIncrease(String increase) {
            this.increase = increase;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getEnergyLand() {
            return energyLand;
        }

        public void setEnergyLand(String energyLand) {
            this.energyLand = energyLand;
        }

        public String getEnergyTotal() {
            return energyTotal;
        }

        public void setEnergyTotal(String energyTotal) {
            this.energyTotal = energyTotal;
        }

        public String getJobLand() {
            return jobLand;
        }

        public void setJobLand(String jobLand) {
            this.jobLand = jobLand;
        }

        public String getJobTotal() {
            return jobTotal;
        }

        public void setJobTotal(String jobTotal) {
            this.jobTotal = jobTotal;
        }

        public String getProfitLand() {
            return profitLand;
        }

        public void setProfitLand(String profitLand) {
            this.profitLand = profitLand;
        }

        public String getProfitTotal() {
            return profitTotal;
        }

        public void setProfitTotal(String profitTotal) {
            this.profitTotal = profitTotal;
        }

        public String getOutPutLand() {
            return outPutLand;
        }

        public void setOutPutLand(String outPutLand) {
            this.outPutLand = outPutLand;
        }

        public String getOutPutTotal() {
            return outPutTotal;
        }

        public void setOutPutTotal(String outPutTotal) {
            this.outPutTotal = outPutTotal;
        }

        public String getTaxesLand() {
            return taxesLand;
        }

        public void setTaxesLand(String taxesLand) {
            this.taxesLand = taxesLand;
        }

        public String getTaxesTotal() {
            return taxesTotal;
        }

        public void setTaxesTotal(String taxesTotal) {
            this.taxesTotal = taxesTotal;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
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

    public EvaluateEnty getData() {
        return data;
    }

    public void setData(EvaluateEnty data) {
        this.data = data;
    }
}
