package data.driven.cto.entity.cto;

/**
 * cto配件信息表
 * @author hejinkai
 * @date 2018/8/21
 */
public class CtoPartsEntity {
    /** 主键 **/
    private String ctoPartsId;
    /** cto产品主键 **/
    private String ctoProductId;
    /** 配件分类外键 **/
    private String catgId;
    /** 配件名称 **/
    private String partsName;
    /** 简称 **/
    private String shortName;
    /** 型号 **/
    private String modelNumbers;
    /** 价格 **/
    private Integer prices;
    /** 图片id **/
    private String pictureId;
    /** 排序 **/
    private Integer ord;

    public String getCtoPartsId() {
        return ctoPartsId;
    }

    public void setCtoPartsId(String ctoPartsId) {
        this.ctoPartsId = ctoPartsId;
    }

    public String getCtoProductId() {
        return ctoProductId;
    }

    public void setCtoProductId(String ctoProductId) {
        this.ctoProductId = ctoProductId;
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

    public Integer getPrices() {
        return prices;
    }

    public void setPrices(Integer prices) {
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
}
