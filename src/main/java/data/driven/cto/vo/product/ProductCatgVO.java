package data.driven.cto.vo.product;

import data.driven.cto.entity.product.ProductCatgEntity;

import java.util.List;

/**
 * 产品分类vo
 * @author hejinkai
 * @date 2018/8/19
 */
public class ProductCatgVO extends ProductCatgEntity{

    /** 子集 **/
    private List<ProductCatgVO> childList;

    public List<ProductCatgVO> getChildList() {
        return childList;
    }

    public void setChildList(List<ProductCatgVO> childList) {
        this.childList = childList;
    }
}
