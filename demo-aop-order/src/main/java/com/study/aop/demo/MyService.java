package com.study.aop.demo;

import com.study.aop.demo.annotation.A;
import com.study.aop.demo.annotation.B;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyService {
  @A @B
  public void hello() {
    log.info("hello");
  }
}