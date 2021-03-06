package data.driven.cto.entity.cto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * cto产品信息表
 * @author hejinkai
 * @date 2018/8/21
 */
public class CtoProductEntity {
    /** 主键 **/
    private String ctoProductId;
    /** 原始产品id **/
    private String productId;
    /** 产品分类表主键 **/
    private String catgId;
    /** 分类层级码 **/
    private String catgCode;
    /** 产品名称 **/
    private String productName;
    /** 产品概述 **/
    private String productSummary;
    /** 产品规格 **/
    private String productSpec;
    /** 建议价格 **/
    private BigDecimal suggestPrices;
    /** 零售价格 **/
    private BigDecimal prices;
    /** 备注 **/
    private String remark;
    /** 图片id **/
    private String pictureId;
    /** 创建时间 **/
    private Date createAt;
    /** 创建人 **/
    private String creator;

    public String getCtoProductId() {
        return ctoProductId;
    }

    public void setCtoProductId(String ctoProductId) {
        this.ctoProductId = ctoProductId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCatgId() {
        return catgId;
    }

    public void setCatgId(String catgId) {
        this.catgId = catgId;
    }

    public String getCatgCode() {
        return catgCode;
    }

    public void setCatgCode(String catgCode) {
        this.catgCode = catgCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSummary() {
        return productSummary;
    }

    public void setProductSummary(String productSummary) {
        this.productSummary = productSummary;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public BigDecimal getSuggestPrices() {
        return suggestPrices;
    }

    public void setSuggestPrices(BigDecimal suggestPrices) {
        this.suggestPrices = suggestPrices;
    }

    public BigDecimal getPrices() {
        return prices;
    }

    public void setPrices(BigDecimal prices) {
        this.prices = prices;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
