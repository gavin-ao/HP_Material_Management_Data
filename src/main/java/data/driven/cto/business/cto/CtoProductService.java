package data.driven.cto.business.cto;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;

/**
 * cto产品信息Service
 * @author hejinkai
 * @date 2018/8/21
 */
public interface CtoProductService {

    /**
     * 新增cto信息
     * @param productId
     * @param preCtoId
     * @param partsIds
     * @param creator
     * @param prices
     * @return
     */
    public JSONObject addCtoProduct(String productId, String preCtoId, String partsIds, String creator, BigDecimal prices);


}
