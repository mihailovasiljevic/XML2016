angular.module('act').controller('actCtrl', ['$scope','$state','Propis','User',
    function($scope,$state,Propis,User) {
		$scope.addAct = function(){
			$state.go('view.addAct');
		}
		
		User.get(function(response){
			if(!response.uloga)
				$state.go('main');
		})
		
		
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
			
			if(response.map!=undefined)
			if(response.map.error!==""){
			$scope.error = response.map.error;
			$scope.act.text = text;
				
			}
			});
		}
		$scope.showForm = function(){
			$state.go("act");
		}
}]);