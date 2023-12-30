package team.hiddenblue.wealthtrack.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.hiddenblue.wealthtrack.dto.Result;
import team.hiddenblue.wealthtrack.service.GiteeImageService;
import team.hiddenblue.wealthtrack.util.GiteeImageUtil;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/gitimage")
@RequiredArgsConstructor
public class GiteeImageController {

    @Autowired
    GiteeImageService giteeImageService;

    /**
     * 上传图片
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadImg")
    @ResponseBody
    public Result uploadImg(@RequestParam("file") MultipartFile file) throws Exception {
        int userId = StpUtil.getLoginIdAsInt();
        if (file != null && !file.isEmpty()) {
            byte[] fileBytes = file.getBytes();
            String base64EncodedString = Base64.getEncoder().encodeToString(fileBytes);
//            System.out.println("base64：" + base64EncodedString);
            return Result.SUCCESS(giteeImageService.uploadImage(base64EncodedString, userId));
        }
        else{
            return Result.FAIL("未收到数据");
        }
    }

}
