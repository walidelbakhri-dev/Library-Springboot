package com.example.library.controller.api;

import com.example.library.dto.*;
import com.example.library.service.LoanService;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanRestController {

    private final LoanService svc;

    public LoanRestController(LoanService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<LoanDto> create(@Validated @RequestBody LoanCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(svc.createLoan(dto));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<LoanDto> returnLoan(@PathVariable Long id) {
        return ResponseEntity.ok(svc.returnLoan(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(svc.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<LoanDto>> list(@RequestParam(required = false) String q,
                                              @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(svc.list(q, pageable));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<Page<LoanDto>> byMember(@PathVariable Long memberId, Pageable pageable) {
        return ResponseEntity.ok(svc.listByMember(memberId, pageable));
    }
}