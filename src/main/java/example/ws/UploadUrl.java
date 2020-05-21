package example.ws;/**
 * Created by Xxs_5918 on 2020/5/20.
 */

import com.alibaba.fastjson.JSONObject;
import example.enums.RequestType;
import example.exception.DefineException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：xiaosong.xiaon
 * @description：
 * @date ：2020/5/20 11:01
 */
public class UploadUrl {
    /**
     * @param filePath  文件路径
     * @param uploadUrl 上传方式创建文件时返回的uploadUrl
     * @return
     * @description 文件流上传文件
     * <p>
     * 说明：
     * 要注意正确设置文件的contentMd5、文件MIME以及字节流等信息，否则会导致Http状态为400的异常
     * @author 宫清
     * @date 2019年7月20日 下午8:26:03
     */
    public static JSONObject streamUpload(String filePath, String uploadUrl) throws DefineException {
        System.out.println("filePath:"+filePath);
        byte[] bytes = FileHelper.getBytes(filePath);
        String contentMd5 = FileHelper.getContentMD5(filePath);
        String conentType = FileHelper.getContentType(filePath);
        JSONObject json = HttpHelper.doUploadHttp(RequestType.PUT, uploadUrl, bytes, contentMd5, conentType);
        return json;
    }

    private static Map<String, String> buildUploadHeader(String contentMd5, String contentType) {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-MD5", contentMd5);
        header.put("Content-Type", contentType);
        return header;
    }

}
