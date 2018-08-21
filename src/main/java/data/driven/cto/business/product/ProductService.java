package data.driven.cto.business.product;

import data.driven.cto.vo.product.ProductVO;

import java.util.List;

/**
 * @author hejinkai
 * @date 2018/8/18
 */
public interface ProductService {

    /**
     * 根据分类查询
     * @param catgCode
     * @return
     */
    public List<ProductVO> findProductByCatgCode(String catgCode);

    /**
     * 根据id获取产品信息
     * @param productId
     * @return
     */
    public ProductVO getProductById(String productId);

}
