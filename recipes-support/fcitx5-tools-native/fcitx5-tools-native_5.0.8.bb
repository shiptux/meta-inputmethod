# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSES/LGPL-2.1-or-later.txt
LICENSE = "LGPL-2.1+"
LIC_FILES_CHKSUM = "file://LICENSES/LGPL-2.1-or-later.txt;md5=2a4f4fd2128ea2f65047ee63fbca9f68"

SRC_URI = "https://download.fcitx-im.org/fcitx5/fcitx5/fcitx5-${PV}_dict.tar.xz"
SRC_URI[sha256sum] = "02f5de5e4d8c9912656b5acf954085ee5cdd567292fc1a915be051f9aed46614"

# Modify these as desired
S = "${WORKDIR}/fcitx5-${PV}"

# NOTE: unable to map the following CMake package dependencies: WaylandScanner ECM Execinfo WaylandProtocols LibUUID XKBCommon fmt XCBImdkit XKeyboardConfig Systemd DL LibIntl LibKVM Doxygen Libevent IsoCodes Wayland

DEPENDS = "ninja extra-cmake-modules virtual/egl fmt libevent"

inherit autotools cmake pkgconfig native

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
EXTRA_OECMAKE = " \
        -DENABLE_TEST=OFF \
        -DENABLE_COVERAGE=OFF \
        -DENABLE_ENCHANT=OFF \
        -DENABLE_X11=OFF \
        -DENABLE_WAYLAND=OFF \
        -DENABLE_DBUS=OFF \
        -DENABLE_KEYBOARD=OFF \
        -DUSE_SYSTEMD=OFF \
        -DENABLE_XDGAUTOSTART=OFF \
        -DENABLE_EMOJI=OFF \
        -DENABLE_LIBUUID=OFF \
	-DDL_INCLUDE_DIR=/usr/include \
	-DLibIntl_INCLUDE_DIR=/usr/include \
	-DPTHREAD_INCLUDE_DIR=/usr/include \
	-DPTHREAD_LIB_FOUND=/usr/lib \
"

LDFLAGS_append = ",--no-as-needed -ldl "

do_install() {
	install -d ${D}/${libdir}/fcitx5/libexec
	install -m 744 src/modules/spell/dict/comp-spell-dict ${D}/${libdir}/fcitx5/libexec
	install -m 644 src/lib/fcitx-utils/libFcitx5Utils.so* ${D}/${libdir}
	install -m 644 src/lib/fcitx-config/libFcitx5Config.so* ${D}/${libdir}
}

BBCLASSEXTEND = "native nativesdk"
