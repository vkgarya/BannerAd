package com.coupon.event.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coupon.event.bean.CustomEventDTO;
import com.coupon.event.bean.EventRequest;
import com.coupon.event.bean.EventResponse;
import com.coupon.event.service.EventService;
import com.coupon.tracker.service.TrackerService;

@RestController
public class EventController {
    @Resource
    private EventService eventService;
    @Resource
    private TrackerService trackerService;

    @PostMapping("/cms/v1/event")
    CustomEventDTO createCustomEvent(@RequestBody final CustomEventDTO requestBody, HttpServletRequest request) {
        trackerService.save(request, "create-custom-event");
        return eventService.saveCustomEvent(requestBody);
    }

    @GetMapping("/cms/v1/event/{id}")
    CustomEventDTO getCustomEvent(@PathVariable("id")final Integer id, HttpServletRequest request) {
        trackerService.save(request, "get-custom-event");
        return eventService.findByCustomEventId(id);
    }

    @DeleteMapping("/cms/v1/event/{id}")
    CustomEventDTO deleteCustomEvent(@PathVariable("id")final Integer id, HttpServletRequest request) {
        trackerService.save(request, "delete-custom-event");
        return eventService.deleteByCustomEventId(id);
    }

    @PutMapping("/cms/v1/event")
    CustomEventDTO editEvent(@RequestBody final CustomEventDTO requestBody, HttpServletRequest request) {
        trackerService.save(request, "update-custom-event");
        return eventService.updateCustomEvent(requestBody);
    }

    @PostMapping("/api/create-user-event")
    EventResponse createUserEvent(@RequestBody final EventRequest requestBody, HttpServletRequest request) {
        trackerService.save(request, "create-user-event");
        return eventService.saveEvent(requestBody);
    }

    @GetMapping("/cms/v1/events")
    List<CustomEventDTO> getCustomEvents(HttpServletRequest request) {
        trackerService.save(request, "get-custom-events");
        return eventService.findAllCustomEvents();
    }

    @GetMapping("/api/custom-events")
    List<CustomEventDTO> getCustomEventsForAPI(HttpServletRequest request) {
        trackerService.save(request, "get-custom-events");
        return eventService.findAllCustomEvents();
    }
}
