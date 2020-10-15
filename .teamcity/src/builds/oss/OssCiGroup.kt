package builds.oss

import addTestSettings
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

class OssCiGroup(val ciGroup: Int, init: BuildType.() -> Unit = {}) : BuildType({
  id("OssCiGroup_$ciGroup")
  name = "CI Group $ciGroup"
  paused = true

  params {
    param("env.KBN_NP_PLUGINS_BUILT", "true")
  }

  steps {
    script {
      name = "OSS CI Group $ciGroup"
      scriptContent =
        """
          #!/bin/bash
          ./.ci/teamcity/oss/ci_group.sh $ciGroup
        """.trimIndent()
    }
  }

  dependencies {
    ossBuild()
  }

  addTestSettings()

  init()
})
