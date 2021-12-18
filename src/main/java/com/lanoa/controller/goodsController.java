package com.lanoa.controller;

import com.lanoa.dto.GoodsFormDto;
import com.lanoa.dto.GoodsListDto;
import com.lanoa.dto.GoodsSearchDto;
import com.lanoa.entity.Goods;
import com.lanoa.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

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

    @GetMapping(value = {"/admin/goodsList", "/admin/goodsList/{page}"})
    public String goodsAdminPage(GoodsSearchDto goodsSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<GoodsListDto> goodsList = goodsService.getAdminGoodsPage(goodsSearchDto, pageable);

        model.addAttribute("goodsList", goodsList);
        model.addAttribute("goodsSearchDto", goodsSearchDto);
        model.addAttribute("maxPage", 5);

        return "goods/goodsManage";
    }
}
