pipeline {
    agent any
    stages {

        stage('Pull Code') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']],
                doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [],
                userRemoteConfigs: [[credentialsId: 'fe346c8b-e7dc-4491-bacf-104c6bdd061c', url: 'git@github.com:CodingBoyPeng/springBoot.git']]])
            }
        }

        stage('Build') {
            steps {
                sh label: '', script: 'mvn clean package'
            }
        }

        stage('Deploy') {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'afc43e5e-4a4e-4de6-984f-b1d5a254e434', path: '', url: 'http://http://47.102.214.56/:8080')], contextPath: null, war: 'target/*.war'
            }
        }
    }
}