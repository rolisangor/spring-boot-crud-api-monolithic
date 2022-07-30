def buildApp() {
    echo 'building the application'
    sh "mvn --version"
}

def testApp() {
    echo 'testing the application'
}

def deployApp() {
    echo 'deploy the application'
}

return this