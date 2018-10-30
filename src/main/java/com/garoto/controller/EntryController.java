package com.garoto.controller;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.garoto.model.Entry;
import com.garoto.repository.EntryRepository;

@Controller
public class EntryController {
		
	@Autowired
	private EntryRepository entryRepo;
			
	@ResponseBody
	@RequestMapping(value = "/api/newEntry", method = RequestMethod.POST)
	public String newChocolate(@RequestParam("chocolate") String choco) {
		
		try {
			Entry newEntry = new Entry(choco);
			entryRepo.save(newEntry);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "database_error";
		}
		
		return "added";
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/stats/getChocoFreq", method = RequestMethod.GET)
	public Map<String, Integer> getChocoFreq (){	
		
		List<Entry> entries = (List<Entry>) entryRepo.findAll();
		
		Map<String, Integer> freq = entries.stream()
			.collect(Collectors.groupingBy(a -> a.getChocolate()))
			.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));
		
		return freq;
	}	

	@ResponseBody
	@RequestMapping(value = "/api/stats/getChocoFreqByDayOfWeek", method = RequestMethod.GET)
	public Map<DayOfWeek, Integer> getChocoFreqByDayOfWeek (@RequestParam("chocolate") String choco){
				
		List<Entry> entries = entryRepo.findByChocolate(choco);
		Map<DayOfWeek, Integer> freq = entries.stream()
			.collect(Collectors.groupingBy(a -> a.getDate().getDayOfWeek() ) )
			.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));
		
		return freq;
	}	
	
	@ResponseBody
	@RequestMapping(value = "/api/stats/getChocoFreqByMonth", method = RequestMethod.GET)
	public Map<Month, Integer> getChocoFreqByDayMonth (@RequestParam("chocolate") String choco){
				
		List<Entry> entries = entryRepo.findByChocolate(choco);
		Map<Month, Integer> freq = entries.stream()
			.collect(Collectors.groupingBy(a -> a.getDate().getMonth() ) )
			.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));
		
		return freq;
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/stats/getFreqByMonth", method = RequestMethod.GET)
	public Map<String, Integer> getFreqByMonth (@RequestParam("month") int month){	
		
		List<Entry> entries = (List<Entry>) entryRepo.findByMonth(month);
		
		Map<String, Integer> freq = entries.stream()
			.collect(Collectors.groupingBy(a -> a.getChocolate()))
			.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));
		
		return freq;
	}	
	
	@ResponseBody
	@RequestMapping(value = "/api/stats/getFreqByDayOfWeek", method = RequestMethod.GET)
	public Map<String, Integer> getFreqByDayOfWeek (@RequestParam("day") String day){	
		
		List<Entry> entries = (List<Entry>) entryRepo.findByDayOfWeek(day);
		
		Map<String, Integer> freq = entries.stream()
			.collect(Collectors.groupingBy(a -> a.getChocolate()))
			.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));
		
		return freq;
	}		
		
}
