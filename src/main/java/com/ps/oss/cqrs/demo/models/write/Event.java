package com.ps.oss.cqrs.demo.models.write;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class Event<T> {

  @Id
  private String id;
  private String eventSourceId;
  private T domainEvent;
  private long createdAt;
  private int version;

}
