package no.kdrs.grouse.persistence;

import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.ProjectFunctionality;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjectFunctionalityRepository
        extends CrudRepository<ProjectFunctionality, Long> {

    List<ProjectFunctionality> findByReferenceProjectAndTypeAndShowMe(
            Project project, String type, Boolean showMe);

    List<ProjectFunctionality> findByReferenceProject(Project project);

    List<ProjectFunctionality>
    findByFunctionalityNumber(String functionalityNumber);

    List<ProjectFunctionality> findByFunctionalityNumberAndReferenceProject(
            String functionalityNumber, Project project);
}
