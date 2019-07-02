package com.sykean.hmhc.util;

import com.sykean.hmhc.common.ResponseCode;
import com.sykean.hmhc.common.RestResponse;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class ResponseUtil {

    /**
     * 输出无权限响应
     * @param response
     * @param responseCode
     */
    public static void printRes(HttpServletResponse response, ResponseCode responseCode, boolean success, Object data) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        OutputStream pw = null;
        try {
            pw = response.getOutputStream();
            RestResponse<Object> restResponse = new RestResponse<>(responseCode, success);
            restResponse.setData(data);
            pw.write(JSONUtils.beanToJson(restResponse).getBytes());
        } catch (IOException e) {
            log.error("printRes:", e);
        }finally {
            if(pw!=null){
                try {
                    pw.close();
                } catch (IOException e) {
                    log.error("pw close failed:", e);
                }
            }
        }
    }
}
