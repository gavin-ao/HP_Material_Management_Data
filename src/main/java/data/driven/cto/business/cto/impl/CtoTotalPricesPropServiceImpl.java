package data.driven.cto.business.cto.impl;

import data.driven.cto.business.cto.CtoTotalPricesPropService;
import data.driven.cto.dao.JDBCBaseDao;
import data.driven.cto.entity.cto.CtoTotalPricesPropEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hejinkai
 * @date 2018/8/30
 */
@Service
public class CtoTotalPricesPropServiceImpl implements CtoTotalPricesPropService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public List<CtoTotalPricesPropEntity> findCtoTotalPricesPropList() {
        String sql = "select show_name,max_num,min_num from cto_total_prices_prop";
        List<CtoTotalPricesPropEntity> list = jdbcBaseDao.queryList(CtoTotalPricesPropEntity.class, sql);
        return list;
    }
}
