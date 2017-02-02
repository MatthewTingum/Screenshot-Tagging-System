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
service.updateSub = updateSub;

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

// Updates an existing submission
// I screwed up the naming of this... 'other' should really be body, and 'body' is really just user-ID... TODO
function updateSub(body, other) {
    var deferred = Q.defer();
	
	var set = {
            Description: other.Description,
            Tags: other.Tags,
            Time: other.Time,
			Character: other.Character,
			Location: other.Location,
    };
	
	console.log("A THING IS HAPPENING\n");
	console.log(set);
	//console.log(body);
	
	db.submissions.update(
            { _id: mongo.helper.toObjectID(body) },
            { $set: set },
            function (err, doc) {
                if (err) deferred.reject(err.name + ': ' + err.message);
                deferred.resolve();
			}
	);

	/*
    db.submissions.insert(
        body,
        function (err, doc) {
            if (err) deferred.reject(err.name + ': ' + err.message);

            deferred.resolve();
        });
	*/

	return deferred.promise;
}