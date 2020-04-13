package safetyrating.data;


import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class map{
    
	@CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public double getHazard(@RequestBody int start, @RequestBody int end, @RequestBody int carModel) throws IOException{
        Rating r = new Rating();
        return r.rating(carModel, start, end);
    }
}