var app = angular.module('grouse-app', []);

/**
 * login.js
 *
 * Used with index.html to handle the login process.
 * On a successful login the application redirects further
 * on in in the application.
 *
 * Also used to create an account for the application
 *
 */

var loginController = app.controller('LoginController',
  ['$scope', '$http', '$httpParamSerializer', function ($scope, $http, $httpParamSerializer) {

    //console.clear();
    console.log("loginController on page load.");

    $http({
      url: startingAddress,
      method: 'GET'
    }).then(function successCallback(response) {
      console.log("GET ON [" + startingAddress + "]" + JSON.stringify(response.data));

      for (var rel in response.data.apiDetails) {
        var relation = response.data.apiDetails[rel].rel;
        if (relation === REL_LOGIN_OAUTH) {
          loginAddress = response.data.apiDetails[rel].href.split(/(\?)/)[0];
          console.log("loginAddress  is set to [" + loginAddress  + "]");
        }
        if (relation === REL_USER) {
          userAddress = response.data.apiDetails[rel].href;
          setUserAddress(userAddress);
          console.log("userAddress  is set to [" + userAddress  + "]");
        }
        if (relation === REL_LOGOUT_OAUTH) {
          logoutAddress = response.data.apiDetails[rel].href;
          setLogoutAddress(logoutAddress);
          console.log("logoutAddress   is set to [" + logoutAddress   + "]");
        }
      }
    }, function errorCallback(response) {
      alert("Ser ut som om tjeneren [" + startingAddress + "] ikke er tilgjengelig! Prøv igjen senere!");
      console.log("ERROR GET ON [" + startingAddress + "]" + JSON.stringify(response));
    });

    $scope.doLogin = function () {
      console.log("doLogin called with username [" + $scope.emailAddress + "]" + ", password[" + $scope.password + "], address [" +
        loginAddress + "]");

      $http({
        url: loginAddress,
        method: 'POST',
        headers: {
          "Authorization": "Basic " + btoa(oauthClientId + ":" + oauthClientSecret),
          "Content-type": "application/x-www-form-urlencoded; charset=utf-8"
        },
        data: $httpParamSerializer({
          grant_type: "password",
          username: $scope.emailAddress,
          password: $scope.password,
          client_id: oauthClientId
        })
      }).then(function successCallback(response) {
        console.log(" Before POST [" + $scope.emailAddress + "]" + JSON.stringify(response.data));
        console.log(" POST  ON [" + loginAddress + "]" + JSON.stringify(response.data));
        setAccessToken(response.data);
        setUsername($scope.emailAddress);
        changeLocation($scope, mainPage, false);
        // Blanking out password straight away!
        $scope.password = '';
      }, function errorCallback(response) {
        alert("Kunne ikke logge på. Feilmelding er " + JSON.stringify(response));
        console.log(" ERROR POST ON [" + loginAddress + "]" + JSON.stringify(response));
      });
    };

    $scope.createAccount = function () {

      $http({
        url: userAddress,
        method: 'POST',
        data: {
          username: $scope.emailAddressRegister,
          password: $scope.passwordRegister
        }
      }).then(function successCallback(response) {
        console.log(" POST  ON [" + userAddress + "]" + JSON.stringify(response.data));
        setAccessToken(response.data);
        setUsername($scope.emailAddress);
        $scope.emailAddress = $scope.emailAddressRegister;
        $scope.password = '';
      }, function errorCallback(response) {
        alert("Kunne ikke logge på. Feilmelding er " + JSON.stringify(response));
        console.log(" ERROR POST ON [" + userAddress + "]" + JSON.stringify(response));
      });
    };
  }]
);
