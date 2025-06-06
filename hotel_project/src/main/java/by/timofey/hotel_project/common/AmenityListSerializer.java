package by.timofey.hotel_project.common;

import by.timofey.hotel_project.infrastructure.entity.Amenity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class AmenityListSerializer extends JsonSerializer<List<Amenity>> {

    @Override
    public void serialize(List<Amenity> amenities, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        generator.writeStartArray();
        for (Amenity amenity : amenities) {
            generator.writeString(amenity.getAmenity());
        }
        generator.writeEndArray();
    }
}
