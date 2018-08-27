package data.driven.cto.business.product.impl;

import data.driven.cto.business.product.ProductService;
import data.driven.cto.dao.JDBCBaseDao;
import data.driven.cto.entity.product.ProductEntity;
import data.driven.cto.vo.product.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hejinkai
 * @date 2018/8/18
 */
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public List<ProductVO> findProductByCatgCode(String catgCode) {
        String sql = "select prod.product_id,prod.product_name,prod.suggest_prices,prod.prices,prod.support_cto,sp.file_path from product_info prod" +
                " left join sys_picture sp on prod.picture_id = sp.picture_id" +
                " where prod.catg_code like ? order by prod.ord,prod.catg_code,prod.product_id";
        List<ProductVO> list = jdbcBaseDao.queryList(ProductVO.class, sql, catgCode + "%");
        return list;
    }

    @Override
    public ProductVO getProductById(String productId) {
        String sql = "select prod.product_id,prod.product_name,prod.suggest_prices,prod.prices,prod.support_cto,prod.product_summary,prod.product_spec,prod.remark,sp.file_path from product_info prod" +
                " left join sys_picture sp on prod.picture_id = sp.picture_id" +
                " where prod.product_id = ?";
        List<ProductVO> list = jdbcBaseDao.queryList(ProductVO.class, sql, productId);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public ProductEntity getProductByIdAll(String productId) {
        String sql = "select prod.product_id,prod.catg_id,prod.catg_code,prod.product_name,prod.product_summary,prod.product_spec,prod.suggest_prices,prod.prices,prod.support_cto,prod.remark,prod.picture_id from product_info prod where prod.product_id = ?";
        List<ProductEntity> list = jdbcBaseDao.queryList(ProductEntity.class, sql, productId);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}
