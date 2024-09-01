package com.zakharkevich.lab.providerservice.converter;

import com.zakharkevich.lab.providerservice.model.dto.BookingDto;
import com.zakharkevich.lab.providerservice.model.entity.Booking;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookingEntityToBookingDtoConverter implements Converter<Booking, BookingDto> {

    @Override
    public BookingDto convert(Booking source) {
        // TODO
        return null;
    }
}