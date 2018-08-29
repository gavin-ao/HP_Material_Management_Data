package data.driven.cto.entity.cto;

import java.math.BigDecimal;

/**
 * 价格区间统计配置表Entity
 * @author hejinkai
 * @date 2018/8/30
 */
public class CtoTotalPricesPropEntity {
    /** 主键 **/
    private String id;
    /** 展示名称 **/
    private String showName;
    /** 最大值 **/
    private BigDecimal maxNum;
    /** 最小值 **/
    private BigDecimal minNum;

    /**
     * 判断数值是否在区间之类：[min,max) ， 包含最小值不含最大值
     * @param value
     * @return
     */
    public boolean inSection(BigDecimal value){
        if(value == null){
            return false;
        }
        if(maxNum != null){
            //如果不小于最小值，就返回false
            if(value.compareTo(maxNum) != -1){
                return false;
            }
        }
        if(minNum != null){
            //如果小于最小值，就返回false
            if(value.compareTo(minNum) == -1){
                return false;
            }
        }

        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public BigDecimal getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(BigDecimal maxNum) {
        this.maxNum = maxNum;
    }

    public BigDecimal getMinNum() {
        return minNum;
    }

    public void setMinNum(BigDecimal minNum) {
        this.minNum = minNum;
    }
}
