angular.module('amendment')
  .controller('AmendmentController', ['$scope', '$rootScope', '$timeout','$location','$state','Main','Search','Amandman','User',
    function($scope,$rootScope, $timeout,$location,$state, Main, Search,Amandman,User){

        //go on mainPage
        //$state.go("main");
        //show documents
	  $scope.loggedIn=false;
	  	  	User.get(function(response){
	  	  		if(response.uloga)
	 	  			$scope.loggedIn = true;
	  	  		else $scope.loggedIn = false;
	  	  	})	
	  
	  
        $scope.propose = function(){
            $state.go('main.amendment');
            User.get(function(response){
    			if(!response.uloga)
    				$state.go('main');
    		})
        }
    

        $scope.find = function(){
            $scope.acts = Main.query(
                function(response){
                     $scope.listForShowing = $scope.acts;
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
			var text = $scope.amendment.text;
			$scope.error="";
			$scope.amendment.$save(function(response) {
			console.log(response);
			if(response.map!=undefined)
			if(response.map.error!=""){
			$scope.error = response.map.error;
			$scope.amendment.text = text;
			}
			});

        }
    }]);