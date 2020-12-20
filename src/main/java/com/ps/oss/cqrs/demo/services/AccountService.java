package com.ps.oss.cqrs.demo.services;

import com.ps.oss.cqrs.demo.enums.EventSourceId;
import com.ps.oss.cqrs.demo.models.write.Event;
import com.ps.oss.cqrs.demo.models.write.EventSource;
import com.ps.oss.cqrs.demo.models.write.domainevent.CreateAccountEvent;
import com.ps.oss.cqrs.demo.models.write.domainevent.TopUpEvent;
import com.ps.oss.cqrs.demo.models.write.domainevent.WithdrawEvent;
import com.ps.oss.cqrs.demo.repositories.EventRepository;
import com.ps.oss.cqrs.demo.repositories.EventSourceRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class AccountService {

  private final EventSourceRepository eventSourceRepository;

  private final EventRepository eventRepository;

  private final KafkaTemplate<Object, Object> kafkaTemplate;

  public AccountService(EventSourceRepository eventSourceRepository, EventRepository eventRepository, KafkaTemplate<Object, Object> kafkaTemplate) {
    this.eventSourceRepository = eventSourceRepository;
    this.eventRepository = eventRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  public void createAccount(CreateAccountEvent createAccountEvent) {

    String eventSourceId = EventSourceId.CREATE_ACCOUNT;

    int version = getEventSourceVersion(eventSourceId);

    Event<CreateAccountEvent> event = Event.<CreateAccountEvent>builder()
        .domainEvent(createAccountEvent)
        .eventSourceId(eventSourceId)
        .version(version)
        .createdAt(System.currentTimeMillis())
        .build();

    kafkaTemplate.send(eventSourceId, event);
  }

  public void topUp(TopUpEvent topUpEvent) {

    String eventSourceId = EventSourceId.TOP_UP;

    int version = getEventSourceVersion(eventSourceId);

    Event<TopUpEvent> event = Event.<TopUpEvent>builder()
        .domainEvent(topUpEvent)
        .eventSourceId(eventSourceId)
        .version(version)
        .createdAt(System.currentTimeMillis())
        .build();

    kafkaTemplate.send(eventSourceId, event);
  }

  public void withdraw(WithdrawEvent withdrawEvent) {

    String eventSourceId = EventSourceId.WITHDRAW;

    int version = getEventSourceVersion(eventSourceId);

    Event<WithdrawEvent> event = Event.<WithdrawEvent>builder()
        .domainEvent(withdrawEvent)
        .eventSourceId(eventSourceId)
        .version(version)
        .createdAt(System.currentTimeMillis())
        .build();

    kafkaTemplate.send(eventSourceId, event);
  }

  private int getEventSourceVersion(String eventSourceId) {
    return eventSourceRepository.findById(eventSourceId)
        .map(EventSource::getVersion)
        .orElseThrow(() -> new RuntimeException("Event source not found"));
  }

}
