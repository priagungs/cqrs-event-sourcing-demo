package com.ps.oss.cqrs.demo.models.write.domainevent;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountEvent {
  private String username;
}
