package com.duck.archive.securitymanager;

import java.io.IOException;

public interface DangerousOperationItfc {
    /**
     * 执行危险命令
     *
     * @param cmd 命令
     * @throws Exception
     */
    void executeDangerCmd(String cmd) throws Exception;
}
