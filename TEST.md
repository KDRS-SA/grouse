# Grouse

## Maven testing

In src/main/test there  are a number of integration tests that test endpoint functionality

## Curl tests

First get a OAuth2 token

    `curl -X POST  -H 'Authorization: Basic Z3JvdXNlLWNsaWVudDpzZWNyZXQ=' http://localhost:9294/grouse/oauth/token -d grant_type=password -d username=user@example.com -d password=password`

This should give you the following

    `{
       "access_token": "7b29cd6d-0af4-414e-987c-2f14b6749f00",
       "token_type": "bearer",
       "refresh_token": "4bf294e7-8927-4773-a3d5-7e7c24eec0c9",
       "expires_in": 42903,
       "scope": "all"
     }

Once you have a token you can call root of the application to see what you can do:

    curl -v -X GET http://localhost:9294/grouse/ -H 'Accept: application/hal+json' -H 'Authorization: Bearer 7b29cd6d-0af4-414e-987c-2f14b6749f00'  


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

    curl -v -X GET http://localhost:9294/grouse/template -H 'Accept: application/hal+json' -H 'Authorization: Bearer 7b29cd6d-0af4-414e-987c-2f14b6749f00'  
  
You will get something like the following:    
    
    {
      "_embedded": {
        "templates": [
          {
            "templateId": "b920dd07-89bd-4702-b1e6-b36910d1482b",
            "templateName": "Noark 5",
            "createdDate": "2020-03-12T10:36:22+01:00",
            "lastModifiedDate": "2020-03-12T10:36:22+01:00",
            "ownedBy": "grouse",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/template/b920dd07-89bd-4702-b1e6-b36910d1482b"
              },
              "function": {
                "href": "http://localhost:9294/grouse/template/b920dd07-89bd-4702-b1e6-b36910d1482b/function"
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
           

     
To get an individual project:

    `curl -v -H 'Authorization: Bearer 07fbcd9d-9dd3-440c-8456-bca9f339683e' -X GET http://localhost:9294/grouse/project/112`

To get a list of projects:

   `curl -v -H 'Authorization: Bearer 07fbcd9d-9dd3-440c-8456-bca9f339683e' -X GET http://localhost:9294/grouse/project`


This will give you a list like the following:
 
    `{
      "_embedded": {
        "projects": [
          {
            "projectId": 112,
            "projectName": "test project",
            "createdDate": "2020-03-05T19:38:08+01:00",
            "lastModifiedDate": "2020-03-05T19:38:08+01:00",
            "ownedBy": "user@example.com",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/project/112"
              },
              "function": {
                "href": "http://localhost:9294/grouse/project/112/function"
              }
            }
          }
        ]
      },
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/project?page=0&size=20"
        }
      },
      "page": {
        "size": 20,
        "totalElements": 1,
        "totalPages": 1,
        "number": 0
      }
    }
`


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

## Get a list of templates
The first step to do anything useful in grouse is to retrieve a list of templates. 

    curl -v -X GET http://localhost:9294/grouse/template -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer 1e0d528b-4395-4abe-af5b-d6c5e5b1f1fc'

This will return the following payload:

    {
      "_embedded": {
        "templates": [
          {
            "templateId": "b920dd07-89bd-4702-b1e6-b36910d1482b",
            "templateName": "Noark 5",
            "createdDate": "2020-03-14T07:54:41+01:00",
            "lastModifiedDate": "2020-03-14T07:54:41+01:00",
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

     curl -v -X POST http://localhost:9294/grouse/template/b920dd07-89bd-4702-b1e6-b36910d1482b/project -H 'Accept: application/hal+json' -H 'Content-type: application/json'  --data '{ "projectName": "Requirements project"}' -H 'Authorization: Bearer 1e0d528b-4395-4abe-af5b-d6c5e5b1f1fc'

This gives the following result:

    {
      "projectId": 718,
      "projectName": "Requirements project",
      "createdDate": "2020-03-14T08:08:37.999472+01:00",
      "lastModifiedDate": "2020-03-14T08:08:37.999472+01:00",
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


    curl -v -X GET http://localhost:9294/grouse/project/718/function -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer 1e0d528b-4395-4abe-af5b-d6c5e5b1f1fc'

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
            "_links": {
              "function": {
                "href": "http://localhost:9294/grouse/projectFunctionality/727/function"
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
            "_links": {
              "function": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1338/function"
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
            "_links": {
              "function": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1378/function"
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

    curl -v -X GET http://localhost:9294/grouse/projectFunctionality/1338/function -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer 1e0d528b-4395-4abe-af5b-d6c5e5b1f1fc'
 
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
            "_links": {
              "requirement": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1339/requirement"
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
            "_links": {
              "requirement": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1345/requirement"
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
            "_links": {
              "requirement": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1354/requirement"
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
            "_links": {
              "requirement": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1364/requirement"
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
            "_links": {
              "requirement": {
                "href": "http://localhost:9294/grouse/projectFunctionality/1372/requirement"
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

Here we can see that there is "function" *rel*, just a "requirement" *rel* for each projectFunctionality. So from here we know there are no sub project functionalities.

The project requirements can be retrieved using the following:

    "requirement": {
           "href": "http://localhost:9294/grouse/projectFunctionality/1372/requirement"
    }

Retrieving a list of requirements using the following:




results in the following payload:

    {
      "_embedded": {
        "projectRequirements": [
          {
            "requirementId": 1373,
            "requirementText": "Leverandøren skal opplyse om eventuelle ytterligere krav som stilles til\n                            komplett løsning, og som ikke er etterspurt her i andre krav. Både minimumskrav og anbefalte\n                            krav beskrives.\n                        ",
            "showOrder": 1,
            "priority": "1 (i)",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/1373"
              }
            }
          },
          {
            "requirementId": 1374,
            "requirementText": "Systemet skal fungere ..",
            "showOrder": 2,
            "priority": "1 (i)",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/1374"
              }
            }
          },
          {
            "requirementId": 1375,
            "requirementText": "Systemet skal fungere tilfredsstillende ....",
            "showOrder": 3,
            "priority": "1 (i)",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/1375"
              }
            }
          },
          {
            "requirementId": 1376,
            "requirementText": "Organisasjonen har en eksisterende lisensavtale  ...",
            "showOrder": 4,
            "priority": "1 (i)",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/1376"
              }
            }
          },
          {
            "requirementId": 1377,
            "requirementText": "Redegjør for hvordan løsningen ivaretar de sju overordnede prinsipper for",
            "showOrder": 5,
            "priority": "1 (i)",
            "_links": {
              "self": {
                "href": "http://localhost:9294/grouse/projectRequirement/1377"
              }
            }
          }
        ]
      },
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/projectFunctionality/1372/requirement?page=0&size=10"
        }
      },
      "page": {
        "size": 10,
        "totalElements": 5,
        "totalPages": 1,
        "number": 0
      }
    }

## Get en individual requirement


    curl -v -X GET http://localhost:9294/grouse/projectRequirement/1376 -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer 1e0d528b-4395-4abe-af5b-d6c5e5b1f1fc'

This generates the following payload:

    {
      "requirementId": 1376,
      "showOrder": 4,
      "requirementText": "Organisasjonen har en eksisterende lisensavtale  ....",
      "priority": "1 (i)",
      "requirementNumber": null,
      "requirement": null,
      "requirementType": null,
      "version": 0,
      "ownedBy": "user@example.com",
      "_links": {
        "self": {
          "href": "http://localhost:9294/grouse/projectRequirement/1376"
        }
      }
    }

Make a note of the ETAG value in the header:

## Get a list of projects

The project is updated with a PATCH request:

    curl -v -H 'ETAG: ""0"" '-H 'Accept: application/hal+json' -H 'Content-type: application/json'  --data '[{ "op": "replace", "path": "/processed", "value": true}]'  -X PATCH http://localhost:9294/grouse/projectRequirement/1376  -H 'Authorization: Bearer 1e0d528b-4395-4abe-af5b-d6c5e5b1f1fc'

The payload for updating is an array:

    [{ "op": "replace", "path": "/processed", "value": true}]

This instructs grouese to replace the processed value with a new value, namely *false*.

Note the  eTag uses double quotes around the value. That is why the header is `-H 'ETAG: ""0""`

## Get a list of projects

    curl -v -X GET http://localhost:9294/grouse/project -H 'Accept: application/hal+json' -H 'Content-type: application/json' -H 'Authorization: Bearer 1e0d528b-4395-4abe-af5b-d6c5e5b1f1fc'    