package com.vicras.blog.repository;

import com.vicras.blog.model.CommonUser;
import com.vicras.blog.model.Department;
import com.vicras.blog.model.Document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByCreator(CommonUser creator);

    @Query("select d from Document d where d.creator.department = ?1")
    List<Document> findAllByDepartment(Department department);

    List<Document> findAllByApproved(boolean approved);
}
