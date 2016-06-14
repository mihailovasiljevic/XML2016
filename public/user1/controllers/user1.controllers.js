angular.module('user1').controller('mainCtrl11', ['$scope','$state',
    function($scope,$state) {
		$state.go("input");
}]);

angular.module('user1').controller('inputCtrl11', ['$scope','$state','User',
	 function($scope,$state,User) {
		$scope.user = new User();
		$scope.create = function() {
			$scope.user.$save(function() {
				$state.go("main.show1");
			});
		}
 }]);

angular.module('user1').controller('inputCtrl22', ['$scope','$state','User',
  function($scope,$state,User) {
	$scope.user = new User();
	$scope.create = function() {
		$scope.user.$save(function() {
			$state.go("main.show1");
		});
	}
}]);                                           	

angular.module('user1').controller('viewCtrl11', ['$scope','$state','User',
 	 function($scope,$state,User) {

	$state.go("view.main1");
	
	User.query(function(response) {
		$scope.users = response;
	}); 
	
	$scope.back = function() {
		$state.go("input");
	}
	

	
	
}]);
