# Grouse

## Maven testing

In src/main/test there  are a number of integration tests that test endpoint functionality

## Manual curl tests

First get a OAuth2 token

    curl -X POST  -H 'Authorization: Basic Z3JvdXNlLWNsaWVudDpzZWNyZXQ=' http://localhost:9294/grouse/oauth/token -d grant_type=password -d username=user@example.com -d password=password

This should give you the following

    {
      "access_token": "def80a60-6ecf-4ebe-bcb4-ef76b8d6b471",
      "token_type": "bearer",
      "refresh_token": "a2fcebd3-9bb4-4379-8373-c1cac4916a06",
      "expires_in": 30674,
      "scope": "all"
    }

Once you have a token you can call root of the application to see what you can do:

    curl -v -X GET http://localhost:9294/grouse/ -H 'Accept: application/hal+json' -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471'  


You should get the following payload back:

    {
      "_links": {
        "login OAuth2": {
          "href": "http://localhost:9294/grouse/oauth/token?parameters={parameters}",
          "templated": true
        },
        "konto": {
          "href": "http://localhost:9294/grouse/user/user@example.com"
        },
        "project-list": {
          "href": "http://localhost:9294/grouse/project"
        },
        "template-list": {
          "href": "http://localhost:9294/grouse/template"
        },
        "logout OAuth2": {
          "href": "http://localhost:9294/grouse/oauth/revoke-token"
        }
      }
    }

The **project-list** REL can be use to get a list of projects, while the **template-list** REL can give a list of templates you can use to create a project. First get a list of templates:

    curl -v -X GET http://localhost:9294/grouse/template -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471'  
  
You will get something like the following:    
    
    {
      "_embedded": {
        "templates": [
          {
            "templateId": "b920dd07-89bd-4702-b1e6-b36910d1482b",
            "templateName": "Noark 5",
            "createdDate": "2020-03-14T13:59:26+01:00",
            "lastModifiedDate": "2020-03-14T13:59:26+01:00",
            "ownedBy": "grouse",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/template/b920dd07-89bd-4702-b1e6-b36910d1482b"
              },
              "project": {
                "href": "http://localhost:9294/grouse/template/b920dd07-89bd-4702-b1e6-b36910d1482b/project"
              }
            }
          }
        ]
      },
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/template?page=0&size=10"
        }
      },
      "page": {
        "size": 10,
        "totalElements": 1,
        "totalPages": 1,
        "number": 0
      }
    }
     
## Get a list of templates

The first step to do anything useful in grouse is to retrieve a list of templates. 

    curl -v -X GET http://localhost:9294/grouse/template -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471'

This will return the following payload:

    {
      "_embedded": {
        "templates": [
          {
            "templateId": "b920dd07-89bd-4702-b1e6-b36910d1482b",
            "templateName": "Noark 5",
            "createdDate": "2020-03-14T13:59:26+01:00",
            "lastModifiedDate": "2020-03-14T13:59:26+01:00",
            "ownedBy": "grouse",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/template/b920dd07-89bd-4702-b1e6-b36910d1482b"
              },
              "project": {
                "href": "http://localhost:9294/grouse/template/b920dd07-89bd-4702-b1e6-b36910d1482b/project"
              }
            }
          }
        ]
      },
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/template?page=0&size=10"
        }
      },
      "page": {
        "size": 10,
        "totalElements": 1,
        "totalPages": 1,
        "number": 0
      }
    }

Make a note of the *href* corresponding to the "project" *rel*. You can use this to create a project from that template. In this case the relevant *rel*/*href* are:

    "project": {
        "href": "http://localhost:9294/grouse/template/b920dd07-89bd-4702-b1e6-b36910d1482b/project"
    }

## Create a project from a template

Use the following example to create (POST)  a project from a template. Note. Currently you have to provide a Project object (--data ) to give the project a name 

     curl -v -X POST http://localhost:9294/grouse/template/b920dd07-89bd-4702-b1e6-b36910d1482b/project -H 'Accept: application/hal+json' -H 'Content-type: application/json'  --data '{ "projectName": "Requirements project"}' -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471'

This gives the following result:

    {
      "projectId": 718,
      "projectName": "Requirements project",
      "createdDate": "2020-03-14T17:35:27.377301+01:00",
      "lastModifiedDate": "2020-03-14T17:35:27.377301+01:00",
      "ownedBy": "user@example.com",
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/project/718"
        },
        "function": {
          "href": "http://localhost:9294/grouse/project/718/function"
        },
        "document": {
          "href": "http://localhost:9294/grouse/project/718/document"
        }
      }
    }

Make a note of the *href* corresponding to the "function" *rel*. You can use this to retrieve the top level functionality descriptions. In this case the relevant *rel*/*href* are:

     "function": {
               "href": "http://localhost:9294/grouse/project/718/function"
     }

## Retrieve top level functionality descriptions 

    curl -v -X GET http://localhost:9294/grouse/project/718/function -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471'

This will return the following:

    {
      "_embedded": {
        "projectFunctionalities": [
          {
            "projectFunctionalityId": 727,
            "functionalityNumber": "4",
            "title": "Funksjonelle krav til ønsket løsning",
            "showMe": true,
            "processed": false,
            "active": false,
            "type": "mainmenu",
            "ownedBy": "user@example.com",
            "hasRequirements": false,
            "hasFunctionality": false,
            "_links": {
              "function": {
                "href": "http://localhost:9294/grouse/projectFunctionality/727/function"
              },
              "self": {
                "href": "http://localhost:9294/grouse/projectFunctionality/727"
              }
            }
          },
          {
            "projectFunctionalityId": 1338,
            "functionalityNumber": "5",
            "title": "Tekniske krav til ønsket løsning",
            "showMe": true,
            "processed": false,
            "active": false,
            "type": "mainmenu",
            "ownedBy": "user@example.com",
            "hasRequirements": false,
            "hasFunctionality": false,
            "_links": {
              "function": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1338/function"
              },
              "self": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1338"
              }
            }
          },
          {
            "projectFunctionalityId": 1378,
            "functionalityNumber": "6",
            "title": "Krav til integrasjoner i ønsket løsning",
            "showMe": true,
            "processed": false,
            "active": false,
            "type": "mainmenu",
            "ownedBy": "user@example.com",
            "hasRequirements": false,
            "hasFunctionality": false,
            "_links": {
              "function": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1378/function"
              },
              "self": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1378"
              }
            }
          }
        ]
      },
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/project/718/function?page=0&size=10"
        }
      },
      "page": {
        "size": 10,
        "totalElements": 3,
        "totalPages": 1,
        "number": 0
      }
    }

Make a note of the *rel*/*href* for each projectFunctionality object. As there is only a "function" *rel*, this means that there are project functionalities to be retrieved from following the *href* associated with the "function" *rel*. The children functionality belonging to project functionality 1338 can be retrieved using: 

    curl -v -X GET http://localhost:9294/grouse/projectFunctionality/1338/function -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471'
 
This results in the following:

    {
      "_embedded": {
        "projectFunctionalities": [
          {
            "projectFunctionalityId": 1339,
            "functionalityNumber": "5.0",
            "title": "Krav til arkivdatabase",
            "description": "",
            "consequence": "",
            "explanation": "",
            "showMe": true,
            "processed": false,
            "active": false,
            "type": "submenu",
            "ownedBy": "user@example.com",
            "hasRequirements": false,
            "hasFunctionality": false,
            "_links": {
              "requirement": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1339/requirement"
              },
              "self": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1339"
              }
            }
          },
          {
            "projectFunctionalityId": 1345,
            "functionalityNumber": "5.1",
            "title": "Tekniske krav",
            "description": "",
            "consequence": "",
            "explanation": "",
            "showMe": true,
            "processed": false,
            "active": false,
            "type": "submenu",
            "ownedBy": "user@example.com",
            "hasRequirements": false,
            "hasFunctionality": false,
            "_links": {
              "requirement": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1345/requirement"
              },
              "self": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1345"
              }
            }
          },
          {
            "projectFunctionalityId": 1354,
            "functionalityNumber": "5.2",
            "title": "Krav til servere",
            "description": "",
            "consequence": "",
            "explanation": "",
            "showMe": true,
            "processed": false,
            "active": false,
            "type": "submenu",
            "ownedBy": "user@example.com",
            "hasRequirements": false,
            "hasFunctionality": false,
            "_links": {
              "requirement": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1354/requirement"
              },
              "self": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1354"
              }
            }
          },
          {
            "projectFunctionalityId": 1364,
            "functionalityNumber": "5.3",
            "title": "Krav til klienter",
            "description": "",
            "consequence": "",
            "explanation": "",
            "showMe": true,
            "processed": false,
            "active": false,
            "type": "submenu",
            "ownedBy": "user@example.com",
            "hasRequirements": false,
            "hasFunctionality": false,
            "_links": {
              "requirement": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1364/requirement"
              },
              "self": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1364"
              }
            }
          },
          {
            "projectFunctionalityId": 1372,
            "functionalityNumber": "5.4",
            "title": "Generelle tekniske krav",
            "description": "",
            "consequence": "",
            "explanation": "",
            "showMe": true,
            "processed": false,
            "active": false,
            "type": "submenu",
            "ownedBy": "user@example.com",
            "hasRequirements": false,
            "hasFunctionality": false,
            "_links": {
              "requirement": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1372/requirement"
              },
              "self": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1372"
              }
            }
          }
        ]
      },
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/projectFunctionality/1338/function?page=0&size=10"
        }
      },
      "page": {
        "size": 10,
        "totalElements": 5,
        "totalPages": 1,
        "number": 0
      }
    }

Retrieving a list of requirements using the following:

    curl -v -X GET http://localhost:9294/grouse/projectFunctionality/980/requirement -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471'


results in the following payload:

    {
      "_embedded": {
        "projectRequirements": [
          {
            "requirementId": 981,
            "requirementText": "NOARK 10.2.1 (V): Det skal være mulig å sette opp varslinger (f. eks. i\n                                form av meldingsbokser) når tidsfrister er overskredet m.v.\n                            ",
            "showOrder": 1,
            "priority": "1",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/981"
              }
            }
          },
          {
            "requirementId": 982,
            "requirementText": "Løsningen skal kunne håndtere all saksbehandling elektronisk slik at man\n                                ikke blir tvunget til å opprettholde papirarkiv pga. begrensninger i løsningen.\n                                Eventuelle begrensninger og forutsetninger knyttet til dette skal beskrives.\n                            ",
            "showOrder": 2,
            "priority": "1 (i)",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/982"
              }
            }
          },
          {
            "requirementId": 983,
            "requirementText": "Løsningen skal muliggjøre sikker og effektiv journalføring av sms/chat.\n                                Løsningen må også håndtere innkommende svar på sendte sms/chat. Leverandøren skal\n                                beskrive hvordan løsningen håndterer dette – ved utsendelse av sms/chat fra selve\n                                løsningen, alternativt også mulighet for journalføring av sms til og fra mobiltelefon.\n                            ",
            "showOrder": 3,
            "priority": "1",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/983"
              }
            }
          },
          {
            "requirementId": 984,
            "requirementText": "Leverandøren skal beskrive hvordan tilbudt løsning tilrettelegger for\n                                forenklet registrering jf. NOARK5, og hvilke utfordringer bruk av forenklet registrering\n                                kan medføre for den enkelte bruker, og for kommunen.\n                            ",
            "showOrder": 4,
            "priority": "O (i)",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/984"
              }
            }
          },
          {
            "requirementId": 985,
            "requirementText": "Mottaker av notater skal enkelt kunne avskrive disse, dvs. det skal ikke\n                                være behov for mer enn én operasjon for å registrere et notat som mottatt og avskrevet.\n                            ",
            "showOrder": 5,
            "priority": "1",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/985"
              }
            }
          },
          {
            "requirementId": 986,
            "requirementText": "Det skal være lett å få oversikt over egne restanser –\n                                henvendelser/dokumenter\n                            ",
            "showOrder": 6,
            "priority": "O",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/986"
              }
            }
          },
          {
            "requirementId": 987,
            "requirementText": "Det skal være mulig å flette svarbrev til mange på en enkel måte, dvs. å\n                                produsere likelydende brev til mange mottakere.\n                            ",
            "showOrder": 7,
            "priority": "O",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/987"
              }
            }
          },
          {
            "requirementId": 988,
            "requirementText": "Saksbehandler skal kunne legge på flere mottakere på en inngående\n                                journalpost og ekspedere dokumenter og vedlegg selv (dvs. videresende dette som en ny\n                                utgående post). Ekspedering må skje med godkjente arkivformat.\n                            ",
            "showOrder": 8,
            "priority": "O",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/988"
              }
            }
          },
          {
            "requirementId": 989,
            "requirementText": "Det skal være enkelt å knytte til vedlegg som skal være med til en\n                                journalpost.\n                            ",
            "showOrder": 9,
            "priority": "O",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/989"
              }
            }
          },
          {
            "requirementId": 990,
            "requirementText": "Et hoveddokument må kunne være vedlegg på en annen journalpost.",
            "showOrder": 10,
            "priority": "O",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/990"
              }
            }
          }
        ]
      },
      "_links": {
        "first": {
          "href": "http://localhost:9294/grouse/projectFunctionality/980/requirement?page=0&size=10"
        },
        "self": {
          "href": "http://localhost:9294/grouse/projectFunctionality/980/requirement?page=0&size=10"
        },
        "next": {
          "href": "http://localhost:9294/grouse/projectFunctionality/980/requirement?page=1&size=10"
        },
        "last": {
          "href": "http://localhost:9294/grouse/projectFunctionality/980/requirement?page=1&size=10"
        }
      },
      "page": {
        "size": 10,
        "totalElements": 13,
        "totalPages": 2,
        "number": 0
      }
    }

## Get en individual requirement


    curl -v -X GET http://localhost:9294/grouse/projectRequirement/990 -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471'

This generates the following payload:

    {
      "requirementId": 990,
      "showOrder": 10,
      "requirementText": "Et hoveddokument må kunne være vedlegg på en annen journalpost.",
      "priority": "O",
      "requirementNumber": null,
      "requirement": null,
      "requirementType": null,
      "version": 0,
      "ownedBy": "user@example.com",
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/projectRequirement/990"
        }
      }
    }

Make a note of the ETAG value in the header, or use the value in version when creating a PATCH request to change the requirement. Note, a user can only change _ownedBy_, _showOrder_, _requirementText_, _priority_ and _requirementNumber_ attributes.

To update an allowable field, issue a PATCH request to the href corresponding to the self rel. A Patch object must be used for this:

    curl -v -X PATCH http://localhost:9294/grouse/projectRequirement/990 -H 'ETAG: ""0""' -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471' --data '[{ "op": "replace", "path": "/requirementText", "value": "Oppdatert kravtekst."}]' 

The updated object looks like this:

    {
      "requirementId": 990,
      "showOrder": 10,
      "requirementText": "Oppdatert kravtekst.",
      "priority": "O",
      "requirementNumber": null,
      "requirement": null,
      "requirementType": null,
      "version": 1,
      "ownedBy": "user@example.com",
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/projectRequirement/990"
        }
      }
    }

Note the change in version number. A further update will require this value or to retrieve the ETAG header and reuse it.

## Get en individual functionality

If we go back to the list of functionalities over, we can retrieve a single functionality and change it with PATCH. To get a particular functionality, look for its self rel e.g.: 

    curl -v -X GET http://localhost:9294/grouse/projectFunctionality/1372 -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471' 
    
This will show the individual functionality object:

    {
      "projectFunctionalityId": 1372,
      "functionalityNumber": "5.4",
      "title": "Generelle tekniske krav",
      "description": "",
      "consequence": "",
      "explanation": "",
      "showMe": true,
      "processed": false,
      "active": false,
      "type": "submenu",
      "ownedBy": "user@example.com",
      "version": 0,
      "hasRequirements": false,
      "hasFunctionality": false,
      "_links": {
        "requirement": {
          "href": "http://localhost:9294/grouse/projectFunctionality/1372/requirement"
        },
        "self": {
          "href": "http://localhost:9294/grouse/projectFunctionality/1372"
        }
      }
    }

The self rel can be used to update the object with a PATCH request. Note. Only the following attributes can be changed: _title_, _ownedBy_, _processed_ and _functionalityNumber_.

    curl -v -X PATCH http://localhost:9294/grouse/projectFunctionality/1372 -H 'ETAG: ""0""' -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471' --data '[{ "op": "replace", "path": "/processed", "value": true}]' 

The following payload shows how the _processed_ value is now set to true:

    {
      "projectFunctionalityId": 1372,
      "functionalityNumber": "5.4",
      "title": "Generelle tekniske krav",
      "description": "",
      "consequence": "",
      "explanation": "",
      "showMe": true,
      "processed": true,
      "active": false,
      "type": "submenu",
      "ownedBy": "user@example.com",
      "version": 1,
      "hasRequirements": false,
      "hasFunctionality": false,
      "_links": {
        "requirement": {
          "href": "http://localhost:9294/grouse/projectFunctionality/1372/requirement"
        },
        "self": {
          "href": "http://localhost:9294/grouse/projectFunctionality/1372"
        }
      }
    }

Note the  eTag uses double quotes around the value. That is why the header is `-H 'ETAG: ""0""`

## Get a list of projects

    curl -v -X GET http://localhost:9294/grouse/project -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471'    
    
To get an individual project:
    
        `curl -v -H 'Authorization: Bearer 07fbcd9d-9dd3-440c-8456-bca9f339683e' -X GET http://localhost:9294/grouse/project/112`
    
    
For the following example, two projects are owned by the user:
     
    {
      "_embedded": {
        "projects": [
          {
            "projectId": 718,
            "projectName": "test",
            "createdDate": "2020-03-14T18:15:17+01:00",
            "lastModifiedDate": "2020-03-14T18:15:17+01:00",
            "ownedBy": "user@example.com",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/project/718"
              },
              "function": {
                "href": "http://localhost:9294/grouse/project/718/function"
              },
              "document": {
                "href": "http://localhost:9294/grouse/project/718/document"
              }
            }
          },
          {
            "projectId": 1435,
            "projectName": "test",
            "createdDate": "2020-03-14T18:15:20+01:00",
            "lastModifiedDate": "2020-03-14T18:15:20+01:00",
            "ownedBy": "user@example.com",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/project/1435"
              },
              "function": {
                "href": "http://localhost:9294/grouse/project/1435/function"
              },
              "document": {
                "href": "http://localhost:9294/grouse/project/1435/document"
              }
            }
          }
        ]
      },
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/project?page=0&size=10"
        }
      },
      "page": {
        "size": 10,
        "totalElements": 2,
        "totalPages": 1,
        "number": 0
      }
    }
    
## Get a list of users
    
Only a user with the ROLE_ADMIN can retrieve a list of users. Log on as an admin:
    
    curl -v -X POST  -H 'Authorization: Basic Z3JvdXNlLWNsaWVudDpzZWNyZXQ=' http://localhost:9294/grouse/oauth/token -d grant_type=password -d username=admin@example.com -d password=password
   
and issue the following:
    
    curl -X GET http://localhost:9294/grouse/user  -H 'Authorization: Bearer 2e234261-620a-4d8e-bd06-7adb82d8ce84
           
You will then get a paged list of users. The default setup of grouse returns three users:
    
        {
          "_embedded": {
            "users": [
              {
                "username": "admin@example.com",
                "accountNonExpired": true,
                "credentialsNonExpired": true,
                "accountNonLocked": true,
                "enabled": true,
                "_links": {
                  "self": {
                    "href": "http://localhost:9294/grouse/user/admin@example.com"
                  },
                  "project": {
                    "href": "http://localhost:9294/grouse/project"
                  },
                  "template": {
                    "href": "http://localhost:9294/grouse/template"
                  }
                }
              },
              {
                "username": "user@example.com",
                "accountNonExpired": true,
                "credentialsNonExpired": true,
                "accountNonLocked": true,
                "enabled": true,
                "_links": {
                  "self": {
                    "href": "http://localhost:9294/grouse/user/user@example.com"
                  },
                  "project": {
                    "href": "http://localhost:9294/grouse/project"
                  },
                  "template": {
                    "href": "http://localhost:9294/grouse/template"
                  }
                }
              },
              {
                "username": "template@example.com",
                "accountNonExpired": true,
                "credentialsNonExpired": true,
                "accountNonLocked": true,
                "enabled": true,
                "_links": {
                  "self": {
                    "href": "http://localhost:9294/grouse/user/template@example.com"
                  },
                  "project": {
                    "href": "http://localhost:9294/grouse/project"
                  },
                  "template": {
                    "href": "http://localhost:9294/grouse/template"
                  }
                }
              }
            ]
          },
          "_links": {
            "self": {
              "href": "http://localhost:9294/grouse/user?page=0&size=20"
            }
          },
          "page": {
            "size": 20,
            "totalElements": 3,
            "totalPages": 1,
            "number": 0
          }
        }
    
## Change a users password

A users password can be changed using a Patch request. First retrieve the user account to check that it exists:

    curl -v -X GET http://localhost:9294/grouse/user/user@example.com -H 'Accept: application/hal+json' -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471'

    {
      "username": "user@example.com",
      "accountNonExpired": true,
      "credentialsNonExpired": true,
      "accountNonLocked": true,
      "enabled": true,
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/user/user@example.com"
        },
        "project": {
          "href": "http://localhost:9294/grouse/project"
        },
        "template": {
          "href": "http://localhost:9294/grouse/template"
        },
        "logout OAuth2": {
          "href": "http://localhost:9294/grouse/oauth/revoke-token"
        }
      }
    }

Then 

    curl -v -X PATCH http://localhost:9294/grouse/user/user@example.com -H 'Accept: application/hal+json' -H 'Content-type: application/json'  --data '[{ "op": "replace", "path": "/password", "value": "updatedPassword"}]' -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471'
    
    
Next. Log the user out of grouse. 

    curl -X GET -H 'Authorization: Bearer def80a60-6ecf-4ebe-bcb4-ef76b8d6b471' http://localhost:9294/grouse/oauth/revoke-token

Try logging in with the updated password:

    curl -X POST  -H 'Authorization: Basic Z3JvdXNlLWNsaWVudDpzZWNyZXQ=' http://localhost:9294/grouse/oauth/token -d grant_type=password -d username=user@example.com -d password=updatedPassword
    
    
## Create a user 

First create a user. This can be done of the application root.

    curl http://localhost:9294/grouse/ 

This shows the following:

    {
      "_links": {
        "login OAuth2": {
          "href": "http://localhost:9294/grouse/oauth/token?parameters={parameters}",
          "templated": true
        },
        "create-user": {
          "href": "http://localhost:9294/grouse/user"
        }
      }
    }

Issue a POST request to the href corresponding the "create-user" rel

          curl -X POST http://localhost:9294/grouse/user -H 'Content-type: application/json'  --data '{ "username": "user1@example.com", "password": "mypassword"}'
      
Accounts need to activated by an email or can be activated by an administrator. \[TODO: INSERT DESCRIPTION OF VALIDATING AN ACCOUNT AS ADMINISTRATOR \] 

Login as the new user. 

    curl -X POST  -H 'Authorization: Basic Z3JvdXNlLWNsaWVudDpzZWNyZXQ=' http://localhost:9294/grouse/oauth/token -d grant_type=password -d username=user1@example.com -d password=mypassword
    
Use the generated password (example 4d79d0ef-2588-4b8a-a57a-17be2644564c). Next create a project from a template.

    curl -v -X POST http://localhost:9294/grouse/template/b920dd07-89bd-4702-b1e6-b36910d1482b/project -H 'Accept: application/hal+json' -H 'Content-type: application/json'  --data '{ "projectName": "Requirements project"}' -H 'Authorization: Bearer 4d79d0ef-2588-4b8a-a57a-17be2644564c'
        
## Delete a user
When you delete a user, all their projects are deleted. The user account is deleted and the session is invalidated.  Currently the documents are not deleted from the document store, but this will be added. Should the deletion of the users account be logged? Using the user account created in the previous section.         

To delete a user account:

    curl -v -X DELETE http://localhost:9294/grouse/user/user1@example.com  -H 'Authorization: Bearer 4d79d0ef-2588-4b8a-a57a-17be2644564c' 

This should produce the following result:

    {"status": "GrouseUser with username user1@example.com was deleted"}

## Create the requirements document

It is possible to create the requirements document. I think we need to decide if it is possible at any stage or only when a percentage is achieved. I think they should be able to generate at any stage. Find your project.

 
    {
            "projectId": "f939bae4-60b8-4bcd-a043-e81f011a6102",
            "projectName": "Requirements project",
            "createdDate": "2020-04-05T08:28:42+02:00",
            "lastModifiedDate": "2020-04-05T08:28:42+02:00",
            "ownedBy": "user@example.com",
            "projectComplete": false,
            "documentCreated": false,
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/project/f939bae4-60b8-4bcd-a043-e81f011a6102"
              },
              "function": {
                "href": "http://localhost:9294/grouse/project/f939bae4-60b8-4bcd-a043-e81f011a6102/function"
              },
              "document": {
                "href": "http://localhost:9294/grouse/project/f939bae4-60b8-4bcd-a043-e81f011a6102/document"
              }
            }
          }
Make a note of the href corresponding the "document" rel. Issue a POST request against this.
    
    curl -X POST http://localhost:9294/grouse/project/f939bae4-60b8-4bcd-a043-e81f011a6102/document -H 'Authorization: Bearer 39150c16-c311-4da3-8148-074964cb4609'
    
    
Make a note of updated value in _documentCreated_.

    {
      "projectId": "f939bae4-60b8-4bcd-a043-e81f011a6102",
      "projectName": "Requirements project",
      "createdDate": "2020-04-05T08:28:42+02:00",
      "lastModifiedDate": "2020-04-05T08:35:02+02:00",
      "ownedBy": "user@example.com",
      "projectComplete": true,
      "documentCreated": true,
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/project/f939bae4-60b8-4bcd-a043-e81f011a6102"
        },
        "function": {
          "href": "http://localhost:9294/grouse/project/f939bae4-60b8-4bcd-a043-e81f011a6102/function"
        },
        "document": {
          "href": "http://localhost:9294/grouse/project/f939bae4-60b8-4bcd-a043-e81f011a6102/document"
        }
      }
    }
    
The document can be downloaded by issuing a GET against the href corresponding to the rel "document".

    curl --output doc.docx -X GET http://localhost:9294/grouse/project/f939bae4-60b8-4bcd-a043-e81f011a6102/document -H 'Authorization: Bearer 39150c16-c311-4da3-8148-074964cb4609'

This should produce the requirements document in the same directory.


## Sharing of a project

Make sure there are two users. There is already a user@example.com user, so create  a new user (See section "Create a user"). In this scenario here we have created a uses called _user1@example.com_ :

    curl -X POST http://localhost:9294/grouse/user -H 'Content-type: application/json'  --data '{ "username": "user1@example.com", "password": "mypassword"}'

Next. Create a project for user@example.com

    curl -v -X POST http://localhost:9294/grouse/template/b920dd07-89bd-4702-b1e6-b36910d1482b/project -H 'Accept: application/hal+json' -H 'Content-type: application/json'  --data '{ "projectName": "Requirements project"}' -H 'Authorization: Bearer 39150c16-c311-4da3-8148-074964cb4609'


This creates a project, that can be shared:
    
    {
      "projectId": "7d4407f0-3e19-4652-a357-26840a08f300",
      "projectName": "Requirements project",
      "createdDate": "2020-04-06T07:53:00.11141+02:00",
      "lastModifiedDate": "2020-04-06T07:53:00.11141+02:00",
      "ownedBy": "user@example.com",
      "projectComplete": false,
      "documentCreated": false,
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/project/7d4407f0-3e19-4652-a357-26840a08f300"
        },
        "function": {
          "href": "http://localhost:9294/grouse/project/7d4407f0-3e19-4652-a357-26840a08f300/function"
        },
        "document": {
          "href": "http://localhost:9294/grouse/project/7d4407f0-3e19-4652-a357-26840a08f300/document"
        },
        "share": {
          "href": "http://localhost:9294/grouse/project/7d4407f0-3e19-4652-a357-26840a08f300/share/user_email_address"
        },
        "access": {
          "href": "http://localhost:9294/grouse/project/7d4407f0-3e19-4652-a357-26840a08f300/access"
        }
      }
    }
    
Make a note of the href corresponding to the rel "share".

    "http://localhost:9294/grouse/project/7d4407f0-3e19-4652-a357-26840a08f300/share/user_email_address"

The client must swap out the _"user_email_address"_ portion of the URL with the email address of the user that has 
access to the project. Note only a project owner can give project access to other users. An attempt to insert a value 
that is not an email address or if the default value is sent back to grouse will result in a *400 Bad request*. In the 
scenario here we give access to user1@example.com so the URL looks like the following:

    "http://localhost:9294/grouse/project/7d4407f0-3e19-4652-a357-26840a08f300/share/user1@example.com"

To assign a share to user1@example.com for project *7d4407f0-3e19-4652-a357-26840a08f300* issue a POST request to:

    curl -v -X POST http://localhost:9294/grouse/project/7d4407f0-3e19-4652-a357-26840a08f300/share/user1@example.com -H 'Authorization: Bearer cef420d9-10ba-446f-888a-7507a6046a8d'

This generates the following result:

    {
      "aclId": "4a4f3e87-34cc-4186-8a86-6d4f32b65018",
      "grouseObject": "7d4407f0-3e19-4652-a357-26840a08f300",
      "grouseUser": "user1@example.com",
      "createdDate": "2020-04-06T08:00:32.379595+02:00",
      "lastModifiedDate": "2020-04-06T08:00:32.379595+02:00",
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/accessControl/4a4f3e87-34cc-4186-8a86-6d4f32b65018",
          "templated": true
        }
      }
    }
     
The project can be shared with any email address. Whether the user has an account or not. 

The list of users that have access to the project can be retrieved using the href corresponding 
to "access" rel. To check which user has access to your project, issue a GET to the following: 

    curl -v -X GET http://localhost:9294/grouse/project/7d4407f0-3e19-4652-a357-26840a08f300/access -H 'Authorization: Bearer cef420d9-10ba-446f-888a-7507a6046a8d'
    
This produces a list of users that have access to project *"7d4407f0-3e19-4652-a357-26840a08f300"*, issues a
GET request to the href associated with the "share" rel: 
    
    {
      "_embedded": {
        "users": [
          {
            "username": "user1@example.com",
            "accountNonExpired": true,
            "credentialsNonExpired": true,
            "accountNonLocked": true,
            "enabled": true,
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/user/user1@example.com"
              },
              "project": {
                "href": "http://localhost:9294/grouse/project"
              },
              "template": {
                "href": "http://localhost:9294/grouse/template"
              },
              "logout OAuth2": {
                "href": "http://localhost:9294/grouse/oauth/revoke-token"
              }
            }
          },
          {
            "username": "user@example.com",
            "accountNonExpired": true,
            "credentialsNonExpired": true,
            "accountNonLocked": true,
            "enabled": true,
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/user/user@example.com"
              },
              "project": {
                "href": "http://localhost:9294/grouse/project"
              },
              "template": {
                "href": "http://localhost:9294/grouse/template"
              },
              "logout OAuth2": {
                "href": "http://localhost:9294/grouse/oauth/revoke-token"
              }
            }
          }
        ]
      },
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/project/7d4407f0-3e19-4652-a357-26840a08f300/access?page=0&size=10"
        }
      },
      "page": {
        "size": 10,
        "totalElements": 2,
        "totalPages": 1,
        "number": 0
      }
    }

### Delete share of a project

An assigned share can be deleted either by issuing a DELETE request the the href associated with the "share" rel or by 
using the href associated with the self rel of ACL object.

    curl -v -X DELETE http://localhost:9294/grouse/project/7d4407f0-3e19-4652-a357-26840a08f300/share/user1@example.com -H 'Authorization: Bearer cef420d9-10ba-446f-888a-7507a6046a8d'
    
Attempt to delete a share that does not exist will return a 404. A user that has been assigned a share can delete that share, but cannot delete shares belonging to other users.  

### Attempt to delete a project not owned by user

A third user is added to grouse (user2@example.com). This user logs into grouse and attempts to delete a project they 
do not have access to. The access token after logging in is *(user2@example.com, "975d926f-a01a-4e56-878e-938bd97a7480")*

    curl -v -X DELETE http://localhost:9294/grouse/project/914890e7-38db-4d33-81fd-d3a608cff00d -H 'Authorization: Bearer 975d926f-a01a-4e56-878e-938bd97a7480'

This will result in the following reply:

    {
      "status": 401,
      "message": "Attempt to access an object without permission. project",
      "developerMessage": "org.springframework.security.access.AccessDeniedException: Attempt to access an object without permission. project",
      "stackTrace": "org.springframework.security.access.AccessDeniedException: Attempt to access an object without permission. project",
      "request": "/grouse/project/914890e7-38db-4d33-81fd-d3a608cff00d"
    }

Note. Even if you have access to a project, only the owner of the project can delete it. 

### Attempt to update a project where user has no access

To update the project name, issue a PATCH request with the access token of *user2@example.com*:

    curl -v -X PATCH http://localhost:9294/grouse/project/914890e7-38db-4d33-81fd-d3a608cff00d" -H 'ETAG: ""0""' -H 'Content-type: application/json' -H 'Authorization: Bearer 975d926f-a01a-4e56-878e-938bd97a7480' --data '[{ "op": "replace", "path": "/projectName", "value": "Updated project"}]'

This will result in the following:

    {
      "status": 401,
      "message": "No access to ObjectId 914890e7-38db-4d33-81fd-d3a608cff00d",
      "developerMessage": "org.springframework.security.access.AccessDeniedException: No access to ObjectId 914890e7-38db-4d33-81fd-d3a608cff00d",
      "stackTrace": "org.springframework.security.access.AccessDeniedException: No access to ObjectId 914890e7-38db-4d33-81fd-d3a608cff00d",
      "request": "/grouse/project/914890e7-38db-4d33-81fd-d3a608cff00d"
    }
    

### Attempt to update a project where user has access

To update the project name, issue a PATCH request with the access token of *user1@example.com*:975d926f-a01a-4e56-878e-938bd97a7480

    curl -v -X PATCH http://localhost:9294/grouse/project/914890e7-38db-4d33-81fd-d3a608cff00d -H 'ETAG: ""0""' -H 'Content-type: application/json' -H 'Authorization: Bearer 975d926f-a01a-4e56-878e-938bd97a7480' --data '[{ "op": "replace", "path": "/projectName", "value": "Updated project"}]'
    
    
This returns the following:

    {
      "projectId": "914890e7-38db-4d33-81fd-d3a608cff00d",
      "projectName": "Updated project",
      "createdDate": "2020-04-06T12:17:08+02:00",
      "lastModifiedDate": "2020-04-06T12:26:32.963074+02:00",
      "ownedBy": "user@example.com",
      "projectComplete": false,
      "documentCreated": false,
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/project/914890e7-38db-4d33-81fd-d3a608cff00d"
        },
        "function": {
          "href": "http://localhost:9294/grouse/project/914890e7-38db-4d33-81fd-d3a608cff00d/function"
        },
        "document": {
          "href": "http://localhost:9294/grouse/project/914890e7-38db-4d33-81fd-d3a608cff00d/document"
        },
        "share": {
          "href": "http://localhost:9294/grouse/project/914890e7-38db-4d33-81fd-d3a608cff00d/share/user_email_address"
        },
        "access": {
          "href": "http://localhost:9294/grouse/project/914890e7-38db-4d33-81fd-d3a608cff00d/access"
        }
      }
    }


## Admin has access to all projects

When a user logs in as admin, they see an additional rel/href that a normal user does not see. 

    curl -X POST  -H 'Authorization: Basic Z3JvdXNlLWNsaWVudDpzZWNyZXQ=' http://localhost:9294/grouse/oauth/token -d grant_type=password -d username=admin@example.com -d password=password 

the access token is *"dd9d29fa-08e1-4d7d-bf8a-13ced5c985ec"*.

    curl -X POST  -H 'Authorization: Basic Z3JvdXNlLWNsaWVudDpzZWNyZXQ=' http://localhost:9294/grouse/oauth/token -d grant_type=password -d username=user@example.com -d password=password 

the access token is *"0d2f9949-1e27-4a61-b9a7-3917903b7e2e"*.

First take a look at what admin can see:

    curl -v -X GET http://localhost:9294/grouse/ -H 'Authorization: Bearer dd9d29fa-08e1-4d7d-bf8a-13ced5c985ec' 
    
Make a note of the project-list-all rel/href:

    {
      "_links": {
        "login OAuth2": {
          "href": "http://localhost:9294/grouse/oauth/token?parameters={parameters}",
          "templated": true
        },
        "create-user": {
          "href": "http://localhost:9294/grouse/user"
        },
        "konto": {
          "href": "http://localhost:9294/grouse/user/admin@example.com"
        },
        "project-list": {
          "href": "http://localhost:9294/grouse/project"
        },
        "template-list": {
          "href": "http://localhost:9294/grouse/template"
        },
        "logout OAuth2": {
          "href": "http://localhost:9294/grouse/oauth/revoke-token"
        },
        "project-list-all": {
          "href": "http://localhost:9294/grouse/project/project-list-all"
        },
        "template-list-all": {
          "href": "http://localhost:9294/grouse/template"
        }
      }
    }
    
Next take a look at what admin can see:

    curl -v -X GET http://localhost:9294/grouse/ -H 'Authorization: Bearer 0d2f9949-1e27-4a61-b9a7-3917903b7e2e' 


There is no "project-list-all" rel:

    {
      "_links": {
        "login OAuth2": {
          "href": "http://localhost:9294/grouse/oauth/token?parameters={parameters}",
          "templated": true
        },
        "create-user": {
          "href": "http://localhost:9294/grouse/user"
        },
        "konto": {
          "href": "http://localhost:9294/grouse/user/user@example.com"
        },
        "project-list": {
          "href": "http://localhost:9294/grouse/project"
        },
        "template-list": {
          "href": "http://localhost:9294/grouse/template"
        },
        "logout OAuth2": {
          "href": "http://localhost:9294/grouse/oauth/revoke-token"
        }
      }
    }

Next, take a look at all the projects that admin can see:

    curl -v -X GET http://localhost:9294/grouse/project/project-list-all -H 'Authorization: Bearer dd9d29fa-08e1-4d7d-bf8a-13ced5c985ec'
    
Here you can see that admin can see a project belonging to user@example.com
    
    {
      "_embedded": {
        "projects": [
          {
            "projectId": "2869bdd7-1356-409a-8d38-148612d08314",
            "projectName": "Requirements project",
            "createdDate": "2020-04-07T08:47:11+02:00",
            "lastModifiedDate": "2020-04-07T08:47:11+02:00",
            "ownedBy": "user@example.com",
            "projectComplete": false,
            "documentCreated": false,
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314"
              },
              "function": {
                "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/function"
              },
              "document": {
                "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/document"
              },
              "share": {
                "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/share/user_email_address"
              },
              "access": {
                "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/access"
              }
            }
          }
        ]
      },
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/project/project-list-all?page=0&size=10"
        }
      },
      "page": {
        "size": 10,
        "totalElements": 1,
        "totalPages": 1,
        "number": 0
      }
    }
The admin can also access the above project:    
    
       curl -v -X GET http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314 -H 'Authorization: Bearer dd9d29fa-08e1-4d7d-bf8a-13ced5c985ec'
       
The result is as follows:       
       
       {
         "projectId": "2869bdd7-1356-409a-8d38-148612d08314",
         "projectName": "Requirements project",
         "createdDate": "2020-04-07T08:47:11+02:00",
         "lastModifiedDate": "2020-04-07T08:47:11+02:00",
         "ownedBy": "user@example.com",
         "projectComplete": false,
         "documentCreated": false,
         "_links": {
           "self": {
             "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314"
           },
           "function": {
             "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/function"
           },
           "document": {
             "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/document"
           },
           "share": {
             "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/share/user_email_address"
           },
           "access": {
             "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/access"
           }
         }
       }
       
The admin can also change values:

    curl -v -X PATCH http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/ -H 'ETAG: ""0""' -H 'Content-type: application/json' -H 'Authorization: Bearer dd9d29fa-08e1-4d7d-bf8a-13ced5c985ec' --data '[{ "op": "replace", "path": "/projectName", "value": "Updated name."}]' 

The admin can not share a project. Only the owner can share the project. 

       curl -v -X POST http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/share/user2@example.com -H 'Authorization: Bearer dd9d29fa-08e1-4d7d-bf8a-13ced5c985ec'
       
       
## Testing ETAGs

user@example.com logs in to Grouse:

    curl -X POST  -H 'Authorization: Basic Z3JvdXNlLWNsaWVudDpzZWNyZXQ=' http://localhost:9294/grouse/oauth/token -d grant_type=password -d username=user@example.com -d password=password 

the access token is *"4ec75da8-2653-4605-bf2c-3561a7d91d4e"*.

user@example.com logs in to Grouse:

    curl -X POST  -H 'Authorization: Basic Z3JvdXNlLWNsaWVudDpzZWNyZXQ=' http://localhost:9294/grouse/oauth/token -d grant_type=password -d username=user1@example.com -d password=password 

the access token is *"9536be09-7851-40fc-879f-6333256071a5"*.


user@example.com shares project 2869bdd7-1356-409a-8d38-148612d08314 with user1@example.com.

       curl -v -X POST http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/share/user1@example.com -H 'Authorization: Bearer 4ec75da8-2653-4605-bf2c-3561a7d91d4e'

This results in the following:

    {
      "aclId": "7a276c52-279b-43ff-8db0-f61344f8c66d",
      "grouseObject": "2869bdd7-1356-409a-8d38-148612d08314",
      "grouseUser": "user1@example.com",
      "createdDate": "2020-04-07T11:18:30+02:00",
      "lastModifiedDate": "2020-04-07T11:18:30+02:00",
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/accessControl/{accessControl}",
          "templated": true
        }
      }
    }

Check that user1@example.com has access to the project:

       curl -v -X GET http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314 -H 'Authorization: Bearer 9536be09-7851-40fc-879f-6333256071a5'

This shows that user1@example.com has access.

    {
      "projectId": "2869bdd7-1356-409a-8d38-148612d08314",
      "projectName": "Updated name.",
      "createdDate": "2020-04-07T08:47:11+02:00",
      "lastModifiedDate": "2020-04-07T08:53:04+02:00",
      "ownedBy": "user@example.com",
      "projectComplete": false,
      "documentCreated": false,
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314"
        },
        "function": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/function"
        },
        "document": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/document"
        },
        "share": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/share/user_email_address"
        },
        "access": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/access"
        }
      }
    }

Make a note of the ETAG from curl request:

    ETAG: ""0""
    
    
user@example.com changes the project name:

     curl -v -X PATCH http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314 -H 'ETAG: ""0""' -H 'Content-type: application/json' -H 'Authorization: Bearer 9536be09-7851-40fc-879f-6333256071a5' --data '[{ "op": "replace", "path": "/projectName", "value": "Updated project name 2"}]'

This produces the following output:

    {
      "projectId": "2869bdd7-1356-409a-8d38-148612d08314",
      "projectName": "Updated project name 2",
      "createdDate": "2020-04-07T08:47:11+02:00",
      "lastModifiedDate": "2020-04-07T11:27:24+02:00",
      "ownedBy": "user@example.com",
      "projectComplete": false,
      "documentCreated": false,
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314"
        },
        "function": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/function"
        },
        "document": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/document"
        },
        "share": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/share/user_email_address"
        },
        "access": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/access"
        }
      }
    }

     
user@example.com changes the project name:

     curl -v -X PATCH http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314 -H 'ETAG: ""0""' -H 'Content-type: application/json' -H 'Authorization: Bearer 9536be09-7851-40fc-879f-6333256071a5' --data '[{ "op": "replace", "path": "/projectName", "value": "Updated project name 2"}]'
     
Make a note of the ETAG from curl request:

    ETAG: ""1""
     
     
Next user1@example.com changes the project name, but uses the original ETAG value ""0"":

     curl -v -X PATCH http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314 -H 'ETAG: ""0""' -H 'Content-type: application/json' -H 'Authorization: Bearer 9536be09-7851-40fc-879f-6333256071a5' --data '[{ "op": "replace", "path": "/projectName", "value": "Updated project name 3"}]'

This results in the following 409 response
     
     {
       "status": 409,
       "message": "Concurrency Exception. Old version [1], new version [0]",
       "developerMessage": "no.kdrs.grouse.utils.exception.ConcurrencyException: Concurrency Exception. Old version [1], new version [0]",
       "stackTrace": "no.kdrs.grouse.utils.exception.ConcurrencyException: Concurrency Exception. Old version [1], new version [0]",
       "request": "/grouse/project/2869bdd7-1356-409a-8d38-148612d08314"
     }
     
So now the client knows that they were working on *stale* information and should inform the user
that the data had been changed. user1@example.com should pull down an updated copy of project and make
a note of the ETAG header and attempt to update:


     curl -v -X PATCH http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314 -H 'ETAG: ""1""' -H 'Content-type: application/json' -H 'Authorization: Bearer 9536be09-7851-40fc-879f-6333256071a5' --data '[{ "op": "replace", "path": "/projectName", "value": "Updated project name 3"}]'


This results in the following payload:

    {
      "projectId": "2869bdd7-1356-409a-8d38-148612d08314",
      "projectName": "Updated project name 3",
      "createdDate": "2020-04-07T08:47:11+02:00",
      "lastModifiedDate": "2020-04-07T12:25:17.977307+02:00",
      "ownedBy": "user@example.com",
      "projectComplete": false,
      "documentCreated": false,
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314"
        },
        "function": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/function"
        },
        "document": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/document"
        },
        "share": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/share/user_email_address"
        },
        "access": {
          "href": "http://localhost:9294/grouse/project/2869bdd7-1356-409a-8d38-148612d08314/access"
        }
      }
    }


## Downloading a document

### Identifying supported formats

Grouse can render the requirements document in various formats. The formats that grouse has tested it can render can be found by looking for a  supported-formats rel/href pair. This can be found either of the application root:

    curl -X GET http://localhost:9294/grouse/ -H 'Authorization: Bearer 9536be09-7851-40fc-879f-6333256071a5'

or when getting a project: 
    
    curl -X GET http://localhost:9294/grouse/project/e2e9643c-1a30-4eb5-b8e5-ba1dd0245397 -H 'Authorization: Bearer 9536be09-7851-40fc-879f-6333256071a5'
    
This will produce the following JSON:    
     
    {
      "supportedFormats": {
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document": "docx",
        "application/vnd.oasis.opendocument.text": "odt",
        "text/html": "html",
        "application/pdf": "pdf",
        "application/rtf": "rtf",
        "text/asciidoc": "adoc",
        "text/markdown": "md"
      },
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/document/supported-formats"
        }
      }
    }

Other file formats can be added or removed using the _supported.formats_ property in _application.yml_.

Grouse generates the requirements document at the same time the document is downloaded. To download the requirements document in a given format use the following command:

    curl --output file.docx -v -H 'Accept: application/vnd.openxmlformats-officedocument.wordprocessingml.document' -X GET http://localhost:9294/grouse/project/37d6ea88-348f-4dea-a75e-e83a6bd3cc05/document -H 'Authorization: Bearer 9536be09-7851-40fc-879f-6333256071a5'  

For the basic Noark template it takes about 3 seconds to generate and download the requirements document in each of the supported formats.

## Creating a template

Take a look at the application root:

     curl -v -X GET http://localhost:9294/grouse/ -H 'Authorization: Bearer feb460c5-5526-423d-a2c1-48dd64728fc5' | jq

Make a note of the _"template"_ rel/href pair. The href can be used to create a  template with a POST request:

    {
      "_links": {
        "login OAuth2": {
          "href": "http://localhost:9294/grouse/oauth/token?parameters={parameters}",
          "templated": true
        },
        "create-user": {
          "href": "http://localhost:9294/grouse/user"
        },
        "konto": {
          "href": "http://localhost:9294/grouse/user/user1@example.com"
        },
        "project-list": {
          "href": "http://localhost:9294/grouse/project"
        },
        "supported-formats": {
          "href": "http://localhost:9294/grouse/document/supported-formats"
        },
        "template-list": {
          "href": "http://localhost:9294/grouse/template/template-list-all"
        },
        "template": {
          "href": "http://localhost:9294/grouse/template"
        },
        "logout OAuth2": {
          "href": "http://localhost:9294/grouse/oauth/revoke-token"
        }
      }
    }
    
Make a note of the function href/rel in the returned payload
    
    {
      "templateId": "b81b70bc-8e88-4cd6-bcc1-dd74e7691e2a",
      "templateName": "Fantastic template",
      "createdDate": "2020-04-21T14:26:33.711367+02:00",
      "lastModifiedDate": "2020-04-21T14:26:33.711367+02:00",
      "ownedBy": "template@example.com",
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/template/b81b70bc-8e88-4cd6-bcc1-dd74e7691e2a"
        },
        "function": {
          "href": "http://localhost:9294/grouse/template/b81b70bc-8e88-4cd6-bcc1-dd74e7691e2a/function"
        },
        "project": {
          "href": "http://localhost:9294/grouse/template/b81b70bc-8e88-4cd6-bcc1-dd74e7691e2a/project"
        }
      }
    }

To create a functionality use href corresponding to the _"function"_ rel: 

    curl -v -X POST http://localhost:9294/grouse/template/b81b70bc-8e88-4cd6-bcc1-dd74e7691e2a/function -H 'Content-type: application/json' --data '{ "functionalityNumber": "1.2", "title": "Test title", "description": "description text", "consequence": "consequence text", "explanation": "explanation text", "type" : "Functional requirements"}' -H 'Authorization: Bearer b498671d-c144-4db0-96a5-46c9442067d7' | jq


This generates the functionality and issues the following reponse:

    {
      "templateFunctionalityId": 718,
      "functionalityNumber": "1.2",
      "title": "Test title",
      "description": "description text",
      "consequence": "consequence text",
      "explanation": "explanation text",
      "type": "Functional requirements",
      "ownedBy": "template@example.com",
      "version": 0,
      "_links": {
        "function": {
          "href": "http://localhost:9294/grouse/template/function/718/function"
        },
        "requirement": {
          "href": "http://localhost:9294/grouse/template/function/718/requirement"
        },
        "self": {
          "href": "http://localhost:9294/grouse/template/function/718"
        }
      }
    }

From here the client can create a "sub"-functionality or a requirement. First we create a "sub"-functionality
    
    curl -v -X POST http://localhost:9294/grouse/template/function/718/function -H 'Content-type: application/json' --data '{ "functionalityNumber": "1.2.1", "title": "Test title 1.2.1", "description": "description text", "consequence": "consequence text", "explanation": "explanation text", "type" : "Functional requirements"}' -H 'Authorization: Bearer b498671d-c144-4db0-96a5-46c9442067d7' | jq
    
This creates a new functionality and issues the following response:

    {
      "templateFunctionalityId": 719,
      "functionalityNumber": "1.2.1",
      "title": "Test title 1.2.1",
      "description": "description text",
      "consequence": "consequence text",
      "explanation": "explanation text",
      "type": "Functional requirements",
      "ownedBy": "template@example.com",
      "version": 0,
      "_links": {
        "function": {
          "href": "http://localhost:9294/grouse/template/function/719/function"
        },
        "requirement": {
          "href": "http://localhost:9294/grouse/template/function/719/requirement"
        },
        "self": {
          "href": "http://localhost:9294/grouse/template/function/719"
        }
      }
    }    

Now we can add a requirement:

    curl -v -X POST http://localhost:9294/grouse/template/function/719/requirement -H 'Content-type: application/json' --data '{ "requirementText": "requirementText title", "priority": "O", "requirementNumber": "2.1"}' -H 'Authorization: Bearer b498671d-c144-4db0-96a5-46c9442067d7' | jq
    

The requirement is created and the following response is generated:

    {
      "requirementId": 720,
      "requirementText": "requirementText title",
      "requirementNumber": "2.1",
      "priority": "O",
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/templateRequirement/720"
        }
      }
    }

If we now retrieve a list of templates, our new template should be in the list:


     curl -v -X GET http://localhost:9294/grouse/template/template-list-all/ -H 'Authorization: Bearer b498671d-c144-4db0-96a5-46c9442067d7' | jq

We will now have two templates:

    {
      "_embedded": {
        "templates": [
          {
            "templateId": "b920dd07-89bd-4702-b1e6-b36910d1482b",
            "templateName": "Noark 5",
            "createdDate": "2020-04-21T14:21:51+02:00",
            "lastModifiedDate": "2020-04-21T14:21:51+02:00",
            "ownedBy": "grouse",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/template/b920dd07-89bd-4702-b1e6-b36910d1482b"
              },
              "function": {
                "href": "http://localhost:9294/grouse/template/b920dd07-89bd-4702-b1e6-b36910d1482b/function"
              },
              "project": {
                "href": "http://localhost:9294/grouse/template/b920dd07-89bd-4702-b1e6-b36910d1482b/project"
              }
            }
          },
          {
            "templateId": "b81b70bc-8e88-4cd6-bcc1-dd74e7691e2a",
            "templateName": "Fantastic template",
            "createdDate": "2020-04-21T14:26:34+02:00",
            "lastModifiedDate": "2020-04-21T14:26:34+02:00",
            "ownedBy": "template@example.com",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/template/b81b70bc-8e88-4cd6-bcc1-dd74e7691e2a"
              },
              "function": {
                "href": "http://localhost:9294/grouse/template/b81b70bc-8e88-4cd6-bcc1-dd74e7691e2a/function"
              },
              "project": {
                "href": "http://localhost:9294/grouse/template/b81b70bc-8e88-4cd6-bcc1-dd74e7691e2a/project"
              }
            }
          }
        ]
      },
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/template/template-list-all/?page=0&size=10"
        }
      },
      "page": {
        "size": 10,
        "totalElements": 2,
        "totalPages": 1,
        "number": 0
      }
    }

Using the href associated with the _"project"_ rel we cna create a project from out template:
     
    curl -v -X POST http://localhost:9294/grouse/template/b81b70bc-8e88-4cd6-bcc1-dd74e7691e2a/project -H 'Content-type: application/json'  --data '{ "projectName": "Fantastic Requirements"}' -H 'Authorization: Bearer b498671d-c144-4db0-96a5-46c9442067d7' | jq
    
    
A new project is created based on the template:

    {
      "projectId": "c72e3083-cf6f-4452-b18e-00c5ddf9423a",
      "projectName": "Fantastic Requirements",
      "createdDate": "2020-04-21T14:39:27.229094+02:00",
      "lastModifiedDate": "2020-04-21T14:39:27.229094+02:00",
      "ownedBy": "template@example.com",
      "projectComplete": false,
      "documentCreated": false,
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/project/c72e3083-cf6f-4452-b18e-00c5ddf9423a"
        },
        "function": {
          "href": "http://localhost:9294/grouse/project/c72e3083-cf6f-4452-b18e-00c5ddf9423a/function"
        },
        "document": {
          "href": "http://localhost:9294/grouse/project/c72e3083-cf6f-4452-b18e-00c5ddf9423a/document"
        },
        "supported-formats": {
          "href": "http://localhost:9294/grouse/document/supported-formats"
        },
        "share": {
          "href": "http://localhost:9294/grouse/project/c72e3083-cf6f-4452-b18e-00c5ddf9423a/share/user_email_address"
        },
        "access": {
          "href": "http://localhost:9294/grouse/project/c72e3083-cf6f-4452-b18e-00c5ddf9423a/access"
        }
      }
    }
    


    