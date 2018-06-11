var mainPage = "kravspec.html";
var startPage = "index.html";

var REQUIREMENT = "krav";
var FUNCTIONALITY = "funksjon";

var REL_PROJECT = "prosjekt";
var REL_SELF = "self";
var REL_REQUIREMENT = "krav";
var REL_DOCUMENT = "dokument";
var REL_FUNCTIONALITY = "funksjon";

var serverLoginAddress = "http://localhost:9294/grouse/oauth/token";
var startingAddress = "http://localhost:9294/grouse/bruker/";
var urlForLogout = "http://localhost:9294/grouse/oauth/revoke-token";
var oauthClientId = "grouse-client";
var oauthClientSecret = "secret";

var setAccessToken = function (t) {
  localStorage.setItem("token", JSON.stringify(t));
  console.log("Adding token " + JSON.stringify(t) + " to local storage");
};

var getAccessToken = function () {
  var token = localStorage.getItem("token");
  if (typeof token !== 'undefined') {
    return JSON.parse(token);
  }
  else {
    return null;
  }
};

var setUsername = function (t) {
  localStorage.setItem("username", JSON.stringify(t));
  console.log("Adding username " + JSON.stringify(t) + " to local storage");
};

var getUsername = function () {
  var token = localStorage.getItem("username");
  if (typeof token !== 'undefined') {
    return JSON.parse(token);
  }
  else {
    return null;
  }
};


var invalidateObjectsOnLogout = function () {
  localStorage.clear();
};


var changeLocation = function ($scope, url, forceReload) {
  $scope = $scope || angular.element(document).scope();
  console.log("changeLocation to URL" + url);
  if (forceReload || $scope.$$phase) {
    window.location = url;
  }
  else {
    $location.path(url);
    $scope.$apply();
  }
};
