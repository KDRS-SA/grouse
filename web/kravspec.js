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
  ['$scope', '$http', '$window', '$location', '$anchorScroll',
    function ($scope, $http, $window, $location, $anchorScroll) {

      $scope.userIsAdmin = false;
      $scope.token = getAccessToken();
      $scope.username = getUsername();
      $scope.userAddress = getUserAddress() + "/";

      if (typeof $scope.token === 'undefined' || $scope.token == null) {
        console.log("No valid token defined! Need to log in." + $scope.token);
        changeLocation($scope, startPage, false);
        return;
      }

      console.log("Valid token exists! " + JSON.stringify($scope.token));

      // Retrieve the current user and what they can do $scope.username

      $http({
        method: 'GET',
        url: $scope.userAddress + $scope.username,
        headers: {'Authorization': 'Bearer ' + $scope.token.access_token}
      }).then(function successCallback(response) {
        $scope.currentUser = response.data;
        console.log(" GET urlForUserDetails [" + $scope.userAddress + $scope.username +
          "] returned " + JSON.stringify(response));
        console.log(" GET $scope.currentUser  [" + $scope.userAddress + $scope.username +
          "] returned " + JSON.stringify($scope.currentUser));

        $scope.projectsView = true;
        $scope.requirementsView = false;
        $scope.loadingData = false;

        $scope.priorityValues = ['O', '1', '2', 'O (i)', '1 (i)', '2 (i)'];

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
          if (relation === REL_PROJECT) {
            var urlForProjects = $scope.currentUser.links[rel].href;
            console.log("Checking urlForProjects[" + urlForProjects);
            $http({
              method: 'GET',
              url: urlForProjects,
              headers: {'Authorization': 'Bearer ' + $scope.token.access_token}
            }).then(function successCallback(response) {
              $scope.projects = response.data;
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
      });

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
       * functionality_selected
       *
       * Sets the chosen functionality from GUI. Retrieves any related metadata.
       */
      $scope.isUserAdmin = function () {

        console.log("Checking (isUserAdmin ) if user is admin [" + JSON.stringify($scope.currentUser) + "].\n");
        for (var authority in $scope.currentUser.authorities) {
          if ($scope.currentUser.authorities[authority].name === "ROLE_ADMIN")
            $scope.userIsAdmin = true;
        }
        $scope.userIsAdmin = false;
      };


      /**
       * updateFunctionalityProcessed
       *
       * handles a change in requirement text.
       */

      updateFunctionalityProcessed = function (functionality) {
        console.log("updateFunctionalityProcessed [" + JSON.stringify(functionality) + "] selected.\n");


        var patchString = '[{ "op": "replace", "path": "/processed", "value": "' +
          functionality.processed + '"}]';

        console.log("updateFunctionalityProcessed. Attempting PATCH[" + patchString + "].\n");

        for (var rel in functionality.links) {
          var relation = functionality.links[rel].rel;
          if (relation === REL_SELF) {
            var urlForFunctionalityChange = functionality.links[rel].href;
            console.log("Checking urlForFunctionalityProcessedChange [" + urlForFunctionalityChange);
            $http({
              method: 'PATCH',
              url: urlForFunctionalityChange,
              headers: {'Authorization': 'Bearer ' + $scope.token.access_token},
              data: patchString
            }).then(function successCallback(response) {
              console.log("PATCH [" + urlForFunctionalityChange +
                "] returned " + JSON.stringify(response));
            }, function errorCallback(response) {
              alert("Kunne ikke endre funksjon. " +
                JSON.stringify(response));
              console.log("PATCH urlForFunctionalityChange l[" + urlForFunctionalityChange +
                "] returned " + JSON.stringify(response));

            });
            return;
          }
        }
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
            return;
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

        console.log("selected Functionality is :" + JSON.stringify($scope.selectedFunctionality));

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

      $scope.deleteRequirementAndRow = function (projectRequirement, index) {
        console.log("deleteRequirementAndRow. Working on [" + JSON.stringify(projectRequirement) + "]");

        if ($window.confirm("Er du sikker du vil slette følgende krav: \n" + projectRequirement.requirementText)) {

          for (var rel in projectRequirement.links) {
            var relation = projectRequirement.links[rel].rel;
            if (relation === REL_SELF) {
              var urlForProjectRequirementDeletion = projectRequirement.links[rel].href;
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
              return;
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
            console.log("Checking urlForFunctionalityRetrieval [" + urlForFunctionalityRetrieval);
            $http({
              method: 'GET',
              url: urlForFunctionalityRetrieval,
              headers: {'Authorization': 'Bearer ' + $scope.token.access_token}
            }).then(function successCallback(response) {
              console.log("GET [" + urlForFunctionalityRetrieval +
                "] returned " + JSON.stringify(response.data));

              $scope.functionalities = response.data;
              $scope.checkFinished();
              /*
              console.log("Check this " + JSON.stringify(response.data[0].links));
              console.log("Check this " + JSON.stringify(response.data[0].referenceChildProjectFunctionality));
              angular.forEach(response.data, function (value, key) {
                console.log("Value is " + JSON.stringify(value.referenceChildProjectFunctionality) + " " + key);
                console.log(Object.keys(value));
                $scope.functionalities.push(value);
              });
*/
              $scope.selectedFunctionality = $scope.functionalities[0].referenceChildProjectFunctionality[0];
              $scope.selectedFunctionality.active = true;

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
        //console.log("continueSelected selected.\n");
        //console.log("Attempting to find [" + $scope.selectedFunctionality.functionalityNumber + "]");
        $scope.selectedFunctionality.showMe = !$scope.selectedFunctionality.showMe;
        $scope.selectedFunctionality.processed = true;
        updateFunctionalityProcessed($scope.selectedFunctionality);
        $location.hash($scope.selectedFunctionality.title);
        $anchorScroll();

        for (var parentIndex in $scope.functionalities) {
          var childFunctionality = $scope.functionalities[parentIndex].referenceChildProjectFunctionality;
          if (childFunctionality != null) {
            for (var childIndex in childFunctionality) {
              // console.log("2nd level is: [" + childFunctionality[childIndex].title + "]");
              //console.log("Checking  [" + childFunctionality[childIndex].functionalityNumber + "," +
              //$scope.selectedFunctionality.functionalityNumber +  "]");
              // Set the selectedFunctionality to be the next functionality
              if ($scope.selectedFunctionality === childFunctionality[childIndex]) {
                //$scope.selectedFunctionality.processed = true;
                $scope.selectedFunctionality.active = false;
//                console.log("Finalising (2) is: [" + childFunctionality[childIndex].title + "]");
                //              console.log("Values are: [" + JSON.stringify(childFunctionality[childIndex]) + "]");
                //            console.log("childIndex : [" + childIndex + "] childFunctionality.length [" +
                //             childFunctionality.length + "]");
                var nextItemIdx = parseInt(childIndex) + 1;
                // If we are at the same level, just set the next node to current node
                if (nextItemIdx < childFunctionality.length) {
                  // If the next node is a parent node, i.e has a referenceChildProjectFunctionality.length != 0
                  // then jump to the first node of that child
                  if (childFunctionality[nextItemIdx].referenceChildProjectFunctionality.length > 0) {
                    $scope.selectedFunctionality = childFunctionality[nextItemIdx].referenceChildProjectFunctionality[0];
                    $scope.selectedFunctionality.active = true;
                  }
                  // This just the next functionality, set selectedFunctionality to the next functionality
                  else {
                    //        console.log("Val is : [" + val + "]");
                    //        console.log("Setting values for: [" + JSON.stringify(childFunctionality[val]) + "]");
                    $scope.selectedFunctionality = childFunctionality[nextItemIdx];
                    $scope.selectedFunctionality.active = true;
                  }
                  return;
                }
                // We have hit the last one. So set the active one to be the first one. This will mean the
                // GUI will loop around. This also allows a user to start at any requirement
                else {
                  // There are no more siblings, and this node contains no children, I must go back to the top level
                  // I have to decide if there is another node at the top level to jump to or that we are finished.

                  var nextParent = parseInt(parentIndex) + 1;

                  // No more, loop around
                  if (typeof $scope.functionalities[nextParent] === 'undefined') {
                    $scope.selectedFunctionality.active = true;
                  }
                  else {
                    $scope.selectedFunctionality = $scope.functionalities[nextParent].referenceChildProjectFunctionality[0];
                    $scope.selectedFunctionality.active = true;
                  }
                  return;
                }
              }
              var childChildFunctionality = childFunctionality[childIndex].referenceChildProjectFunctionality;
              for (var childChildIndex in childChildFunctionality) {
                // console.log("3rd level is: [" + childChildFunctionality[childChildIndex].title + "]");
                // This is the current functionality
                if ($scope.selectedFunctionality === childChildFunctionality[childChildIndex]) {
                  //       console.log("Finalising (3) is: [" + childChildFunctionality[childChildIndex].title + "]");
                  //       console.log("Values are: [" + childChildFunctionality[childChildIndex] + "]");
                  //       console.log("childChildIndex : [" + childChildIndex + "] childChildFunctionality.length [" +
                  //        childChildFunctionality.length + "]");
                  //$scope.selectedFunctionality.processed = true;
                  $scope.selectedFunctionality.active = false;
                  // If we are at the same level, just set the next node to current node
                  if (childChildIndex < childChildFunctionality.length) {
                    var subVal = parseInt(childChildIndex) + parseInt(1);

                    // Hit the end of all the children, go back up to parent
                    console.log("childChildIndex : [" + childChildIndex + "] childChildFunctionality.length [" +
                      childChildFunctionality.length + "]");

                    if (subVal === childChildFunctionality.length) {
                      var parentIdx = parseInt(childIndex) + 1;
                      $scope.selectedFunctionality = childFunctionality[parentIdx];
                      $scope.selectedFunctionality.active = true;

                      // Need to decide if we stay at parent or parent has children so set child as current
                      if ($scope.selectedFunctionality.referenceChildProjectFunctionality.length > 0) {
                        $scope.selectedFunctionality = $scope.selectedFunctionality.referenceChildProjectFunctionality[0];
                        $scope.selectedFunctionality.active = true;
                      }
                      return;
                    }
                    //       console.log("Val is : [" + subVal + "]");
                    //       console.log("Setting values for: [" + JSON.stringify(childChildFunctionality[subVal]) + "]");
                    $scope.selectedFunctionality = childChildFunctionality[subVal];
                    $scope.selectedFunctionality.active = true;
                  }
                  // Go back up to the parent!
                  else {

                    var nextIndex = parseInt(parentIndex) + 1;
                    // Here!!
                    $scope.selectedFunctionality = $scope.functionalities[nextIndex].referenceChildProjectFunctionality[0];
                    $scope.selectedFunctionality.active = true;
                  }
                  return;
                }
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

        $scope.loadingData = true;
        for (var rel in $scope.currentUser.links) {
          var relation = $scope.currentUser.links[rel].rel;
          if (relation === REL_PROJECT) {
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
              $scope.loadingData = false;
              $scope.projects.push(response.data);
            }, function errorCallback(response) {
              $scope.loadingData = false;
              alert("Kunne ikke opprette nytt prosjekt. " +
                JSON.stringify(response));
              console.log("POST urlForProjectCreation[" + urlForProjectCreation +
                "] returned " + JSON.stringify(response));
            });
          }
        }
      };


      $scope.selectFunctionality = function (functionality, index) {

        // deselect the previously selected functionality
        if ($scope.selectedFunctionality != null) {
          $scope.selectedFunctionality.active = false;
        }

        $scope.selectedFunctionality = functionality;
        $scope.selectedFunctionality.active = true;

        console.log("Current functionality is " + JSON.stringify($scope.selectedFunctionality));
      };


      /**
       * Checks if all requirements have been checked off. Takes a short cut by
       * just checking the top level.
       *
       */

      $scope.checkFinished = function () {

        var totalCount = 0;
        var countTrue = 0;
        var countFalse = 0;

        for (var parentIndex in $scope.functionalities) {
          var childFunctionality = $scope.functionalities[parentIndex].referenceChildProjectFunctionality;
          if (childFunctionality != null) {
            for (var childIndex in childFunctionality) {
              var childChildFunctionality = childFunctionality[childIndex].referenceChildProjectFunctionality;

              //  console.log("Second level  [" + childIndex + "]" +
              // JSON.stringify(childFunctionality[childIndex].referenceChildProjectFunctionality) );
              if (typeof childChildFunctionality !== 'undefined' && childChildFunctionality.length > 0) {
                //console.log("+ Second level  [" + childIndex + "]" + JSON.stringify(childChildFunctionality));
                for (var childChildIndex in childChildFunctionality) {
                  totalCount++;

                  //console.log(childChildFunctionality[childChildIndex].title + ", [" +
                  //  childChildFunctionality[childChildIndex].processed  + "]");
                  if (childChildFunctionality[childChildIndex].processed === true) {
                    countTrue++;
                  }
                  else if (childChildFunctionality[childChildIndex].processed === false) {
                    countFalse++;
                  }
                }
              }
              else {
                //console.log("-- Second level  [" + childIndex + "]" + JSON.stringify(childFunctionality));
                totalCount++;
                if (childFunctionality[childIndex].processed === true) {
                  countTrue++;
                }
                else if (childFunctionality[childIndex].processed === false) {
                  countFalse++;
                }
              }
            }
          }
        }

        var percentage = (countTrue / totalCount) * 100;
        console.log("Number that is true is [" + countTrue + "], false is [" + countFalse + "], total is [" + totalCount + "], percentage is [" + percentage + "]");
        $scope.progressBarValue = percentage;
        if (countTrue === totalCount) {
          $scope.funk1 = true;
          for (var rel in $scope.selectedProject.links) {
            console.log("checking $scope.selectedProject.links[rel].rel [" + $scope.selectedProject.links[rel].rel + "]");

            var relation = $scope.selectedProject.links[rel].rel;
            console.log("relation [" + relation + "]");
            console.log("REL_DOCUMENT" + REL_DOCUMENT + "]");
            if (relation === REL_DOCUMENT) {
              var urlForDocumentCreation = $scope.selectedProject.links[rel].href;
              console.log("Checking urlForDocumentCreation[" + urlForDocumentCreation);
              $http({
                method: 'POST',
                url: urlForDocumentCreation,
                headers: {'Authorization': 'Bearer ' + $scope.token.access_token}
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
      ;

      $scope.chooseAndContinue = function () {

      };

      $scope.doLogout = function () {
        var urlForLogout = getLogoutAddress();
        $http({
          method: 'GET',
          url: urlForLogout,
          headers: {'Authorization': 'Bearer ' + $scope.token.access_token}
        }).then(function successCallback(response) {
          $scope.projects = null;
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
      };


      $scope.doLoadProjectView = function () {
        $scope.projectsView = true;
        $scope.requirementsView = false;
      };

      /**
       * download the document. The code here is adapted from:
       *  https://stackoverflow.com/questions/14215049/how-to-download-file-using-angularjs-and-calling-mvc-api
       */
      $scope.doDownloadDocument = function () {
        $http({
          method: 'GET',
          url: $scope.documentHref,
          headers: {'Authorization': 'Bearer ' + $scope.token.access_token},
          responseType: 'arraybuffer'
        }).then(function successCallback(response) {

          var file = new Blob([response.data], {
            type: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
          });

          var fileURL = URL.createObjectURL(file);
          var a = document.createElement('a');
          a.href = fileURL;
          a.target = '_blank';
          a.download = 'kravspec.docx';
          document.body.appendChild(a);
          a.click();
          document.body.removeChild(a);

        }, function errorCallback(response) {
          alert("Kunne ikke logge ut. " +
            JSON.stringify(response));
          console.log(" GET documentHref[" + $scope.documentHref +
            "] returned " + JSON.stringify(response));
        });
      }
    }
  ]
);
