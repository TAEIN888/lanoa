package com.lanoa.controller;

import com.lanoa.dto.*;
import com.lanoa.entity.RackCode;
import com.lanoa.entity.User;
import com.lanoa.service.GoodsService;
import com.lanoa.service.WmsService;
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
public class WmsController {

    private final WmsService wmsService;

    @GetMapping(value = "/admin/rackcode/new")
    public String rackCodeForm(Model model) {
        model.addAttribute("rackCodeFormDto", new RackCodeFormDto());
        return "rackcode/rackCodeForm";
    }

    @PostMapping(value = "/admin/rackcode/new")
    public String rackCodeNew(@Valid RackCodeFormDto rackCodeFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "rackcode/rackCodeForm";
        }

        try {
            wmsService.saveRackCode(rackCodeFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "랙코드 등록 중 오류가 발생하였습니다.");
            return "rackcode/rackCodeForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = {"/admin/rackcodelist", "/admin/rackcodelist/{page}"})
    public String userAdminPage(RackCodeSearchDto rackCodeSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<RackCode> rackCodeList = wmsService.getAdminRackCodePage(rackCodeSearchDto, pageable);

        model.addAttribute("rackCodeList", rackCodeList);
        model.addAttribute("rackCodeSearchDto", rackCodeSearchDto);
        model.addAttribute("maxPage", 5);

        return "rackcode/rackCodeManage";
    }
}
