package com.coupon.event.service;

import java.util.List;

import com.coupon.event.bean.CustomEventDTO;
import com.coupon.event.bean.EventRequest;
import com.coupon.event.bean.EventResponse;

public interface EventService {
    EventResponse saveEvent(EventRequest eventRequest);

    CustomEventDTO saveCustomEvent(CustomEventDTO requestBody);

    List<CustomEventDTO> findAllCustomEvents();

    CustomEventDTO findByCustomEventId(Integer id);

    CustomEventDTO deleteByCustomEventId(Integer id);

    CustomEventDTO updateCustomEvent(CustomEventDTO requestBody);
}
