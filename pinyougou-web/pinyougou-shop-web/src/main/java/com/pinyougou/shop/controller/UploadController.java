package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.service.GoodsDescService;
import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UploadController {
    /** 注入文件服务器访问地址 */
    @Value("${fileServerUrl}")
    private String fileServerUrl;
    /** 文件上传 */
    @PostMapping("/upload")
    public Map<String, Object>
    upload(@RequestParam("file")MultipartFile multipartFile) {

        Map<String, Object> data = new HashMap<>();
        data.put("status", 500);
        try {
            /** 加载配置文件，产生该文件绝对路径 */
            String conf_filename = this.getClass()
                    .getResource("/fastdfs_client.conf").getPath();
            /** 初始化客户端全局对象 */
            ClientGlobal.init(conf_filename);
            /** 创建存储客户端对象 */
            StorageClient storageClient = new StorageClient();
            /** 获取原文件名 */
            String originalFilename =
                    multipartFile.getOriginalFilename();
            /** 上传文件到FastDFS服务器 */
            String[] arr = storageClient
                    .upload_file(multipartFile.getBytes(),
                            FilenameUtils.getExtension(originalFilename), null);
            /** 拼接返回的 url 和 ip 地址，拼装成完整的 url */
            StringBuilder url = new StringBuilder(fileServerUrl);
            for (String str : arr){
                url.append("/" + str);
            }
            data.put("status", 200);
            data.put("url", url.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}

