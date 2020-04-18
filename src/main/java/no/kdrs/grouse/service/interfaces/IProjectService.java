package no.kdrs.grouse.service.interfaces;


import no.kdrs.grouse.model.AccessControl;
import no.kdrs.grouse.model.GrouseUser;
import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

public interface IProjectService {

    Page<Project> findAll(Pageable page);

    Page<Project> findAllForUser(Pageable page);

    Page<Project> findAllForUser(String username, Pageable page);

    Project findById(UUID projectId);

    Project createProject(Project project);

    Project createProjectFromTemplate(Project project, UUID templateId);

    Project update(UUID projectId, PatchObjects patchObjects)
            throws EntityNotFoundException;

    void delete(UUID projectId);

    Page<ProjectFunctionality> findFunctionalityForProjectByType(
            Pageable pageable, UUID projectId, String type);

    Iterable<Project> findByOwnedBy(String username);

    ProjectFunctionality createFunctionality(
            UUID projectId,
            ProjectFunctionality projectFunctionality);

    Project updateProjectFinalised(UUID projectId);

    AccessControl shareProject(UUID projectId, String username);

    Page<GrouseUser> getProjectUsers(UUID projectId, Pageable pageable);

    void deleteProjectShare(UUID projectId, String username);
}
