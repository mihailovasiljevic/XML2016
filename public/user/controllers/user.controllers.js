angular.module('user').controller('mainCtrl1', ['$scope','$state',
    function($scope,$state) {
		$state.go("input");
}]);

angular.module('user').controller('inputCtrl1', ['$scope','$state','User','UserRegister',
	 function($scope,$state,User,UserRegister) {
		$scope.user = new UserRegister();
		$scope.user.uloga = "odbornik";
		$scope.create = function() {
			if(!$scope.user.username){
				$scope.error = "Polje ne sme biti prazno. Unesite Username.";
				return;
			}
			if(!$scope.user.password){
				$scope.error = "Sifra mora imati izmedju 8-20 znakova.Mora sadrzati makar jedno malo i makar jedno veliko slovo i non alpha karakter(broj ili simbol).";
				return;
			}
			
			if(!$scope.user.repeat_password){
				$scope.error = "Polje ne sme biti prazno. Unesite Repeat Password.";
				return;
			}
			
			if(!$scope.user.ime){
				$scope.error = "Polje ne sme biti prazno. Unesite Ime.";
				return;
			}
			if(!$scope.user.prezime){
				$scope.error = "Polje ne sme biti prazno. Unesite Prezime.";
				return;
			}
			
			if(!$scope.user.email){
				$scope.error = "Email mora biti u formatu: __@__.__";
				return;
			}
			
			if(!$scope.user.certificate){
				$scope.error = "Polje ne sme biti prazno. Unesite Certificate.";
				return;
			}
			
		
			$scope.user.$save(function(response) {
				if(response.map.error){
					console.log('usao1');
					$scope.error = response.map.error;		
				}
				else
				 $state.go("main.show");
			});
		}
 }]);

angular.module('user').controller('inputCtrl2', ['$scope','$state','User',
  function($scope,$state,User) {
	
	User.get(function(response){
 		console.log(JSON.stringify(response));
 		if(response.uloga)
		$state.go('main');
 	})
	
	$scope.user = new User();
	$scope.create = function() {
		
		if(!$scope.user.username){
			$scope.error = "Polje ne sme biti prazno. Unesite Username.";
			return;
		}
		if(!$scope.user.password){
			$scope.error = "Polje ne sme biti prazno. Unesite Password.";
			return;
		}
				
		$scope.user.$save(function(response) {
			
			if(response.map.error){
				console.log(response);
				$scope.error = response.map.error;		
			}	
			else 
				$state.go("main.show");				
		});
		
	
	}
}]);                                           	

angular.module('user').controller('viewCtrl1', ['$scope','$state','User',
 	 function($scope,$state,User) {
	
	

	$state.go("view.main");
	
	User.query(function(response) {
		$scope.users = response;
	}); 
	
	$scope.back = function() {
		$state.go("input");
	}
	

	
	
}]);
