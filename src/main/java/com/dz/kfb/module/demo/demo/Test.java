package com.dz.kfb.module.demo.demo;

import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        Map<String, String> info = new Test().remoteShell3("root@172.30.0.185", "/root/test/shellforkettle/test.sh", "20180906", 100);
        System.out.println(info.get("errorInfo"));

    }
    public Map<String, String> remoteShell3(String userAndIp, String fileAbsolutePathInLinux, String busiDate, long jobLogId) {
        Map<String, String> info = new HashMap<>();
        try {
            String userName = userAndIp.split("@")[0];
            String ipAddr = userAndIp.split("@")[1];
            String password = "dgzqtest123";
            SSHUtils.DestHost destHost = new SSHUtils.DestHost(ipAddr, userName, password);
            Session session = SSHUtils.getJSchSession(destHost);
            String s1 = SSHUtils.execCommandByJSch(session, "sh /root/test/test.sh " + busiDate + " " + jobLogId);
//            String cmd = "sh " + fileAbsolutePathInLinux + " " + busiDate + " " + jobLogId;
            String cmd = "sh /root/test/shellforkettle/test.sh " + busiDate + jobLogId;
            String str = SSHUtils.execCommandByJSch(session, cmd);
            session.disconnect();
            if ("".equals(str)) {
                info.put("errorNo", "0");
                info.put("errorInfo", "0");
            } else {
                info.put("errorNo", "1");
                info.put("errorInfo", str);
            }

            return info;
        } catch (Exception e) {
            e.printStackTrace();
            info.put("errorNo", "-1");
            info.put("errorInfo", e + "");
            return info;
        }
    }


    public void test() {
        try {
            String cmd = "ssh -l root 172.30.0.185 : \"/root/test/shellforkettle/test.sh 20180906 10\"";
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
            process.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
