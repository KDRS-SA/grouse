# Remaining tasks
The following is a list of tasks to finish Grouse.

## Server side
  * Add ownedby to all entities so we limit authorization to the correct user. Currently ownedBy
is limited to projects. This means that users could change other users functionalites by
interacting with Grouse via command-line. This is not an issue from the GUI.
  * Deletion of projects when a user wishes to delete one or more projects
  * Deletion of account upon user request
  * Check which endpoints are not in use and remove them
  * Add hateoas links to all objects returned by endpoints 

## GUI
  * Look at how to handle refreshing of tokens and situation when token has
expired but user hasn't logged out.
  * Look at functionality to allow a user to edit project name / organisation name
  * Develop ADMIN page allowing admin to create/delete accounts 
  
We are seeing that the server responds with the following if the token times out:

```
{"data":{
"error":"invalid_token",
"error_description":"Invalid access token: 198ad3b7-d1cd-44eb-83bc-7b5953db09fa"},
"status":401,
....
}  
```
  
## Test framework  
  * Continue developing tests
  
## Document
  * Continue document development

## Descriptions
  * Copy all descriptions
  * Some requirements should not be possible to remove. Search for "Dette er satt til et obligatorisk 
  krav og kan ikke velges bort" and prevent deletion / editing of such requirements
