var config = require('config.json');
var express = require('express');
var router = express.Router();
var submissionService = require('services/submission.service');
//var userService = require('services/submission.service');

// routes
router.post('/submission', submissionPost);
router.get('/submission', submissionGet);

module.exports = router;

// Puts a submission in the database
function submissionPost(req, res) {
    submissionService.submissionPost(req.body)
        .then(function () {
            res.sendStatus(200);
        })
        .catch(function (err) {
            res.status(400).send(err);
        });
}

// Gets all submissions from the database
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