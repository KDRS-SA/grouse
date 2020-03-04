package no.kdrs.grouse.utils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = PatchObjectsDeserializer.class)
@JsonSerialize(using = PatchObjectsSerializer.class)
public class PatchObjects {

    private List<PatchObject> patchObjects = new ArrayList<>();

    public PatchObjects() {
    }

    public PatchObjects(PatchObject patchObject) {
        patchObjects.add(patchObject);
    }

    public PatchObjects(List<PatchObject> patchObjects) {
        this.patchObjects.addAll(patchObjects);
    }

    public void add(PatchObject patchObject) {
        patchObjects.add(patchObject);
    }

    public List<PatchObject> getPatchObjects() {
        return patchObjects;
    }
}
