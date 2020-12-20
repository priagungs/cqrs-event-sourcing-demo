package com.ps.oss.cqrs.demo.controllers;

import com.ps.oss.cqrs.demo.models.write.domainevent.CreateAccountEvent;
import com.ps.oss.cqrs.demo.models.write.domainevent.TopUpEvent;
import com.ps.oss.cqrs.demo.models.write.domainevent.WithdrawEvent;
import com.ps.oss.cqrs.demo.services.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WriteController {

  private final AccountService accountService;

  public WriteController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping(path = "/account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> createAccount(@RequestBody CreateAccountEvent accountEvent) {
    log.info(String.format("request to /account with body=%s", accountEvent.toString()));
    accountService.createAccount(accountEvent);
    return ResponseEntity.ok().body(true);
  }

  @PostMapping(path = "/account/_topup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> topup(@RequestBody TopUpEvent topUpEvent) {
    log.info(String.format("request to /account/_topup with body=%s", topUpEvent.toString()));
    accountService.topUp(topUpEvent);
    return ResponseEntity.ok().body(true);
  }

  @PostMapping(path = "/account/_withdraw", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> withdraw(@RequestBody WithdrawEvent withdrawEvent) {
    log.info(String.format("request to /account/_topup with body=%s", withdrawEvent.toString()));
    accountService.withdraw(withdrawEvent);
    return ResponseEntity.ok().body(true);
  }

}
