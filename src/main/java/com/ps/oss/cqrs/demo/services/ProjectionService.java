package com.ps.oss.cqrs.demo.services;

import com.ps.oss.cqrs.demo.models.read.Account;
import com.ps.oss.cqrs.demo.models.write.domainevent.CreateAccountEvent;
import com.ps.oss.cqrs.demo.models.write.domainevent.TopUpEvent;
import com.ps.oss.cqrs.demo.models.write.domainevent.WithdrawEvent;
import com.ps.oss.cqrs.demo.repositories.AccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ProjectionService {

  private final AccountRepository accountRepository;

  public ProjectionService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account apply(TopUpEvent event) {
    Account account = accountRepository.findByUsername(event.getUsername())
        .orElseThrow(() -> new RuntimeException("Account not found"));

    account.setBalance(account.getBalance() + event.getAmount());
    return accountRepository.save(account);
  }

  public Account apply(CreateAccountEvent event) {
    return accountRepository.findByUsername(event.getUsername())
        .orElseGet(() -> accountRepository.save(Account.builder()
            .username(event.getUsername())
            .build()));
  }

  public Account apply(WithdrawEvent event) {
    Account account = accountRepository.findByUsername(event.getUsername())
        .orElseThrow(() -> new RuntimeException("Account not found"));

    account.setBalance(account.getBalance() - event.getAmount());
    return accountRepository.save(account);
  }

}
