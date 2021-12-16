package com.lanoa.controller;

import com.lanoa.dto.GoodsFormDto;
import com.lanoa.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class goodsController {

    private final GoodsService goodsService;

    @GetMapping(value = "/admin/goods/new")
    public String goodsForm(Model model) {
        model.addAttribute("goodsFormDto", new GoodsFormDto());
        return "goods/goodsForm";
    }

    @PostMapping(value = "/admin/goods/new")
    public String goodsNew(@Valid GoodsFormDto goodsFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "goods/goodsForm";
        }

        try {
            goodsService.saveGoods(goodsFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 오류가 발생하였습니다.");
            return "goods/goodsForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/admin/goods/{goodsCode}")
    public String getGoodsInfo(@PathVariable("goodsCode") String goodsCode, Model model) {
        try {
            GoodsFormDto goodsFormDto = goodsService.getGoodsInfo(goodsCode);
            model.addAttribute("goodsFormDto", goodsFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("goodsFormDto", new GoodsFormDto());
            return "goods/goodsForm";
        }

        return "goods/goodsForm";
    }

    @PostMapping(value = "/admin/goods/{goodsCode}")
    public String updateGoodsInfo(@Valid GoodsFormDto goodsFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "goods/goodsForm";
        }

        try {
            goodsService.updateGoods(goodsFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }

        return "redirect:/";
    }
}
