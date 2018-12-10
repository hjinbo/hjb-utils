package com.dz.kfb.module.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Enumeration;

public class IpUtils {

    private static Logger logger = LoggerFactory.getLogger(IpUtils.class);

    public static String getLocalIp() {
        try {
            String osName = System.getProperty("os.name");
            if (osName.contains("Linux")) {
                return getLinuxLocalIp();
            } else if (osName.contains("Windows")) {
                return getWindowsLocalIp();
            } else {
                throw new Exception("当前操作系统暂不支持获取ip");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String getWindowsLocalIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    private static String getLinuxLocalIp() throws SocketException {
        String ip = null;
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        for (; nis.hasMoreElements();) {
            NetworkInterface ni = nis.nextElement();
            Enumeration<InetAddress> ias = ni.getInetAddresses();
            for (; ias.hasMoreElements();) {
                InetAddress ia = ias.nextElement();
                if (ia instanceof Inet4Address && !ia.getHostAddress().equals("127.0.0.1")) {
                    ip = ia.getHostAddress();
                }
            }
        }
        return ip;
    }
}
