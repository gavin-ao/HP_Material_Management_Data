package data.driven.cto.controller.wechat;

import data.driven.cto.business.wechat.WechatAppInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hejinkai
 * @date 2018/7/5
 */
@Controller
@RequestMapping(path = "/wechat/appinfo")
public class WechatAppInfoController {

    private static final Logger logger = LoggerFactory.getLogger(WechatAppInfoController.class);

    @Autowired
    private WechatAppInfoService wechatAppInfoService;

}
