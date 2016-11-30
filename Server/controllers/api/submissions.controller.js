// Matthew Tingum

var config = require('config.json');
var express = require('express');
var router = express.Router();
var submissionService = require('services/submission.service');
//var userService = require('services/submission.service');

// routes
router.post('/submission', submissionPost);
router.get('/submission', submissionGet);
router.get('/loadSub/:_id', loadSub);

module.exports = router;

// Uses submissionService to post a submission to the database
function submissionPost(req, res) {
    submissionService.submissionPost(req.body)
        .then(function () {
            res.sendStatus(200);
        })
        .catch(function (err) {
            res.status(400).send(err);
        });
}

// Uses submissionService to get all submissions in the database
function submissionGet(req, res) {
    submissionService.submissionGet()
        .then(function (subs) {
            //res.sendStatus(200);
			res.send(subs);
        })
        .catch(function (err) {
            res.status(400).send(err);
        });
}

// Loads the information from one particular submission for use in a submission specific page
function loadSub(req, res) {
	
	//console.log(req.params);
	
	submissionService.loadSub(req.params._id)
        .then(function (retSub) {
            if (retSub) {
                res.send(retSub);
            } else {
                res.sendStatus(404);
            }
        })
        .catch(function (err) {
            res.status(400).send(err);
        });
		
}