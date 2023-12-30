package team.hiddenblue.wealthtrack.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.hiddenblue.wealthtrack.dto.Result;
import team.hiddenblue.wealthtrack.mapper.ImageMapper;
import team.hiddenblue.wealthtrack.service.GiteeImageService;
import team.hiddenblue.wealthtrack.util.GiteeImageUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class GiteeImageServiceImpl implements GiteeImageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ImageMapper imageMapper;

    @Override
    public Result uploadImage(String img, int userId){
        logger.info("请求成功");
        //设置转存到Gitee仓库参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", GiteeImageUtil.ACCESS_TOKEN);
        paramMap.put("message", GiteeImageUtil.ADD_MESSAGE);
        paramMap.put("content", img);
        //设置图片名字
        String fileName = UUID.randomUUID().toString() + ".jpg";
        //转存文件路径
        String requestUrl = GiteeImageUtil.CREATE_REPOS_URL + "/" + fileName;
        logger.info("请求Gitee仓库路径:{}",requestUrl);
        String resultJson = HttpUtil.post(requestUrl, paramMap);
        JSONObject jsonObject = JSONUtil.parseObj(resultJson);
        //表示操作失败
        if (jsonObject==null || jsonObject.getObj("commit") == null) {
            return Result.FAIL();
        }
        //成功情况，获取URL并保存到数据库
        JSONObject content = JSONUtil.parseObj(jsonObject.getObj("content"));
        String downloadUrl = content.getStr("download_url");
        imageMapper.insertHeadImage(downloadUrl,userId); // 假设这是保存URL的方法

        return Result.SUCCESS("成功", downloadUrl);
    }


}
