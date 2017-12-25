# aws-lambda-gradle-template
A dummy AWS lambda template for SNS events using Gradle.

## Why create this repo
Tried to follow the eclipse/maven tutorial on AWS lambda documentation, and generate a uber-jar. However it kept showing me "ClassNotFound" error for SNSEvent class. Then I gave up, switch to sweet gradle.

## The example SNSEventProcessor
The dummy SNSEventProcessor is listening on incoming SNS message, then publish the same message (with an extra timestamp sufix) to another SNS at a fixed rate, until it reachs the time out. 

## Is that how you are supposed to use Lambda?...
Yep I totally agree. This is my first time using lambda, just wanna play with it and try something new. Although this is not the current recommended way to use AWS Lmabda (running a long lasting job/task), I can imagine somebody may have some usecases like this, and using Lmabda can save tons of trouble. 
