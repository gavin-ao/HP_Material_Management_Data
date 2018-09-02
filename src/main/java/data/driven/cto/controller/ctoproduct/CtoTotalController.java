package data.driven.cto.controller.ctoproduct;

import com.alibaba.fastjson.JSONObject;
import data.driven.cto.business.ctoproduct.CtoTotalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author hejinkai
 * @date 2018/8/21
 */
@Controller
@RequestMapping(path = "/ctoproduct/total")
public class CtoTotalController {

    private static final Logger logger = LoggerFactory.getLogger(CtoTotalController.class);

    @Autowired
    public CtoTotalService ctoTotalService;

    @RequestMapping("/dataStatistics")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("/data-statistics/index");
        return mv;
    }

    /**
     * 根据产品分类统计环状图
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalCtoCatgView")
    public JSONObject totalCtoCatgView(String startDate, String endDate){
        return ctoTotalService.totalCtoCatgView(startDate, endDate);
    }

    /**
     * 统计最受欢迎机型 top10 - 条形图
     * @param startDate
     * @param endDate
     * @param topNum
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalHotTopTenView")
    public JSONObject totalHotTopTenView(String startDate, String endDate, Integer topNum){
        return ctoTotalService.totalHotTopTenView(startDate, endDate, topNum);
    }

    /**
     * 高频定制需求分析 - 根据产品名称，统计变更的配件信息 - 柱状图
     * @param startDate
     * @param endDate
     * @param productName
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalChangePartsView")
    public JSONObject totalChangePartsView(String startDate, String endDate, String productName){
        return ctoTotalService.totalChangePartsView(startDate, endDate, productName);
    }

    /**
     * 最受欢迎价格区间——雷达图
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalHotPricesView")
    public JSONObject totalHotPricesView(String startDate, String endDate){
        return ctoTotalService.totalHotPricesView(startDate, endDate);
    }

    /**
     * 获取产品列表，用于下拉选择
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/selectProductList")
    public JSONObject selectProductList(String startDate, String endDate){
        return ctoTotalService.selectProductList(startDate, endDate);
    }

}
