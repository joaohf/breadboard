# breadboard

This is a Yocto Project demonstration project which provides a solution composed by the following projects:

- [meta-erlang](https://meta-erlang.github.io/)
- [erlang-red](https://github.com/gorenje/erlang-red)
- [NervesCloud](https://nervescloud.com/)
- [NervesHubAgent](https://github.com/joaohf/nerves_hub_agent)

The purpose is to make these four projects working together in order to create an all-needed solution for [Breadboard Programming](https://github.com/gorenje/erlang-red?tab=readme-ov-file#breadboard-programming) with erlang-red as central point to promote flow based programming.

The role of NervesCloud is because I also want an easy and elegant solution for firmware updates. Without needing to flash a new firmware image for updates.

## Current status

This project is able to build a working image, however there are some missing parts like automatic network detection, helper for configuring linux networking and the total footprint is not great.

These are open points for future improvements. However, erlang-red usable and works as intent.

### Ideas to do with erlang-red

- explore raspberrypi hardware modules
- connect a cluster breadboard with erlang-red
- control some LEDs and other hardware devices using i2c, spi, gpio
- read sensors

## Procedures for baking breadboard image

### Requirements

1. Access to a working linux environment. It could be container, VM or a real machine
1. Use an updated linux distribution, prefer LTS versions as build host
1. This project uses [kas](https://github.com/siemens/kas) as setup tool for bitbake based projects. Make sure to
install kas, check its official documentation for procedures: [Dependencies & installation](https://kas.readthedocs.io/en/latest/userguide/getting-started.html#dependencies-installation)

### Step-by-step for getting an image built

The aim here is to build a ready to flash raspberrypi image made with Yocto Project:

1. Create a file called _nhl.sh_ which is a bash script with some environment variables for configuring Nerves Cloud credentials and also private/public key pairs for signing fwup firmware:

    ```
    export FWUP_PRIVATE_KEY_FILE="/work/fwup-key.priv"
    export FWUP_PUBLIC_KEY_FILE="/work/fwup-key.pub"

    export NERVES_HUB_LINK_PRODUCT_KEY="my key"
    export NERVES_HUB_LINK_PRODUCT_SECRET="my secret"

    export BB_ENV_PASSTHROUGH_ADDITIONS="${BB_ENV_PASSTHROUGH_ADDITIONS} NERVES_HUB_LINK_PRODUCT_KEY NERVES_HUB_LINK_PRODUCT_SECRET FWUP_PRIVATE_KEY_FILE FWUP_PUBLIC_KEY_FILE"
    ```

   TBD: document the full process of generating credentials and keys.

1. Use `kas` to instantiate a bitbake build environment:
    ```
    $ kas shell kas/demos/raspberry4-64-wifi.yml
    ```

   The above command fetches all dependencies and makes a pre-configured bitbake environment based on yml fragment files available at `kas` folder.

   Note that note raspberrypi0 and raspberrypi4 are also supported (see _kas/demos_ folder).

1. Call bitbake to create the target image _breadboard-image-cmdline_:
    ```
    $ bitbake breadboard-image-cmdline
    ```

   bitbake reads all configuration and builds each component in order to assembly the final image. It will takes some time for build all components.

1. After build has finished, the file name called `breadboard-image-cmdline-raspberrypi4-64.rootfs.fwup` will be available at `tmp/deploy/images/raspberrypi4-64/` folder. This file is a full image ready to be written into a sdcard.

1. The command that I use to write image to sdcard is [bmaptool](https://docs.yoctoproject.org/dev-manual/bmaptool.html). There are many ways to get it installed. If you are using a build host based on Debian, it is available in apt repository.

    ```
    $ bmaptool copy images/raspberrypi4-64/breadboard-image-cmdline-raspberrypi4-64.rootfs.fwup /dev/sdc
    ```

   The above command writes the fwup image into /dev/sdc (this is where my sdcard got mapped).

### Running breadboard image

After the building and sdcard flash process is done. It's time to insert sdcard with breadboard image into a raspberrypi board and turn it on.

There is no graphic environment and you will need to access it with serial console for the initial configuration.

What you need to perform is network configuration using _connmanctl_ tool. A good (and working) resource about how to setup network is here: [The Yocto Project on Raspberry Pi 5 Episode 8: Connecting to Wi-Fi with ConnMan Network Manager](https://anavi.org/article/325/).

After get network configured, you can access erlang-red web interface: http://[raspberrypi4-64 IP address]:8080/erlang-red.

And also inspect if raspberry gets connected into Nerves Cloud service at: https://nervescloud.com/

## Acknowledges

- [Gerrit Riessen](https://github.com/gorenje), [erlang-red](https://github.com/gorenje/erlang-red)'s author, for exploring flow based programming with BEAM languages
- [NervesCloud](https://nervescloud.com/) and [fwup](https://github.com/fwup-home/fwup) team.