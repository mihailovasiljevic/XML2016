angular.module('main')
  .controller('MainController', ['$scope', '$rootScope', '$timeout','$location','$state','$window','Main','Search','Propis','Xhtml', 'Pdf','MetaData','UserLogout','User',
    function($scope,$rootScope, $timeout,$location,$state,$window, Main, Search,Propis, Xhtml, Pdf,MetaData,UserLogout,User){


         $scope.xhtmlDoc = {};
        //go on mainPage
        //$state.go("main");
        //show documents
        $scope.propose = function(){
            $state.go('main.act');
        }
    
        $scope.search = function(){
            $state.go('main.search');
        }

        $scope.find = function(){
            $scope.acts = Main.query(
                function(response){
                	$scope.listForShowing = [];
                	for(var i = 0; i < response.length; i++) {
                		if(response[i].status != "povucen") {
                			$scope.listForShowing.push(response[i]);
                		}
                	}
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
                    $scope.resultsForShowing = $scope.results;
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

        };
        
        
        
        $scope.withdrawAct = function(act){
            if(act.map){
                var index1 = $scope.resultsForShowing.indexOf(act);
                var index2 = $scope.results.indexOf(act);
                act.uri=act.map.uri.replace('/acts/','');
                act.uri=act.map.uri.replace('.xml','');
                
                propis = Propis.get({actId:act.map.uri},function(response){ 
                response.$update({actId:act.map.uri},function(response) {
                    act.map.status="povucen";
                    $scope.resultsForShowing.splice(index1,1);
                    $scope.results.splice(index2,1);
                            
                    
                    //Popraviti ovo
                    //if(response.map.error)
                    //	$scope.error = response.map.error;
                });
                });
            }else{
                var index1 = $scope.listForShowing.indexOf(act);
                var index2 = $scope.acts.indexOf(act);
                act.uri=act.uri.replace('/acts/','');
                act.uri=act.uri.replace('.xml','');
                
                propis = Propis.get({actId:act.uri},function(response){ 
                response.$update({actId:act.uri},function(response) {
                    act.status="povucen";
                    $scope.listForShowing.splice(index1,1);
                    $scope.acts.splice(index2,1);
                            
                    
                    //Popraviti ovo
                    //if(response.map.error)
                    //	$scope.error = response.map.error;
                });
                });
            }
        };
      //  alert($scope.$parent.isPredsednik);
        
        $scope.loggedIn = false;

        User.get(function(response){
        	if(response.uloga)
        	$scope.loggedIn = true;
        	else $scope.loggedIn=false;
        	
        			
        	
        })
        
        
        
       
        $scope.login = function(){
        	$state.go('login');
        }
        
        $scope.logout = function(){
        	UserLogout.get(function(response){
        		console.log(response);
        		$timeout(function(){
					$window.location.replace("http://localhost:9000");
					
				},0)
        		$scope.loggedIn = false;
        		
        		
        		
        	})
        }
        $scope.xhtml = function(uri){
            var xhtml = new Xhtml();
            xhtml.$get({uri: uri}, function(response){
                $scope.xhtmlDoc = response.map.html;
                console.log(response);
            });
        }
        $scope.pdf = function(uri){
            var pdf = new Pdf();
            pdf.$get({uri: uri}, function(response){
                $scope.path = response.map.path;
                $window.open('http://localhost:9000'+response.map.path, '_blank');
                //$window.location.href = 'http://localhost:9000'+response.map.path;
            });
        };
        $scope.metadata = function(){
            $state.go("main.metadata");
        };
        $scope.searchByMetadata = function(){
            var metaData = new MetaData();
            
            if($scope.glasaloZaOd != null  && $scope.glasaloZaOd != undefined){
                metaData.glasaliZaOd = $scope.glasaloZaOd ;
            }
            if($scope.glasaloZaDo != null  && $scope.glasaloZaDo != undefined){
                metaData.glasaliZaDo = $scope.glasaloZaDo ;
            }
            if($scope.datumKreiranjaOd != null  && $scope.datumKreiranjaOd != undefined){
                x = makeDate($scope.datumKreiranjaOd);
                metaData.datumKreiranjaOd =  x;
            }
            if($scope.datumKreiranjaDo != null  && $scope.datumKreiranjaDo != undefined){
                x = makeDate($scope.datumKreiranjaDo);
                metaData.datumKreiranjaDo =x ;
            }
            if($scope.datumUsvajanjaUNaceluOd != null  && $scope.datumUsvajanjaUNaceluOd != undefined){
                x = makeDate($scope.datumUsvajanjaUNaceluOd);
                metaData.datumUsvajanjaUNaceluOd = x ;
            }
            if($scope.datumUsvajanjaUNaceluDo != null  && $scope.datumUsvajanjaUNaceluDo != undefined){
                x = makeDate($scope.datumUsvajanjaUNaceluDo);
                metaData.datumUsvajanjaUNaceluDo =x ;
            }
            if($scope.datumUsvajanjaUCelostiOd != null  && $scope.datumUsvajanjaUCelostiOd != undefined){
                x = makeDate($scope.datumUsvajanjaUCelostiOd);
                metaData.datumUsvajanjaUCelostiOd = x ;
            }
            if($scope.datumUsvajanjaUCelostiDo != null  && $scope.datumUsvajanjaUCelostiDo != undefined){
                x = makeDate($scope.datumUsvajanjaUCelostiDo);
                metaData.datumUsvajanjaUCelostiDo =x ;
            }

            metaData.$save(function(response){
               $scope.results = response.myArrayList;
               $scope.resultsForShowing = response.myArrayList;
            }, function(errorResponse){
               alert(errorResponse);
            });
            
            
        };

        var makeDate = function(raw){
            var x = new Date(raw);
            year = x.getFullYear();
            month = x.getMonth() + 1;
            month = month+"";
            if(month.length == 1){
                month = "0"+month;
            }
            day = x.getDate();

            return year+"-"+month+"-"+day;
        }
    }])
    .controller('SearchResultsController', ['$scope', '$rootScope', '$timeout','$location','$state','Main','Search',
    function($scope,$rootScope, $timeout,$location,$state, Main, Search){

        $scope.results = $scope.$parent.results
    }])
    .controller('filterActsController', ['$scope', '$rootScope', '$timeout','$location','$state','Main','Search',
    function($scope,$rootScope, $timeout,$location,$state, Main, Search){

        $scope.acts = $scope.$parent.acts;

        $scope.selected = "";

        $scope.filter = function(){

            $scope.$parent.listForShowing = [];

            for(var i = 0; i < $scope.acts.length; i++){
                    if($scope.acts[i].status == $scope.selected){
                       $scope.$parent.listForShowing.push($scope.acts[i]);
                    }
            }

        }
    }])
    .controller('filterSearchController', ['$scope', '$rootScope', '$timeout','$location','$state','Main','Search',
    function($scope,$rootScope, $timeout,$location,$state, Main, Search){

        $scope.results = $scope.$parent.results;

        $scope.searchSelected = "";

        $scope.filter = function(){

            $scope.$parent.resultsForShowing = [];

            for(var i = 0; i < $scope.results.length; i++){
                    if($scope.results[i].map.status == $scope.searchSelected){
                       $scope.$parent.resultsForShowing.push($scope.results[i]);
                    }
            }

        }

    }])
    .controller('sednicaController', ['$scope', '$rootScope', '$timeout','$location','$state','$window','Main','Search','User',
                                  function($scope,$rootScope, $timeout,$location,$state,$window, Main, Search,User){

    			$scope.isPredsednik = false;
				User.get(function(response){
			    	if(response.uloga)
			    		if(response.uloga=="predsednik"){
			    			$scope.isPredsednik = true;
			    			
			    		}
			    	
			    })
  }]);
