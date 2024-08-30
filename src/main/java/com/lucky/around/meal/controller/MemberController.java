package com.lucky.around.meal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lucky.around.meal.controller.record.RegisterRecord;
import com.lucky.around.meal.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

  private final MemberService memberService;

  @PostMapping
  public ResponseEntity<String> signUp(@RequestBody RegisterRecord registerRecord) {
    // 계정명 중복 검증
    memberService.isExistInDB(registerRecord);
    // 회원가입
    memberService.signUp(registerRecord);
    return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
  }
}