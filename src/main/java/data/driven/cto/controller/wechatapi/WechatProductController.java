package data.driven.cto.controller.wechatapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import data.driven.cto.business.cto.CtoProductService;
import data.driven.cto.business.product.ProductCatgService;
import data.driven.cto.business.product.ProductPreCtoService;
import data.driven.cto.business.product.ProductService;
import data.driven.cto.business.product.ProductSupportPartsService;
import data.driven.cto.common.Constant;
import data.driven.cto.common.WechatApiSession;
import data.driven.cto.common.WechatApiSessionBean;
import data.driven.cto.entity.parts.PartsCatgEntity;
import data.driven.cto.entity.parts.PartsEntity;
import data.driven.cto.entity.product.ProductPreCtoEntity;
import data.driven.cto.util.JSONUtil;
import data.driven.cto.vo.product.PrePartsVO;
import data.driven.cto.vo.product.ProductCatgVO;
import data.driven.cto.vo.product.ProductVO;
import data.driven.cto.vo.product.SupportPartsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 产品controller
 * @author hejinkai
 * @date 2018/8/18
 */
@Controller
@RequestMapping(path = "/wechatapi/product")
public class WechatProductController {

    private final Logger logger = LoggerFactory.getLogger(WechatProductController.class);

    @Autowired
    private ProductCatgService productCatgService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductPreCtoService productPreCtoService;
    @Autowired
    private ProductSupportPartsService productSupportPartsService;
    @Autowired
    private CtoProductService ctoProductService;

    /**
     * 查找所有的产品分类
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/findProDuctCatgList")
    public JSONObject findProDuctCatgList(){
        JSONObject result = new JSONObject();
        List<ProductCatgVO> list = productCatgService.findProDuctCatgList();
        result.put("success", true);
        result.put("data", JSONArray.parseArray(JSONArray.toJSONString(list)));
        return result;
    }

    /**
     * 根据分类查询产品
     * @param catgCode
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/findProductByCatgCode")
    public JSONObject findProductByCatgCode(String catgCode){
        JSONObject result = new JSONObject();
        List<ProductVO> list = productService.findProductByCatgCode(catgCode);

        if(list != null && list.size() > 0){
            for(ProductVO product : list){
                if(product != null && product.getFilePath() != null){
                    product.setFilePath(Constant.STATIC_FILE_PATH + product.getFilePath());
                }
            }
        }

        result.put("success", true);
        result.put("data", JSONArray.parseArray(JSONArray.toJSONString(list)));
        return result;
    }

    /**
     * 根据id获取产品信息
     * @param productId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/getProductById")
    public JSONObject getProductById(String productId){
        JSONObject result = new JSONObject();
        ProductVO product = productService.getProductById(productId);
        if(product != null && product.getFilePath() != null){
            product.setFilePath(Constant.STATIC_FILE_PATH + product.getFilePath());
        }
        result.put("success", true);
        result.put("data", JSONObject.parseObject(JSONObject.toJSONString(product)));
        return result;
    }

    /**
     * 根据产品获取预配置列表
     * @param productId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/findProductPreCtoList")
    public JSONObject findProductPreCtoList(String productId){
        JSONObject result = new JSONObject();
        List<ProductPreCtoEntity> list = productPreCtoService.findProductPreCtoListByProductId(productId);
        result.put("success", true);
        result.put("data", JSONArray.parseArray(JSONArray.toJSONString(list)));
        return result;
    }

    /**
     * 根据产品获取产品信息和预配置列表
     * @param productId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/getCtoProductAndPre")
    public JSONObject getCtoProductAndPre(String productId){
        JSONObject result = new JSONObject();
        result.put("success", true);
        ProductVO product = productService.getProductById(productId);
        if(product != null && product.getFilePath() != null){
            product.setFilePath(Constant.STATIC_FILE_PATH + product.getFilePath());
            result.put("product", JSONObject.parseObject(JSONObject.toJSONString(product)));
            //查询标准的配件信息
            List<SupportPartsVO> supportPartsList = productSupportPartsService.findStandardPartsByProductId(productId);
            //查询预配置名称和id
            List<ProductPreCtoEntity> productPreCtoList = productPreCtoService.findProductPreCtoListByProductId(productId);
            if(productPreCtoList != null && productPreCtoList.size() > 0){
                //查询所有预配置的配件
                List<PrePartsVO> allPrePartsList = productPreCtoService.findPartsListByProductId(productId);
                //根据查询的所有预配置的配件以预配置id进行分组
                Map<String, List<PrePartsVO>> groupPartsByCtoMap = null;
                if(allPrePartsList != null && allPrePartsList.size() > 0){
                    groupPartsByCtoMap = allPrePartsList.stream().collect(Collectors.groupingBy(o -> o.getPreCtoId()));
                }
                if(supportPartsList!= null && supportPartsList.size() > 0){
                    //获取第一个预配置id，根据第一个预配置出产品的介绍
                    String preCtoId = productPreCtoList.get(0).getPreCtoId();
                    List<PrePartsVO> prePartsList = null;
                    if(groupPartsByCtoMap != null){
                        prePartsList = groupPartsByCtoMap.get(preCtoId);
                    }
                    //如果第一个预配置的配件信息不为空，就根据配件分类替换该配件，并且如果预配置的配件信息比标准多，就将多余的配件信息填充展示
                    if(prePartsList != null && prePartsList.size() > 0){
                        Map<String, PrePartsVO> prePartsMap = prePartsList.stream().collect(Collectors.toMap(o -> o.getCatgId(), o -> o));
                        for(SupportPartsVO supportPartsVO : supportPartsList){
                            PrePartsVO preParts = prePartsMap.get(supportPartsVO.getCatgId());
                            if(preParts != null){
                                BeanUtils.copyProperties(preParts, supportPartsVO);
                                prePartsMap.remove(supportPartsVO.getCatgId());
                            }
                        }
                        if(prePartsMap.values().size()>0){
                            for(PrePartsVO preParts : prePartsMap.values()){
                                SupportPartsVO supportPartsVO = new SupportPartsVO();
                                BeanUtils.copyProperties(preParts, supportPartsVO);
                                supportPartsList.add(supportPartsVO);
                            }
                        }
                    }
                }
                List<JSONObject> preCtoList = new ArrayList<JSONObject>(productPreCtoList.size());
                if(groupPartsByCtoMap != null && groupPartsByCtoMap.size() > 0){
                    for(ProductPreCtoEntity preCtoEntity : productPreCtoList){
                        JSONObject preJson = JSON.parseObject(JSON.toJSONString(preCtoEntity));
                        String shortName = dealPartsShortNameToStr(groupPartsByCtoMap.get(preCtoEntity.getPreCtoId()));
                        String showText = preCtoEntity.getPreName();
                        if(shortName != null){
                            showText = showText + "(" + shortName + ")";
                        }
                        preJson.put("showText", showText);
                        preCtoList.add(preJson);
                    }
                }
                result.put("preCtoList", preCtoList);
            }

            if(supportPartsList!= null && supportPartsList.size() > 0){
                StringBuilder details = new StringBuilder();
                String splitStr = "\n";
                for(SupportPartsVO supportPartsVO : supportPartsList){
                    details.append(supportPartsVO.getPartsName()).append(splitStr);
                }
                details.delete(details.length() - splitStr.length(), details.length());
                result.put("details", details);
            }

        }else{
            return JSONUtil.putMsg(false, "101", "查询的产品为空");
        }

        return result;
    }

    /**
     * 根据产品获取产品信息和预配置列表
     * @param productId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/getDetailsByPreCtoId")
    public JSONObject getDetailsByPreCtoId(String productId, String preCtoId){
        JSONObject result = new JSONObject();
        result.put("success", true);
        ProductVO product = productService.getProductById(productId);
        if(product != null && product.getFilePath() != null){
            //查询标准的配件信息
            List<SupportPartsVO> supportPartsList = productSupportPartsService.findStandardPartsByProductId(productId);
            if(supportPartsList!= null && supportPartsList.size() > 0){
                List<PartsEntity> prePartsList = productPreCtoService.findPartsListByPreCtoId(preCtoId);
                //如果预配置的配件信息不为空，就根据配件分类替换该配件，并且如果预配置的配件信息比标准多，就将多余的配件信息填充展示
                if(prePartsList != null && prePartsList.size() > 0){
                    Map<String, PartsEntity> prePartsMap = prePartsList.stream().collect(Collectors.toMap(o -> o.getCatgId(), o -> o));
                    for(SupportPartsVO supportPartsVO : supportPartsList){
                        PartsEntity preParts = prePartsMap.get(supportPartsVO.getCatgId());
                        if(preParts != null){
                            BeanUtils.copyProperties(preParts, supportPartsVO);
                            prePartsMap.remove(supportPartsVO.getCatgId());
                        }
                    }
                    if(prePartsMap.values().size()>0){
                        for(PartsEntity preParts : prePartsMap.values()){
                            SupportPartsVO supportPartsVO = new SupportPartsVO();
                            BeanUtils.copyProperties(preParts, supportPartsVO);
                            supportPartsList.add(supportPartsVO);
                        }
                    }
                }
            }

            if(supportPartsList!= null && supportPartsList.size() > 0){
                StringBuilder details = new StringBuilder();
                String splitStr = "\n";
                for(SupportPartsVO supportPartsVO : supportPartsList){
                    details.append(supportPartsVO.getPartsName()).append(splitStr);
                }
                details.delete(details.length() - splitStr.length(), details.length());
                result.put("details", details);
            }else{
                return JSONUtil.putMsg(false, "102", "配件列表为空");
            }
        }else{
            return JSONUtil.putMsg(false, "101", "查询的产品为空");
        }

        return result;
    }

    /**
     * 将配件集合根据shortname拼接成字符串
     * @param partsList
     * @return
     */
    private String dealPartsShortNameToStr(List<PrePartsVO> partsList){
        if(partsList != null && partsList.size() > 0){
            StringBuilder shortNames = new StringBuilder();
            String splitStr = "、";
            //默认展示前四条
            int i = 1;
            for(PrePartsVO prePartsVO : partsList){
                shortNames.append(prePartsVO.getShortName()).append(splitStr);
                if(i==4){
                    break;
                }
                i++;
            }
            shortNames.delete(shortNames.length() - splitStr.length(), shortNames.length());
            return shortNames.toString();
        }
        return null;
    }



    /**
     * 挑选配件
     * @param productId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/selectParts")
    public JSONObject selectParts(String productId, String preCtoId){
        JSONObject result = new JSONObject();
        List<JSONObject> resultList = new ArrayList<JSONObject>();
        List<PartsCatgEntity> partsCatgEntityList = productSupportPartsService.findPartsCatgList(productId);
        if(partsCatgEntityList != null && partsCatgEntityList.size() > 0){
            List<SupportPartsVO> supportPartsList = productSupportPartsService.findPartsByProductId(productId);
            Map<String, String> prePartsMap = null;
            if(preCtoId != null){
                List<PartsEntity> prePartsList = productPreCtoService.findPartsListByPreCtoId(preCtoId);
                prePartsMap = prePartsList.stream().collect(Collectors.toMap(o -> o.getCatgId(), o -> o.getPartsId()));
            }

            if(supportPartsList != null && supportPartsList.size() > 0){
                Map<String, List<SupportPartsVO>> supportPartsMap = supportPartsList.stream().collect(Collectors.groupingBy(o -> o.getCatgId()));

                for (PartsCatgEntity partsCatg : partsCatgEntityList){
                    JSONObject catg = new JSONObject();
                    catg.put("catg", partsCatg);
                    List<SupportPartsVO> partsVOList = supportPartsMap.get(partsCatg.getCatgId());
                    if(partsVOList != null && partsVOList.size() > 0){
                        String prePartsId = prePartsMap.get(partsCatg.getCatgId());
                        catg.put("prePartsId", prePartsId);
                    }
                    catg.put("parts", partsVOList);
                    resultList.add(catg);
                }
            }
        }
        result.put("success", true);
        result.put("data", JSONArray.parseArray(JSONArray.toJSONString(resultList)));
        return result;
    }

    @ResponseBody
    @RequestMapping("/submitCto")
    public JSONObject submitCto(String sessionID, String productId, String preCtoId, String partsIds, BigDecimal prices){
        WechatApiSessionBean wechatApiSessionBean = WechatApiSession.getSessionBean(sessionID);
        String creator = wechatApiSessionBean.getUserInfo().getWechatUserId();
        return ctoProductService.addCtoProduct(productId, preCtoId, partsIds, creator, prices);
    }
}
