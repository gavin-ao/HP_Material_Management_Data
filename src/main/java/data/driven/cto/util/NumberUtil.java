package data.driven.cto.util;

import java.math.BigDecimal;

/**
 * 数字工具类
 * @author hejinkai
 * @date 2018/9/2
 */
public class NumberUtil {

    /**
     * BigDecimal相加，如果都为空默认返回0
     * @param first
     * @param second
     * @return
     */
    public static BigDecimal addDefaultZero(BigDecimal first, BigDecimal second){
        if(first == null){
            return second==null ? new BigDecimal(0) : second;
        }
        if(second == null){
            return first==null ? new BigDecimal(0) : first;
        }
        return first.add(second);
    }

    /**
     * BigDecimal相加，如果都为空返回空
     * @param first
     * @param second
     * @return
     */
    public static BigDecimal add(BigDecimal first, BigDecimal second){
        if(first == null){
            return second;
        }
        if(second == null){
            return first;
        }
        return first.add(second);
    }

    /**
     * BigDecimal减法，如果都为空默认返回0
     * @param first
     * @param second
     * @return
     */
    public static BigDecimal subtractDefaultZero(BigDecimal first, BigDecimal second){
        if(first == null){
            return second==null ? new BigDecimal(0) : second;
        }
        if(second == null){
            return first==null ? new BigDecimal(0) : first;
        }
        return first.subtract(second);
    }

    /**
     * BigDecima减法，如果都为空返回空
     * @param first
     * @param second
     * @return
     */
    public static BigDecimal subtract(BigDecimal first, BigDecimal second){
        if(first == null){
            return second;
        }
        if(second == null){
            return first;
        }
        return first.subtract(second);
    }

}
