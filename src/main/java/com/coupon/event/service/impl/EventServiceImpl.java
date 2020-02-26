package com.coupon.event.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coupon.constants.EventStatusCodes;
import com.coupon.event.bean.CustomEventDTO;
import com.coupon.event.bean.CustomEventFieldDTO;
import com.coupon.event.bean.EventRequest;
import com.coupon.event.bean.EventResponse;
import com.coupon.event.bean.jpa.CustomEventFieldEntity;
import com.coupon.event.bean.jpa.CustomEventsEntity;
import com.coupon.event.bean.jpa.EventEntity;
import com.coupon.event.bean.jpa.EventFieldsEntity;
import com.coupon.event.constants.EventType;
import com.coupon.event.repository.EventFieldRepository;
import com.coupon.event.repository.EventRepository;
import com.coupon.event.repository.CustomEventRepository;
import com.coupon.event.repository.CustomEventFieldRepository;
import com.coupon.event.service.EventService;
import com.coupon.exceptions.BadRequestException;
import com.coupon.exceptions.ResourceNotFoundException;
import com.coupon.service.mapper.ServiceMapper;
import com.coupon.utils.TimeUtil;

@Service
public class EventServiceImpl implements EventService {
    @Resource
    private EventRepository eventRepository;
    @Resource
    private EventFieldRepository eventFieldRepository;
    @Resource
    private CustomEventRepository customEventRepository;
    @Resource
    private CustomEventFieldRepository customEventFieldRepository;
    @Resource
    private ServiceMapper<CustomEventFieldDTO, CustomEventFieldEntity> customEventFieldMapper;
    @Resource
    private ServiceMapper<CustomEventDTO, CustomEventsEntity> customEventMapper;

    @Transactional
    @Override
    public EventResponse saveEvent(EventRequest eventRequest) {
        if (eventRequest.getEvent_code() == null) {
            return new EventResponse(EventStatusCodes.VALUES.get(EventStatusCodes.EVENT_CODE_MISSING));
        }

        if (!eventRepository.findByTxnId(eventRequest.getTxn_id()).isEmpty()) {
            return new EventResponse(EventStatusCodes.VALUES.get(EventStatusCodes.DUPLICATE_TRANSACTION_ID));
        }

        Map<String, CustomEventsEntity> staticCustomEventEntityMap = getStaticEventsAsMap();
        Map<Integer, CustomEventsEntity> staticCustomEventEntityIdMap = getStaticEventsAsMapById();
        Optional<CustomEventsEntity> eventsEntity = customEventRepository.findByEventCodeAndActiveTrue(eventRequest.getEvent_code());

        if (!eventsEntity.isPresent()) {
            return new EventResponse(EventStatusCodes.VALUES.get(EventStatusCodes.INVALID_EVENT_CODE));
        }

        Iterable<CustomEventFieldEntity> staticEventFieldEntities = customEventFieldRepository.findByCustomEventsEntity_idAndActiveTrue(eventsEntity.get().getId());
        Map<String, CustomEventFieldEntity> mandatoryStaticFields = getMandatoryStaticEventFieldsAsMap(staticEventFieldEntities);
        EventResponse response = new EventResponse(EventStatusCodes.VALUES.get(EventStatusCodes.SUCCESS));
        List<String> missingFields = checkAllMandatoryFields(eventRequest.getFields(), mandatoryStaticFields);

        if (!missingFields.isEmpty()) {
            response = new EventResponse(EventStatusCodes.VALUES.get(EventStatusCodes.MANDATORY_EVENT_FIELDS_MISSING));
            String strList = missingFields.toString();

            strList = strList.replace("[", "")
                    .replace("]", "")
                    .replace(" ", "");
            response.setMsg(response.getMsg().replace("%fields%", strList));
        }



        if (!staticCustomEventEntityMap.containsKey(eventRequest.getEvent_code())) {
            return new EventResponse(EventStatusCodes.VALUES.get(EventStatusCodes.INVALID_EVENT_CODE));
        }

        EventEntity eventEntity = new EventEntity();
        eventEntity.setCustomEventId(staticCustomEventEntityMap.get(eventRequest.getEvent_code()).getId());
        eventEntity.setTxnId(eventRequest.getTxn_id());
        eventEntity.setUserId(eventRequest.getUser_id());

        EventFieldsEntity eventFieldEntity;
        List<EventFieldsEntity> eventFieldEntityList = new ArrayList<>();

        EventEntity savedEventEntity = eventRepository.save(eventEntity);
        EventEntity tempEntity = new EventEntity();
        tempEntity.setId(savedEventEntity.getId());

        for (Map<String, Object> map: eventRequest.getFields()) {
            if (map.keySet().size() != 1) {
                response = new EventResponse(EventStatusCodes.VALUES.get(EventStatusCodes.INVALID_FIELD_VALUES));
                break;
            }

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (!validateFieldValue(entry.getKey(), entry.getValue(), staticEventFieldEntities)) {
                    response = new EventResponse(EventStatusCodes.VALUES.get(EventStatusCodes.INVALID_FIELD_VALUES));
                }

                eventFieldEntity = new EventFieldsEntity();
                eventFieldEntity.setFieldName(entry.getKey());
                eventFieldEntity.setFieldValue(entry.getValue().toString());
                eventFieldEntity.setEventEntity(tempEntity);
                eventFieldEntityList.add(eventFieldEntity);
            }
        }

        if (!"error".equals(response.getStatus())) {
            eventFieldRepository.saveAll(eventFieldEntityList);
        }

        return response;
    }

    @Transactional
    @Override
    public CustomEventDTO saveCustomEvent(CustomEventDTO requestBody) {
        if (requestBody.getEventCode() == null) {
            throw new BadRequestException("Missing Event Code");
        }
        Optional<CustomEventsEntity> customEventsEntityOptional = customEventRepository.findByEventCodeAndActiveTrue(requestBody.getEventCode());

        if (customEventsEntityOptional.isPresent()) {
            throw new BadRequestException("Duplicate Event Code");
        }

        ZonedDateTime now = TimeUtil.getCurrentUTCTime();
        CustomEventsEntity customEventsEntity = new CustomEventsEntity();
        customEventMapper.mapDTOToEntity(requestBody, customEventsEntity);
        customEventsEntity.setCreatedOn(now);
        customEventsEntity.setActive(true);
        CustomEventsEntity savedCustomEventsEntity = customEventRepository.save(customEventsEntity);
        CustomEventFieldEntity customEventFieldEntity;
        List<CustomEventFieldEntity> customEventFieldEntities = new ArrayList<>();

        for (CustomEventFieldDTO customEventFieldDTO : requestBody.getFields()) {
            customEventFieldEntity = new CustomEventFieldEntity();
            customEventFieldMapper.mapDTOToEntity(customEventFieldDTO, customEventFieldEntity);
            customEventFieldEntity.setCreatedOn(now);
            customEventFieldEntity.setCustomEventsEntity(savedCustomEventsEntity);
            customEventFieldEntity.setActive(true);
            customEventFieldEntities.add(customEventFieldEntity);
        }

        Iterable<CustomEventFieldEntity> savedFields = customEventFieldRepository.saveAll(customEventFieldEntities);
        List<CustomEventFieldDTO> savedFieldDTOs = new ArrayList<>();

        CustomEventDTO response = customEventMapper.mapEntityToDTO(savedCustomEventsEntity, CustomEventDTO.class);

        for (CustomEventFieldEntity entity : savedFields) {
            savedFieldDTOs.add(customEventFieldMapper.mapEntityToDTO(entity, CustomEventFieldDTO.class));
        }

        response.setFields(savedFieldDTOs);

        return response;
    }

    @Override
    public List<CustomEventDTO> findAllCustomEvents() {
        List<CustomEventsEntity> eventsEntities = customEventRepository.findAll();
        List<Integer> ids = eventsEntities.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<CustomEventDTO> customEventDTOS = new ArrayList<>();
        CustomEventDTO customEventDTO;

        List<CustomEventFieldEntity> fieldEntities = customEventFieldRepository.findByCustomEventsEntity_idInAndActiveTrue(ids);
        Map<Integer, List<CustomEventFieldDTO>> fieldMap = new HashMap<>();

        for (CustomEventFieldEntity entity : fieldEntities) {
            fieldMap.putIfAbsent(entity.getCustomEventsEntity().getId(), new ArrayList<>());

            fieldMap.get(entity.getCustomEventsEntity().getId()).add(customEventFieldMapper.mapEntityToDTO(entity, CustomEventFieldDTO.class));
        }

        for (CustomEventsEntity entity : eventsEntities) {
            customEventDTO = customEventMapper.mapEntityToDTO(entity, CustomEventDTO.class);

            customEventDTO.setFields(fieldMap.get(entity.getId()));
            customEventDTOS.add(customEventDTO);
        }

        return customEventDTOS;
    }

    @Override
    public CustomEventDTO findByCustomEventId(Integer id) {
        Optional<CustomEventsEntity> customEventsEntityOptional = customEventRepository.findById(id);

        if (!customEventsEntityOptional.isPresent()) {
            throw new ResourceNotFoundException("Custom Event", "id", id);
        }

        CustomEventsEntity customEventsEntity = customEventsEntityOptional.get();
        CustomEventDTO customEventDTO;

        Iterable<CustomEventFieldEntity> fieldEntities = customEventFieldRepository.findByCustomEventsEntity_idAndActiveTrue(id);
        Map<Integer, List<CustomEventFieldDTO>> fieldMap = new HashMap<>();

        for (CustomEventFieldEntity entity : fieldEntities) {
            fieldMap.putIfAbsent(entity.getCustomEventsEntity().getId(), new ArrayList<>());

            fieldMap.get(entity.getCustomEventsEntity().getId()).add(customEventFieldMapper.mapEntityToDTO(entity, CustomEventFieldDTO.class));
        }

        customEventDTO = customEventMapper.mapEntityToDTO(customEventsEntity, CustomEventDTO.class);

        customEventDTO.setFields(fieldMap.get(customEventsEntity.getId()));

        return customEventDTO;
    }

    @Override
    public CustomEventDTO deleteByCustomEventId(Integer id) {
        Optional<CustomEventsEntity> customEventsEntityOptional = customEventRepository.findById(id);

        if (!customEventsEntityOptional.isPresent()) {
            throw new ResourceNotFoundException("Custom Event", "id", id);
        }

        CustomEventsEntity customEventsEntity = customEventsEntityOptional.get();
        customEventsEntity.setActive(false);

        CustomEventDTO customEventDTO;

        Iterable<CustomEventFieldEntity> fieldEntities = customEventFieldRepository.findByCustomEventsEntity_idAndActiveTrue(id);
        Map<Integer, List<CustomEventFieldDTO>> fieldMap = new HashMap<>();

        for (CustomEventFieldEntity entity : fieldEntities) {
            fieldMap.putIfAbsent(entity.getCustomEventsEntity().getId(), new ArrayList<>());

            fieldMap.get(entity.getCustomEventsEntity().getId()).add(customEventFieldMapper.mapEntityToDTO(entity, CustomEventFieldDTO.class));
        }

        customEventDTO = customEventMapper.mapEntityToDTO(customEventsEntity, CustomEventDTO.class);

        customEventDTO.setFields(fieldMap.get(customEventsEntity.getId()));

        customEventRepository.save(customEventsEntity);

        return customEventDTO;
    }

    @Override
    public CustomEventDTO updateCustomEvent(CustomEventDTO requestBody) {
        if (requestBody.getEventCode() == null) {
            throw new BadRequestException("Missing Event Code");
        }

        if (requestBody.getId() == null) {
            throw new ResourceNotFoundException("Custom Event", "id", "");
        }

        Optional<CustomEventsEntity> customEventsEntityOptional = customEventRepository.findById(requestBody.getId());

        if (!customEventsEntityOptional.isPresent()) {
            throw new ResourceNotFoundException("Custom Event", "id", requestBody.getId());
        }

        ZonedDateTime now = TimeUtil.getCurrentUTCTime();
        CustomEventsEntity customEventsEntity = customEventsEntityOptional.get();
        customEventMapper.mapDTOToEntity(requestBody, customEventsEntity);
        customEventsEntity.setUpdatedOn(now);
        CustomEventsEntity savedCustomEventsEntity = customEventRepository.save(customEventsEntity);
        CustomEventFieldEntity customEventFieldEntity;
        List<CustomEventFieldEntity> customEventFieldEntities = new ArrayList<>();

        Iterable<CustomEventFieldEntity> oldCustomEventFieldEntities = customEventFieldRepository.findByCustomEventsEntity_idAndActiveTrue(requestBody.getId());

        for (CustomEventFieldEntity entity : oldCustomEventFieldEntities) {
            entity.setActive(false);
        }

        customEventFieldRepository.saveAll(oldCustomEventFieldEntities);

        for (CustomEventFieldDTO customEventFieldDTO : requestBody.getFields()) {
            customEventFieldEntity = new CustomEventFieldEntity();
            customEventFieldMapper.mapDTOToEntity(customEventFieldDTO, customEventFieldEntity);
            customEventFieldEntity.setCreatedOn(now);
            customEventFieldEntity.setCustomEventsEntity(savedCustomEventsEntity);
            customEventFieldEntity.setActive(true);
            customEventFieldEntities.add(customEventFieldEntity);
        }

        Iterable<CustomEventFieldEntity> savedFields = customEventFieldRepository.saveAll(customEventFieldEntities);
        List<CustomEventFieldDTO> savedFieldDTOs = new ArrayList<>();

        CustomEventDTO response = customEventMapper.mapEntityToDTO(savedCustomEventsEntity, CustomEventDTO.class);

        for (CustomEventFieldEntity entity : savedFields) {
            savedFieldDTOs.add(customEventFieldMapper.mapEntityToDTO(entity, CustomEventFieldDTO.class));
        }

        response.setFields(savedFieldDTOs);

        return response;
    }

    private List<String> checkAllMandatoryFields(List<Map<String, Object>> fields, Map<String, CustomEventFieldEntity> mandatoryStaticFields) {
        List<String> missingFields = new ArrayList<>();
        List<String> fieldNamesSent = new ArrayList<>();

        for (Map<String, Object> map : fields) {
            fieldNamesSent.addAll(map.keySet());
        }

        for (String mandatoryName : mandatoryStaticFields.keySet()) {
            if (fieldNamesSent.indexOf(mandatoryName) == -1) {
                missingFields.add(mandatoryName);
            }
        }

        return missingFields;
    }

    private Map<String, CustomEventsEntity> getStaticEventsAsMap () {
        Iterable<CustomEventsEntity> staticCustomEventEntities = customEventRepository.findByActiveTrue();
        Map<String, CustomEventsEntity> map = new HashMap<>();

        for (CustomEventsEntity entity : staticCustomEventEntities) {
            map.put(entity.getEventCode(), entity);
        }

        return map;
    }

    private Map<Integer, CustomEventsEntity> getStaticEventsAsMapById () {
        Iterable<CustomEventsEntity> staticCustomEventEntities = customEventRepository.findByActiveTrue();
        Map<Integer, CustomEventsEntity> map = new HashMap<>();

        for (CustomEventsEntity entity : staticCustomEventEntities) {
            map.put(entity.getId(), entity);
        }

        return map;
    }

    private Map<String, CustomEventFieldEntity> getMandatoryStaticEventFieldsAsMap (Iterable<CustomEventFieldEntity> staticEventFieldEntities) {
        Map<String, CustomEventFieldEntity> map = new HashMap<>();

        for (CustomEventFieldEntity entity : staticEventFieldEntities) {
            if (entity.getMandatory()) {
                map.put(entity.getFieldName(), entity);
            }

        }

        return map;
    }


    private Boolean validateFieldValue (String fieldName, Object fieldValue, Iterable<CustomEventFieldEntity> staticEvents) {
        Boolean valid = false;
        EventType eventType;

        for (CustomEventFieldEntity entity : staticEvents) {
            if (entity.getFieldName().equals(fieldName)) {
                eventType = entity.getType();

                switch (eventType) {
                    case intType:
                        if (fieldValue instanceof Integer) {
                            valid = true;
                        }
                        break;
                    case floatType:
                        if (fieldValue instanceof Double) {
                            valid = true;
                        }
                        break;
                    case stringType:
                        if (fieldValue instanceof String) {
                            valid = true;
                        }
                        break;
                    case booleanType:
                        if (fieldValue instanceof Boolean) {
                            valid = true;
                        }
                        break;
                    case dateType:
                        valid = isValidDate(fieldValue.toString(), "yyyy-MM-dd");
                        break;
                    case datetimeType:
                        valid = isValidDate(fieldValue.toString(), "yyyy-MM-dd HH:mm:ss");
                        break;
                }

                if (valid) {
                    break;
                }
            }
        }

        return valid;
    }

    private static boolean isValidDate(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(dateString.trim());
        } catch (ParseException pe) {
            return false;
        }

        return true;
    }
}
