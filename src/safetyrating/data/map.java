package safetyrating.data;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Entry;
import Rating;

@RestController
@RequestMapping("/")
public class map{
    
    @PostMapping
    public int getHazard(@RequestBody int start, @RequestBody int end, @RequestBody int carModel){
        Rating r = new Rating();
        return r.rating(carModel, start, end);
    }

    @GetMapping
    public String getVType(int num){
        Entry e = new Entry();
        return e.V_TYPE(num);
    }
}