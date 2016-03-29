# EMLauncher Jenkins Plugin
... with **Slack** integration

This is a fork of the [EMLauncher Jenkins Plugin](https://github.com/KLab/emlauncher-jenkins-plugin).

## Build

Need Maven, JDK

`mvn package` on project root.

-> This command generate target/emlauncher.hpi

-> From Jenkins Plugin Settings, install this .hpi.

In Jenkins General Settings, Setting EMLauncher Server configuration.

And In your Build configuration, Add EMLauncher Plugin to post build process and set some parameter.
