package com.smart.controller.check;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/"})
public class CheckController {
    public CheckController() {
    }

    @RequestMapping({"/check.do"})
    public ModelAndView test() {
        return new ModelAndView("check/maSystem");
    }

    @RequestMapping({"/intro.do"})
    public ModelAndView introduce() {
        return new ModelAndView("check/introduce");
    }
}
