package com.kshitz.pandit.chat;

/**
 * Created by xetiz on 06/08/2017.
 */

public interface ICommandServer {
    void authenticate(String sessionCode);
    void shutdown();
    void restart();
}
