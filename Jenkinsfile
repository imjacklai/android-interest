node {
  stage('checkout') {
    echo 'checkout from github'
    git url: 'https://github.com/imjacklai/android-interest.git', branch: 'master'
  }

  stage('move file') {
    echo 'move file'
    sh "mv /var/lib/jenkins/google-services.json ${env.WORKSPACE}/app/"
  }

  stage('clean') {
    echo 'clean'
    sh './gradlew clean'
  }

  stage('test') {
    echo 'test'
    sh './gradlew test'
  }

  stage('build apk') {
    echo 'build apk'
    withCredentials([string(credentialsId: 'android_signing_password', variable: 'PASSWORD')]) {
      sh '''
        set +x
        ./gradlew -PkeyAlias='android' -PkeyPassword=${PASSWORD} -PstorePassword=${PASSWORD} -PstoreFile='/home/imjacklai/ctl.jks' assembleRelease
      '''
    }
  }

  stage('upload to google play') {
    echo 'upload to google play'
    androidApkUpload googleCredentialsId: 'android-interest', apkFilesPattern: '**/*-release.apk', trackName: 'beta', recentChangeList: [[language: 'zh-TW', text: "Beta 新版上架！"]]
  }
}