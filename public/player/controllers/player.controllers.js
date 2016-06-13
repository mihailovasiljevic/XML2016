angular.module('player').controller('mainCtrl', ['$scope','$state',
    function($scope,$state) {
		$state.go("input");
}]);

angular.module('player').controller('inputCtrl', ['$scope','$state','Player',
	 function($scope,$state,Player) {
		$scope.player = new Player();
		$scope.create = function() {
			$scope.player.$save(function() {
				$state.go("view");
			});
		}
 }]);

angular.module('player').controller('viewCtrl', ['$scope','$state','Player',
 	 function($scope,$state,Player) {
		
	Player.query(function(response) {
		$scope.players = response;
	}); 
	
	$scope.back = function() {
		$state.go("input");
	}
	
}]);