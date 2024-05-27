package com.tb;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class R<T> {
    private int code;
    private String msg;
    private T data;
    private Object ext;

    public static R ok(String msg, Object data) {
        return R.builder().code(0).msg(msg).data(data).build();
    }

    public static R ok(Object data) {
        return ok("ok", data);
    }

    public static R fail(int code, String msg, Object data) {
        return R.builder().code(code).msg(msg).data(data).build();
    }

    public static R fail(String msg) {
        return fail(1, msg, null);
    }

    public static R fail() {
        return fail(1, "请稍后再试", null);
    }
}
