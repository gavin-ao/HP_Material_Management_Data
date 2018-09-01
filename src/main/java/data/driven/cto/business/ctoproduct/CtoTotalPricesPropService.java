package data.driven.cto.business.ctoproduct;

import data.driven.cto.entity.cto.CtoTotalPricesPropEntity;

import java.util.List;

/**
 * 价格区间统计配置表Service
 * @author hejinkai
 * @date 2018/8/30
 */
public interface CtoTotalPricesPropService {

    /**
     * 查找统计价格区间的配置
     * @return
     */
    public List<CtoTotalPricesPropEntity> findCtoTotalPricesPropList();

}
