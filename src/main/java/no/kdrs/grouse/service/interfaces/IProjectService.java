package no.kdrs.grouse.service.interfaces;


import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by tsodring on 9/25/17.
 */
public interface IProjectService {

    Page<Project> findAll(Pageable page);

    Project findById(Long id);

    Project createProject(Project project);

    Project createProjectFromTemplate(Long templateId);

    Project update(Long id, PatchObjects patchObjects)
            throws EntityNotFoundException;

    Page<Project> findByOwnedBy(String ownedBy, Pageable pageable);

    void delete(Long id);

    List<ProjectRequirement> findByProjectIdOrderByProjectName(
            Long projectId, String functionalityNumber);

    List<ProjectFunctionality> findFunctionalityForProjectByType(
            Long projectId, String type);

    Iterable<Project> findByOwnedBy(String username);
}
