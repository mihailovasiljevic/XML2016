angular.module('act').controller('actCtrl', ['$scope','$state',
    function($scope,$state) {
		$scope.addAct = function(){
			$state.go('view.addAct');
		}
}]);