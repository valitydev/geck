#!groovy
build('geck', 'docker-host') {
    checkoutRepo()
    loadBuildUtils()

    def javaLibPipeline
    runStage('load JavaLib pipeline') {
        javaLibPipeline = load("build_utils/jenkins_lib/pipeJavaLib.groovy")
    }

    def buildImageTag = "3750c129119b83ea399dc4aa0ed923fb0e3bf0f0"
    javaLibPipeline(buildImageTag)
}
