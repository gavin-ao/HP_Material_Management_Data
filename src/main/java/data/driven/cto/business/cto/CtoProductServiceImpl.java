package data.driven.cto.business.cto;

import com.alibaba.fastjson.JSONObject;
import data.driven.cto.business.product.ProductPreCtoService;
import data.driven.cto.business.product.ProductService;
import data.driven.cto.dao.JDBCBaseDao;
import data.driven.cto.entity.cto.CtoPartsEntity;
import data.driven.cto.entity.cto.CtoProductEntity;
import data.driven.cto.entity.parts.PartsEntity;
import data.driven.cto.entity.product.ProductEntity;
import data.driven.cto.util.JSONUtil;
import data.driven.cto.util.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author hejinkai
 * @date 2018/8/21
 */
@Service
public class CtoProductServiceImpl implements CtoProductService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductPreCtoService productPreCtoService;

    @Override
    public JSONObject addCtoProduct(String productId, String preCtoId, String partsIds, String creator, BigDecimal prices) {
        if(productId == null || preCtoId == null || partsIds == null){
            return JSONUtil.putMsg(false, "101", "传入的参数为空");
        }
        ProductEntity productEntity = productService.getProductByIdAll(productId);
        if(productEntity == null){
            return JSONUtil.putMsg(false, "102", "查询到的产品对象为空");
        }
        List<PartsEntity> oldPartsEntityList = productPreCtoService.findPartsListByPreCtoId(preCtoId);
        if(oldPartsEntityList == null || oldPartsEntityList.size() < 1){
            return JSONUtil.putMsg(false, "103", "查询到预配置的配件为空");
        }
        StringBuffer partsWhere = new StringBuffer();
        List<String> partsIdList = Arrays.asList(partsIds.split(","));
        for (String str : partsIdList){
            partsWhere.append(",?");
        }
        if(partsWhere.length() > 0){
            partsWhere.delete(0,1);
        }else{
            return JSONUtil.putMsg(false, "104", "配件id为空");
        }
        String partsSql = "select parts.parts_id,parts.catg_id,parts.parts_name,parts.short_name,parts.model_numbers,parts.prices from parts_info parts" +
                " where parts.parts_id in (" + partsWhere + ")";
        List<PartsEntity> changePartsList = jdbcBaseDao.queryListWithListParam(PartsEntity.class, partsSql, partsIdList);
        if(changePartsList == null || changePartsList.size() < 1){
            return JSONUtil.putMsg(false, "103", "查询到修改的配件为空");
        }
        CtoProductEntity ctoProductEntity = new CtoProductEntity();
        BeanUtils.copyProperties(productEntity, ctoProductEntity);
        String ctoProductId = UUIDUtil.getUUID();
        ctoProductEntity.setCtoProductId(ctoProductId);
        ctoProductEntity.setCreateAt(new Date());
        ctoProductEntity.setCreator(creator);
        ctoProductEntity.setPrices(prices);
        jdbcBaseDao.insert(ctoProductEntity, "cto_product_info");

        List<CtoPartsEntity> ctoPartsEntityList = new ArrayList<CtoPartsEntity>();
        List<String> catgIdList = new ArrayList<String>(changePartsList.size());
        //插入最新的配件数据
        for(PartsEntity partsEntity : changePartsList){
            CtoPartsEntity ctoPartsEntity = new CtoPartsEntity();
            BeanUtils.copyProperties(partsEntity, ctoPartsEntity);
            ctoPartsEntity.setCtoPartsId(UUIDUtil.getUUID());
            ctoPartsEntity.setCtoProductId(ctoProductId);
            ctoPartsEntity.setBeChanged(0);
            ctoPartsEntityList.add(ctoPartsEntity);
            catgIdList.add(partsEntity.getCatgId());
        }
        //插入老配件数据，被替换的数据
        for(PartsEntity partsEntity : oldPartsEntityList){
            //处理过的配件不需要再处理一遍
            if(partsIdList.contains(partsEntity.getPartsId())){
                continue;
            }
            CtoPartsEntity ctoPartsEntity = new CtoPartsEntity();
            BeanUtils.copyProperties(partsEntity, ctoPartsEntity);
            ctoPartsEntity.setCtoPartsId(UUIDUtil.getUUID());
            ctoPartsEntity.setCtoProductId(ctoProductId);
            ctoPartsEntity.setBeChanged(1);
            ctoPartsEntityList.add(ctoPartsEntity);
        }
        String insertSql = "insert into cto_parts_info(cto_parts_id,cto_product_id,catg_id,parts_name,short_name,model_numbers,prices,picture_id,be_changed) ";
        String valuesSql = "(:cto_parts_id,:cto_product_id,:catg_id,:parts_name,:short_name,:model_numbers,:prices,:picture_id,:be_changed)";
        jdbcBaseDao.executeBachOneSql(insertSql, valuesSql, ctoPartsEntityList);
        return JSONUtil.putMsg(true, "200", "提交成功");
    }
}
