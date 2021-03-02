package com.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * @author god
 * @date 2020年 01月08日 19:51:11
 */
//创建websocket 握手拦截器（如果没有权限拦截等需求，这一步不是必须的）
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
         /*
         * 最后需要要显示调用父类方法，父类的 beforeHandshake 方法
         * 把 ServerHttpRequest 中 session 中对应的值拷贝到 WebSocketSession 中。
         * 如果我们没有实现这个方法，我们在最后的 handler 处理中 是拿不到 session 中的值
         * 作为测试 可以注释掉下面这一行 可以发现自定义处理器中 session 的 username 总是为空
         */
        System.out.println("链接前");
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception ex) {
        System.out.println("链接后");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
