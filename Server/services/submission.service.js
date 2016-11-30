// Matthew Tingum

var config = require('config.json');
var _ = require('lodash');
var jwt = require('jsonwebtoken');
var bcrypt = require('bcryptjs');
var Q = require('q');
var mongo = require('mongoskin');
var db = mongo.db(config.connectionString, { native_parser: true });
db.bind('submissions');

var service = {};

service.submissionPost = submissionPost;
service.submissionGet = submissionGet;

module.exports = service;

// Puts the key-value pairs of body into the database as one cohesive entry
function submissionPost(body) {
    var deferred = Q.defer();

    db.submissions.insert(
        body,
        function (err, doc) {
            if (err) deferred.reject(err.name + ': ' + err.message);

            deferred.resolve();
        });


    return deferred.promise;
}

// Returns all submissions from the database
function submissionGet() {
    var deferred = Q.defer();
		
	db.submissions.find({}).toArray( function (err, subs) {
        
		if (err) deferred.reject(err);
					

		if (subs) {
			//console.log(subs);	// Log returned submissions to console
			deferred.resolve(subs);
		} else {
			// Not found
			deferred.resolve();
		}
	});
	

    return deferred.promise;
}