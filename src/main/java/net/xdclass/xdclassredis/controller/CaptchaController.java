package net.xdclass.xdclassredis.controller;

import com.google.code.kaptcha.Producer;
import net.xdclass.xdclassredis.util.CommonUtil;
import net.xdclass.xdclassredis.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/captcha")
public class CaptchaController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // auto wire with qualifier annotation
    @Autowired
    private Producer captcharProducer;

    /**
     * get image captcha
     * @param request
     * @param response
     */
    @GetMapping("get_captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        String captchaText = captcharProducer.createText();
        String key = getCaptchaKey(request);
        redisTemplate.opsForValue().set(key, captchaText, 5, TimeUnit.MINUTES);
        BufferedImage bufferedImage = captcharProducer.createImage(captchaText);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * check if captcha is corrent and send code to phone then
     * @param to
     * @param captcha
     * @param request
     * @return JsonData
     */
    @GetMapping("send_code")
    public JsonData sendCode(@RequestParam(value="to",required = true) String to,
                             @RequestParam(value="captcha",required = true) String captcha,
                             HttpServletRequest request) {

        String key = getCaptchaKey(request);
        String cacheCaptcha = redisTemplate.opsForValue().get(key);
        if (captcha != null && cacheCaptcha != null && cacheCaptcha.equalsIgnoreCase(captcha)) {
            redisTemplate.delete(key);

            // TODO send code
            System.out.println("reach here");
            System.out.println(JsonData.buildSuccess());
            return JsonData.buildSuccess();
        } else {
            return JsonData.buildError("error captcha");
        }
    }


    private String getCaptchaKey(HttpServletRequest request) {
        String ip = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");
        String key = "user-service:captcha:" + CommonUtil.MD5(ip+userAgent);
        return key;
    }
}
