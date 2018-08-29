package data.driven.cto.business.wechat;

import data.driven.cto.entity.wechat.WechatAppInfoEntity;

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

}
