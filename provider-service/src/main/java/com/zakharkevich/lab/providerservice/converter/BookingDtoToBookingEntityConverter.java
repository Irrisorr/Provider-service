package com.zakharkevich.lab.providerservice.converter;

import com.zakharkevich.lab.providerservice.model.dto.BookingDto;
import com.zakharkevich.lab.providerservice.model.entity.Booking;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookingDtoToBookingEntityConverter implements Converter<BookingDto, Booking> {

    @Override
    public Booking convert(BookingDto source) {
        // TODO
        return null;
    }
}