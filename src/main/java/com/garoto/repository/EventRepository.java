package com.garoto.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.garoto.model.Event;

@RepositoryRestResource(collectionResourceRel = "event", path = "event") 
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

}
