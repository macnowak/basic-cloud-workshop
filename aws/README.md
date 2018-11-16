# Amazon first steps
            
## Setup account and cli  

First of all we need to setup our AWS account and install AWS CLI. 

### Create amazon account

	https://docs.aws.amazon.com/AmazonECR/latest/userguide/get-set-up-for-amazon-ecr.html#sign-up-for-aws

### Install AWS CLI         

 	https://docs.aws.amazon.com/AmazonECR/latest/userguide/get-set-up-for-amazon-ecr.html#install_aws_cli 
 	
 	https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-started.html#cli-quick-configuration 

## Deploy to elasticbeanstalk

Build application :

	gradle clean build
	

Create s3 bucket 

	aws s3 mb s3://basic-cloud-workshop-app


Upload artifact to s3 bucket

	aws s3 cp ./build/libs/basic-cloud-workshop-app-0.0.1-SNAPSHOT.jar s3://basic-cloud-workshop-app

Create a elastic beanstalk application
	
	aws elasticbeanstalk create-application --application-name basic-cloud-workshop-app --description "Simple cloud spring boot app"

Create a application version and point to S3 bucket name, key name

	aws elasticbeanstalk create-application-version --application-name basic-cloud-workshop-app --version-label sca-v1 --description sca-v1-descr --source-bundle S3Bucket="basic-cloud-workshop-app",S3Key="basic-cloud-workshop-app-0.0.1-SNAPSHOT.jar" --auto-create-application

Create a new environment dev,test etc. with the application version. Also, specify a cname-prefix for the URL.

Please note that cname-prefix should be unique across AWS. If the commands throws error, change the cname-prefix to a different value

	aws elasticbeanstalk create-environment --application-name basic-cloud-workshop-app --environment-name BasicCloudWorkshopApp-dev --cname-prefix BasicCloudWorkshopApp-dev --version-label sca-v1 --solution-stack-name "64bit Amazon Linux 2018.03 v2.7.6 running Java 8" --tier Name="WebServer",Type="Standard"

Get the URL of this environment. Navigate to "AWS Management console > Elastic Beanstalk" and monitor the status of environment creation

	aws elasticbeanstalk describe-environments --environment-names BasicCloudWorkshopApp-dev --application-name basic-cloud-workshop-app --query 'Environments[0].{URL:CNAME}'

Check if its working ( note that url can be different )

	curl -Xget http://BasicCloudWorkshopApp-dev.sphwbid66p.us-west-2.elasticbeanstalk.com/manage/info


            
## Deploy to docker image 

Quick steps to deploy sample application embedded inside docker image at AWS ECS using AWS CloudFormation


### Build docker image 

First of all we need to build docker image with our application inside 

	gradle clean build docker
		

### Upload image to ECR

We need to create reporitory in AWS ECR where our docker images will be stored

	aws ecr create-repository --repository-name simple-cloud-app
	
Response should be like this 

	{
        "repository": {
            "repositoryArn": "arn:aws:ecr:us-west-2:693568640595:repository/simple-cloud-app",
            "registryId": "693568640595",
            "repositoryName": "simple-cloud-app",
            "repositoryUri": "693568640595.dkr.ecr.us-west-2.amazonaws.com/simple-cloud-app",
            "createdAt": 1542299265.0
        }
    }	
	
Tag image with ECR URL repository

	docker tag <image_id> 693568640595.dkr.ecr.us-west-2.amazonaws.com/simple-cloud-app
	

Push image to ECR

	docker push 693568640595.dkr.ecr.us-west-2.amazonaws.com/simple-cloud-app
	

### Create cluster using AWS CloudFormation 

Template from : https://github.com/awslabs/aws-cloudformation-templates/tree/master/aws/services/ECS 

Create cluster infrastructure (subnets, loadbalancers, target groups, etc...)

	aws cloudformation deploy --stack-name=production --template-file=aws/public-vpc.yml --capabilities=CAPABILITY_IAM
	
Create ECS service ( template should be customized, imageURL variable should point to repositoryUri)

	aws cloudformation deploy --stack-name=service --template-file=aws/public-service.yml --capabilities=CAPABILITY_IAM 


Log in to AWS and go to 

	https://us-west-2.console.aws.amazon.com/cloudformation/home?region=us-west-2#/stacks?filter=active
	
Check out production stact output, find load balancer url, then check if application is working with 

	curl <LB_URL>:5000/manage/info


If everything works well the ip address in above response should be changing according to accessing to different tasks in ECS	

