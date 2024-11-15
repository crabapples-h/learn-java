package cn.crabapples.turing;

import com.alibaba.fastjson2.JSON;
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
public class TuringApiService {
    private final TuringApiProperties properties;
    private static final Logger logger = LoggerFactory.getLogger(TuringApiService.class);

    private final RestTemplate restTemplate = new RestTemplate();

    public TuringApiService(TuringApiProperties properties) {
        this.properties = properties;
    }

    /**
     * 请求图灵Api接口
     *
     * @param message     要发送的消息
     * @param userId      用户id
     * @param requestType 请求类型(因为文档丢失，目前只知道可以传0)
     * @return 图灵接口返回数据
     */
    public String sendMessage(String message, String userId, int requestType) {
        TuringRequestDTO requestDTO = createTuringRequestDTO(message, userId, requestType);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(properties.getUrl(), requestDTO.toString(), String.class);
        TuringResponseDTO turingResponseDTO = JSON.parseObject(responseEntity.getBody(), TuringResponseDTO.class);
        logger.info("图灵接口返回的数据:[{}]", turingResponseDTO);
        StringBuffer result = new StringBuffer();
        for (TuringResponseDTO.Results results : turingResponseDTO.getResults()) {
            result.append(results.getValues().getText());
        }
        logger.info("处理图灵接口返回的数据:[{}]", result);
        return result.toString();
    }

    /**
     * 生成图灵接口需要的格式参数
     *
     * @param text 微信携带的文本信息
     * @return 图灵接口需要的格式参数
     */
    private TuringRequestDTO createTuringRequestDTO(String text, String userId, int requestType) {
        TuringRequestDTO turingRequestDTO = new TuringRequestDTO();
        TuringRequestDTO.Perception perception = new TuringRequestDTO.Perception();
        TuringRequestDTO.InputText inputText = new TuringRequestDTO.InputText();
        TuringRequestDTO.UserInfo userInfo = new TuringRequestDTO.UserInfo();

        inputText.setText(text);
        perception.setInputText(inputText);
        userInfo.setApiKey(properties.getAppKey());
        userInfo.setUserId(userId);
        turingRequestDTO.setReqType(requestType);
        turingRequestDTO.setPerception(perception);
        turingRequestDTO.setUserInfo(userInfo);
        return turingRequestDTO;

    }
}
