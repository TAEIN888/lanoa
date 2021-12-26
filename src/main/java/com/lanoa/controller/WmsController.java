package com.lanoa.controller;

import com.lanoa.dto.*;
import com.lanoa.entity.RackCode;
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

        return this.userAdminPage(new RackCodeSearchDto(), Optional.of(0), model);
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

    @GetMapping(value = "/admin/rackcode/{rackCodeId}")
    public String getRackCodeInfo(@PathVariable("rackCodeId") String rackCodeId, Model model) {
        try {
            RackCodeFormDto rackCodeFormDto = wmsService.getRackCodeInfo(rackCodeId);
            model.addAttribute("rackCodeFormDto", rackCodeFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("rackCodeFormDto", new RackCodeFormDto());
            return "rackcode/rackCodeForm";
        }

        return "rackcode/rackCodeForm";
    }

    @PostMapping(value = "/admin/rackcode/{rackCodeId}")
    public String updateRackCodeInfo(@Valid RackCodeFormDto rackCodeFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "rackcode/rackCodeForm";
        }

        try {
            wmsService.updateRackCode(rackCodeFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "랙코드 정보 수정 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }

        return this.userAdminPage(new RackCodeSearchDto(), Optional.of(0), model);
    }

    @GetMapping(value = {"/rack/rackcodelist", "/rack/rackcodelist/{page}"})
    public String getWareHousingRackCode(RackCodeSearchDto rackCodeSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<RackCode> rackCodeList = wmsService.getAdminRackCodePage(rackCodeSearchDto, pageable);

        model.addAttribute("rackCodeList", rackCodeList);
        model.addAttribute("rackCodeSearchDto", rackCodeSearchDto);
        model.addAttribute("maxPage", 5);

        return "rackcode/rackCodeWareHousing";
    }

    @GetMapping(value = "/rack/warehousing/{rackCode}")
    public String rackWarehousingForm(@PathVariable("rackCode") String rackCode, Model model) {
        RackFormDto rackFormDto = new RackFormDto();
        rackFormDto.setRackCode(rackCode);

        model.addAttribute("rackFormDto", rackFormDto);
        return "rack/rackForm";
    }

    @PostMapping(value = "/rack/warehousing/{rackCode}")
    public String rackWarehousing(@Valid RackFormDto rackFormDto, BindingResult bindingResult, @PathVariable("rackCode") String rackCode, Model model) {

        if (bindingResult.hasErrors()) {
            return "rack/rackForm";
        }

        try {
            wmsService.rackWarehousing(rackFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "입고 처리 중 오류가 발생하였습니다.");
            return "rack/rackForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = {"/rack/racklist", "/rack/racklist/{page}"})
    public String rackList(RackSearchDto rackSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<RackListDto> rackList = wmsService.getRackListPage(rackSearchDto, pageable);

        model.addAttribute("rackList", rackList);
        model.addAttribute("rackSearchDto", rackSearchDto);
        model.addAttribute("maxPage", 5);

        return "rack/rackManage";
    }
}
