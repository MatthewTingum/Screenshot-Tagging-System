// TODO: sort submissions returned by SubmissionService.GetAll() by some criteria

(function () {
    'use strict';

    angular
        .module('app')
        .controller('Home.IndexController', Controller);

    function Controller(UserService, SubmissionService) {
        var vm = this;

        vm.user = null;
		vm.loadedSub = loadSub;
		vm.loadSub = loadSub;
		
		// Page is either showing results, or showing one particular submission
		vm.showResults = true;
		
		// All submissions to be displayed
		vm.submissions = null;

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
			
			
        }
		
		
		// Hides all search results and shows just the one result clicked on by user
		function loadSub(id) {
			
			vm.curSub = id;
			vm.showResults = !vm.showResults;
			
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
    }

})();