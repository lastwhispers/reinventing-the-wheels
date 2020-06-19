package cn.lastwhisper.spring.controllers;

import cn.lastwhisper.spring.bean.AutoWired;
import cn.lastwhisper.spring.service.SalaryService;
import cn.lastwhisper.spring.web.mvc.Controller;
import cn.lastwhisper.spring.web.mvc.RequestMapping;
import cn.lastwhisper.spring.web.mvc.RequestParam;

/**
 * @author lastwhisper
 */
@Controller
public class SalaryController {
    @AutoWired
    private SalaryService salaryService;

    @RequestMapping("/getSalary")
    public Integer getSalary(@RequestParam("name") String name,
                             @RequestParam("experience") String experience) {
        // http://localhost:6699/getSalary.json?name=1000&experience=456
        //return 10000;
        return salaryService.calculateSalary(name, experience);
    }
}
