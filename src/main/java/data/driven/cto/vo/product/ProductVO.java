package data.driven.cto.vo.product;

import data.driven.cto.entity.product.ProductEntity;

/**
 * 产品信息表VO
 * @author hejinkai
 * @date 2018/8/18
 */
public class ProductVO extends ProductEntity{

    /** 图片路径 **/
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
