package com.ruizton.main.auto.order;

import com.ruizton.main.auto.TestCoinInfo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lx on 17-1-19.
 */
public class User {
    // 用户id
    private int uid;
    // 虚拟币金额
    private BigDecimal xnb;
    // 人民币金额
    private BigDecimal rmb;

    private BigDecimal xnbFrozen;

    private BigDecimal rmbFrozen;
    // 用户的状态
    private Integer orderTye;

    private Map<Integer,Boolean> statusMap = new HashMap<Integer, Boolean>();

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public BigDecimal getXnb() {
        return xnb;
    }

    public void setXnb(BigDecimal xnb) {
        this.xnb = xnb;
    }

    public BigDecimal getRmb() {
        return rmb;
    }

    public void setRmb(BigDecimal rmb) {
        this.rmb = rmb;
    }

    public BigDecimal getXnbFrozen() {
        return xnbFrozen;
    }

    public void setXnbFrozen(BigDecimal xnbFrozen) {
        this.xnbFrozen = xnbFrozen;
    }

    public BigDecimal getRmbFrozen() {
        return rmbFrozen;
    }

    public void setRmbFrozen(BigDecimal rmbFrozen) {
        this.rmbFrozen = rmbFrozen;
    }

    public Integer getOrderTye() {
        return orderTye;
    }

    public void setOrderTye(Integer orderTye) {
        this.orderTye = orderTye;
    }

    /**
     * 初始化状态
     * @param autoConfig
     */
    public void initStatusMap(AutoConfig autoConfig){
        this.statusMap.clear();
        Boolean xnbFlag = xnb.compareTo(autoConfig.getXnb()) > 0?Boolean.TRUE:Boolean.FALSE;
        Boolean rmbFlag = rmb.compareTo(autoConfig.getCny()) > 0?Boolean.TRUE:Boolean.FALSE;
        statusMap.put(OrderType.sell.getCode(),xnbFlag);
        statusMap.put(OrderType.buy.getCode(),rmbFlag);
        Boolean errorFlag = Boolean.FALSE;
        if (!xnbFlag && !rmbFlag){
            errorFlag = Boolean.TRUE;
        }
        statusMap.put(OrderType.error.getCode(),errorFlag);
    }

    /**
     * 取第一个可以够状态
     * @return
     */
    private Integer getMapValue(){
        return getMapValue(null);
    }

    /**
     * 取认购状态
     * @param no
     * @return
     */
    public Integer getMapValue(Integer no){
        for (Map.Entry<Integer,Boolean> entry : statusMap.entrySet()){
            if (entry.getValue()){
                if (no == null || no.intValue() != entry.getKey().intValue()){
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    /**
     * 加载用户的状态
     * @param last
     */
    public void loadStatus(User last){
        if (this.orderTye == null){
            // 初始化用户的方向
            OrderType orderType = last == null?OrderType.sell:OrderType.buy;
            this.orderTye = new Integer(orderType.getCode());
        }
        Integer temp  = statusMap.get(orderTye)?orderTye:getMapValue();
        if (last != null){
            if (temp.intValue() == last.getOrderTye().intValue()){
                Integer next = getMapValue(temp);
                if (next == null){
                    // 只能改变last的数据
                    Integer lastTemp = last.getMapValue(last.getOrderTye());
                    if (lastTemp != null){
                        last.setOrderTye(lastTemp);
                    }
                } else {
                    temp = next;
                }
            }
        }
        this.orderTye = temp;
    }

    public Map<Integer, Boolean> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<Integer, Boolean> statusMap) {
        this.statusMap = statusMap;
    }
}
