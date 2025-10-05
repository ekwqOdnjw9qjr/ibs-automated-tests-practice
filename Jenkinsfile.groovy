pipeline {
    agent any

    parameters {
        choice(
                name: 'BROWSER',
                choices: ['chrome', 'firefox'],
                description: 'Выберите браузер для тестов'
        )
        choice(
                name: 'DRIVER',
                choices: ['remote', 'local'],
                description: 'Выберите тип драйвера'
        )
        choice(
                name: 'PROFILE',
                choices: ['cucumber', 'api'],
                description: 'Выберите профиль тестирования'
        )
        choice(
                name: 'TAGS',
                choices: ['@sandbox', '@opencart','@all',' '],
                description: 'Выберите теги для выполнения (только для cucumber), null - при запуске профиля api'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                        url: 'https://github.com/ekwqOdnjw9qjr/ibs-automated-tests-practice.git'
            }
        }

        stage('Tests') {
            steps {
                script {
                    def mvnCommand = 'mvn clean test'

                    mvnCommand += " \"-Dtype.browser=${params.BROWSER}\""
                    mvnCommand += " \"-Dtype.driver=${params.DRIVER}\""
                    mvnCommand += " -P ${params.PROFILE}"

                    if (params.PROFILE == 'cucumber') {
                        mvnCommand += " \"-Dcucumber.filter.tags=${params.TAGS}\""
                    }

                    withCredentials([
                            string(credentialsId: 'DATABASE_URL', variable: 'DATABASE_URL'),
                            string(credentialsId: 'DATABASE_USER', variable: 'DATABASE_USER'),
                            string(credentialsId: 'DATABASE_PASSWORD', variable: 'DATABASE_PASSWORD')
                    ]) {
                        mvnCommand += " -Ddb.url=${DATABASE_URL} -Ddb.user=${DATABASE_USER} -Ddb.password=${DATABASE_PASSWORD}"
                        sh mvnCommand
                    }
                }
            }
        }

        stage('Allure Report') {
            steps {
                script {
                    allure([
                            includeProperties: false,
                            jdk: '',
                            properties: [],
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: 'target/allure-results']]
                    ])
                }
            }
        }
    }

    post {
        success {
            echo "Успех"
        }
        failure {
            echo "Ошибка"
        }
    }
}
