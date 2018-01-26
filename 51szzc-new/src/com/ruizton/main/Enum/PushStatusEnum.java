package com.ruizton.main.Enum;

/**
 * 是否推送状态
 * Created by zygong on 17-4-12.
 */
public enum PushStatusEnum {
    SEND(2, true),
    NO_SEND(1, false);

    private int key;
    private boolean value;

    PushStatusEnum(int key, boolean value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public static  PushStatusEnum getByKey(int key){
         PushStatusEnum[] list =  PushStatusEnum.values();
        for( PushStatusEnum i : list){
            if(i.getKey() == key ){
                return i;
            }
        }
        return null;
    }

    public static  PushStatusEnum getByValue(boolean value){
         PushStatusEnum[] list =  PushStatusEnum.values();
        for( PushStatusEnum i : list){
            if(i.isValue() == value){
                return i;
            }
        }
        return null;
    }
}
