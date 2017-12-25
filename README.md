# aws-lambda-gradle-template
A dummy AWS lambda template for SNS events handling using Gradle.

## Why create this repo
Tried to follow the eclipse/maven tutorial on AWS lambda documentation, and generate a uber-jar. However it kept showing me "ClassNotFound" error for SNSEvent class. Then I gave up, switch to sweet Gradle.

## The example SNSEventProcessor
The dummy SNSEventProcessor is listening on incoming SNS messages, then publish the same message (with an extra timestamp sufix) to another SNS at a fixed rate, until it reaches the time out. It's literally doing nothing, it's just 

## Is that how you are supposed to use Lambda?...
Yep, I guess this is not the preferred way to use AWS Lambda. This is my first time using lambda, just wanna play with it and try something new. Although this is not the current recommended way to use AWS Lmabda (running a long lasting job/task), I can imagine people do have some use cases like this, and using Lmabda can save tons of time.

## How about money?
AWS Lambda is charged based on the num of requests it has processed, and the duration it's been running. Check the current pricing of AWS Lambda [here](https://aws.amazon.com/lambda/pricing/). 

For the dummy Lambda function contained in this template, Assume the number of incoming request is within free tier (1M), and we have a single lambda instance, running 24/7 (assuming we limit the concurrency to 1, a new lambda instance is invoked once the previous one finishes), with 1GB memory (enough for a lot of different use cases), it will cost us $36.5 per month.

## Usage

Run `gradle build` to build and generate the required .zip file. Then follow the AWS Lambdas [Getting Started](http://docs.aws.amazon.com/lambda/latest/dg/getting-started.html) documentation to create a Lmabda function. 

**Note:** since the .zip file is too large, you have to upload the .zip file to S3 (instead of directly upload it to your Lambda function.
