package by.timofey.hotel_project.features.get_all_hotels_by_param;

public enum Parameters {
    NAME("name"),
    BRAND("brand"),
    CITY("city"),
    COUNTRY("country"),
    AMENITIES("amenities");
    private final String value;
    Parameters(String value) {
        this.value = value;
    }

    // Метод для получения связанного значения
    public String getValue() {
        return value;
    }
}
