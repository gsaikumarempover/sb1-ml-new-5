package com.mladmin.portal.repository;

import com.mladmin.portal.entity.Project;
import com.mladmin.portal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwner(User owner);
}