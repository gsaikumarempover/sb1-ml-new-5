package com.mladmin.portal.service;

import com.mladmin.portal.entity.Project;
import com.mladmin.portal.entity.User;
import com.mladmin.portal.repository.ProjectRepository;
import com.mladmin.portal.repository.UserRepository;
import com.mladmin.portal.dto.ProjectInput;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Cacheable(value = "projects")
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Cacheable(value = "projects", key = "#id")
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Cacheable(value = "projects", key = "#username")
    public List<Project> getProjectsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return projectRepository.findByOwner(user);
    }

    @Transactional
    @CacheEvict(value = "projects", allEntries = true)
    public Project createProject(ProjectInput input, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = new Project();
        project.setName(input.getName());
        project.setDescription(input.getDescription());
        project.setOwner(user);

        return projectRepository.save(project);
    }

    @Transactional
    @CacheEvict(value = "projects", key = "#id")
    public Project updateProject(Long id, ProjectInput input) {
        Project project = getProjectById(id);
        project.setName(input.getName());
        project.setDescription(input.getDescription());

        return projectRepository.save(project);
    }

    @Transactional
    @CacheEvict(value = "projects", allEntries = true)
    public Boolean deleteProject(Long id) {
        Project project = getProjectById(id);
        projectRepository.delete(project);
        return true;
    }
}