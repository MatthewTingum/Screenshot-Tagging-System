// TODO: sort submissions returned by SubmissionService.GetAll() by some criteria

(function () {
    'use strict';

    angular
        .module('app')
        .controller('Home.IndexController', Controller)	
		
		// Filters the list of submissions by some query and criteria
		// This is a really basic filter (TODO: Make it more comprehensive)
		.filter('sortFilter', function(){
		  return function(input, query, param){
			var output = [];
			angular.forEach(input, function(search){
				//param = "Location"
				//query = "Deathknell";
				//var madRegex = "^.*Deathknell.*$";
				var madRegex = "\bDeathknell\b";
				
				//var exp = "^.*"
				//var exp2 = ".*$";
				//var madRegex = exp.concat(query);
				//madRegex = madRegex.concat(exp2);
				
				//var madRegex = "^.*Deathknell.*$";	// This one works
				
				if (param === 'Location'){
					// if search.Location contains any part of query then give it the ole' push
					var match = new RegExp(madRegex);
					if (match.test(query)){
						output.push(search);
					}
				}
				/*
				switch(param) {
					case param.match(/Location/):
						output.push(search);
						break;
					case 5:
						//code block
						break;
					default:
						default;// code block
				}
				*/
				// Filter should search by: all, title, tags, location, character, and words / phrases existing in the chat
				//if(search.Location === 'Deathknell'){	
					//output.push(search);
				//}
			})
			return output;
		  }
		});


    function Controller(UserService, SubmissionService) {
        var vm = this;

        vm.user = null;
		vm.loadedSub = loadSub;
		vm.loadSub = loadSub;
		vm.nextPage = nextPage;
		vm.lastPage = lastPage;

		
		// Page is either showing results, or showing one particular submission
		vm.showResults = true;
		
		// Method by which to sort results
		vm.sort = "Time";
		
		// All submissions to be displayed
		vm.submissions = null;
		
		// Current page number of submissions
		// TODO: This is not a good way of handling things, every submission gets loaded all at once which is not needed. Just load 10 at a time. (API returns all submissions)
		vm.pageNum = 0;
		vm.showBack = false;
		vm.showNext = true;
		vm.showSort = true;
		
		vm.sorts = ["Newest First", "Oldest First"];
		vm.params = ["All", "Location"];

        initController();

        function initController() {
			
            // get current user
            UserService.GetCurrent().then(function (user) {
                vm.user = user;
            });
			
			
			// Get all submissions from database for display on page			
			SubmissionService.GetAll().then(function (submissions) {
				vm.submissions = submissions;
			});
			
			
			// Determine if the next button should be hidden
			if (vm.pageNum * 10 + 9 >= vm.loadedSub.length){
				vm.showNext = false;
			}
			
			
        }
		
		
		// Hides all search results and shows just the one result clicked on by user
		function loadSub(id) {
			
			vm.curSub = id;
			vm.showResults = !vm.showResults;
			vm.showSort = false;
			
			SubmissionService.LoadSub(vm.curSub)
                .then(function (loadedSub) {
					vm.loadedSub = loadedSub;
                    //FlashService.Success('Submission Loaded');
					//console.log("It worked fam");
                })
                .catch(function (error) {
                    FlashService.Error(error);
					//console.log(":(");
                });
			
			
			//vm.getSubmissions = UserService.GetSubmissions(user);
			
		}
		
		// Shows the next set of submissions and handles the last page case
		function nextPage() {
			vm.pageNum += 1;
			vm.showBack = true;
			
			// Determine if the next button should be hidden
			if (vm.pageNum * 10 + 9 >= vm.loadedSub.length){
				vm.showNext = false;
			}
		}
		
		// Shows the last set of submissions and handles the page 0 case
		function lastPage() {
			vm.pageNum -= 1;
			
			if (vm.pageNum == 0){
				vm.showBack = false;
			}
		}
		
		
    }

})();