package com.example.demo.demo.controller;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.Writer;

/**
 * @Auther: changzhaoliang
 * @Date: 2019/1/31 16:34
 * @Description:
 */
public class FreemarkerExceptionHandler implements TemplateExceptionHandler {
    @Override
    public void handleTemplateException(TemplateException te, Environment env,
                                        Writer out) throws TemplateException {

        //log.warn("[Freemarker Error: " + te.getMessage() + "]");
        String missingVariable = "undefined";
        try {
            String[] tmp = te.getMessageWithoutStackTop().split("\n");
            if (tmp.length > 1)
                tmp = tmp[1].split(" ");
            if (tmp.length > 1)
                missingVariable = tmp[1];

            out.write("[出错了，请联系网站管理员：${ " + missingVariable
                    + "}]");
        } catch (IOException e) {
            throw new TemplateException(
                    "Failed to print error message. Cause: " + e, env);
        }
    }
}
