#!/bin/bash

java -Xmx256m -cp `sh getclasspath.sh`:classes cs310.Main $@
