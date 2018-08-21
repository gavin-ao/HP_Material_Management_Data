package data.driven.cto.business.user;

import data.driven.cto.entity.user.UserInfoEntity;

/**
 * 系统用户service
 * @author hejinkai
 * @date 2018/7/1
 */

public interface UserInfoService {

    /**
     * 根据用户和密码获取用户信息
     * @param userName
     * @param pwd
     * @return
     */
    public UserInfoEntity getUser(String userName, String pwd);

}
