package com.outer;

import com.duck.archive.securitymanager.DangerousOperationItfc;
import com.duck.common.utils.PrintUtil;

import java.io.IOException;

/**
 * 外部危险操作实现类，实现了DangerousOperationItfc接口
 */
public class OuterDangerousOperation implements DangerousOperationItfc {
    /**
     * 执行危险命令
     *
     * @param cmd 命令
     * @throws IOException
     */
    public void executeDangerCmd(String cmd) throws IOException {
        PrintUtil.println("start executeDangerCmd: " + cmd);
        Runtime.getRuntime().exec(cmd);
        PrintUtil.println("end executeDangerCmd");
    }
}
