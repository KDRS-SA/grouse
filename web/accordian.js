var app = angular.module('test', []);

app.controller('requirements', function ($scope) {

  $scope.selectedFunctionality = null;
  $scope.funk1 = false;

  console.log("Hello");
  $scope.kravene = [{
    title: "Krav til oppfyllelse av NOARK5-krav O og B",
    done: false,
    active: false
  }, {
    title: "Krav til ivaretakelse av spesielle bestemmelser",
    done: false,
    active: false
  }, {
    title: "Krav til brukergrensesnitt og brukervennlighet",
    done: false,
    active: false
  }, {
    title: "Krav til arkivdatabase",
    done: false,
    active: false
  }];

  $scope.selectFunctionality = function (functionality, index) {
    $scope.selectedFunctionality = functionality;
    $scope.selectedFunctionality.active = true;
    $scope.indexValue = index;

    for (var i in $scope.kravene) {
      console.log("Current val is [" + i + "] index is [" + index + "]");
      if (i != index) {
        $scope.kravene[i].active = false;
      }
      else {
        $scope.kravene[i].active = true;
      }
      console.log("Current functionality is " + JSON.stringify($scope.kravene[i]));
    }
  };

  $scope.checkFinished = function () {

    var numberDone = 0;

    for (var val in $scope.kravene) {

      if ($scope.kravene[val].done === true) {
        numberDone++;
      }
    }

    // Finished so set entire card to success
    if (numberDone === $scope.kravene.length) {
      $scope.funk1 = true;
    }
  };

  $scope.chooseAndContinue = function () {

    for (var val in $scope.kravene) {
      if ($scope.selectedFunctionality === $scope.kravene[val]) {
        $scope.kravene[val].active = false;
        $scope.kravene[val].done = true;
        var nextOne = parseInt(val) + 1;
        // You are not the last one so
        // point to the next next one
        if (nextOne !== $scope.kravene.length) {
          $scope.kravene[nextOne].active = true;
          $scope.selectedFunctionality = $scope.kravene[nextOne];
          return;
        }
        // Force a loop around if they don't start at 0
        else {
          $scope.kravene[0].active = true;
          $scope.selectedFunctionality = $scope.kravene[0];
        }
      }
    }
  }

});
