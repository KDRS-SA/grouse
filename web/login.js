var app = angular.module('grouse-app', []);

/**
 * login.js
 *
 * Used with index.html to handle the login process.
 * On a successful login the application redirects further
 * on in in the application.
 *
 */

var loginController = app.controller('LoginController',
  ['$scope', '$http', '$httpParamSerializer', function ($scope, $http, $httpParamSerializer) {

    console.log("loginController on page load.");
    $scope.username = "admin@kdrs.no";
    $scope.password = "password";

    $scope.doLogin = function () {
      console.log("doLogin called with username [" + $scope.username + "] [" + $scope.password + "]");

      $http({
        url: serverLoginAddress,
        method: 'POST',
        headers: {
          "Authorization": "Basic " + btoa(oauthClientId + ":" + oauthClientSecret),
          "Content-type": "application/x-www-form-urlencoded; charset=utf-8"
        },
        data: $httpParamSerializer({
          grant_type: "password",
          username: $scope.username,
          password: $scope.password,
          client_id: oauthClientId
        }),
      }).then(function successCallback(response) {
        console.log(" POST  ON [" + serverLoginAddress + "]" + JSON.stringify(response.data));
        setAccessToken(response.data);
        setUsername($scope.username);
        changeLocation($scope, mainPage, false);
        // Blanking out password straight away!
        $scope.password = '';
      }, function errorCallback(response) {
        console.log(" ERROR POST ON [" + serverLoginAddress + "]" + JSON.stringify(response));
      });
    };
  }]
);
