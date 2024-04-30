package com.mindata.searchservice.search.domain;

import java.util.Objects;
import java.util.UUID;

public final class HotelId {

    private UUID value;

    public HotelId(UUID value) {
        ensureValidVIsDefined(value);

        this.value = value;
    }

    private void ensureValidVIsDefined(UUID value) {
        if (value == null ) {
            throw new IllegalArgumentException("Value cannot be null");
        }
    }

    public UUID getValue() {
        return value;
    }

    public void setValue(UUID value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelId hotelId = (HotelId) o;
        return Objects.equals(value, hotelId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
