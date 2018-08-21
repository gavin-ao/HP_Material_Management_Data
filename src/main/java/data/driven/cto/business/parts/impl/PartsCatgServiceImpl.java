package data.driven.cto.business.parts.impl;

import data.driven.cto.business.parts.PartsCatgService;
import data.driven.cto.dao.JDBCBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 配件分类Service
 * @author hejinkai
 * @date 2018/8/18
 */
@Service
public class PartsCatgServiceImpl implements PartsCatgService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

}
