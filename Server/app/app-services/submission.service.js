// Matthew Tingum


(function () {
    'use strict';

    angular
        .module('app')
        .factory('SubmissionService', Service);

    function Service($http, $q) {
        var service = {};

        service.GetAll = GetAll;

        return service;

		// returns all submissions in the database (used by index controller)
        function GetAll() {
            return $http.get('/api/submissions/submission').then(handleSuccess, handleError);
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
