package data.driven.cto.vo.product;

import data.driven.cto.entity.parts.PartsEntity;

/**
 * 预配置的配件vo
 * @author hejinkai
 * @date 2018/8/20
 */
public class PrePartsVO extends PartsEntity{

    private String preCtoId;

    public String getPreCtoId() {
        return preCtoId;
    }

    public void setPreCtoId(String preCtoId) {
        this.preCtoId = preCtoId;
    }
}
