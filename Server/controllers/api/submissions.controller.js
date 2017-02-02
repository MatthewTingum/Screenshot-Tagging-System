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
router.put('/updateSub/:_id', updateSub);

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

// Updates an existing submission
function updateSub(req, res) {
	
	//console.log(req.params);
	//console.log(req.user.sub);
	//console.log("\n\n\n\n\n\nSEPARATION\n\n\n\n\n");
	//console.log(req.sub);
	//console.log(req.params);
	
	//if (req.params._id !== req.user.sub) {
	if (req.body.UID !== req.user.sub) {
		
		console.log("This submission doesn't belong to you... good try tho\n");
		console.log(req.body.UID);
		console.log(req.user.sub);
		
        // can only update own account
        return res.status(401).send('You can only update your own account');
    }
	
	submissionService.updateSub(req.params._id, req.body)
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