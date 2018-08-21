package data.driven.cto.entity.product;

/**
 * 产品预配置表
 * @author hejinkai
 * @date 2018/8/18
 */
public class ProductPreCtoEntity {
    /** 主键 **/
    private String preCtoId;
    /** 产品id **/
    private String productId;
    /** 预配置的名称 **/
    private String preName;
    /** 排序 **/
    private String ord;
    /** 创建时间 **/
    private String createAt;
    /** 创建人 **/
    private String creator;

    public String getPreCtoId() {
        return preCtoId;
    }

    public void setPreCtoId(String preCtoId) {
        this.preCtoId = preCtoId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPreName() {
        return preName;
    }

    public void setPreName(String preName) {
        this.preName = preName;
    }

    public String getOrd() {
        return ord;
    }

    public void setOrd(String ord) {
        this.ord = ord;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
