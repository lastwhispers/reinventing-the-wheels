package cn.lastwhisper.spring.service;

import cn.lastwhisper.spring.bean.Bean;

/**
 * @author lastwhisper
 */
@Bean
public class SalaryService {

    public Integer calculateSalary(String name, String experience) {
        return Integer.parseInt(name) * Integer.parseInt(experience);
    }
}
