angular.module('act').controller('actCtrl', ['$scope','$state','$timeout','$window','$location','Propis',
    function($scope,$state,$timeout, $location,$window,Propis) {
		$scope.addAct = function(){
			$state.go('view.addAct');
		}
		
		$scope.act = new Propis();
		
		$scope.sendAct = function(){
			if(!$scope.act.text){
				$scope.error = "Polje ne sme biti prazno. Molim vas unesite dokument.";
				return;
			}
			var text = $scope.act.text;
			$scope.error=""
			$scope.act.$save(function(response) {
				console.log(response);
				$scope.act.text="";
				
				if(response.map!=undefined){
					if(response.map.error!==""){
						$scope.error = response.map.error;
						$scope.act.text = text;
						return;
					}
				}else{
					$scope.act.text="";
					$scope.error = "Uspesno ste uneli akt.";
					$timeout(function(){
						$window.location.replace("http://localhost:9000");
						
					},0)
				}
			});
		}
		$scope.showForm = function(){
			$state.go("act");
		}
}]);