angular.module('session')
  .controller('SessionController', ['$scope', '$rootScope', '$timeout','$location','$state','Main','Search',
    function($scope,$rootScope, $timeout,$location,$state, Main, Search){

        var acts = new Main({
            criteria: 'predlozen'
        });

        $scope.find = function(){
            var searchCriteria = new Search({
                criteria: "predlozen"
            });
            searchCriteria.$save(function(response){
                if(response.map){
                    if(response.map.error){
                        if($scope.results) $scope.results = [];
                        $scope.error = response.map.error;
                        return;                   
                    }
                }
                else{
                    $scope.error = "";
                    $scope.results = response.myArrayList;
                }
            })
        };

        $scope.start = function(){
            var date = new Date();
            var today = date.getDay()+"."+date.getMonth()+"."+date.getFullYear()+".";
            var hour = date.getHours()+":"+date.getMinutes();
            $scope.sessionDescription = "Počela sednica za dan " + today + " u "+hour+" . Na glasanje se mogu staviti sledeći akti: ";
        }

    }]);