package no.kdrs.grouse.model;



import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsodring on 2/2/17.
 */
public class APIDetails extends RepresentationModel
        implements Serializable {
    protected List<APIDetail> aPIDetails = new ArrayList<>();

    public APIDetails() {
    }

    public boolean addApiDetal(APIDetail apiDetail) {
        return aPIDetails.add(apiDetail);
    }
    public List<APIDetail> getApiDetails() {
        return aPIDetails;
    }

    public void setApiDetails(List<APIDetail> aPIDetails) {
        this.aPIDetails = aPIDetails;
    }
}
