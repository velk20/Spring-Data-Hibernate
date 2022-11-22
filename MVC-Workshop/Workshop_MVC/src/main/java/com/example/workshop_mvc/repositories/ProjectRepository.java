package com.example.workshop_mvc.repositories;

import com.example.workshop_mvc.models.entitiy.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
}
