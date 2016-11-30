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

function submissionPost(userParam) {
    var deferred = Q.defer();

    db.submissions.insert(
        userParam,
        function (err, doc) {
            if (err) deferred.reject(err.name + ': ' + err.message);

            deferred.resolve();
        });


    return deferred.promise;
}

function submissionGet() {
    var deferred = Q.defer();
	
	//deferred.resolve(db.submissions.find());

	/*
    db.submissions.findOne(	
		{ title: "2" },
		//{},
		
        function (err, doc) {
            if (err) deferred.reject(err.name + ': ' + err.message);
			
			console.log(doc);

            deferred.resolve(doc);
        });
		*/
		
	db.submissions.find({}).toArray( function (err, subs) {
        
		if (err) deferred.reject(err);
					

		if (subs) {
			console.log(subs);
			deferred.resolve(subs);
		} else {
			// Not found
			deferred.resolve();
		}
	});
	

    return deferred.promise;
}