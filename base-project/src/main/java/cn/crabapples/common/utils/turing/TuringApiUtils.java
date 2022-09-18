package cn.crabapples.common.utils.turing;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * TODO 图灵机器人工具类
 *
 * @author Mr.He
 * 9/5/20 3:02 PM
 * e-mail hequan@gogpay.cn
 * qq 294046317
 * pc-name root
 */
@Component
public class TuringApiUtils {
    private static final Logger logger = LoggerFactory.getLogger(TuringConfigure.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private final TuringConfigure turingConfigure;

    public TuringApiUtils(TuringConfigure turingConfigure) {
        this.turingConfigure = turingConfigure;
    }

    /**
     * 请求图灵Api接口
     *
     * @param requestString 微信携带的文本信息
     * @return 图灵接口返回数据
     */
    public String sendToTuringApi(String requestString) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(turingConfigure.getUrl(), createTuringRequestDTO(requestString).toString(), String.class);
        TuringResponseDTO turingResponseDTO = JSON.parseObject(responseEntity.getBody(), TuringResponseDTO.class);
        logger.info("图灵接口返回的数据：【{}】", turingResponseDTO);
        StringBuffer result = new StringBuffer();
        for (TuringResponseDTO.Results results : turingResponseDTO.getResults()) {
            result.append(results.getValues().getText());
        }
        logger.info("处理图灵接口返回的数据：【{}】", result);
        return result.toString();
    }

    /**
     * 生成图灵接口需要的格式参数
     *
     * @param text 微信携带的文本信息
     * @return 图灵接口需要的格式参数
     */
    private TuringRequestDTO createTuringRequestDTO(String text) {
        TuringRequestDTO turingRequestDTO = new TuringRequestDTO();
        TuringRequestDTO.Perception perception = new TuringRequestDTO.Perception();
        TuringRequestDTO.InputText inputText = new TuringRequestDTO.InputText();
        TuringRequestDTO.UserInfo userInfo = new TuringRequestDTO.UserInfo();

        inputText.setText(text);
        perception.setInputText(inputText);
        userInfo.setApiKey(turingConfigure.getAppKey());
        userInfo.setUserId("123");
        turingRequestDTO.setReqType("0");
        turingRequestDTO.setPerception(perception);
        turingRequestDTO.setUserInfo(userInfo);
        return turingRequestDTO;

    }
}
