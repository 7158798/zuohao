package com.otc.facade.sys.enums;

/**
 * Created by lx on 17-5-8.
 */
public interface ProcessNode {

    /**
     * 获取流程的下一个节点
     * @param node
     * @return
     */
    String getNextNode(ProcessNode node);

    /**
     * 获取流程的编号
     * @param node
     * @return
     */
    int getNodeNo(ProcessNode node);

    /**
     * 获取结束节点
     * @return
     */
    String getEndNode();
}
