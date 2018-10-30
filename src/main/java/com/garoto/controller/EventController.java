package com.garoto.controller;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garoto.model.Event;
import com.garoto.model.User;
import com.garoto.repository.EventRepository;
import com.garoto.repository.UserRepository;



@Controller
public class EventController {
	
	@Autowired
	private EventRepository eventRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private ObjectMapper mapper;
	
	@GetMapping ("/api/ack")
	public @ResponseBody String ack (){		
		return "ACK";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/api/updateEvent", method = RequestMethod.POST)
	public String updateEvent(@RequestBody Map<String, Object> payload) {
		
		Long id = Long.parseLong((String)payload.get("id"));
		String allowedUserStr = (String)payload.get("allowedUser");
		String eventNameStr = (String)payload.get("name");
		String oldEventName = "";
		
		// Getting the event from the database
		Event event;
		try {
			event = eventRepo.findById(id).get();
			if (event == null) {
				return "event_not_found";
			} else {
				event.setLastUpdate(LocalDate.now());
				User allowedUser = userRepo.findOneByUsername(allowedUserStr);
				if (allowedUser == null) {
					return "user_not_found";
				} else {
					
					// Checking whether the event has change the name
					if (!eventNameStr.equals(event.getName())) {
						oldEventName = event.getName();
						event.setName(eventNameStr);
					}					
					event.setAllowedUser(allowedUser);
					eventRepo.save(event);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "event_database_error";
		}
		
		
		// Updating the json file
		try {
			
			String eventFolderName;
			if (oldEventName.equals("")) {
				eventFolderName = "events/" + event.getName() + "_" + event.getId().toString();
			} else {
				eventFolderName = "events/" + oldEventName + "_" + event.getId().toString();
			}
			
			File folder = new File (eventFolderName);
			
			if (!folder.exists()) {
				return "event_folder_not_found";
			}			
			
			
			if (!oldEventName.equals("")) {				
				// In this case, the event name has change, so it's needed to clean and create a new folder
				FileUtils.cleanDirectory(folder); 
				folder.delete();
				
				// Creating the event folder using the possible new name
				folder.mkdirs();
			}
			
			mapper.writeValue(new File(eventFolderName + "/eventData.json"), payload);		
					
		} catch (Exception e) {
			e.printStackTrace();
			return "event_folder_creation_error";
		}

		return "updated";
	}	
	
	@ResponseBody
	@RequestMapping(value = "/api/deleteEvent", method = RequestMethod.POST)
	public String deleteEvent(@RequestParam("id") Long id) {
		
		// Deleting the event from the database
		Event event;
		try {
			event = eventRepo.findById(id).get();
			if (event == null) {
				return "event_not_found";
			} else {
				eventRepo.delete(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "event_database_error";
		}
		
		// Deleting the event folder
		try {
			File folder = new File ("events/" + event.getName() + "_" + event.getId().toString());
			FileUtils.cleanDirectory(folder); 
			folder.delete();
			
		} catch (Exception e) {
			e.printStackTrace();
			return "event_folder_deletion_error";
		}
		
		return "deleted";
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/createNewEvent", method = RequestMethod.POST)
	public String createNewEvent(@RequestBody Map<String, Object> payload) {		
		
		
		// Getting some information from the json file before proceed the event creation
		String eventName = (String)payload.get("name");
		String allowedUserStr = (String)payload.get("allowedUser");
		String execDateStr = (String)payload.get("date");
		LocalDate execDate = LocalDate.parse(execDateStr);
		
		
		User allowedUser;
		try {		
			// Checking if the allowed user exists in the database
			allowedUser = userRepo.findOneByUsername(allowedUserStr);
			if (allowedUser == null) {
				return "user_not_found";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "user_database_error";
		}
			
		Event event;
		try {
			// Saving the event in the database
			event = new Event(eventName, LocalDate.now(), execDate, allowedUser);			
			event = eventRepo.save(event);
		} catch (Exception e) {
			e.printStackTrace();
			return "event_database_error";
		}
			
		try {
			// Creating the event folder and json file			
			String eventFolderName = "events/" + eventName + "_" + event.getId();
			payload.put("id", event.getId().toString());  
			File folder = new File (eventFolderName);
			
			if (folder.exists()) {
				return "event_exist";
			} else {
				folder.mkdirs();
				mapper.writeValue(new File(eventFolderName + "/eventData.json"), payload);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			return "event_folder_creation_error";
		}
		
		return "created";
	
	}
		
	
		
}
