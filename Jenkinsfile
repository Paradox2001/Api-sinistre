pipeline {
    agent any
    environment {
        SCANNER_HOME = tool 'sonar-scanner'
        MAVEN_HOME = tool 'maven3' // doit matcher le nom défini dans Jenkins
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
    }
    stages {
        stage ("Clean workspace") {
            steps {
                cleanWs()
            }
        }

        stage ("Git Checkout") {
            steps {
                git branch: 'master', url: 'https://github.com/Paradox2001/Api-sinistre.git'
            }
        }

        stage("Analyse SonarQube") {
            steps {
                withSonarQubeEnv('sonar-server') { 
                    dir('api-sinistre') {
                          
    sh "mvn clean verify sonar:sonar -Dsonar.projectKey=axa_sinistre -Dsonar.projectName=axa_sinistre"
}



                    
                }
            }
        }

        stage("Vérification Quality Gate") {
            steps {
                script {
                    waitForQualityGate abortPipeline: true 
                }
            }
        }

        stage("OWASP Dependency Check") {
            steps {
                dependencyCheck additionalArguments: '--scan ./ --disableYarnAudit --disableNodeAudit', odcInstallation: 'DP-Check' // 🔁 Doit exister dans Jenkins Tools
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
            }
        }

        stage("Trivy Scan") {
            steps {
                sh "trivy fs . > trivy.txt"     
            }
        }

        stage("Build Docker Image") {
            steps {
                sh "docker build -t axa_sinistre ./api-sinistre" // 
            }
        }

        stage("Push Image to DockerHub") {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'dockercred') { 
                        sh "docker tag axa_sinistre zakariahmimssa/axa_sinistre:latest" 
                        sh "docker push zakariahmimssa/axa_sinistre:latest"  
                    }
                }
            }
        }

        stage("Docker Scout Scan") {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'dockercred') {
                        sh 'docker-scout quickview zakariahmimssa/axa_sinistre:latest'
                        sh 'docker-scout cves zakariahmimssa/axa_sinistre:latest'
                        sh 'docker-scout recommendations zakariahmimssa/axa_sinistre:latest'
                    }
                }
            }
        }

        stage("Déploiement local avec Docker") {
            steps {
                sh "docker run -d --name axa_sinistre -p 8082:8080 tondockerhub/axa_sinistre:latest" 
            }
        }
    }

    post {
        always {
            emailext attachLog: true,
                subject: "'${currentBuild.result}' - Build ${env.BUILD_NUMBER}",
                body: """
                    <html>
                    <body>
                        <h3 style="color: #007ACC;">Pipeline DevSecOps terminé</h3>
                        <ul>
                            <li><b>Projet :</b> ${env.JOB_NAME}</li>
                            <li><b>Build # :</b> ${env.BUILD_NUMBER}</li>
                            <li><b>URL :</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></li>
                        </ul>
                        <p>Voir le fichier Trivy joint pour les vulnérabilités.</p>
                    </body>
                    </html>
                """,
                to: 'hmimssazakaria@gmail.com;',
                mimeType: 'text/html',
                attachmentsPattern: 'trivy.txt'
        }
    }
}
