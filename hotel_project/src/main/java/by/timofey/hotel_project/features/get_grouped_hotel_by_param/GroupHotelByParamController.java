package by.timofey.hotel_project.features.get_grouped_hotel_by_param;

import by.timofey.hotel_project.common.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${hotel.prefix}")
@Tag(name = "Grouping hotels by param controller")
public class GroupHotelByParamController {
    private final GroupHotelByParamHandler groupHotelByParamHandler;

    @Autowired
    public GroupHotelByParamController(GroupHotelByParamHandler groupHotelByParamHandler) {
        this.groupHotelByParamHandler = groupHotelByParamHandler;
    }
@GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, String>> groupHotelByParam(@PathVariable String param)
        throws BadRequestException {
    Map<String, String>  result = groupHotelByParamHandler.execute(param);
        return ResponseEntity.ok(result);
    }

}
