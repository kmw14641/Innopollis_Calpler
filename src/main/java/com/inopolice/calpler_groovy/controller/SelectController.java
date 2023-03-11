package com.inopolice.calpler_groovy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@RequestMapping("/select")
@Controller
public class SelectController {
    @GetMapping("")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/{area}")
    public String AreaPage(@PathVariable("area") String area, Model model) {
        Map<String, String> map = new LinkedHashMap<>();
        if (area.equals("")) {
            return "main";
        }
        else if (area.equals("gyeongsangdo")) {
            model.addAttribute("areaText", "경상도");
            map.put("pohang", "포항");
            map.put("busan", "부산");
            map.put("ulsan", "울산");
            model.addAttribute("map", map);
            return "main_area";
        }
        else if (area.equals("gyeonggido")) {
            model.addAttribute("areaText", "경기도");
            map.put("uijeongbu", "의정부");
            model.addAttribute("map", map);
            return "main_area";
        }
        else if (area.equals("pohang")) {
            model.addAttribute("areaText", "포항");
            map.put("postech", "포스텍");
            map.put("pohangdae", "포항대학교");
            model.addAttribute("map", map);
            return "main_university";
        }
        else if (area.equals("uijeongbu")) {
            model.addAttribute("areaText", "의정부");
            map.put("other", "다른대학들(임시)");
            model.addAttribute("map", map);
            return "main_university";
        }
        else {
            return "redirect:/select";
        }
    }
}
