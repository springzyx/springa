package com.example.demo.demo.controller;

import com.example.demo.demo.Upload.OssUpload;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping(value="/imgUpload", method= RequestMethod.POST)
    @ResponseBody
    public String imgUpload(HttpServletRequest request, HttpServletResponse response) throws Exception{
        JSONObject jsonObject = new JSONObject();
        String status = "SUCCESS";
        String msg = "ok";
        String src="";
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multipartRequest.getFile("myImg");
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            fileName = new Date().getTime()+ suffixName;
            src= OssUpload.upload(file.getInputStream(),fileName);

        } else {
            status="FAIL";
        }
        jsonObject.put("Status", status);
        jsonObject.put("Msg", msg);
        jsonObject.put("Src", src);
        return jsonObject.toString();
    }
}
