angular.module('main')
  .controller('MainController', ['$scope', '$rootScope', '$timeout','$location','$state','Main',
    function($scope,$rootScope, $timeout,$location,$state, Main){

        //go on mainPage
        $state.go("main");
        //show documents
        $scope.propose = function(){
            $state.go('main.act');
        }

        $scope.find = function(){
            $scope.acts = Main.query();
        };

        $scope.showAct = function(uri){
            console.log(uri);
        };
    }]);

