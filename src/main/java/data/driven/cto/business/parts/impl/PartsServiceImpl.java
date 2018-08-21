package data.driven.cto.business.parts.impl;

import data.driven.cto.business.parts.PartsService;
import data.driven.cto.dao.JDBCBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 配件信息Service
 * @author hejinkai
 * @date 2018/8/18
 */
@Service
public class PartsServiceImpl implements PartsService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

}
