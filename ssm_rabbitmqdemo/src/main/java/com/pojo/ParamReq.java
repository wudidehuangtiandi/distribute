package com.pojo;

/**
 * 实体类
 *
 * @author GC
 * @date 2020年 01月30日 12:38:29
 */

public class ParamReq {
    private String messageType;

    private String content;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ParamReq [messageType=" + messageType + ", content=" + content + "]";
    }

}