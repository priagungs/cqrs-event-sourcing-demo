package com.ps.oss.cqrs.demo.models.read;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class Account {

  @Id
  private String id;
  private String username;
  private long balance;

}
