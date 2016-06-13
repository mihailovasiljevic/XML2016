angular.module('acts')
  .controller('ActsController', ['$scope', '$rootScope', '$timeout','$location','$state','Acts',
    function($scope,$rootScope, $timeout,$location,$state, Acts){

        //go on mainPage
        //$state.go('main');
        //show documents
        $scope.callSearch = function(){
            $state.go('search');
        }

        $scope.find = function(){
            $scope.acts = Acts.query();
        }
    }]);