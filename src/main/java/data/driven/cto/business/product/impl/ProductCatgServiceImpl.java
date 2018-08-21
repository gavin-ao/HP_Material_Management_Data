package data.driven.cto.business.product.impl;

import data.driven.cto.business.product.ProductCatgService;
import data.driven.cto.dao.JDBCBaseDao;
import data.driven.cto.vo.product.ProductCatgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 产品分类表Entity
 * @author hejinkai
 * @date 2018/8/18
 */
@Service
public class ProductCatgServiceImpl implements ProductCatgService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public List<ProductCatgVO> findProDuctCatgList() {
        String sql = "select catg_id,catg_name,catg_code,catg_level,ord from product_catg_info order by catg_level,ord,catg_id";
        List<ProductCatgVO> list = jdbcBaseDao.queryList(ProductCatgVO.class, sql);
        if(list != null && list.size() > 0){
            return dealCatgOrdList(list);
        }
        return null;
    }

    /**
     * 根据结果填充子集
     * @param list
     * @return
     */
    public List<ProductCatgVO> dealCatgOrdList(List<ProductCatgVO> list){
        //根据code分组进行分组。
        Map<String, List<ProductCatgVO>> groupMap = list.stream().collect(Collectors.groupingBy(o -> {
            if (o.getCatgLevel().intValue() == 1){
                return "0";
            }else{
                int codeLength = o.getCatgCode().length();
                return o.getCatgCode().substring(0, codeLength - 3);
            }
        }));
        List<ProductCatgVO> resultList = groupMap.get("0");
        if(resultList != null && resultList.size() > 0){
            dealCatgOrdList(resultList, groupMap);
        }
        return resultList;
    }


    /**
     * 对分组后的结果进行处理，填充子集返回
     * @param resultList
     * @param groupMap
     */
    private void dealCatgOrdList(List<ProductCatgVO> resultList, Map<String, List<ProductCatgVO>> groupMap){
        for(ProductCatgVO catg : resultList){
            List<ProductCatgVO> childList = groupMap.get(catg.getCatgCode());
            if(childList != null && childList.size() > 0){
                dealCatgOrdList(childList, groupMap);
                catg.setChildList(childList);
            }
        }
    }

}
