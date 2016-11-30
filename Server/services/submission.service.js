// Matthew Tingum
// TODO: Create Service to return only a limited number of submissions rather than all
// TODO: Modify submission get services to return based on some search criteria

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
service.loadSub = loadSub;

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

// Returns just one submission based on ID from database
function loadSub(_id) {
    var deferred = Q.defer();
	
	//console.log('shamalammadingdong');
	//console.log(_id);
	//console.log('shamalammadingdong');

    db.submissions.findById(_id, function (err, sub) {
        if (err) deferred.reject(err);
 
        if (sub) {
            // submission found and returned
			//console.log(sub);
            deferred.resolve(sub);
        } else {
            // didn't find that sub for some reason
            deferred.resolve();
        }
    });

    return deferred.promise;
}