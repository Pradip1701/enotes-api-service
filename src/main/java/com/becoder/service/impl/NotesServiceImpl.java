package com.becoder.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesDto.CategoryDto;
import com.becoder.dto.NotesResponse;
import com.becoder.entity.FileDetails;
import com.becoder.entity.Notes;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.CategoryRepository;
import com.becoder.repository.FileRepository;
import com.becoder.repository.NotesRepository;
import com.becoder.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService{
	
	@Autowired
	private NotesRepository notesRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Value("${file.upload.path}")
	private String uploadpath;
	
	@Autowired
	private FileRepository fileRepo;
	
	@Override
	public Boolean saveNotes(String notes,MultipartFile file) throws Exception {
		
		ObjectMapper ob=new ObjectMapper();
		NotesDto notesDto=ob.readValue(notes,NotesDto.class);
		
        if(!ObjectUtils.isEmpty(notesDto.getId()))
        {
        	updateNotes(notesDto,file);
        	return true;
        }
        
		//category validation
		checkCategoryExist(notesDto.getCategory());
	
		Notes note=mapper.map(notesDto, Notes.class);
		
		FileDetails fileDetails=saveFileDetails(file);
		
		if(!ObjectUtils.isEmpty(fileDetails))
		{
			note.setFileDetails(fileDetails);
		}else {
			note.setFileDetails(null);
		}
		
		Notes saved=notesRepo.save(note);
		if(!ObjectUtils.isEmpty(saved))
		{
			return true;
		}
		return false;
	}

	
	private void updateNotes(NotesDto notesDto, MultipartFile file) throws ResourceNotFoundException, IOException {
		
		Notes existingNotes=notesRepo.findById(notesDto.getId())
				.orElseThrow(()->new ResourceNotFoundException("id is not valid"));
		
		existingNotes.setTitle(notesDto.getTitle());
		existingNotes.setDescription(notesDto.getDescription());
		if (file != null && !file.isEmpty()) {
		    // user sent a new file -> save it and replace the old one
		    FileDetails fileDetails = saveFileDetails(file);
		    existingNotes.setFileDetails(fileDetails);
		}
		// else: do nothing, the old file details stay as they are
		notesRepo.save(existingNotes);
		
	}


	private FileDetails saveFileDetails(MultipartFile file) throws IOException {

		if(!ObjectUtils.isEmpty(file) && !file.isEmpty())
		{
			FileDetails saved=new FileDetails();
			saved.setOriginalFileName(file.getOriginalFilename());
			saved.setDisplayFileName(getDisplayFileName(file.getOriginalFilename()));
			
			String exte=FilenameUtils.getExtension(file.getOriginalFilename());
			String name=FilenameUtils.removeExtension(file.getOriginalFilename());
		    String rndname=UUID.randomUUID().toString();
		    rndname=rndname+"."+exte;
			saved.setUploadFileName(rndname);
			saved.setFileSize(file.getSize());
			
			File saveFile=new File(uploadpath);
			if(!saveFile.exists())
			{
				saveFile.mkdir();
			}
			
			// path : enotesapiservice/notes/java.pdf
			String storePath=uploadpath.concat(rndname);
			
			saved.setPath(storePath);
			
			//upload file
			long upload=Files.copy(file.getInputStream(), Paths.get(storePath));
			if(upload!=0)
			{
				FileDetails savefile=fileRepo.save(saved);
				return savefile;
			}
			
		}
		return null;
	}


	private String getDisplayFileName(String originalFilename) {

		String orgname=originalFilename;
		String exte=FilenameUtils.getExtension(orgname);
		String name=FilenameUtils.removeExtension(orgname);
		
		if(name.length()>8)
		{
			name=name.substring(0, 8);
		}
		String fileDisplayname=name+"."+exte;
		return fileDisplayname;
	}


	private void checkCategoryExist(CategoryDto category) throws ResourceNotFoundException {
		
		categoryRepo.findById(category.getId()).orElseThrow(()-> new ResourceNotFoundException("category id invalid"));
	}

	
	@Override
	public List<NotesDto> getAllNotes() {
		
		return notesRepo.findAll()
				.stream()
				.map(note->mapper.map(note,NotesDto.class)).toList();
	}


	public FileDetails getFileDetails(Integer id) throws Exception {
		FileDetails filedtls=fileRepo.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("id is not available"));
		
		return filedtls;
	}


	public byte[] downloadFile(FileDetails filedtls) throws Exception{
		FileInputStream io=new FileInputStream(filedtls.getPath());
		
		return StreamUtils.copyToByteArray(io);
	}
	
	@Override
	public NotesResponse getAllNotesPage(Integer id) {
		
		Pageable pageable=PageRequest.of(1, 5);
		Page<Notes> pageNotes= notesRepo.findByCreatedByAndIsDeletedFalse(id,pageable);
		
		List<NotesDto> notesDto=pageNotes.get().map(n->mapper.map(n,NotesDto.class)).toList();
		
		NotesResponse notes=NotesResponse.builder()
				                         .notes(notesDto)
                                         .pageNo(pageNotes.getNumber())
                                         .pageSize(pageNotes.getSize())
                                         .totalElements(pageNotes.getTotalElements())
                                         .totalPages(pageNotes.getTotalPages())
                                         .isFirst(pageNotes.isFirst())
                                         .isLast(pageNotes.isLast())
                                         .build();
			
				return notes;                                  
	}

	@Override
	public void deleteNotes(Integer id) throws ResourceNotFoundException {

		Notes deletedNotes=notesRepo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("id is invalid :not found"));
		
		deletedNotes.setIsDeleted(true);
		deletedNotes.setDeletedOn(new Date());
		notesRepo.save(deletedNotes);
	}

	@Override
	public void restoreNotes(Integer id) throws ResourceNotFoundException {

		Notes restoreNote=notesRepo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("id is invalid :not found"));
		
		restoreNote.setIsDeleted(false);
		restoreNote.setDeletedOn(null);
		notesRepo.save(restoreNote);
	}


	@Override
	public List<Notes> recycleNotes(Integer id) {
		
        List<Notes> recycled=notesRepo.findByCreatedByAndIsDeletedTrue(id);
		
		if(!CollectionUtils.isEmpty(recycled))
		{
		return recycled;
		}
		else
			return null;
		
		//in this api i return entity but this is not good practice bcz when we have field like passwords then
		//its essentail to keep it private and dto exactly so this bcz in dtos we take field as our flexibilty
		//so use dtos and in this api use or return dto means make return tpye dto
	}


	

}
