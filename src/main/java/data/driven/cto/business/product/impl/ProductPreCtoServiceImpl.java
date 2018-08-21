package data.driven.cto.business.product.impl;

import data.driven.cto.business.product.ProductPreCtoService;
import data.driven.cto.dao.JDBCBaseDao;
import data.driven.cto.entity.parts.PartsEntity;
import data.driven.cto.entity.product.ProductPreCtoEntity;
import data.driven.cto.vo.product.PrePartsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品预配置表
 * @author hejinkai
 * @date 2018/8/18
 */
@Service
public class ProductPreCtoServiceImpl implements ProductPreCtoService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public List<ProductPreCtoEntity> findProductPreCtoListByProductId(String productId) {
        String sql = "select pre_cto_id,pre_name from product_pre_cto_info where product_id = ? order by ord,pre_cto_id";
        List<ProductPreCtoEntity> list = jdbcBaseDao.queryList(ProductPreCtoEntity.class, sql, productId);
        return list;
    }

    @Override
    public List<PartsEntity> findPartsListByPreCtoId(String preCtoId) {
        String sql = "select parts.parts_id,parts.catg_id,parts.parts_name,parts.short_name,parts.model_numbers,parts.prices from product_pre_cto_parts ppcp" +
                " left join parts_info parts on ppcp.parts_id = parts.parts_id where ppcp.pre_cto_id = ? order by parts.ord,parts.parts_id";
        List<PartsEntity> list = jdbcBaseDao.queryList(PartsEntity.class, sql, preCtoId);
        return list;
    }
    @Override
    public List<PrePartsVO> findPartsListByProductId(String productId) {
        String sql = "select pre.pre_cto_id,parts.parts_id,parts.catg_id,parts.parts_name,parts.short_name,parts.model_numbers,parts.prices from product_pre_cto_parts ppcp" +
                " left join product_pre_cto_info pre on ppcp.pre_cto_id = pre.pre_cto_id" +
                " left join parts_info parts on ppcp.parts_id = parts.parts_id" +
                " where pre.product_id = ? order by parts.catg_id,parts.ord,parts.parts_id";
        List<PrePartsVO> list = jdbcBaseDao.queryList(PrePartsVO.class, sql, productId);
        return list;
    }
}
