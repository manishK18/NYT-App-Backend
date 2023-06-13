pipeline {
    agent any
//     environment {
//         AWS_ACCESS_KEY_ID     = credentials('aws_access_key_id')
//         AWS_SECRET_ACCESS_KEY = credentials('aws_secret_access_key')
//     }
    stages {
        stage('Build') {
            steps {
                echo "Build stage starting..."
//                 sh "mvn clean package -DskipTests"
                sh "${env.NYT_API_KEY}"
                echo "Build stage completed..."
            }
        }
        stage('Test') {
            steps {
                echo "Test stage starting..."
//                 withAWS(credentials: 'Jenkins-test-user', region: 'eu-north-1') {
// //                     INSTANCE_ID="i-040e613ab6afec7a7"
// //
// //                     ENV_VARS=$(aws ec2 describe-instances \
// //                            --instance-ids $INSTANCE_ID \
// //                            --query "Reservations[].Instances[].Tags[?Key=='EnvironmentVariables'].Value[]" \
// //                            --output text)
//                     echo "Environment Variables: ${AWS_ACCESS_KEY_ID}"
// //                     sh "mvn -Dspring.profiles.active=test -DnytApiKey=$NYT_API_KEY -DguardianApiKey=$GUARDIAN_API_KEY test"
//                     echo "Test stage completed..."
//                 }
//                 sh "mvn -Dspring.profiles.active=test -DnytApiKey=$NYT_API_KEY -DguardianApiKey=$GUARDIAN_API_KEY test"
                echo "Test stage completed..."
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploy stage starting..."
//                 sh 'docker-compose up --build -d'
                echo "Deployment completed..."
            }
        }
    }
}
