package data.driven.cto.business.product;

import data.driven.cto.entity.product.ProductEntity;
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
     * 根据id获取产品信息 - 部分信息，带图片地址url
     * @param productId
     * @return
     */
    public ProductVO getProductById(String productId);

    /**
     * 根据id获取产品信息 - 全面信息，不带图片地址
     * @param productId
     * @return
     */
    public ProductEntity getProductByIdAll(String productId);

}
