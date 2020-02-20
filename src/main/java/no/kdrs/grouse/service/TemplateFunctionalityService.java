package no.kdrs.grouse.service;

import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.persistence.ITemplateFunctionalityRepository;
import no.kdrs.grouse.service.interfaces.ITemplateFunctionalityService;
import no.kdrs.grouse.utils.PatchObject;
import no.kdrs.grouse.utils.PatchObjects;
import no.kdrs.grouse.utils.exception.PatchMisconfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Service
@Transactional
public class TemplateFunctionalityService
        implements ITemplateFunctionalityService {

    private static final Logger logger =
            LoggerFactory.getLogger(TemplateFunctionalityService.class);

    private ITemplateFunctionalityRepository functionalityRepository;

    public TemplateFunctionalityService(ITemplateFunctionalityRepository
                                                functionalityRepository) {
        this.functionalityRepository = functionalityRepository;
    }

    @Override
    public List<TemplateFunctionality> findAll() {
        return functionalityRepository.findAll();
    }

    @Override
    public List<TemplateFunctionality>
    findByShowMeAndReferenceParentFunctionality(
            Boolean menuItem, String parent) {
        TemplateFunctionality templateFunctionality = new TemplateFunctionality.
                FunctionalityBuilder().functionalityNumber(parent).build();
        return functionalityRepository.
                findByShowMeAndReferenceParentTemplateFunctionality(
                        menuItem, templateFunctionality);
    }

    @Override
    public TemplateFunctionality findById(Long id) {
        return getFunctionalityOrThrow(id);
    }

    @Override
    public TemplateFunctionality findByFunctionalityNumber(
            String functionalityNumber) {
        TemplateFunctionality templateFunctionality =
                functionalityRepository.findByFunctionalityNumber
                        (functionalityNumber);
        if (templateFunctionality == null) {
            throw new EntityNotFoundException("No TemplateFunctionality " +
                    " exists with Id " + functionalityNumber);
        }
        return templateFunctionality;

    }

    @Override
    public TemplateFunctionality save(
            TemplateFunctionality templateFunctionality) {
        return functionalityRepository.save(templateFunctionality);
    }

    @Override
    public TemplateFunctionality updateProjectFunctionality(
            PatchObjects patchObjects, Long requirementNumber) {

        TemplateFunctionality functionality =
                getFunctionalityOrThrow(requirementNumber);

        for (PatchObject patchObject : patchObjects.getPatchObjects()) {
            if ("replace" .equalsIgnoreCase(patchObject.getOp())
                    && null != patchObject.getPath()
                    && patchObject.getPath().length() > 2
                    && null != patchObject.getValue()) {
                String path = patchObject.getPath();
                if ("/" .equals(path.substring(0, 1))) {
                    path = path.substring(1);
                }
                String methodName = "set" + path.substring(0, 1)
                        .toUpperCase() + path.substring(1);
                try {
                    Method method = functionality.getClass()
                            .getMethod(methodName, String.class);
                    method.invoke(functionality, patchObject.getValue());
                } catch (SecurityException | NoSuchMethodException |
                        IllegalArgumentException | IllegalAccessException |
                        InvocationTargetException e) {
                    String error = "Cannot find internal method from Patch : " +
                            patchObject.toString() + " : " + e.getMessage();
                    logger.error(error);
                    throw new PatchMisconfigurationException(error);
                }
            } else {
                String error = "Cannot handle this PatchObject : " +
                        patchObject.toString();
                logger.error(error);
                throw new PatchMisconfigurationException(error);
            }
        }
        return functionality;
    }

    @Override
    public void delete(Long id) {
        functionalityRepository.deleteById(id);
    }

    @Override
    public void createTemplateRequirement(
            Long templateFunctionalityId,
            TemplateRequirement templateRequirement) {

    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid TemplateFunctionality back. If
     * there is no valid TemplateFunctionality, a EntityNotFoundException
     * exception is thrown
     *
     * @param id The systemId of the functionality object to retrieve
     * @return the functionality object
     */
    private TemplateFunctionality getFunctionalityOrThrow(@NotNull Long id)
            throws EntityNotFoundException {
        return functionalityRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No TemplateFunctionality exists " +
                                        "with Id " + id));
    }
}
