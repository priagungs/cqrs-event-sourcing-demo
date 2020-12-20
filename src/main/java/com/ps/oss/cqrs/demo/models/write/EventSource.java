package com.ps.oss.cqrs.demo.models.write;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("event_source")
public class EventSource {

  @Id
  private String id;
  private int version;

}
