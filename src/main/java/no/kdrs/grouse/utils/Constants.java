package no.kdrs.grouse.utils;

/**
 * Application constants.
 */
public final class Constants {

    // Used as part of webaddress
    public static final String SLASH = "/";
    public static final String REQUIREMENT = "krav";
    public static final String FUNCTIONALITY = "function";
    public static final String SELF = "self";
    public static final String USER = "bruker";
    public static final String DOCUMENT = "dokument";
    public static final String PROJECT = "prosjekt";
    public static final String TEMPLATE = "template";
    public static final String GROUSE = "grouse";
    public static final String PROJECT_REQUIREMENT = "projectRequirement";
    public static final String PROJECT_FUNCTIONALITY = "projectFunctionality";
    public static final String TEMPLATE_REQUIREMENT = "templateRequirement";
    public static final String TEMPLATE_FUNCTIONALITY = "templateFunctionality";
    public static final String REQUIREMENT_TYPE = "krav_type";

    public static final String PROJECT_NUMBER = "projectNumber";
    public static final String TEMPLATE_ID = "templateId";
    public static final String PROJECT_NUMBER_PARAMETER = "{" +
            PROJECT_NUMBER + "}";
    public static final String FUNCTIONALITY_PARAMETER = "{" +
            FUNCTIONALITY + "}";
    public static final String TEMPLATE_ID_PARAMETER = "{" +
            TEMPLATE_ID + "}";

    // Column names
    public static final String REQUIREMENT_NAME = "requirement_name";
    public static final String REQUIREMENT_PK_ID = "requirement_id";
    public static final String FUNCTIONALITY_NAME = "functionality_name";
    public static final String FUNCTIONALITY_PK_ID = "functionality_id";
    public static final String TEMPLATE_NAME = "template_name";
    public static final String TEMPLATE_PK_ID = "template_id";
    public static final String CREATED_DATE = "created_date";
    public static final String CHANGED_DATE = "changed_date";
    public static final String OWNED_BY = "owned_by";
    public static final String VERSION = "version";

    public static final String TEMPLATE_FUNCTIONALITY_AREAS =
            "template_functionality_areas";

    // Table cell ordering
    /**
     * Identify the column number for requirements. The title e.g. 'Kravnr' is
     * in COLUMN_TITLE or the first(0) column while the actual text e.g '5.2.1' is in
     * column COLUMN_DESCRIPTION or the second (1) column.
     */
    public static final Integer COLUMN_NUMBER = 0;
    public static final Integer COLUMN_FUNCTIONALITY_TITLE = 1;
    public static final Integer COLUMN_PRIORITY = 2;
    public static final Integer COLUMN_ANSWER = 3;
    public static final Integer COLUMN_REFERENCE = 4;

    public static final Integer REQUIRMENT_TABLE_NUMBER_COLUMNS = 5;

    public static final Integer REQUIRMENT_TABLE_NUMBER_WIDTH = 1000;
    public static final Integer REQUIRMENT_TABLE_TITLE_WIDTH = 4000;
    public static final Integer REQUIRMENT_TABLE_PRIORITY_WIDTH = 1000;
    public static final Integer REQUIRMENT_TABLE_ANSWER_WIDTH = 1000;
    public static final Integer REQUIRMENT_TABLE_REFERENCE_WIDTH = 1000;

    // Text of title / words
    public static final String TEXT_REQUIREMENT_NUMBER = "Nr.";
    public static final String TEXT_REQUIREMENT_TYPE = "Krav type";
    public static final String TEXT_REQUIREMENT_PRIORITY = "Pri";
    public static final String TEXT_EXPLANATION = "Forklaring";
    public static final String TEXT_REQUIREMENT_ANSWER = "Svar";
    public static final String TEXT_REFERENCE_REQUIREMENT = "Ref.";
    public static final String TEXT_CONSEQUENCE = "Konsekvens";
    public static final String TEXT_REQUIREMENT_GENERAL_REQUIREMENT = "Generelt krav";

    // Add RELS
    public static final String REL_LOGIN_OAUTH = "login OAuth2";
    public static final String REL_LOGOUT_OAUTH = "logout OAuth2";
    public static final String REL_USER = "konto";
}
