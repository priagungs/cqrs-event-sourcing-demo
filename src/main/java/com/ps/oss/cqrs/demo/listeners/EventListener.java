package com.ps.oss.cqrs.demo.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.oss.cqrs.demo.enums.EventSourceId;
import com.ps.oss.cqrs.demo.models.write.Event;
import com.ps.oss.cqrs.demo.models.write.domainevent.CreateAccountEvent;
import com.ps.oss.cqrs.demo.models.write.domainevent.TopUpEvent;
import com.ps.oss.cqrs.demo.models.write.domainevent.WithdrawEvent;
import com.ps.oss.cqrs.demo.services.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventListener {

  private final EventService eventService;

  private final ObjectMapper objectMapper;

  public EventListener(EventService eventService, ObjectMapper objectMapper) {
    this.eventService = eventService;
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = EventSourceId.CREATE_ACCOUNT)
  private void createAccount(String event) throws JsonProcessingException {
    log.info("CreateAccountEvent Triggered. value= " + event);
    eventService.createAccount(objectMapper.readValue(event, new TypeReference<Event<CreateAccountEvent>>() {}));
  }

  @KafkaListener(topics = EventSourceId.TOP_UP)
  private void topUp(String event) throws JsonProcessingException {
    log.info(String.format("TopUpEvent Triggered: value=%s", event));
    eventService.topUp(objectMapper.readValue(event, new TypeReference<Event<TopUpEvent>>() {}));
  }

  @KafkaListener(topics = EventSourceId.WITHDRAW)
  private void withdraw(String event) throws JsonProcessingException {
    log.info(String.format("TopUpEvent Triggered: value=%s", event));
    eventService.withDraw(objectMapper.readValue(event, new TypeReference<Event<WithdrawEvent>>() {}));
  }

}
