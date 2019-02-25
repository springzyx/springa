package com.example.demo.demo.Upload;

import com.aliyun.oss.OSSClient;

import java.io.InputStream;


public class OssUpload {

    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAI3PihKLTG30LW";
    private static String accessKeySecret = "1VIk6FasNemjTI7qgDW9TaEc0le2EZ";

    private static String bucketName = "apollobucket";
    private static String key = "";

    public static String upload(InputStream is, String fileName) {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, is);
        // 关闭client
        ossClient.shutdown();
        String src = "http://" + bucketName + "." + endpoint + "/" + fileName;
        return src;
    }

    public static void delete(String fileName) {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName,fileName);
        // 关闭client
        ossClient.shutdown();
    }
}
