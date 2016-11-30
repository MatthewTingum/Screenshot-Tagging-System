// Matthew Tingum


(function () {
    'use strict';

    angular
        .module('app')
        .factory('SubmissionService', Service);

    function Service($http, $q) {
        var service = {};

        service.GetAll = GetAll;
		service.LoadSub = LoadSub;

        return service;

		// returns all submissions in the database (used by index controller)
        function GetAll() {
            return $http.get('/api/submissions/submission').then(handleSuccess, handleError);
        }
		
		// Loads just one submission based on its's ID
		function LoadSub(id) {
            return $http.get('/api/submissions/loadSub/' + id).then(handleSuccess, handleError);
        }

        // private functions

        function handleSuccess(res) {
            return res.data;
        }

        function handleError(res) {
            return $q.reject(res.data);
        }
    }

})();
