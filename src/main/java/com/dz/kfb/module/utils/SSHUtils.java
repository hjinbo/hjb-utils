package com.dz.kfb.module.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SSHUtils {
    private static final String ENCODING = "UTF-8";
    private static final int port = 22;
    private static final int timeout = 60 * 1000 * 30; // 30分钟的会话超时时间
    private static Session session;

    public static void connect(String host, String userName, String password) throws JSchException {
        JSch jsch = new JSch();
        session = jsch.getSession(userName, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setTimeout(timeout);
        session.connect();
    }

    public static void disconnect() {
        session.disconnect();
    }

    public static Map<String, String> execCommand(String command, Object[] params) throws IOException, JSchException {
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        InputStream in = channelExec.getInputStream();
        InputStream err = channelExec.getErrStream();
        StringBuilder sb = new StringBuilder(command);
        for (int i = 0; i < params.length; i++) {
            sb.append(" ").append(params[i]);
        }
        channelExec.setCommand(sb.toString());
        channelExec.connect();
        String result = IOUtils.toString(in, ENCODING).trim();
        String error = IOUtils.toString(err, ENCODING).trim();
        channelExec.disconnect();
        Map<String, String> map = new HashMap<>();
        map.put("strOut", result);
        map.put("errOut", error);
        return map;
    }
}
