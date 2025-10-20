SUMMARY = "A console-only image with more full-featured Linux system \
functionality installed and erlang-red installed."

IMAGE_FEATURES += "ssh-server-openssh"

IMAGE_INSTALL += "\
    ${CORE_IMAGE_BASE_INSTALL} \
    packagegroup-core-full-cmdline \
    packagegroup-breadboard-base \
    packagegroup-breadboard-nerves-hub \
    packagegroup-breadboard-connman \
    "

inherit core-image
