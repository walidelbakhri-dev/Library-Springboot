package com.example.library.controller.api;

import com.example.library.dto.*;
import com.example.library.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberRestController {

    private final MemberService svc;

    public MemberRestController(MemberService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<MemberDto> create(@Valid @RequestBody MemberCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(svc.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(svc.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<MemberDto>> list(
            @RequestParam(required = false) String q,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(svc.list(q, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> update(
            @PathVariable Long id,
            @Valid @RequestBody MemberUpdateDto dto) {
        return ResponseEntity.ok(svc.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
