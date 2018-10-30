package com.garoto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.garoto.model.Entry;



@RepositoryRestResource(collectionResourceRel = "entry", path = "entry") 
public interface EntryRepository extends PagingAndSortingRepository<Entry, Long> {

	List<Entry> findByChocolate (String chocolate);
	
	@Query(value="select * from entry where MONTH(date) = ?1", nativeQuery=true)
	List<Entry> findByMonth (int month);
	
	@Query(value="select * from entry where DAYNAME(date) = ?1", nativeQuery=true)
	List<Entry> findByDayOfWeek (String day);
	
	
	
	
}
