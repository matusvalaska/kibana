package builds.oss

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object OssVisualRegression : OssFunctionalBase({
  id("OssVisualRegression")
  name = "Visual Regression"

  params {
    password("env.PERCY_TOKEN", "credentialsJSON:a1e37d40-830c-4ab6-a047-226688d2d81a", display = ParameterDisplay.HIDDEN)
  }

  steps {
    script {
      name = "OSS Visual Regression"
      scriptContent =
        """
          #!/bin/bash
          ./.ci/teamcity/oss/visual_regression.sh
        """.trimIndent()
    }
  }
})
