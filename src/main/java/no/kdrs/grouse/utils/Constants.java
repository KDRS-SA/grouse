package no.kdrs.grouse.utils;

/**
 * Application constants.
 */
public final class Constants {

    // Used as part of webaddress
    public static final String SLASH = "/";
    public static final String QUESTION_MARK = "?";
    public static final String EQUALS = "=";
    public static final String ESCAPE = "\\";
    public static final String AMPERSAND = "&";
    public static final String REQUIREMENT = "krav";
    public static final String FUNCTIONALITY = "function";
    public static final String SELF = "self";
    public static final String USER = "user";
    public static final String DOCUMENT = "document";
    public static final String PROJECT = "project";
    public static final String TEMPLATE = "template";
    public static final String GROUSE = "grouse";
    public static final String CONTEXT_PATH = GROUSE;
    public static final String SYSTEM_USER = "grouse";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ACCOUNT_NON_EXPIRED = "account_non_expired";
    public static final String CREDENTIALS_NON_EXPIRED =
            "credentials_non_expired";
    public static final String ACCOUNT_NON_LOCKED = "account_non_locked";
    public static final String ENABLED = "enabled";
    public static final String ACCOUNT_CREATED_DATE = "account_created_date";

    public static final String PROJECT_REQUIREMENT = "projectRequirement";
    public static final String PROJECT_FUNCTIONALITY = "projectFunctionality";
    public static final String TEMPLATE_REQUIREMENT = "templateRequirement";
    public static final String TEMPLATE_FUNCTIONALITY = "templateFunctionality";
    public static final String REQUIREMENT_TYPE = "krav_type";
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final Integer DEFAULT_PAGE_NUMBER = 0;
    public static final Integer DEFAULT_PAGE_SIZE = 20;
    public static final String ETAG = "ETAG";
    public static final String REPLACE = "replace";

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
    public static final String AUTHORITY_NAME = "authority_name";
    public static final String AUTHORITIES = "authorities";
    public static final String AUTHORITY = "authority";

    public static final String PROJECT_TABLE_NAME = "projects";

    public static final String TEMPLATE_FUNCTIONALITY_AREAS_TABLE_NAME =
            "template_functionality_areas";
    public static final String TEMPLATE_REQUIREMENT_TABLE_NAME =
            "template_requirements";
    public static final String USER_TABLE_NAME = "user";
    public static final String AUTHORITY_TABLE_NAME = "authority";

    // Join tables
    public static final String USER_AUTHORITY_JOIN = "user_authority";

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

    public static final String ETAG_NAN =
            "ETAG value is not numeric! ETAG must be a number >= 0.";
    public static final String ETAG_MISSING =
            "ETAG value is missing! Patch request requires an ETAG!";
    public static final String ETAG_VALUE_LESS_0 =
            "ETAG value is < 0! ETAG values start at 0!";
}
