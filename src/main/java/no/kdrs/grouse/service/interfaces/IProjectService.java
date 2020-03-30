package no.kdrs.grouse.service.interfaces;


import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

/**
 * Created by tsodring on 9/25/17.
 */
public interface IProjectService {

    Page<Project> findAll(Pageable page);

    Project findById(UUID projectId);

    Project createProject(Project project);

    Project createProjectFromTemplate(Project project, UUID templateId);

    Project update(UUID projectId, PatchObjects patchObjects)
            throws EntityNotFoundException;

    Page<Project> findByOwnedBy(String ownedBy, Pageable pageable);

    void delete(UUID projectId);

    List<ProjectRequirement> findByProjectIdOrderByProjectName(
            UUID projectId, String functionalityNumber);

    Page<ProjectFunctionality> findFunctionalityForProjectByType(
            Pageable pageable, UUID projectId, String type);

    Iterable<Project> findByOwnedBy(String username);

    ProjectFunctionality createFunctionality(
            UUID projectId,
            ProjectFunctionality projectFunctionality);
}
