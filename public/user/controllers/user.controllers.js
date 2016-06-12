angular.module('user').controller('mainCtrl1', ['$scope','$state',
    function($scope,$state) {
		$state.go("input");
}]);

angular.module('user').controller('inputCtrl1', ['$scope','$state','User',
	 function($scope,$state,User) {
		$scope.user = new User();
		$scope.create = function() {
			$scope.user.$save(function() {
				$state.go("view");
			});
		}
 }]);

angular.module('user').controller('viewCtrl1', ['$scope','$state','User',
 	 function($scope,$state,User) {
		
	User.query(function(response) {
		$scope.users = response;
	}); 
	
	$scope.back = function() {
		$state.go("input");
	}
	
}]);