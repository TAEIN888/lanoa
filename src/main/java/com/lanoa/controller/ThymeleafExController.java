package com.lanoa.controller;

import com.lanoa.dto.GoodsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/thymeleaf")
public class ThymeleafExController {

    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model) {
        String text = "123123zzzz";
        model.addAttribute("data", "타임리프 예제 입니다!!");
        return "thymeleafEx/thymeleafEx01";
    }

    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model) {
        GoodsDto goodsDto = new GoodsDto();
        goodsDto.setGoodsDetail("상품 상세 설명");
        goodsDto.setGoodsName("테스트 상품1");
        goodsDto.setPrice(10000);
        goodsDto.setRegTime(LocalDateTime.now());

        model.addAttribute("goodsDto", goodsDto);
        return "thymeleafEx/thymeleafEx02";
    }

    @GetMapping(value = "/ex03")
    public String thymeleafExample03(Model model) {

        List<GoodsDto> goodsDtoList = new ArrayList<>();

        for (int i=1; i<=10; i++) {
            GoodsDto goodsDto = new GoodsDto();
            goodsDto.setGoodsDetail("상품 상세 설명" + i);
            goodsDto.setGoodsName("테스트 상품" + i);
            goodsDto.setPrice(1000*i);
            goodsDto.setRegTime(LocalDateTime.now());

            goodsDtoList.add(goodsDto);
        }

        model.addAttribute("goodsDtoList", goodsDtoList);

        return "thymeleafEx/thymeleafEx03";
    }

    @GetMapping(value = "/ex04")
    public String thymeleafExample04(Model model) {

        List<GoodsDto> goodsDtoList = new ArrayList<>();

        for (int i=1; i<=10; i++) {
            GoodsDto goodsDto = new GoodsDto();
            goodsDto.setGoodsDetail("상품 상세 설명" + i);
            goodsDto.setGoodsName("테스트 상품" + i);
            goodsDto.setPrice(1000*i);
            goodsDto.setRegTime(LocalDateTime.now());

            goodsDtoList.add(goodsDto);
        }

        model.addAttribute("goodsDtoList", goodsDtoList);

        return "thymeleafEx/thymeleafEx04";
    }

    @GetMapping(value = "/ex05")
    public String ThymeleafExample05(Model model) {
        return "thymeleafEx/thymeleafEx05";
    }

    @GetMapping(value = "/ex06")
    public String ThymeleafExample06(String param1, String param2, Model model) {
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        return "thymeleafEx/thymeleafEx06";
    }

    @GetMapping(value = "/ex07")
    public String ThymeleafExample07(Model model) {
        return "thymeleafEx/thymeleafEx07";
    }
}
