package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传的控制层
 */
@RestController
@RequestMapping(value = "/admin/product")
public class FileController {

    @Value("${fileServer.url}")
    private String url;

    /**
     * 文件上传
     * @param file 上传的文件
     * @return Result
     */
    @PostMapping(value = "/fileUpload")
    public Result<?> fileUpload(@RequestParam MultipartFile file) throws Exception{
        //文件上传
        String path = FileUtil.fileUpload(file);
        //判断成功还是失败
        if(StringUtils.isEmpty(path)){
            return Result.fail();
        }
        //返回结果 [0]= 组名 [1] = 全量路径和文件名
        return Result.ok(url + path);
    }
}
