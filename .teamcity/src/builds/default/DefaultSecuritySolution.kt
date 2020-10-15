package builds.default

import addTestSettings
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object DefaultSecuritySolution : BuildType({
  id("DefaultSecuritySolution")
  name = "Security Solution"
  paused = true

  steps {
    script {
      name = "Build OSS Plugins"
      scriptContent =
        """
                #!/bin/bash
                ./.ci/teamcity/oss/build_plugins.sh
        """.trimIndent()
    }

    // TODO is there a way to re-use what was built in the DefaultBuild job?
    script {
      name = "Build Default Plugins"
      scriptContent =
        """
                #!/bin/bash
                ./.ci/teamcity/default/build_plugins.sh
        """.trimIndent()
    }

    script {
      name = "Default Security Solution"
      scriptContent =
        """
                #!/bin/bash
                ./.ci/teamcity/default/security_solution.sh
        """.trimIndent()
    }
  }

  dependencies {
    defaultBuild()
  }

  addTestSettings()
})
