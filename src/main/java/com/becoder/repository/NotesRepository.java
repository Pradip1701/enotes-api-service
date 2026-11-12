package com.becoder.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.becoder.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes,Integer>{

	Page<Notes> findByCreatedBy(Integer id, Pageable pageable);

	List<Notes> findByCreatedByAndIsDeletedTrue(Integer id);

	Page<Notes> findByCreatedByAndIsDeletedFalse(Integer id, Pageable pageable);

	List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutDate);

	  
	@Query("select n from Notes n where (Lower(n.title) like lower(concat('%',:keyword,'%')) "  //here atlast space imp
		   +"or Lower(n.description) like lower(concat('%',:keyword,'%')) "         //here at last space is imp
		   +"or Lower(n.category.name) like lower(concat('%',:keyword,'%'))) "      //here space is imp
		   +"and isDeleted=false "
		   +"and createdBy=:id") // in this query spaces are also imp
	Page<Notes> findNotesWithSearch(Pageable pageable, Integer id, String keyword);
	

}
