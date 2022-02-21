package com.hzl.hadoop.gp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzl.hadoop.executor.SingleExecutor;
import com.hzl.hadoop.gp.entity.XlwebImagesEntity;
import com.hzl.hadoop.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.data.redis.connection.DataType;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.gp.entity.BaiduImagesEntity;
import com.hzl.hadoop.gp.service.BaiduImagesService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.vo.BaiduImagesVO;
import org.springframework.http.ResponseEntity;




/**
 * 新浪微博爬虫-用户图片库
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-17 17:47:41
 */
@Slf4j
@RestController
@RequestMapping("gp/baiduimages")
public class BaiduImagesController {
    @Autowired
    private BaiduImagesService baiduImagesService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<BaiduImagesEntity>> list(BaiduImagesEntity params,@RequestParam int start, @RequestParam int pageSize){
		PageInfo<BaiduImagesEntity> page = baiduImagesService.queryPage(params,start,pageSize);

        return new ResponseEntity(page, HttpStatus.OK);
    }

    /**
     * 下载落库
     */
    @GetMapping("/get/image")
    public ResponseEntity<Boolean> getImage(@RequestParam(value = "searchName") String searchName,@RequestParam(value = "size") int size){
        Boolean page = baiduImagesService.getImage(searchName,size);
        return new ResponseEntity(page, HttpStatus.OK);
    }

    /**
     * <p>
     * 通用文件下载，可以下载任何格式的文件
     * </p>
     * savePath 文件存储路径
     * searchName 数据库中存储的搜索名称
     * @author hzl 2020/01/05 2:36 PM
     */
    @GetMapping(value = "/download/image", name = "图片下载")
    public void downFileResponse(@RequestParam(value = "searchName") String searchName,@RequestParam(value = "savePath",required = false) String savePath) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("search_name", searchName);
        queryWrapper.eq("is_down", false);

        List<BaiduImagesEntity> allImages = baiduImagesService.list(queryWrapper);

        String path= StringUtils.hasLength(savePath)?savePath:"/Users/hzl/Desktop/baiduimage/"+searchName+"/";

        //1.如果文件不存在，则创建
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        List<CompletableFuture> completableFutures = new ArrayList<>();

        allImages.forEach(a->{
            CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
                try {
                    log.info("文件名称{}", searchName);
                    //下载文件
                    try (InputStream inputStream = HttpUtils.download(a.getOriginalImageUrl())) {
                        //文件保存目录
                        File file = new File(path+ a.getId() + ".jpg");
                        try (FileOutputStream fileOutputStream = new FileOutputStream(file);) {
                            IOUtils.copy(inputStream, fileOutputStream);

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, SingleExecutor.getInstance());

            completableFutures.add(completableFuture);

        });
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
        //因为不需要多次初始化，所有用完关闭线程池
        //SingleExecutor.getInstance().shutdown();
        log.info("线程池是否为空结束:{}",SingleExecutor.getInstance().isShutdown());

    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<BaiduImagesEntity> info(@PathVariable("id") Long id){
		BaiduImagesEntity baiduImages = baiduImagesService.getById(id);

        return new ResponseEntity(baiduImages,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody BaiduImagesEntity baiduImages){
		baiduImagesService.save(baiduImages);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody BaiduImagesEntity baiduImages){
		baiduImagesService.updateById(baiduImages);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		baiduImagesService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
