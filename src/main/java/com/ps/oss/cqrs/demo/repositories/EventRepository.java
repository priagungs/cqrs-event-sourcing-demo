package com.ps.oss.cqrs.demo.repositories;

import com.ps.oss.cqrs.demo.models.write.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface EventRepository extends MongoRepository<Event, String> {

  List<Event> findByEventSourceIdOrderByCreatedAt(String eventSourceId);

}
