package com.example.library.controller.mvc;

import com.example.library.dto.*;
import com.example.library.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberMvcController {

    private final MemberService svc;

    /* =========================
       LIST
    ========================== */
    @GetMapping
    public String list(Model model) {
        model.addAttribute(
                "members",
                svc.list(null, PageRequest.of(0, 20)).getContent()
        );
        model.addAttribute("pageTitle", "Membres");
        model.addAttribute("content", "members/list :: content");
        return "fragments/layout";
    }

    /* =========================
       CREATE
    ========================== */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("member", new MemberCreateDto());
        model.addAttribute("pageTitle", "Nouveau membre");
        model.addAttribute("content", "members/form :: content");
        return "fragments/layout";
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute("member") MemberCreateDto dto,
            BindingResult br,
            Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Nouveau membre");
            model.addAttribute("content", "members/form :: content");
            return "fragments/layout";
        }
        svc.create(dto);
        return "redirect:/members";
    }

    /* =========================
       EDIT
    ========================== */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {

        MemberDto member = svc.getById(id);

        MemberUpdateDto dto = MemberUpdateDto.builder()
                .fullName(member.getFullName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .build();

        model.addAttribute("member", dto);
        model.addAttribute("memberId", id);
        model.addAttribute("pageTitle", "Modifier membre");
        model.addAttribute("content", "members/form :: content");

        return "fragments/layout";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("member") MemberUpdateDto dto,
            BindingResult br,
            Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("memberId", id);
            model.addAttribute("pageTitle", "Modifier membre");
            model.addAttribute("content", "members/form :: content");
            return "fragments/layout";
        }
        svc.update(id, dto);
        return "redirect:/members";
    }

    /* =========================
       DELETE
    ========================== */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        svc.delete(id);
        return "redirect:/members";
    }
}
