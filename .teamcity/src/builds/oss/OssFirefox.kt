package builds.oss

import addTestSettings
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object OssFirefox : BuildType({
  id("OssFirefox")
  name = "Firefox"
  paused = true

  params {
    param("env.KBN_NP_PLUGINS_BUILT", "true")
  }

  steps {
    script {
      name = "OSS Firefox"
      scriptContent =
        """
          #!/bin/bash
          ./.ci/teamcity/oss/firefox.sh
        """.trimIndent()
    }
  }

  dependencies {
    ossBuild()
  }

  addTestSettings()
})
