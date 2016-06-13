angular.module('main')
  .controller('MainController', ['$scope', '$rootScope', '$timeout','$location','$state','Main',
    function($scope,$rootScope, $timeout,$location,$state, Main){

        //go on mainPage
        $state.go("main");
        //show documents
    }]);


angular.module('main')
.controller('MainController1', ['$scope', '$rootScope', '$timeout','$location','$state','Main',
  function($scope,$rootScope, $timeout,$location,$state, Main){

      //go on mainPage
      $state.go("main.search");
      //show documents
  }]);