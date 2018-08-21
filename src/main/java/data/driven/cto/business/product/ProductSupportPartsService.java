package data.driven.cto.business.product;

import data.driven.cto.entity.parts.PartsCatgEntity;
import data.driven.cto.vo.product.SupportPartsVO;

import java.util.List;

/**
 * 产品支持的配件Service
 * @author hejinkai
 * @date 2018/8/18
 */
public interface ProductSupportPartsService {

    /**
     * 根据产品id获取支持该产品的配件信息列表
     * @param productId
     * @return
     */
    public List<SupportPartsVO> findPartsByProductId(String productId);

    /**
     * 根据产品id获取支持该产品的配件的标配信息列表
     * @param productId
     * @return
     */
    public List<SupportPartsVO> findStandardPartsByProductId(String productId);

    /**
     * 根据产品id获取支持该产品的配件的分类信息
     * @param productId
     * @return
     */
    public List<PartsCatgEntity> findPartsCatgList(String productId);

}
