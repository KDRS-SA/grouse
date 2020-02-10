package no.kdrs.grouse.service.interfaces;


import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;

import java.util.List;

/**
 * Created by tsodring on 9/25/17.
 */
public interface IProjectService {
    List<Project> findAll();
    Project findById(Long id);

    Project createProject(Project project);

    Project update(Long id, Project project) throws Exception;
    void delete(Long id);
    List<Project> findByOwnedBy(String ownedBy);
    List<ProjectRequirement> findByProjectIdOrderByProjectName (
            Long projectId, String functionalityNumber);
    List<ProjectFunctionality> findFunctionalityForProjectByType(
            Long projectId, String type);

}
