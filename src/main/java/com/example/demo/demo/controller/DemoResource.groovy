package com.example.demo.demo.controller

import com.example.demo.demo.Upload.OssUpload
import com.github.pagehelper.PageHelper
import net.sf.json.JSONObject
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

import javax.annotation.Resource

/**
 * @Auther: changzhaoliang
 * @Date: 2019/1/31 16:23
 * @Description:
 */
@RestController
class DemoResource {

    @Resource
    TNewsMapper tNewsMapper

//    @GetMapping("/index3")
//    ModelAndView index2() {
//        ModelAndView mv = new ModelAndView()
//        mv.viewName = "index3"
//        mv
//    }

    @GetMapping("/newsList")
    ModelAndView newsList(Integer p) {
        ModelAndView mv = new ModelAndView()
        if (p == null || p<=0) {
            p = 1
        }
        PageHelper.startPage(p,10);
        List<TNewsEntity> tNewsEntities = tNewsMapper.selectAll()
        Integer countNums = tNewsMapper.countItems()
        PageBean<TNewsEntity> pageData= new PageBean<>(p,10,countNums)
        pageData.setItems(tNewsEntities)
        mv.addObject("page",pageData)
        mv.viewName = "news-list"
        mv
    }

    @PostMapping("/news")
    String newsSave(String title,long universityId,String author,String txt,String content){
        TNewsEntity tNewsEntity = new TNewsEntity(title: title,universityId: universityId,author:author,content:txt,createTime: new Date(),updateTime: new Date())
        tNewsMapper.insert(tNewsEntity)

        InputStream is = new ByteArrayInputStream(content.getBytes())
        String url = OssUpload.upload(is,"news_"+tNewsEntity.id+".html")
        tNewsEntity.url = url
        tNewsMapper.update(tNewsEntity)

        JSONObject jsonObject = new JSONObject()
        jsonObject.put("status","success")
        jsonObject.toString()
    }

    @PutMapping("/news/{id}")
    String newsUpdate(@PathVariable long id,String title,long universityId,String author,String txt,String content){
        TNewsEntity tNewsEntity = tNewsMapper.findOne(id)
        if(tNewsEntity != null){
            OssUpload.delete("news_"+id+".html")
            tNewsEntity.title = title
            tNewsEntity.universityId = universityId
            tNewsEntity.author = author
            tNewsEntity.content = txt

            InputStream is = new ByteArrayInputStream(content.getBytes())
            String url = OssUpload.upload(is,"news_"+id+".html")
            tNewsEntity.url = url
            tNewsMapper.update(tNewsEntity)
        }

        JSONObject jsonObject = new JSONObject()
        jsonObject.put("status","success")
        jsonObject.toString()
    }

    @GetMapping("/newsEdit")
    ModelAndView newsEdit(Long id) {
        ModelAndView mv = new ModelAndView()
        if(id != null){
            TNewsEntity tNewsEntity = tNewsMapper.findOne(id)
            mv.addObject("news",tNewsEntity)
        }
        mv.viewName = "news-edit"
        mv
    }

    @DeleteMapping("/news")
    String newsDelete(Long id){
        TNewsEntity tNewsEntity = tNewsMapper.findOne(id)
        if(tNewsEntity != null){
            OssUpload.delete("news_"+id+".html")
            tNewsMapper.delete(tNewsEntity.id)
        }

        JSONObject jsonObject = new JSONObject()
        jsonObject.put("status","success")
        jsonObject.toString()
    }
}
