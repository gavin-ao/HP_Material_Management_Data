package data.driven.cto.business.ctoproduct.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import data.driven.cto.business.ctoproduct.CtoTotalPricesPropService;
import data.driven.cto.business.ctoproduct.CtoTotalService;
import data.driven.cto.dao.JDBCBaseDao;
import data.driven.cto.entity.cto.CtoTotalPricesPropEntity;
import data.driven.cto.util.DateFormatUtil;
import data.driven.cto.util.JSONUtil;
import data.driven.cto.vo.cto.CtoTotalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hejinkai
 * @date 2018/8/29
 */
@Service
public class CtoTotalServiceImpl implements CtoTotalService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;
    @Autowired
    private CtoTotalPricesPropService ctoTotalPricesPropService;

    @Override
    public JSONObject totalCtoCatgView(String startDate, String endDate) {
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "101", "时间获取失败，请检查时间格式");
        }
        String totalSql = "select count(ctop.cto_product_id) as total_num,pcatg.catg_code,pcatg.catg_name from cto_product_info ctop" +
                " left join product_catg_info pcatg on ctop.catg_id = pcatg.catg_id" +
                " where ctop.create_at between ? and ? and pcatg.catg_code is not null group by ctop.catg_id";
        List<CtoTotalVO> totalList = jdbcBaseDao.queryList(CtoTotalVO.class, totalSql, start, end);
        if(totalList == null || totalList.size() < 1){
            return JSONUtil.putMsg(false, "102", "统计数据为空");
        }
        Map<String, List<CtoTotalVO>> catgMap = totalList.stream().collect(Collectors.groupingBy(o -> o.getCatgCode().substring(0, 3)));
        StringBuilder whereSql = new StringBuilder();
        List<Object> paramList = new ArrayList<Object>(catgMap.keySet());
        for(Object obj : paramList){
            whereSql.append(",?");
        }
        whereSql.delete(0, 1);
        String parentSql = "select pcatg.catg_code,pcatg.catg_name from product_catg_info pcatg where pcatg.catg_code in (" + whereSql + ")";
        List<CtoTotalVO> parentList = jdbcBaseDao.queryListWithListParam(CtoTotalVO.class, parentSql, paramList);
        List<JSONObject> dataList = new ArrayList<JSONObject>(parentList.size());
        for(CtoTotalVO parent : parentList){
            JSONObject data = new JSONObject();
            data.put("name", parent.getCatgName());
            Long count = 0L;
            List<CtoTotalVO> childCtoList = catgMap.get(parent.getCatgCode());
            List<JSONObject> childJsonList = new ArrayList<JSONObject>(childCtoList.size());
            for(CtoTotalVO childCto : childCtoList){
                JSONObject childJson = new JSONObject();
                childJson.put("name", childCto.getCatgName());
                childJson.put("number", childCto.getTotalNum());
                count += childCto.getTotalNum();
                childJsonList.add(childJson);
            }
            data.put("number", count);
            data.put("child", childJsonList);
            dataList.add(data);
        }
        JSONObject result = new JSONObject();
        result.put("data", dataList);
        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject totalHotTopTenView(String startDate, String endDate, Integer topNum) {
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "101", "时间获取失败，请检查时间格式");
        }
        if(topNum == null || topNum.intValue() < 1){
            topNum = 10;
        }
        String sql = "select count(ctop.cto_product_id) as total_num,ctop.product_name as show_text from cto_product_info ctop where ctop.create_at between ? and ? group by ctop.product_name order by total_num desc limit ?";
        List<CtoTotalVO> list = jdbcBaseDao.queryList(CtoTotalVO.class, sql, start, end, topNum);

        JSONObject result = new JSONObject();
        result.put("data", JSON.parseArray(JSON.toJSONString(list)));
        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject totalChangePartsView(String startDate, String endDate, String productName) {
        if(productName == null || productName.trim().length() < 1){
            return JSONUtil.putMsg(false, "101", "传入的产品名称为空");
        }

        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String sql = "select count(parts.cto_parts_id) as total_num,pscatg.catg_name as show_text from cto_parts_info parts" +
                " left join cto_product_info ctop on ctop.cto_product_id = parts.cto_product_id" +
                " left join parts_catg_info pscatg on pscatg.catg_id = parts.catg_id" +
                " where ctop.product_name = ? and ctop.create_at between ? and ? and parts.be_changed = 1" +
                " group by pscatg.catg_name";
        List<CtoTotalVO> list = jdbcBaseDao.queryList(CtoTotalVO.class, sql, productName, start, end);
        JSONObject result = new JSONObject();
        result.put("data", JSON.parseArray(JSON.toJSONString(list)));
        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject totalHotPricesView(String startDate, String endDate) {
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "101", "时间获取失败，请检查时间格式");
        }
        List<CtoTotalPricesPropEntity> totalPropList = ctoTotalPricesPropService.findCtoTotalPricesPropList();
        if(totalPropList == null){
            return JSONUtil.putMsg(false, "102", "配置信息为空，请联系管理员");
        }

        String sql = "select prices from cto_product_info";
        List<BigDecimal> pricesList = jdbcBaseDao.getColumns(BigDecimal.class, sql);
        if(pricesList == null || pricesList.size() < 1){
            return JSONUtil.putMsg(false, "103", "查询的数据为空");
        }
        String exitsKey = "exits";
        Map<String, Long> resultMap = pricesList.stream().collect(Collectors.groupingBy(o -> {
            for (CtoTotalPricesPropEntity totalProp : totalPropList){
                if(totalProp.inSection(o)){
                    return totalProp.getShowName();
                }
            }
            return exitsKey;
        }, Collectors.counting()));
        resultMap.remove(exitsKey);
        JSONObject result = new JSONObject();
        for (CtoTotalPricesPropEntity totalProp : totalPropList){
            if(!resultMap.containsKey(totalProp.getShowName())){
                resultMap.put(totalProp.getShowName(), 0L);
            }
        }
        result.put("data", resultMap);
        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject selectProductList(String startDate, String endDate) {
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "101", "时间获取失败，请检查时间格式");
        }
        String sql = "select distinct product_name from cto_product_info where create_at between ? and ?";
        List<String> productNameList = jdbcBaseDao.getColumns(String.class, sql, start, end);
        if(productNameList == null || productNameList.size() < 1){
            return JSONUtil.putMsg(false, "102", "查询的数据为空");
        }
        JSONObject result = new JSONObject();
        result.put("data", productNameList);
        result.put("success", true);
        return result;
    }

}
