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
  
## Test framework  
  * Continue developing tests
  
## Document
  * Continue document development

## Descriptions
  * Copy all descriptions
