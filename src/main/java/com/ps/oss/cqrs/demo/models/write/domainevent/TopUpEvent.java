package com.ps.oss.cqrs.demo.models.write.domainevent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopUpEvent {

  private String username;
  private long amount;

}
