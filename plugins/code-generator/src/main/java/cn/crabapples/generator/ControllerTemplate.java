package cn.crabapples.generator;

import org.example.base.BaseController;
import org.example.base.ResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


//@RestController
//@Slf4j
@RequestMapping("/api/")
public class ControllerTemplate extends BaseController {

//
//    @PostMapping("/save")
//    public ResponseDTO<Boolean> save() {
//        return new ResponseDTO<>();
//    }
//
//    @GetMapping("/page")
//    public ResponseDTO<Page> page(
//            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
//            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
//        return new ResponseDTO<>();
//    }
//
    @GetMapping("/list")
//    @JwtIgnore
    public ResponseDTO<List> list() {
        return new ResponseDTO<>();

    }
//
//    @GetMapping("/get/{id}")
//    public ResponseDTO info(@PathVariable String id) {
//        return new ResponseDTO<>();
//    }
//
//    @DeleteMapping("/remove/{id}")
//    public ResponseDTO<Boolean> remove(@PathVariable String id) {
//        return new ResponseDTO<>();
//    }
}
