import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class map{
    
    @PostMapping
    public void getHazard(){
        
    }

    @GetMapping
    public Object[] getData(){
        
    }
}