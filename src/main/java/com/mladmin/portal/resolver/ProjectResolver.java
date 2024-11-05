package com.mladmin.portal.resolver;

import com.mladmin.portal.entity.Project;
import com.mladmin.portal.service.ProjectService;
import com.mladmin.portal.dto.ProjectInput;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectResolver {

    private final ProjectService projectService;

    @QueryMapping
    public List<Project> projects() {
        return projectService.getAllProjects();
    }

    @QueryMapping
    public Project project(@Argument Long id) {
        return projectService.getProjectById(id);
    }

    @QueryMapping
    public List<Project> myProjects() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return projectService.getProjectsByUsername(username);
    }

    @MutationMapping
    public Project createProject(@Argument ProjectInput input) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return projectService.createProject(input, username);
    }

    @MutationMapping
    public Project updateProject(@Argument Long id, @Argument ProjectInput input) {
        return projectService.updateProject(id, input);
    }

    @MutationMapping
    public Boolean deleteProject(@Argument Long id) {
        return projectService.deleteProject(id);
    }
}