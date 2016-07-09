#!/bin/bash
mkdir logs
java -Xmx256m -cp `sh getclasspath.sh`:classes com.sshtools.daemon.SshDaemon $@
