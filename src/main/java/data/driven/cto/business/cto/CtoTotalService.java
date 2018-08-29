package data.driven.cto.business.cto;

import com.alibaba.fastjson.JSONObject;

/**
 * cto统计service
 * @author hejinkai
 * @date 2018/8/29
 */
public interface CtoTotalService {

    /**
     * 根据产品分类统计环状图
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalCtoCatgView(String startDate, String endDate);

    /**
     * 统计最受欢迎机型 top10 - 条形图
     * @param startDate
     * @param endDate
     * @param topNum 默认为10
     * @return
     */
    public JSONObject totalHotTopTenView(String startDate, String endDate, Integer topNum);

    /**
     * 高频定制需求分析 - 根据产品名称，统计变更的配件信息 - 柱状图
     * @param startDate
     * @param endDate
     * @param productName 产品名称
     * @return
     */
    public JSONObject totalChangePartsView(String startDate, String endDate, String productName);

    /**
     * 最受欢迎价格区间——雷达图
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalHotPricesView(String startDate, String endDate);

}
