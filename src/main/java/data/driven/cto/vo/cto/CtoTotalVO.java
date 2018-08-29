package data.driven.cto.vo.cto;

/**
 * cto统计工具类
 *
 * @author hejinkai
 * @date 2018/8/30
 */
public class CtoTotalVO {
    /** 合计 **/
    private Long totalNum;
    /** 分类层次码 **/
    private String catgCode;
    /** 分类名称 **/
    private String catgName;
    /** 产品名称 **/
    private String productName;
    /** 统计时展示名称 **/
    private String showText;

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public String getCatgCode() {
        return catgCode;
    }

    public void setCatgCode(String catgCode) {
        this.catgCode = catgCode;
    }

    public String getCatgName() {
        return catgName;
    }

    public void setCatgName(String catgName) {
        this.catgName = catgName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShowText() {
        return showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }
}
