package com.amaker.toutiao.service;

import com.amaker.toutiao.util.TouTiaoUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @program: toutiao
 * @Date: 2018/12/18 0018 14:01
 * @Author: GHH
 * @Description:
 */
@Service
public class QiniuService {

    private static Logger logger=LoggerFactory.getLogger(QiniuService.class);

    //构造一个带指定Zone对象的配置类
    Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释

    UploadManager uploadManager = new UploadManager(cfg);
    //...生成上传凭证，然后准备上传
    String accessKey = "cfsVyua3_RjLKa1b01NpdvJBJO1rLZorKn10BEXh";
    String secretKey = "gS3K-kW82HfgnQcGPWU2uodDL-kpAvLTUKIUbNSI";
    String bucket = "nowcoder";

    Auth auth = Auth.create(accessKey, secretKey);
    String upToken = auth.uploadToken(bucket);

    public String saveImage(MultipartFile file) throws Exception {
        try {

            int dopos=file.getOriginalFilename().lastIndexOf(".");
            if(dopos<0){
                return null;
            }
            String ext=file.getOriginalFilename().substring(dopos+1).toLowerCase();
            if(!TouTiaoUtil.isFileAllowed(ext)){
                return null;
            }
            String fileName=UUID.randomUUID().toString().replaceAll("-","")+"."+ext;


            Response response = uploadManager.put(file.getBytes(), fileName, upToken);

                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
//            System.err.println(response.bodyString());
                return TouTiaoUtil.QINIU_DOMAIN_PREFIX+putRet.key;


        } catch (QiniuException e) {
                logger.error("七牛云异常！"+e.getMessage());
                return null;
        }
    }
}
