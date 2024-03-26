LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSES/LGPL-2.1-or-later.txt;md5=2a4f4fd2128ea2f65047ee63fbca9f68"

DEPENDS = "fcitx5 fcitx5-tools-native gettext-native qtbase-native wayland wayland-protocols dbus libxkbcommon"
RDEPENDS_${PN} = "fcitx5 qtbase"

SRC_URI = "https://github.com/fcitx/fcitx5-qt/archive/refs/tags/${PV}.tar.gz\
           file://0001-Solution-for-build-in-yocto.patch\
"

SRC_URI[md5sum] = "a3548c92ff88edd656302ab26e21e239"
SRC_URI[sha256sum] = "f4f5176190e4e1fe51516edf8bacd3a05b2042073d36fe3792cdf7dc1e1ad778"

S = "${WORKDIR}/${PN}-${PV}/"

inherit cmake pkgconfig cmake_qt5

EXTRA_OECMAKE += " \
            -DENABLE_WAYLAND=On\
            -DENABLE_DBUS=On\
            -DENABLE_QT5=On\
            -DENABLE_QT4=Off\
"

do_install () {
         install -d 0755 ${D}/${libdir}/qt5/platforminputcontexts/
         install -d 0755 ${D}/${libdir}/fcitx5/qt5/quickphrase-editor/

         install -d 0755 ${D}/usr/libexec/

         install -m 0755 ${B}/qt5/platforminputcontext/libfcitx5platforminputcontextplugin.so ${D}/${libdir}/qt5/platforminputcontexts/libfcitx5platforminputcontextplugin.so
         install -m 0755 ${B}/qt5/guiwrapper/fcitx5-qt5-gui-wrapper ${D}/usr/libexec/fcitx5-qt5-gui-wrapper
         install -m 0755 ${B}/qt5/quickphrase-editor/libfcitx-quickphrase-editor5.so ${D}/${libdir}/fcitx5/qt5/quickphrase-editor/libfcitx-quickphrase-editor5.so
         
         install -m 0755 ${B}/qt5/dbusaddons/libFcitx5Qt5DBusAddons.so.5.0.10 ${D}/${libdir}/libFcitx5Qt5DBusAddons.so.5.0.10
         ln -s libFcitx5Qt5DBusAddons.so.5.0.10 ${D}/${libdir}/libFcitx5Qt5DBusAddons.so.1
         ln -s libFcitx5Qt5DBusAddons.so.1 ${D}/${libdir}/libFcitx5Qt5DBusAddons.so

         install -m 0755 ${B}/qt5/widgetsaddons/libFcitx5Qt5WidgetsAddons.so.5.0.10 ${D}/${libdir}/libFcitx5Qt5WidgetsAddons.so.5.0.10
         ln -s libFcitx5Qt5WidgetsAddons.so.5.0.10 ${D}/${libdir}/libFcitx5Qt5WidgetsAddons.so.2
         ln -s libFcitx5Qt5WidgetsAddons.so.2 ${D}/${libdir}/libFcitx5Qt5WidgetsAddons.so

}

FILES_${PN} += "${libdir}/qt5/platforminputcontexts/libfcitx5platforminputcontextplugin.so \
    ${libdir}/fcitx5/qt5/quickphrase-editor/libfcitx-quickphrase-editor5.so \
    /usr/libexec/fcitx5-qt5-gui-wrapper \
    ${libdir}/libFcitx5Qt5DBusAddons.so.5.0.10 \
    ${libdir}/libFcitx5Qt5DBusAddons.so.1 \
    ${libdir}/libFcitx5Qt5DBusAddons.so \
    ${libdir}/libFcitx5Qt5WidgetsAddons.so.5.0.10 \
    ${libdir}/libFcitx5Qt5WidgetsAddons.so.2 \
    ${libdir}/libFcitx5Qt5WidgetsAddons.so \
    ${bindir}/fcitx5-qt5-immodule-probing \
"

FILES_${PN}-dev += "${libdir}/cmake/FcitxQt5WidgetsAddons \
    ${libdir}/cmake/FcitxQt5DBusAddons \
"
