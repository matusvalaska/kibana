package builds.default

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object DefaultVisualRegression : DefaultFunctionalBase({
  id("DefaultVisualRegression")
  name = "Visual Regression"

  params {
    password("env.PERCY_TOKEN", "credentialsJSON:a1e37d40-830c-4ab6-a047-226688d2d81a", display = ParameterDisplay.HIDDEN)
  }

  steps {
    script {
      name = "Default Visual Regression"
      scriptContent =
        """
          #!/bin/bash
          ./.ci/teamcity/default/visual_regression.sh
        """.trimIndent()
    }

    // TODO does this need to go somewhere else? does it need to be its own job? it's really short
    script {
      name = "Default Saved Object Field Metrics"
      scriptContent =
        """
          #!/bin/bash
          ./.ci/teamcity/default/saved_object_field_metrics.sh
        """.trimIndent()
    }
  }
})
