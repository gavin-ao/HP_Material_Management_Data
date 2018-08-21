package data.driven.cto.business.product.impl;

import data.driven.cto.business.product.ProductSupportPartsService;
import data.driven.cto.dao.JDBCBaseDao;
import data.driven.cto.entity.parts.PartsCatgEntity;
import data.driven.cto.vo.product.SupportPartsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品支持的配件Service
 * @author hejinkai
 * @date 2018/8/18
 */
@Service
public class ProductSupportPartsServiceImpl implements ProductSupportPartsService {
    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public List<SupportPartsVO> findPartsByProductId(String productId) {
        String sql = "select parts.parts_id,parts.catg_id,parts.parts_name,parts.short_name,parts.model_numbers,parts.prices,psp.standard from product_support_parts psp" +
                " left join parts_info parts on psp.parts_id = parts.parts_id where psp.product_id = ? order by parts.ord,parts.parts_id";
        List<SupportPartsVO> list = jdbcBaseDao.queryList(SupportPartsVO.class, sql, productId);
        return list;
    }

    @Override
    public List<SupportPartsVO> findStandardPartsByProductId(String productId) {
        String sql = "select parts.parts_id,parts.catg_id,parts.parts_name,parts.short_name,parts.model_numbers,parts.prices,psp.standard from product_support_parts psp" +
                " left join parts_info parts on psp.parts_id = parts.parts_id where psp.product_id = ? and psp.standard = 1 order by parts.ord,parts.parts_id";
        List<SupportPartsVO> list = jdbcBaseDao.queryList(SupportPartsVO.class, sql, productId);
        return list;
    }

    @Override
    public List<PartsCatgEntity> findPartsCatgList(String productId) {
        String sql = "select distinct pc.catg_id,pc.catg_name,pc.catg_code,pc.ord from product_support_parts psp" +
                " left join parts_catg_info pc on psp.parts_catg_id = pc.catg_id where psp.product_id = ? order by pc.ord,pc.catg_id";
        List<PartsCatgEntity> list = jdbcBaseDao.queryList(PartsCatgEntity.class, sql, productId);
        return list;
    }
}
