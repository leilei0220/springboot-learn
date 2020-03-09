package com.leilei.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName JSONMgrReturn @Author leilei @Date 2019-08-28 09:21 @Description
 */
@ApiModel(description = "统一响应返回对象")
public class JsonReturn {

    @ApiModelProperty(name = "head", value = "响应头", example = "true/false", required = true)
    private Boolean head;
    @ApiModelProperty(name = "status", value = "响应状态码", example = "200/500", required = true)
    private Integer status;
    @ApiModelProperty(name = "message", value = "可自定义的响应消息", example = "操作成功")
    private String message;
    @ApiModelProperty(name = "content", value = "响应内容", example = "需要返回的数据")
    private Object content;

    private static boolean SUCCESS = true;
    private static boolean FAILURE = false;

    public JsonReturn() {
    }

    public JsonReturn(Boolean head, Object content, Integer status, String message) {
        try {
            this.head = head;
            this.status = status;
            this.message = message;
            this.content = content;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JsonReturn build(boolean head, Object content, Integer status, String message) {
        return new JsonReturn(head, content, status, message);
    }

    public static JsonReturn buildSuccess(Object content, Integer status) {
        return build(SUCCESS, content, status, "操作成功");
    }

    public static JsonReturn buildSuccess(Object content) {
        return buildSuccess(content, ServerCode.DEFAULT);
    }

    public static JsonReturn buildSuccess(Object content, String message) {
        return buildSuccess(content, ServerCode.DEFAULT, message);
    }

    public static JsonReturn buildSuccess(Object content, Integer status, String message) {
        return build(SUCCESS, content, status, message);
    }

    public static JsonReturn buildSuccessEmptyBody() {
        return buildSuccess("");
    }

    public static JsonReturn buildFailure(String message, Integer status) {
        return build(FAILURE, "", status, message);
    }

    public static JsonReturn buildFailure(String message) {
        return buildFailure(message, ServerCode.FAILURE);
    }

    public static JsonReturn buildFailureContent(Object content, int state, String message) {
        return build(FAILURE, content, state, message);
    }

    public Boolean getHead() {
        return head;
    }

    public void setHead(Boolean head) {
        this.head = head;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "JSONMgrReturn{"
                + "head="
                + head
                + ", status="
                + status
                + ", message='"
                + message
                + '\''
                + ", content="
                + content
                + '}';
    }
}
