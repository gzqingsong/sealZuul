package com.example.sealZuul.seal.filter;

import com.netflix.zuul.ZuulFilter;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import org.springframework.http.HttpStatus;
import java.io.IOException;

@Component
@Log
public class SealFilter extends ZuulFilter {
    @Override
    public String filterType(){
        return "pre";//includes pre post route error
    }

    /**
     * 执行顺序
     * 数值越小，优先级越高
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 执行条件
     * true 开启
     * false 关闭
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }
    /**
     * 动作（具体操作）
     * 具体逻辑
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();
        // Getting request token
        String token = request.getParameter("token");
        // business handle
        if (null == token) {
            log.info("token is null...");
            // end the request and not continue。
            rc.setSendZuulResponse(false);
            // response code，HTTP 401 which is unauthorization
            rc.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            // response type
            rc.getResponse().setContentType("application/json;charset=utf-8");
            PrintWriter writer = null;
            try {
                writer = rc.getResponse().getWriter();
                // content of response
                writer.print("{\"message\":\"" +
                        HttpStatus.UNAUTHORIZED.getReasonPhrase() + "\"}");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != writer)
                    writer.close();
            }
        } else {
            // To check token and verify if is correctly to continue
            log.info("token is OK!");
        }

        return null;
    }
}
