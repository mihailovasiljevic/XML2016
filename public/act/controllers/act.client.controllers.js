angular.module('act').controller('actCtrl', ['$scope','$state','Propis',
    function($scope,$state,Propis) {
		$scope.addAct = function(){
			$state.go('view.addAct');
		}
		
		$scope.act = new Propis();
		
		$scope.sendAct = function(){
			
			$scope.act.$save(function() {
			
				$state.go("view");
				
			});
		}
}]);