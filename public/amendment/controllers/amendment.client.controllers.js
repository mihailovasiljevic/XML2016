angular.module('amendment')
  .controller('AmendmentController', ['$scope', '$rootScope', '$timeout','$location','$state','Search','Amandman',
    function($scope,$rootScope, $timeout,$location,$state, Search,Amandman){

        //go on mainPage
        //$state.go("main");
        //show documents
        $scope.propose = function(){
            $state.go('main.amendment');
        }
    

        $scope.find = function(){
            $scope.amendments = Amandman.query(
                function(response){
                     $scope.listForShowing = $scope.amendments;
            });
           
        };

        $scope.showAct = function(uri){
            console.log(uri);
        };
        $scope.criteria = "";


        $scope.searchActs = function(){
            
            if(!$scope.criteria){
                $scope.error = "Morate uneti tekst pretrage.";
                return;
            }
            var searchCriteria = new Search({
                criteria: $scope.criteria
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

        $scope.selected = "";

        $scope.filter = function(){

            $scope.listForShowing = [];

            for(var i = 0; i < $scope.acts.length; i++){
                    if($scope.acts[i].status == $scope.selected){
                        $scope.listForShowing.push($scope.acts[i]);
                    }
            }

        }
        
        
        $scope.amendment = new Amandman();
        $scope.sendAmendment = function(){
			if(!$scope.amendment.text){
				$scope.error = "Polje ne sme biti prazno. Molim vas unesite dokument.";
				return;
			}
			$scope.amendment.$save(function(response) {
				console.log(response);
				if(response.map.error)
					$scope.error = response.map.error;
			});

        }
    }]);