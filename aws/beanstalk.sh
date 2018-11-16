## Create bucket

	aws s3 mb s3://basic-cloud-workshop-app

## Upload artifact to s3 bucket
	aws s3 cp ./build/libs/basic-cloud-workshop-app-0.0.1-SNAPSHOT.jar s3://basic-cloud-workshop-app

## Creating and deploying application to elastic beanstalk

## Create a elastic beanstalk application
	aws elasticbeanstalk create-application --application-name basic-cloud-workshop-app --description "Simple cloud spring boot app"

## Create a application version and point to S3 bucket name, key name
	aws elasticbeanstalk create-application-version --application-name basic-cloud-workshop-app --version-label sca-v1 --description sca-v1-descr --source-bundle S3Bucket="basic-cloud-workshop-app",S3Key="basic-cloud-workshop-app-0.0.1-SNAPSHOT.jar" --auto-create-application

## Create a new environment dev,test etc. with the application version. Also, specify a cname-prefix for the URL.
## Please note that cname-prefix should be unique across AWS. If the commands throws error, change the cname-prefix to a different value
## Replace the local file path for env-options.json accordingly
	aws elasticbeanstalk create-environment --application-name basic-cloud-workshop-app --environment-name BasicCloudWorkshopApp-dev --cname-prefix BasicCloudWorkshopApp-dev --version-label sca-v1 --solution-stack-name "64bit Amazon Linux 2018.03 v2.7.6 running Java 8" --tier Name="WebServer",Type="Standard"

## Get the URL of this environment. Navigate to "AWS Management console > Elastic Beanstalk" and monitor the status of environment creation
	aws elasticbeanstalk describe-environments --environment-names BasicCloudWorkshopApp-dev --application-name basic-cloud-workshop-app --query 'Environments[0].{URL:CNAME}'

curl -Xget http://BasicCloudWorkshopApp-dev.sphwbid66p.us-west-2.elasticbeanstalk.com/manage/info


