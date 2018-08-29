package data.driven.cto.business.wechat.impl;

import data.driven.cto.business.wechat.WechatAppInfoService;
import data.driven.cto.dao.JDBCBaseDao;
import data.driven.cto.entity.wechat.WechatAppInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hejinkai
 * @date 2018/6/28
 */
@Service
public class WechatAppInfoServiceImpl implements WechatAppInfoService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public WechatAppInfoEntity getAppInfo(String appid) {
        String sql = "select app_info_id,app_name,appid,secret,create_at,creator from wechat_app_info where appid = ?";
        List<WechatAppInfoEntity> list = jdbcBaseDao.queryList(WechatAppInfoEntity.class, sql, appid);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

}
