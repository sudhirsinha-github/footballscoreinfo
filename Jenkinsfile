pipeline {
  environment {
    registry = "sudhir.azurecr.io/football"
    registryCredential = 'azureDockerRegistry'
    azureChatSpCredentialsId = 'azureDockerRegistryId'
    dockerImageName = 'football'
    webAppName = 'football'
    resourceGroup = 'rg-football'
  }
  agent any
  stages {
    stage('Git Checkout') {
      steps {
        git branch: 'ci-cd',
        credentialsId: 'token-user',
        url: 'https://github.com/football-score-info.git'
      }
    }
    stage('Build Chatbot') {
       steps {
         dir("${env.WORKSPACE}/application"){
            sh 'gradle clean build'
         }
       }
    }
    stage('Build Docker Image') {
      steps{
        sh 'cp -rf application /'
        dir("${env.WORKSPACE}"){
           script {
             dockerImage = docker.build registry + ":latest"
           }
         }
      }
    }
    stage('Push to ACR') {
      steps{
        script {
          docker.withRegistry("https://"+registry ,registryCredential ) {
            dockerImage.push()
          }
        }
      }
    }
    stage('Remove Docker Image') {
      steps{
        sh "docker rmi $registry:latest"
      }
    }
  }
  post{
        always {
            deleteDir() /* clean up our workspace */
        }
  }
}