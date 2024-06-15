import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // @GetMapping("/gift")
    // public String gift() {
    //     return "gift/gift";
    // }

    // @GetMapping("/comparison")
    // public String comparison() {
    //     return "comparison/comparison";
    // }
}