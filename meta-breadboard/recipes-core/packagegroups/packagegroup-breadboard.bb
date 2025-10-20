# Copyright (C) 2025 João Henrique Ferreira de Freitas <joaohf@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Breadboard base packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR = "r0"

inherit packagegroup

PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES = "\
    ${PN}-base \
    ${PN}-nerves-hub \
    ${PN}-connman \
    "

SUMMARY:${PN}-base = "Install breadboard base packages"
RDEPENDS:${PN}-base = "\
    erlang-red \
    "

SUMMARY:${PN}-nerves-hub = "Install nerves-hub-agent and dependencies"
RDEPENDS:${PN}-nerves-hub = "\
    nerves-hub-agent \
    fwup-fw-env \
    util-linux \
    "

SUMMARY:${PN}-connman = "Install connman"
RDEPENDS:${PN}-connman = "\
    connman \
    connman-client \
    "

