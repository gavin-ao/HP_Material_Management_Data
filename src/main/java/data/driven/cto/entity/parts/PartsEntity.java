package data.driven.cto.entity.parts;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 配件信息表Entity
 * @author hejinkai
 * @date 2018/8/18
 */
public class PartsEntity {
    /** 主键 **/
    private String partsId;
    /** 配件分类外键 **/
    private String catgId;
    /** 配件名称 **/
    private String partsName;
    /** 简称 **/
    private String shortName;
    /** 型号 **/
    private String modelNumbers;
    /** 价格 **/
    private BigDecimal prices;
    /** 图片id **/
    private String pictureId;
    /** 排序 **/
    private Integer ord;
    /** 创建时间 **/
    private Date createAt;
    /** 创建人 **/
    private String creator;

    public String getPartsId() {
        return partsId;
    }

    public void setPartsId(String partsId) {
        this.partsId = partsId;
    }

    public String getCatgId() {
        return catgId;
    }

    public void setCatgId(String catgId) {
        this.catgId = catgId;
    }

    public String getPartsName() {
        return partsName;
    }

    public void setPartsName(String partsName) {
        this.partsName = partsName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getModelNumbers() {
        return modelNumbers;
    }

    public void setModelNumbers(String modelNumbers) {
        this.modelNumbers = modelNumbers;
    }

    public BigDecimal getPrices() {
        return prices;
    }

    public void setPrices(BigDecimal prices) {
        this.prices = prices;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
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
