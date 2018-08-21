package data.driven.cto.controller.cto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author hejinkai
 * @date 2018/8/21
 */
@Controller
@RequestMapping(path = "/cto/total")
public class CtoTotalController {

    private static final Logger logger = LoggerFactory.getLogger(CtoTotalController.class);

    @RequestMapping("/dataStatistics")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("/data-statistics/index");
        return mv;
    }

}
