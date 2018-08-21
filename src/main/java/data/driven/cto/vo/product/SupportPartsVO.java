package data.driven.cto.vo.product;

import data.driven.cto.entity.parts.PartsEntity;

/**
 * @author hejinkai
 * @date 2018/8/18
 */
public class SupportPartsVO extends PartsEntity {
    /** 是否标配(1-是标配，0-不是标配) **/
    private Integer standard;

    public Integer getStandard() {
        return standard;
    }

    public void setStandard(Integer standard) {
        this.standard = standard;
    }
}
