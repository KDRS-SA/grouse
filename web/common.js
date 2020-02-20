var mainPage = "kravspec.html";
var startPage = "index.html";

var REQUIREMENT = "krav";
var FUNCTIONALITY = "funksjon";

var REL_PROJECT = "prosjekt";
var REL_SELF = "self";
var REL_REQUIREMENT = "krav";
var REL_DOCUMENT = "document";
var REL_FUNCTIONALITY = "funksjon";
var REL_LOGIN_OAUTH = "login OAuth2";
var REL_LOGOUT_OAUTH = "logout OAuth2";

var REL_USER = "konto";

var startingAddress = "http://localhost:9294/grouse/";
var loginAddress = "";
var logoutAddress = "";
var userAddress = "";
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
  localStorage.setItem("username", t);
  console.log("Adding username " + t + " to local storage");
};

var setUserAddress = function (t) {
  localStorage.setItem("userAddress", t);
  console.log("Adding userAddress " + t + " to local storage");
};

var getUserAddress = function () {
  var token = localStorage.getItem("userAddress");
  console.log("userAddress is " + token);
  if (typeof token !== 'undefined') {
    return token;
  }
  else {
    return null;
  }
};

var setLogoutAddress = function (t) {
  localStorage.setItem("logoutAddress", t);
  console.log("Adding logoutAddress " + t + " to local storage");
};

var getLogoutAddress = function () {
  var token = localStorage.getItem("logoutAddress");
  console.log("logoutAddress is " + token);
  if (typeof token !== 'undefined') {
    return token;
  }
  else {
    return null;
  }
};

var getUsername = function () {
  var token = localStorage.getItem("username");
  console.log("Username is " + token);
  if (typeof token !== 'undefined') {
    return token;
  }
  else {
    return null;
  }
};


var invalidateObjectsOnLogout = function () {
  setAccessToken('');
  setUsername('');
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
