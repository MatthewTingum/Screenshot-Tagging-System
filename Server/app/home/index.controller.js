(function () {
    'use strict';

    angular
        .module('app')
        .controller('Home.IndexController', Controller);

    function Controller(UserService, SubmissionService) {
        var vm = this;

        vm.user = null;
		
		// All submissions to be displayed
		vm.submissions = null;

        initController();

        function initController() {
			
            // get current user
            UserService.GetCurrent().then(function (user) {
                vm.user = user;
            });
			
			
			// Get all submissions
			
			SubmissionService.GetAll().then(function (submissions) {
				vm.submissions = submissions;
			});
			
			
        }
    }

})();