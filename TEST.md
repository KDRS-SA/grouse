# Grouse

## Maven testing

In src/main/test there  are a number of integration tests that test endpoint functionality

## Curl tests

First get a OAut2 token

    `curl -X POST  -H 'Authorization: Basic Z3JvdXNlLWNsaWVudDpzZWNyZXQ=' http://localhost:9294/grouse/oauth/token -d grant_type=password -d username=user@example.com -d password=password`

This should give you the following

    `{
       "access_token": "07fbcd9d-9dd3-440c-8456-bca9f339683e",
       "token_type": "bearer",
       "refresh_token": "4bf294e7-8927-4773-a3d5-7e7c24eec0c9",
       "expires_in": 42903,
       "scope": "all"
     }

Once you have a token you can try to create a project

    `curl -v --data '{"projectName":"test project"}'  -H 'Authorization: Bearer 07fbcd9d-9dd3-440c-8456-bca9f339683e' -H 'Content-type: application/json' -X POST http://localhost:9294/grouse/project`


You will get something like the following:    
    
    `{
       "projectId": 112,
       "projectName": "test project",
       "createdDate": "2020-03-05T19:38:07.829368+01:00",
       "lastModifiedDate": "2020-03-05T19:38:07.829368+01:00",
       "ownedBy": "user@example.com",
       "_links": {
         "self": {
           "href": "http://localhost:9294/grouse/project/112"
         },
         "function": {
           "href": "http://localhost:9294/grouse/project/112/function"
         },
         "document": {
           "href": "http://localhost:9294/grouse/project/112/document"
         }
       }
     }`
     
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
