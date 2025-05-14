pipeline {
    agent any

    parameters {
        string(name: 'IMAGETAG', defaultValue: 'latest', description: 'Tag de l’image Docker à déployer')
        choice(name: 'BRANCH', choices: ['master', 'main'], description: 'Branche du dépôt')
        password(name: 'PASSWD', defaultValue: '', description: 'Token GitHub ou mot de passe') // ✅ nouveau nom
    }

    environment {
        REPO_URL = 'https://github.com/Paradox2001/api-sinistre.git'
        DEPLOY_PATH = 'kubernetes/deployment.yml'
        GIT_USERNAME = 'Paradox2001'
    }

    stages {
        stage('Cloner le dépôt') {
            steps {
                git branch: "${params.BRANCH}", url: "${env.REPO_URL}", credentialsId: 'GitHub-Credentials'
            }
        }

        stage('Modifier le tag Docker dans le YAML') {
            steps {
                script {
                    sh """
                    sed -i 's|image: zakariahmimssa/axa_sinistre:.*|image: zakariahmimssa/axa_sinistre:${params.IMAGETAG}|' ${DEPLOY_PATH}
                    """
                }
            }
        }

        stage('Commit & Push des modifications') {
            steps {
                script {
                    sh """
                    git config user.email "hmimssazakaria@gmail.com"
                    git config user.name "Paradox2001"
                    git commit -am "Mise à jour du tag Docker vers ${params.IMAGETAG}"
                    git push https://${GIT_USERNAME}:${params.PASSWD}@github.com/Paradox2001/Api-sinistre.git ${params.BRANCH}
                    """
                }
            }
        }
    }
}
