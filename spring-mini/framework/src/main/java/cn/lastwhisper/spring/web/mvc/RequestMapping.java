package cn.lastwhisper.spring.web.mvc;

import java.lang.annotation.*;

/**
 * @author lastwhisper
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RequestMapping {
    String value();
}
