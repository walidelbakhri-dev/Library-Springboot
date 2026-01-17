package com.example.library.controller.mvc;

import com.example.library.dto.*;
import com.example.library.service.BookService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookMvcController {

    private final BookService svc;

    public BookMvcController(BookService svc) { this.svc = svc; }

    @GetMapping
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Model model) {
        Page<BookDto> books = svc.search(q, PageRequest.of(page, size));
        model.addAttribute("books", books);
        model.addAttribute("q", q);
        return "books/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("book", new BookCreateDto());
        return "books/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        BookDto dto = svc.getById(id);
        model.addAttribute("book", dto);
        return "books/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("book") BookCreateDto dto, BindingResult br) {
        if (br.hasErrors()) return "books/form";
        svc.create(dto);
        return "redirect:/books";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("book") BookUpdateDto dto, BindingResult br) {
        if (br.hasErrors()) return "books/form";
        svc.update(id, dto);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        svc.delete(id);
        return "redirect:/books";
    }
}