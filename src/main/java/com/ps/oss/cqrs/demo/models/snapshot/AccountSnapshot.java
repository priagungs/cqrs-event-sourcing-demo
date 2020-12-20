package com.ps.oss.cqrs.demo.models.snapshot;

import com.ps.oss.cqrs.demo.models.read.Account;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("account_snapshot")
@Builder
@Data
public class AccountSnapshot {

  @Id
  private String id;
  private Account account;
  private int version;
  private long createdAt;

}
