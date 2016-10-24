package com.find.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by buri on 2016. 10. 24..
 */
@Controller
public class ViewController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView goMain() {

        //TODO DB에 저장된 정보를 장소 정보를 가져온다

        return new ModelAndView("layout");
    }
}
