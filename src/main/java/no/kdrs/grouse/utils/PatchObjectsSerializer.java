package no.kdrs.grouse.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PatchObjectsSerializer
        extends StdSerializer<PatchObjects> {

    private static final Logger logger =
            LoggerFactory.getLogger(PatchObjectsSerializer.class);

    public PatchObjectsSerializer() {
        super(PatchObjects.class);
    }

    @Override
    public void serialize(
            PatchObjects patchObjects, JsonGenerator jgen,
            SerializerProvider serializerProvider) throws IOException {

        jgen.writeStartArray();

        for (PatchObject patchObject : patchObjects.getPatchObjects()) {
            jgen.writeStartObject();
            jgen.writeObjectField("op", patchObject.getOp());
            jgen.writeObjectField("path", patchObject.getPath());
            // Do we have to handle various types here? String, Integer, Boolean
            jgen.writeObjectField("value", patchObject.getValue());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
    }
}
