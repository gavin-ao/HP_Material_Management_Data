package data.driven.cto.business.cto;

/**
 * cto产品信息Service
 * @author hejinkai
 * @date 2018/8/21
 */
public interface CtoProductService {

    /**
     * 新增cto信息
     * @param productId
     * @param partsIds
     * @param creator
     * @return
     */
    public boolean addCtoProduct(String productId, String partsIds, String creator);

}
