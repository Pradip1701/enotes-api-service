package com.becoder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesResponse;
import com.becoder.entity.Category;
import com.becoder.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes,Integer>{

	Page<Notes> findByCreatedBy(Integer id, Pageable pageable);

	List<Notes> findByCreatedByAndIsDeletedTrue(Integer id);

	Page<Notes> findByCreatedByAndIsDeletedFalse(Integer id, Pageable pageable);
	

}
