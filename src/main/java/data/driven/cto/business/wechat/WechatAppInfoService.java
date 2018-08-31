package data.driven.cto.business.wechat;

import data.driven.cto.entity.wechat.WechatAppInfoEntity;

import java.util.List;

/**
 * 小程序信息service
 * @author hejinkai
 * @date 2018/6/27
 */
public interface WechatAppInfoService {

    /**
     * 根据appid查询对象
     * @param appid
     * @return
     */
    public WechatAppInfoEntity getAppInfo(String appid);

    /**
     * 根据微信小程序查询轮播图
     * @param appInfoId
     * @return
     */
    public List<String> findSowingMap(String appInfoId);

    /**
     * 根据主键获取手机号码-联系方式
     * @param appInfoId
     * @return
     */
    public String getMobilePhone(String appInfoId);

}
