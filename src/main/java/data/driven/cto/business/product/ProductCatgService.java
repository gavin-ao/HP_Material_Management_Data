package data.driven.cto.business.product;

import data.driven.cto.vo.product.ProductCatgVO;

import java.util.List;

/**
 * 产品分类表Entity
 * @author hejinkai
 * @date 2018/8/18
 */
public interface ProductCatgService {

    /**
     * 查找所有的产品分类
     * @return
     */
    public List<ProductCatgVO> findProDuctCatgList();

}
