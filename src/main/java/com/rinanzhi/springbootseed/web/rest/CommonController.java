package com.rinanzhi.springbootseed.web.rest;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/common")
public class CommonController {

    @Builder
    @Data
    static public class Setting {
        private String environment;
    }

    @GetMapping("setting")
    public Setting setting() {
        return Setting.builder().environment("local").build();
    }

}
