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
