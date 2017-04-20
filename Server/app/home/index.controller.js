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
			angular.forEach(input, function(search, key){

				
				var found = false;
				
				
				if (param === 'All'){
					
					// Only need to do this once?
					if (!query){
						output.push(search);
						found = true;
					}
					
				
					// Location
					if (!found && input[key].Location.toLowerCase().indexOf(query.toLowerCase()) >= 0){
						output.push(search);
						found = true;
						//console.log(search);
					}
					
					// Description
					if (!found && input[key].Description.toLowerCase().indexOf(query.toLowerCase()) >= 0){
						output.push(search);
						found = true;
					}
					
					// Tags
					if (!found && input[key].Tags.toLowerCase().indexOf(query.toLowerCase()) >= 0){
						output.push(search);
						found = true;
					}
					
					// Character
					if (!found && input[key].Character.toLowerCase().indexOf(query.toLowerCase()) >= 0){
						output.push(search);
						found = true;
					}
				
				}
				
				
				if (param === 'Location'){
					if (input[key].Location.toLowerCase().indexOf(query.toLowerCase()) >= 0){
						output.push(search);
					}
				}
				
				if (param === 'Description'){
					if (input[key].Description.toLowerCase().indexOf(query.toLowerCase()) >= 0){
						output.push(search);
					}
				}
				
				if (param === 'Tags'){
					if (input[key].Tags.toLowerCase().indexOf(query.toLowerCase()) >= 0){
						output.push(search);
					}
				}
				
				if (param === 'Character'){
					if (input[key].Character.toLowerCase().indexOf(query.toLowerCase()) >= 0){
						output.push(search);
					}
				}
				

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
		vm.showEditBtn = false;
		vm.updateSub = updateSub;

		
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
		vm.editing = false;
		
		vm.sorts = ["Newest First", "Oldest First"];
		vm.params = ["All", "Location", "Description", "Tags", "Character"];

        initController();

        function initController() {
			
            // get current user
            UserService.GetCurrent().then(function (user) {
                vm.user = user;
            });
			
			
			// Get all submissions from database for display on page			
			SubmissionService.GetAll().then(function (submissions) {
				vm.submissions = submissions;
				console.log(submissions);
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
					
					// ng-bind-html doesn't want to play nice so do this instead
					vm.loadedSub.ChatSplit = vm.loadedSub.Chat.split("\\n");
					
					
					
					
					
                    //FlashService.Success('Submission Loaded');
					//console.log("It worked fam");
                })
                .catch(function (error) {
                    FlashService.Error(error);
					//console.log(":(");
                });
			
			if (vm.loadedSub.UID === vm.user._id){
				vm.showEditBtn = true;
			}
			
			vm.showEditBtn = true;
			//vm.showEditBtn = true;
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
		
		function updateSub(){
			SubmissionService.UpdateSub(vm.loadedSub, vm.user).then(function (retVal) {
				//vm.submissions = submissions;
				console.log(retVal);
			});
		}
		
		
    }

})();