package by.timofey.hotel_project.features.create_hotel;

import by.timofey.hotel_project.common.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
@Component
public class HotelRequestValidation {
    public void validate(HotelRequest hotelRequest) throws BadRequestException {
        if (hotelRequest == null) {
            throw new BadRequestException("Empty request");
        }
        String phone = hotelRequest.getContact().getPhone();
        if (phone != null && !phone.isEmpty() && !Pattern.matches("^\\+?[0-9\\-\\s]+$", phone)) {
            throw new BadRequestException("Not valid format of phone");
        }
        String email = hotelRequest.getContact().getEmail();
        if (email != null && !email.isEmpty() && !Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$", email)) {
            throw new BadRequestException("Not valid format of email");
        }
        String checkIn = hotelRequest.getArrivalTime().getCheckIn();
        if (checkIn != null && !checkIn.isEmpty()
                && !Pattern.matches("^(?:[01]\\d|2[0-3]):[0-5]\\d$", checkIn)) {
            throw new BadRequestException("Not valid format of check in time");
        }
        String checkOut = hotelRequest.getArrivalTime().getCheckOut();
        if (checkOut != null && !checkOut.isEmpty()
                && !Pattern.matches("^(?:[01]\\d|2[0-3]):[0-5]\\d$", checkOut)) {
            throw new BadRequestException("Not valid format of check out time");
        }

    }
}
