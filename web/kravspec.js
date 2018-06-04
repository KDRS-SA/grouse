var app = angular.module('grouse-app', []);

/**
 * kravspec.js
 *
 * Used with kravspec.html to provide the following functionality:
 *
 * 1. When page loads, check to see that the user is in posession of a valid token
 * Note. We are not checking for expired token!
 * 2. TBD, check that token hasn't expired (Make a note of time it is set to expire)
 * 3.
 *
 * TBD: Find out how to redirect to login page to to avoid
 */
var requirementsController = app.controller('RequirementsController',
  ['$scope', '$http', '$window', function ($scope, $http, $window) {


    $scope.token = getAccessToken();
    $scope.username = getUsername();

    if (typeof $scope.token === 'undefined' || $scope.token == null) {
      console.log("No valid token defined! Need to log in." + $scope.token);
//      changeLocation($scope, startPage, false);
 //     return;
    }

    console.log("Valid token exists! " + JSON.stringify($scope.token));

    // Retrieve the current user and what they can do $scope.username
    var urlForUserDetails = startingAddress + $scope.username;

    console.log("urlForUserDetails " + urlForUserDetails);

   /* $http({
      method: 'GET',
      url: urlForUserDetails,
      headers: {'Authorization': 'Bearer ' + $scope.token.access_token}
    }).then(function successCallback(response) {
      $scope.currentUser = response.data;
      console.log(" GET urlForUserDetails [" + urlForUserDetails +
        "] returned " + JSON.stringify(response));
      console.log(" GET $scope.currentUser  [" + urlForUserDetails +
        "] returned " + JSON.stringify($scope.currentUser));
   */
   // TODO: THIS IS TO BE DELTED .....
    $scope.currentUser =
        JSON.parse("{\"username\":\"admin@kdrs.no\",\"password\":\"{bcrypt}$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC\",\"firstname\":\"John\",\"lastname\":\"Smith\",\"roles\":[{\"role\":\"ROLE_ADMIN\"},{\"role\":\"ROLE_USER\"}],\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost:9294/grouse/bruker/admin@kdrs.no\",\"hreflang\":null,\"media\":null,\"title\":null,\"type\":null,\"deprecation\":null},{\"rel\":\"prosjekt\",\"href\":\"http://localhost:9294/grouse/bruker/admin@kdrs.no/prosjekt\",\"hreflang\":null,\"media\":null,\"title\":null,\"type\":null,\"deprecation\":null}]}");


    $scope.token = JSON.parse("{\"access_token\":\"1\"}");
    $scope.projects = [];//JSON.parse("{\"access_token\":\"1\"}");


      $scope.projectsView = true;
      $scope.requirementsView = false;
      $scope.requirementsView = false;

      $scope.priorityValues = ['O', '1', '2'];

      $scope.newRequirementText = "";
      $scope.newRequirementPriority = "O";

      $scope.selectedProject = null;
      $scope.progressBarText = null;
      $scope.selectedFunctionality = null;
      $scope.documentHref = null;

      $scope.progressBarValue = 0;

      console.log("Grouse kravspec.js page load. User token is set to " + $scope.token.access_token);
      console.log("Retrieving projects on page load. Current user is [" + $scope.username + "] .\n");

      for (var rel in $scope.currentUser.links) {
        var relation = $scope.currentUser.links[rel].rel;
        if (relation == REL_PROJECT) {
          var urlForProjects = $scope.currentUser.links[rel].href;
          console.log("Checking urlForProjects[" + urlForProjects);
          $http({
            method: 'GET',
            url: urlForProjects,
            headers: {'Authorization': 'Bearer ' + $scope.token.access_token}
          }).then(function successCallback(response) {
            $scope.projects = response.data;
            // TODO: REMOVE THIS ...
            $scope.projects = [];//JSON.parse("{\"access_token\":\"1\"}");
            console.log(" GET urlForProjects[" + urlForProjects +
              "] returned " + JSON.stringify(response));
          }, function errorCallback(response) {
            alert("Kunne ikke finne prosjektene dine. " +
              JSON.stringify(response));
            console.log(" GET urlForProjects[" + urlForProjects +
              "] returned " + JSON.stringify(response));
          });
        }
      }
     /* }, function errorCallback(response) {
      alert("Kunne ikke hente ut opplysninger om bruker. " +
        JSON.stringify(response));
      console.log(" GET urlForUserDetails[" + urlForUserDetails +
        "] returned " + JSON.stringify(response));
    });*/




    /**
     * functionality_selected
     *
     * Sets the chosen functionality from GUI. Retrieves any related metadata.
     */
    $scope.functionality_selected = function (functionality) {
      console.log("functionality[" + JSON.stringify(functionality) + "] selected.\n");
      $scope.selectedFunctionality = functionality;
    };

    /**
     * functionality_selected
     *
     * Sets the chosen functionality from GUI. Retrieves any related metadata.
     */
    $scope.selectForDelete = function (functionality) {
      console.log("functionality[" + JSON.stringify(functionality) + "] selected.\n");
      $scope.selectedFunctionality = functionality;

    };

    /**
     * updateRequirementText
     *
     * handles a change in requirement text.
     */
    $scope.updateRequirementText = function (requirement) {
      console.log("updateRequirementText [" + JSON.stringify(requirement) + "] selected.\n");
      $scope.selectedRequirement = requirement;

      var patchString = '[{ "op": "replace", "path": "/requirementText", "value": "' +
        requirement.requirementText + '"}]';

      console.log("updateRequirementText. Attempting PATCH[" + patchString + "].\n");

      for (var rel in $scope.selectedRequirement.links) {
        var relation = $scope.selectedRequirement.links[rel].rel;
        if (relation === REL_SELF) {
          var urlForRequirementChange = $scope.selectedRequirement.links[rel].href;
          console.log("Checking urlForRequirementTextChange [" + urlForRequirementChange);
          $http({
            method: 'PATCH',
            url: urlForRequirementChange,
            headers: {'Authorization': 'Bearer ' + $scope.token.access_token},
            data: patchString
          }).then(function successCallback(response) {
            console.log("PATCH [" + urlForRequirementChange +
              "] returned " + JSON.stringify(response));
            $scope.selectedRequirement = response.data;
          }, function errorCallback(response) {
            alert("Kunne ikke endre krav for funksjon. " +
              JSON.stringify(response));
            console.log("PATCH urlForRequirementChange l[" + urlForRequirementChange +
              "] returned " + JSON.stringify(response));
          });
        }
      }

    };
    /**
     * updateRequirementPriority
     *
     * handles a change in requirement priority.
     */
    $scope.updateRequirementPriority = function (requirement) {
      console.log("updateRequirementPriority[" + JSON.stringify(requirement) + "] selected.\n");
      $scope.selectedRequirement = requirement;

      var patchString = '[{ "op": "replace", "path": "/priority", "value": "' +
        requirement.priority + '"}]';
      console.log("updateRequirementPriority. Attempting PATCH[" + patchString + "].\n");

      for (var rel in $scope.selectedRequirement.links) {
        var relation = $scope.selectedRequirement.links[rel].rel;
        if (relation === REL_SELF) {
          var urlForRequirementChange = $scope.selectedRequirement.links[rel].href;
          console.log("Checking urlForRequirementChange [" + urlForRequirementChange);
          $http({
            method: 'PATCH',
            url: urlForRequirementChange,
            headers: {'Authorization': 'Bearer ' + $scope.token.access_token},
            data: patchString
          }).then(function successCallback(response) {
            console.log("PATCH [" + urlForRequirementChange +
              "] returned " + JSON.stringify(response));
            $scope.selectedRequirement = response.data;
          }, function errorCallback(response) {
            alert("Kunne ikke endre krav for funksjon. " +
              JSON.stringify(response));
            console.log("PATCH urlForRequirementChange l[" + urlForRequirementChange +
              "] returned " + JSON.stringify(response));
          });
        }
      }
    };

    $scope.addRequirement = function () {
      console.log("addRequirement selected.");

      // Make sure you push the one returned from the database
      for (var rel in $scope.selectedFunctionality.links) {
        var relation = $scope.selectedFunctionality.links[rel].rel;
        if (relation == REL_REQUIREMENT) {
          var urlForProjectRequirementCreation = $scope.selectedFunctionality.links[rel].href;
          console.log("Checking urlForProjectRequirementCreation[" + urlForProjectRequirementCreation);
          $http({
            method: 'POST',
            url: urlForProjectRequirementCreation,
            headers: {'Authorization': 'Bearer ' + $scope.token.access_token},
            data: {
              "requirementText": $scope.newRequirementText,
              "priority": $scope.newRequirementPriority
            }
          }).then(function successCallback(response) {
            console.log("POST urlForProjectRequirementCreation [" + urlForProjectRequirementCreation +
              "] returned " + JSON.stringify(response));
            $scope.selectedFunctionality.referenceProjectRequirement.push(response.data);
          }, function errorCallback(response) {
            alert("Kunne ikke slette prosjekt krav. " +
              JSON.stringify(response));
            console.log("POST urlForProjectRequirementCreation [" + urlForProjectRequirementCreation +
              "] returned " + response);
          });
        }
      }


    };

    $scope.deleteRequirementAndRow = function (index) {
      console.log("deleteRequirementAndRow selected. Index is [" + index + "]");
      console.log("Working on [" + JSON.stringify($scope.selectedFunctionality.referenceProjectRequirement[index]) + "]");

      var requirement = $scope.selectedFunctionality.referenceProjectRequirement[index].requirementText;
      if ($window.confirm("Er du sikker du vil slette følgende krav: \n" + requirement)) {

        console.log("Working on [" + JSON.stringify($scope.selectedFunctionality.referenceProjectRequirement[index].links) + "]");
        for (var rel in $scope.selectedFunctionality.referenceProjectRequirement[index].links) {
          var relation = $scope.selectedFunctionality.referenceProjectRequirement[index].links[rel].rel;
          if (relation == REL_SELF) {
            var urlForProjectRequirementDeletion = $scope.selectedFunctionality.referenceProjectRequirement[index].links[rel].href;
            console.log("Checking urlForProjectRequirementDeletion[" + urlForProjectRequirementDeletion);
            $http({
              method: 'DELETE',
              url: urlForProjectRequirementDeletion,
              headers: {'Authorization': 'Bearer ' + $scope.token.access_token}
            }).then(function successCallback(response) {
              console.log("DELETE urlForProjectRequirementDeletion [" + urlForProjectRequirementDeletion +
                "] returned " + JSON.stringify(response));
              $scope.selectedFunctionality.referenceProjectRequirement.splice(index, 1);
            }, function errorCallback(response) {
              alert("Kunne ikke slette prosjekt krav. " +
                JSON.stringify(response));
              console.log("DELETE urlForProjectRequirementDeletion [" + urlForProjectRequirementDeletion +
                "] returned " + response);
            });
          }
        }

      }
    };

    /**
     * projectSelected
     *
     * The user has selected a project, change the view so that the functionality areas
     * are shown and the user can start choosing details related to the project.
     *
     */
    $scope.projectSelected = function (project) {
      console.log("projectSelected[" + JSON.stringify(project) + "] selected.\n");
      $scope.selectedProject = project;

      $scope.projectsView = false;
      $scope.requirementsView = true;

      for (var rel in $scope.selectedProject.links) {
        var relation = $scope.selectedProject.links[rel].rel;
        if (relation == REL_FUNCTIONALITY) {
          var urlForFunctionalityRetrieval = $scope.selectedProject.links[rel].href;
          console.log("Checking urlForFunctionalityRetrieval[" + urlForFunctionalityRetrieval);
          $http({
            method: 'GET',
            url: urlForFunctionalityRetrieval,
            headers: {'Authorization': 'Bearer ' + $scope.token.access_token}
          }).then(function successCallback(response) {
            console.log("GET [" + urlForFunctionalityRetrieval +
              "] returned " + JSON.stringify(response.data));

            $scope.functionalities = [];
            console.log("Check this " + JSON.stringify(response.data[0].links));
            console.log("Check this " + JSON.stringify(response.data[0].referenceChildProjectFunctionality));
            angular.forEach(response.data, function(value, key) {
              console.log("Value is " + JSON.stringify(value.referenceChildProjectFunctionality) + " " + key);
              console.log(Object.keys(value));
              $scope.functionalities.push(value);
            });

            $scope.selectedFunctionality = null;
          }, function errorCallback(response) {
            alert("Kunne ikke hente funksjonalitetsbeskrivelse for prosjekt. " +
              JSON.stringify(response));
            console.log("GET urlForFunctionalityRetrieval[" + urlForFunctionalityRetrieval +
              "] returned " + JSON.stringify(response));
          });
        }
      }
    };

    /**
     * continueSelected
     *
     * When a user accepts a subset of defined requirements are acceptable
     * this method is called. The functionality gets a success status and the progress
     * bar is updated.
     *
     */
    $scope.continueSelected = function () {
      console.log("continueSelected selected.\n");
      console.log("Attempting to find [" + $scope.selectedFunctionality.functionalityNumber + "]");
      $scope.selectedFunctionality.showMe = !$scope.selectedFunctionality.showMe;
      $scope.selectedFunctionality.processed = true;
      for (var val in $scope.functionalities) {
        if ($scope.selectedFunctionality === $scope.functionalities[val]) {
          $scope.functionalities[val].active = false;
          $scope.functionalities[val].done = true;
          var nextOne = parseInt(val) + 1;
          // You are not the last one so
          // point to the next next one
          if (nextOne !== $scope.functionalities.length) {
            $scope.functionalities[nextOne].active = true;
            $scope.selectedFunctionality = $scope.functionalities[nextOne];
            break;
          }
          // Force a loop around if they don't start at 0
          else {
            $scope.functionalities[0].active = true;
            $scope.selectedFunctionality = $scope.functionalities[0];
          }
        }
      }

      var countTrue = 0;
      var countFalse = 0;
      // Find out how far we have progressed through all requirements
      // set progress bar accordingly.

      for (var rel in $scope.functionalities) {
        $scope.functionalities[rel].processed === true ? countTrue++ : countFalse++;
      }

      console.log("Number that is true is [" + countTrue + "]");
      console.log("Number that is false is [" + countFalse + "]");
      console.log("Number that is total is [" + rel + "]");
      var percentage = (countTrue / ++rel) * 100;
      console.log("Percentage is [" + percentage + "]");
      $scope.progressBarValue = percentage;
      console.log("Number that is total is [" + rel + "]");
      // The GUI thinks the document is ready to be created
      if (countTrue === rel) {
        console.log("countTrue === rel CHECKING $scope.selectedProject.documentCreated " + $scope.selectedProject.documentCreated);
        // We do this check to avoid regenerating the document every time someone reloads the project
        // But if they go back and change something, we need to set documentCreated to false
        //if ($scope.selectedProject.documentCreated === false) {
        {
          for (var rel in $scope.selectedProject.links) {
            console.log("checking $scope.selectedProject.links[rel].rel [" + $scope.selectedProject.links[rel].rel + "]");

            var relation = $scope.selectedProject.links[rel].rel;
            console.log("relation [" + relation + "]");
            console.log("REL_DOCUMENT" + REL_DOCUMENT + "]");
            if (relation == REL_DOCUMENT) {
              var urlForDocumentCreation = $scope.selectedProject.links[rel].href;
              console.log("Checking urlForDocumentCreation[" + urlForDocumentCreation);
              $http({
                method: 'POST',
                url: urlForDocumentCreation,
                headers: {'Authorization':  'Bearer ' + $scope.token.access_token}
              }).then(function successCallback(response) {
                console.log("POST urlForDocumentCreation [" + urlForDocumentCreation +
                  "] returned " + JSON.stringify(response));

                // If 201 CREATED, set a download link in progress bar
                $scope.progressBarText = "Last ned dokument";
                $scope.documentHref = urlForDocumentCreation;
              }, function errorCallback(response) {
                alert("Kunne ikke opprette Dokument. " +
                  JSON.stringify(response));
                console.log("POST urlForDocumentCreation [" + urlForDocumentCreation +
                  "] returned " + JSON.stringify(response));
              });
            }
          }
        }
      }
    };


    /**
     * createProject
     *
     * creates a new project associated with the current user.
     *
     * Takes information about the current user from the scope and
     * traverses the RELs looking for a REL_PROJECT (prosjekt). Once
     * it finds this it uses the associated HREF as the address of
     * where to POST the project object.
     *
     *
     */
    $scope.createProject = function () {
      console.log("createProject. Current user is [" + JSON.stringify($scope.currentUser) + "] .\n");

      for (var rel in $scope.currentUser.links) {
        var relation = $scope.currentUser.links[rel].rel;
        if (relation == REL_PROJECT) {
          var urlForProjectCreation = $scope.currentUser.links[rel].href;
          console.log("Checking urlForProjectCreation[" + urlForProjectCreation);
          $http({
            method: 'POST',
            url: urlForProjectCreation,
            headers: {'Authorization': 'Bearer ' + $scope.token.access_token},
            data: {
              projectName: $scope.projectName,
              organisationName: $scope.organisationName
            }
          }).then(function successCallback(response) {
            console.log("POST urlForProjectCreation[" + urlForProjectCreation +
              "] returned " + JSON.stringify(response));

            $scope.projects.push(response.data);
          }, function errorCallback(response) {
            alert("Kunne ikke opprette nytt prosjekt. " +
              JSON.stringify(response));
            console.log("POST urlForProjectCreation[" + urlForProjectCreation +
              "] returned " + JSON.stringify(response));
          });
        }
      }
    };


    $scope.selectFunctionality = function (functionality, index) {
      $scope.selectedFunctionality = functionality;
      $scope.selectedFunctionality.active = true;

      console.log("Current functionality is " + JSON.stringify($scope.selectedFunctionality));

      $scope.indexValue = index;
/*
      for (var i in $scope.functionalities) {
        console.log("Current val is [" + i + "] index is [" + index + "]");
        if (i != index) {
          $scope.functionalities[i].active = false;
        }
        else {
          $scope.functionalities[i].active = true;
        }
        console.log("Current functionality is " + JSON.stringify($scope.functionalities[i]));
      }*/
    };

    $scope.checkFinished = function () {

      var numberDone = 0;

      for (var val in $scope.functionalities) {

        if ($scope.functionalities[val].done === true) {
          numberDone++;
        }
      }

      // Finished so set entire card to success
      if (numberDone === $scope.functionalities.length) {
        $scope.funk1 = true;
      }
    };

    $scope.chooseAndContinue = function () {

    };

    $scope.doLogout= function () {
      $http({
        method: 'GET',
        url: urlForLogout,
        headers: {'Authorization': 'Bearer ' + $scope.token.access_token}
      }).then(function successCallback(response) {
        $scope.projects=null;
        invalidateObjectsOnLogout();
        console.log(" GET urlForLogout[" + urlForLogout +
          "] returned " + JSON.stringify(response));
        changeLocation($scope, startPage, false);
      }, function errorCallback(response) {
        alert("Kunne ikke logge ut. " +
          JSON.stringify(response));
        console.log(" GET urlForLogout[" + urlForLogout +
          "] returned " + JSON.stringify(response));
      });
    }


  }]);
