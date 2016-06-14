angular.module('act').controller('actCtrl', ['$scope','$state','Propis',
    function($scope,$state,Propis) {
		$scope.addAct = function(){
			$state.go('view.addAct');
		}
		
		$scope.act = new Propis();
		
		$scope.sendAct = function(){
			
			$scope.act.$save(function(response) {
				console.log(response);
				if(response.map.error)
					$scope.error = response.map.error;
			});
		}
		$scope.showForm = function(){
			$state.go("act");
		}
}]);