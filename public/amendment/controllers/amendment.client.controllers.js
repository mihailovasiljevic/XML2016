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
			var text = $scope.amendment.text;
			$scope.error="";
			$scope.amendment.$save(function(response) {
				console.log(response);
				if(response.map!=undefined){
                    if(response.map.error!=""){
                        $scope.error = response.map.error;
                        $scope.amendment.text = text;
                    }
                    else{
                     $scope.amendment.text="";
                     $scope.error = "";
                    }
                }

			});
			
			

        }
        
        
        $scope.withdrawAmendment = function(amendment){
        	var index1 = $scope.listForShowing.indexOf(amendment);
        	var index2 = $scope.amendments.indexOf(amendment);
        	amendment.uri=amendment.uri.replace('/acts/','');
        	amendment.uri=amendment.uri.replace('.xml','');
        	console.log(amendment.uri);
        	Amandman.get({amendmentId:amendment.uri},function(response){ 
        	response.$update({amendmentId:amendment.uri},function(response) {
        		amendment.status="povucen";
        		$scope.listForShowing.splice(index1,1);
        		$scope.amendments.splice(index2,1);
        				
   
        		
				//Popraviti ovo
				//if(response.map.error)
				//	$scope.error = response.map.error;
			});
        	 });
        };
    }]);