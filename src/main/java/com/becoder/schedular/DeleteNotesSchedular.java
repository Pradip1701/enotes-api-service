package com.becoder.schedular;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.becoder.entity.Notes;
import com.becoder.repository.NotesRepository;

@Component
public class DeleteNotesSchedular {
	
	
	@Autowired
	private NotesRepository notesRepo;
	
	//@Scheduled(cron="* * * ? * *"                       //runs for every second
	@Scheduled(cron="0 0 0 * * ?")           //runs every days at night 12 am
	public void deleteNotes()
	{
		LocalDateTime cutDate=LocalDateTime.now().minusDays(7);
		List<Notes> deleteNotes=notesRepo.findAllByIsDeletedAndDeletedOnBefore(true,cutDate);
		
		notesRepo.deleteAll(deleteNotes);
	}

}
