# basic-cloud-workshop [![Build Status](https://travis-ci.com/macnowak/basic-cloud-workshop.svg?branch=master)](https://travis-ci.com/macnowak/basic-cloud-workshop) [![codecov](https://codecov.io/gh/macnowak/basic-cloud-workshop/branch/master/graph/badge.svg)](https://codecov.io/gh/macnowak/basic-cloud-workshop)


## About

Project created for learning purpose. 

Simple SpringBoot application with in-memory cache and rest endpoints. 

### Goals 

- [x] SpringBoot 
- [x] Rest
- [x] Build on travis-ci
- [x] Measure CodeCov
- [x] Build and run on docker container
- [x] Push docker image to repository ( hub.docker.com )
- [x] Deploy to AWS Elastic Beanstalk
- [ ] Add another 'microservice'
- [ ] Add mongo integration
- [ ] Add deployment to AWS ECS 
- [ ] Add RestDocs




## API

`POST /user` - add new user 

`GET /user/{id}` - getting user with id  

`GET /user` - get user list