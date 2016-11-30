// TODO: sort submissions returned by SubmissionService.GetAll() by some criteria

(function () {
    'use strict';

    angular
        .module('app')
        .controller('Home.IndexController', Controller);

    function Controller(UserService, SubmissionService) {
        var vm = this;

        vm.user = null;
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
		
		
		function loadSub(id) {
			
			vm.test = id;
			
		/*	
			UserService.LoadSub(vm.test)
                .then(function (loadedSub) {
					vm.loadedSub = loadedSub;
                    //FlashService.Success('Submission Loaded');
                })
                .catch(function (error) {
                    FlashService.Error(error);
                });
		*/
			
			vm.showResults = !vm.showResults;
			
			//vm.getSubmissions = UserService.GetSubmissions(user);
			
		}
    }

})();