package com.jucaifu.core.rpc;

import java.net.InetSocketAddress;

import com.alibaba.dubbo.rpc.RpcContext;

/**
 * RpcHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/15.
 */
public class RpcHelper {

    /**
     * Gets local address.
     *
     * @return the local address
     */
    public static InetSocketAddress getLocalAddress() {
        return RpcContext.getContext().getLocalAddress();
    }

    /**
     * Gets remote address.
     *
     * @return the remote address
     */
    public static InetSocketAddress getRemoteAddress() {
        return RpcContext.getContext().getRemoteAddress();
    }

}
