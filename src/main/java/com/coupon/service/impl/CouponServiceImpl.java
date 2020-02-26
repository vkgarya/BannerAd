package com.coupon.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.coupon.auth.security.CurrentUserProvider;
import com.coupon.bean.CouponDTO;
import com.coupon.bean.CouponDataDTO;
import com.coupon.bean.CouponFileDTO;
import com.coupon.bean.CouponProductRestrictionDTO;
import com.coupon.bean.CouponStatus;
import com.coupon.bean.CouponUserRestrictionDTO;
import com.coupon.bean.EventFieldDTO;
import com.coupon.bean.LangValueDTO;
import com.coupon.bean.RuleCalendarDTO;
import com.coupon.bean.RuleCategoryDTO;
import com.coupon.bean.RuleEventDTO;
import com.coupon.bean.RuleOfferDTO;
import com.coupon.bean.RuleTransactionDTO;
import com.coupon.bean.jpa.CartDataEntity;
import com.coupon.bean.jpa.ConversionDataCouponMappingEntity;
import com.coupon.bean.jpa.ConversionDataEntity;
import com.coupon.bean.jpa.CouponCodeLanguageMappingEntity;
import com.coupon.bean.jpa.CouponDescriptionLanguageMappingEntity;
import com.coupon.bean.jpa.CouponEntity;
import com.coupon.bean.jpa.CouponFileMapEntity;
import com.coupon.bean.jpa.CouponProductRestrictionsEntity;
import com.coupon.bean.jpa.CouponUserRestrictionsEntity;
import com.coupon.bean.jpa.RuleCalendarMappingEntity;
import com.coupon.bean.jpa.RuleCategoryMappingEntity;
import com.coupon.bean.jpa.RuleEventEntity;
import com.coupon.bean.jpa.RuleEventFieldEntity;
import com.coupon.bean.jpa.RuleOfferMappingEntity;
import com.coupon.bean.jpa.RuleTransactionFieldsMappingEntity;
import com.coupon.bean.jpa.RuleTransactionMappingEntity;
import com.coupon.constants.CalenderType;
import com.coupon.constants.Condition;
import com.coupon.constants.Language;
import com.coupon.constants.Relation;
import com.coupon.constants.RestrictionType;
import com.coupon.event.bean.jpa.EventEntity;
import com.coupon.event.bean.jpa.EventFieldsEntity;
import com.coupon.event.repository.EventFieldRepository;
import com.coupon.event.repository.EventRepository;
import com.coupon.exceptions.ResourceNotFoundException;
import com.coupon.partner.constants.Status;
import com.coupon.repository.CartDataRepository;
import com.coupon.repository.ConversionDataCouponMappingRepository;
import com.coupon.repository.ConversionDataRepository;
import com.coupon.repository.CouponCodeLanguageMappingRepository;
import com.coupon.repository.CouponDescriptionLanguageMappingRepository;
import com.coupon.repository.CouponFileMapRepository;
import com.coupon.repository.CouponProductRestrictionsrepository;
import com.coupon.repository.CouponRepository;
import com.coupon.repository.CouponUserRestrictionsRepository;
import com.coupon.repository.RuleCalendarMappingRepository;
import com.coupon.repository.RuleCategoryMappingRepository;
import com.coupon.repository.RuleEventFieldRepository;
import com.coupon.repository.RuleEventRepository;
import com.coupon.repository.RuleOfferMappingRepository;
import com.coupon.repository.RuleTransactionFieldsMappingRepository;
import com.coupon.repository.RuleTransactionMappingRepository;
import com.coupon.service.CouponService;
import com.coupon.service.mapper.ServiceMapper;
import com.coupon.user.bean.jpa.PartnerEntity;
import com.coupon.user.bean.jpa.UserPartnerEntity;
import com.coupon.user.repository.PartnerRepository;
import com.coupon.user.repository.UserPartnerRepository;
import com.coupon.user.service.UserService;
import com.coupon.utils.CampaignUtil;
import com.coupon.utils.TimeUtil;
import com.coupon.utils.xlsx.XLSXException;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {
    @Resource
    private CouponRepository couponRepository;
    @Resource
    private RuleCalendarMappingRepository ruleCalendarMappingRepository;
    @Resource
    private CouponCodeLanguageMappingRepository couponCodeLanguageMappingRepository;
    @Resource
    private CouponDescriptionLanguageMappingRepository couponDescriptionLanguageMappingRepository;
    @Resource
    private RuleCategoryMappingRepository ruleCategoryMappingRepository;
    @Resource
    private RuleOfferMappingRepository ruleOfferMappingRepository;
    @Resource
    private RuleTransactionMappingRepository ruleTransactionMappingRepository;
    @Resource
    private RuleTransactionFieldsMappingRepository ruleTransactionFieldsMappingRepository;
    @Resource
    private CouponProductRestrictionsrepository couponProductRestrictionsrepository;
    @Resource
    private CouponUserRestrictionsRepository couponUserRestrictionsRepository;
    @Resource
    private RuleEventFieldRepository ruleEventFieldRepository;
    @Resource
    private RuleEventRepository ruleEventRepository;
    @Resource
    private ServiceMapper<RuleCategoryDTO, RuleCategoryMappingEntity> ruleCategoryMapper;
    @Resource
    private ServiceMapper<RuleCalendarDTO, RuleCalendarMappingEntity> ruleCalendarMapper;
    @Resource
    private ServiceMapper<RuleTransactionDTO, RuleTransactionMappingEntity> ruleTransactionMapper;
    @Resource
    private ServiceMapper<RuleOfferDTO, RuleOfferMappingEntity> ruleOfferMapper;
    @Resource
    private ServiceMapper<RuleEventDTO, RuleEventEntity> ruleEventMapper;
    @Resource
    private ServiceMapper<CouponDataDTO, CouponEntity> couponDataMapper;
    @Resource
    private ServiceMapper<EventFieldDTO, RuleEventFieldEntity> eventFieldMapper;
    @Resource
    private ServiceMapper<CouponFileDTO, CouponFileMapEntity> couponFileMapper;
    @Resource
    private CouponFileMapRepository couponFileMapRepository;
    @Resource
    private UserService userService;
    @Resource
    private UserPartnerRepository userPartnerRepository;
    @Resource
    private ConversionDataRepository conversionDataRepository;
    @Resource
    private ConversionDataCouponMappingRepository conversionDataCouponMappingRepository;
    @Resource
    private CartDataRepository cartDataRepository;
    @Resource
    private PartnerRepository partnerRepository;
    @Resource
    private EventRepository eventRepository;
    @Resource
    private EventFieldRepository eventFieldRepository;

    private static final String OFFER_FILE = "offers";
    private static final String PRODUCTS_EXCLUDE_FILE = "productsExcluded";
    private static final String PRODUCTS_INCLUDE_FILE = "productsIncluded";
    private static final String USERS_EXCLUDE_FILE = "usersExcluded";
    private static final String USERS_INCLUDE_FILE = "usersIncluded";

    @Transactional
    @Override
    public CouponDTO create(MultipartFile offerFile, MultipartFile productsIncluded, MultipartFile productsExcluded, MultipartFile usersIncluded, MultipartFile usersExcluded, CouponDataDTO data) throws IOException {
        CouponEntity couponEntity = new CouponEntity();

        couponEntity.setCreatedOn(TimeUtil.getCurrentUTCTime());

        CouponDTO response = this.save(offerFile, productsIncluded, productsExcluded, usersIncluded, usersExcluded, data, couponEntity);

        return response;
    }

    private CouponDTO save (MultipartFile offerFile, MultipartFile productsIncluded, MultipartFile productsExcluded, MultipartFile usersIncluded,
                            MultipartFile usersExcluded, CouponDataDTO data, CouponEntity couponEntity) throws IOException {
        List<RuleOfferDTO> offerRules = new ArrayList<>();
        Boolean multipleOffers;
        RuleOfferDTO ruleOffer, ruleOffer1;

        couponDataMapper.mapDTOToEntity(data, couponEntity);
        CouponProductRestrictionDTO couponProductExcludeDTO = null;
        CouponProductRestrictionDTO couponProductIncludeDTO = null;
        CouponProductRestrictionsEntity couponProductExcludeRestrictionsEntity = null;
        CouponProductRestrictionsEntity couponProductIncludeRestrictionsEntity = null;

        CouponUserRestrictionDTO couponUserExcludeDTO = null;
        CouponUserRestrictionDTO couponUserIncludeDTO = null;
        CouponUserRestrictionsEntity couponUserExcludeRestrictionsEntity = null;
        CouponUserRestrictionsEntity couponUserIncludeRestrictionsEntity = null;


        if (!data.getOfferRules().isEmpty()) {
            ruleOffer1 = data.getOfferRules().get(0);
            multipleOffers = ruleOffer1.getIsMultiple();
            offerRules = extractOfferRules(offerFile, multipleOffers);

            if (!offerRules.isEmpty()) {
                if (!multipleOffers) {
                    ruleOffer = offerRules.get(0);

                    ruleOffer.setOfferDescription(ruleOffer1.getOfferDescription());
                    ruleOffer.setOfferQuantity(ruleOffer1.getOfferQuantity());
                    ruleOffer.setBuyQuantity(ruleOffer1.getBuyQuantity());
                }
            } else {
                offerRules = data.getOfferRules();
            }
        }

        PartnerEntity partnerEntity = new PartnerEntity();

        if (couponEntity.getStatus() == null) {
            if (userService.hasAdminAccess()) {
                couponEntity.setStatus(Status.ACTIVE);
            } else {
                couponEntity.setStatus(Status.INACTIVE);
            }
        }

        CouponEntity savedCouponEntity = couponRepository.save(couponEntity);
        Integer id = savedCouponEntity.getId();
        CouponEntity tempEntity = new CouponEntity();
        tempEntity.setId(id);

        //saving calendar rules
        RuleCalendarMappingEntity ruleCalendarMappingEntity;
        List<RuleCalendarMappingEntity> ruleCalendarMappingEntities = new ArrayList<>();

        for (RuleCalendarDTO ruleCalendarDTO : data.getCalendarRules()) {
            ruleCalendarMappingEntity = new RuleCalendarMappingEntity(ruleCalendarDTO);
            ruleCalendarMappingEntity.setCouponEntity(tempEntity);

            ruleCalendarMappingEntities.add(ruleCalendarMappingEntity);
        }

        if (!ruleCalendarMappingEntities.isEmpty()) {
            ruleCalendarMappingRepository.saveAll(ruleCalendarMappingEntities);
        }

        //saving category rules
        RuleCategoryMappingEntity ruleCategoryMappingEntity;
        List<RuleCategoryMappingEntity> ruleCategoryMappingEntities = new ArrayList<>();

        for (RuleCategoryDTO ruleCategoryDTO : data.getCategoryRules()) {
            ruleCategoryMappingEntity = new RuleCategoryMappingEntity(ruleCategoryDTO);
            ruleCategoryMappingEntity.setCouponEntity(tempEntity);

            ruleCategoryMappingEntities.add(ruleCategoryMappingEntity);
        }

        if (!ruleCategoryMappingEntities.isEmpty()) {
            ruleCategoryMappingRepository.saveAll(ruleCategoryMappingEntities);
        }

        //saving offer rules
        RuleOfferMappingEntity ruleOfferMappingEntity;
        List<RuleOfferMappingEntity> ruleOfferMappingEntities = new ArrayList<>();

        for (RuleOfferDTO ruleOfferDTO : offerRules) {
            ruleOfferMappingEntity = new RuleOfferMappingEntity(ruleOfferDTO);
            ruleOfferMappingEntity.setCouponEntity(tempEntity);

            if (ruleOfferDTO.getBuySkus() != null && ruleOfferDTO.getOfferSkus() != null && ruleOfferDTO.getBuyQuantity() != null && ruleOfferDTO.getOfferQuantity() != null) {
                ruleOfferMappingEntities.add(ruleOfferMappingEntity);
            }
        }

        if (!ruleOfferMappingEntities.isEmpty()) {
            ruleOfferMappingRepository.saveAll(ruleOfferMappingEntities);
        }

        //saving transaction rules
        RuleTransactionMappingEntity ruleTransactionMappingEntity;
        RuleTransactionFieldsMappingEntity ruleTransactionFieldsMappingEntity;
        RuleTransactionMappingEntity savedRuleTransactionMappingEntity;
        List<RuleTransactionFieldsMappingEntity> ruleTransactionFieldsMappingEntities = new ArrayList<>();

        for (RuleTransactionDTO ruleTransactionDTO : data.getTransactionRules()) {
            ruleTransactionMappingEntity = new RuleTransactionMappingEntity();
            ruleTransactionMapper.mapDTOToEntity(ruleTransactionDTO, ruleTransactionMappingEntity);
            ruleTransactionMappingEntity.setCouponEntity(tempEntity);
            savedRuleTransactionMappingEntity = ruleTransactionMappingRepository.save(ruleTransactionMappingEntity);

            for (String key : ruleTransactionDTO.getFields().keySet()) {
                ruleTransactionFieldsMappingEntity = new RuleTransactionFieldsMappingEntity();
                ruleTransactionFieldsMappingEntity.setFieldName(key);
                ruleTransactionFieldsMappingEntity.setFieldValue(ruleTransactionDTO.getFields().get(key));
                ruleTransactionFieldsMappingEntity.setRuleTransactionMappingEntity(savedRuleTransactionMappingEntity);
                ruleTransactionFieldsMappingEntities.add(ruleTransactionFieldsMappingEntity);
            }
        }

        if (!ruleTransactionFieldsMappingEntities.isEmpty()) {
            ruleTransactionFieldsMappingRepository.saveAll(ruleTransactionFieldsMappingEntities);
        }

        //saving event rules
        RuleEventEntity ruleEventEntity;
        RuleEventFieldEntity ruleEventFieldEntity;
        RuleEventEntity savedRuleEventEntity;
        List<RuleEventFieldEntity> ruleEventFieldEntities = new ArrayList<>();

        for (RuleEventDTO ruleEventDTO : data.getEventRules()) {
            ruleEventEntity = new RuleEventEntity();
            ruleEventEntity.setEventId(ruleEventDTO.getEventId());
            ruleEventEntity.setRelation(ruleEventDTO.getRelation());
            ruleEventEntity.setCouponEntity(savedCouponEntity);

            savedRuleEventEntity = ruleEventRepository.save(ruleEventEntity);

            for (EventFieldDTO eventFieldDTO : ruleEventDTO.getFields()) {
                ruleEventFieldEntity = new RuleEventFieldEntity();
                eventFieldMapper.mapDTOToEntity(eventFieldDTO, ruleEventFieldEntity);
                ruleEventFieldEntity.setRuleEventEntity(savedRuleEventEntity);

                ruleEventFieldEntities.add(ruleEventFieldEntity);
            }
        }

        if (!ruleEventFieldEntities.isEmpty()) {
            ruleEventFieldRepository.saveAll(ruleEventFieldEntities);
        }

        //saving coupon product restrictions
        if (productsExcluded != null && !productsExcluded.isEmpty()) {
            couponProductExcludeDTO = this.getProductRestrictionDTO(productsExcluded, RestrictionType.exclusive);
            couponProductExcludeRestrictionsEntity = new CouponProductRestrictionsEntity(couponProductExcludeDTO);

            couponProductExcludeRestrictionsEntity.setCouponEntity(savedCouponEntity);
            couponProductRestrictionsrepository.save(couponProductExcludeRestrictionsEntity);
        }

        if (productsIncluded != null && !productsIncluded.isEmpty()) {
            couponProductIncludeDTO = this.getProductRestrictionDTO(productsIncluded, RestrictionType.inclusive);
            couponProductIncludeRestrictionsEntity = new CouponProductRestrictionsEntity(couponProductIncludeDTO);

            couponProductIncludeRestrictionsEntity.setCouponEntity(savedCouponEntity);
            couponProductRestrictionsrepository.save(couponProductIncludeRestrictionsEntity);
        }

        //saving coupon user restrictions
        if (usersExcluded != null && !usersExcluded.isEmpty()) {
            couponUserExcludeDTO = this.getUserRestrictionDTO(usersExcluded, RestrictionType.exclusive);
            couponUserExcludeRestrictionsEntity = new CouponUserRestrictionsEntity(couponUserExcludeDTO);

            couponUserExcludeRestrictionsEntity.setCouponEntity(savedCouponEntity);
            couponUserRestrictionsRepository.save(couponUserExcludeRestrictionsEntity);
        }

        if (usersIncluded != null && !usersIncluded.isEmpty()) {
            couponUserIncludeDTO = this.getUserRestrictionDTO(usersIncluded, RestrictionType.inclusive);
            couponUserIncludeRestrictionsEntity = new CouponUserRestrictionsEntity(couponUserIncludeDTO);

            couponUserIncludeRestrictionsEntity.setCouponEntity(savedCouponEntity);
            couponUserRestrictionsRepository.save(couponUserIncludeRestrictionsEntity);
        }

        //saving coupon codes
        CouponCodeLanguageMappingEntity couponCodeLanguageMappingEntity;
        List<CouponCodeLanguageMappingEntity> codes = new ArrayList<>();

        for(LangValueDTO langValueDTO: data.getCodes()) {
            couponCodeLanguageMappingEntity = new CouponCodeLanguageMappingEntity();
            couponCodeLanguageMappingEntity.setCouponCode(langValueDTO.getValue());
            couponCodeLanguageMappingEntity.setLanguage(langValueDTO.getLanguage());
            couponCodeLanguageMappingEntity.setCouponEntity(tempEntity);
            codes.add(couponCodeLanguageMappingEntity);
        }

        couponCodeLanguageMappingRepository.saveAll(codes);

        //saving coupon descriptions
        CouponDescriptionLanguageMappingEntity couponDescriptionLanguageMappingEntity;
        List<CouponDescriptionLanguageMappingEntity> descriptions = new ArrayList<>();

        for(LangValueDTO langValueDTO: data.getDescriptions()) {
            couponDescriptionLanguageMappingEntity = new CouponDescriptionLanguageMappingEntity();
            couponDescriptionLanguageMappingEntity.setCouponDesc(langValueDTO.getValue());
            couponDescriptionLanguageMappingEntity.setLanguage(langValueDTO.getLanguage());
            couponDescriptionLanguageMappingEntity.setCouponEntity(tempEntity);
            descriptions.add(couponDescriptionLanguageMappingEntity);
        }

        couponDescriptionLanguageMappingRepository.saveAll(descriptions);

        //save files
        List<CouponFileMapEntity> couponFileMapEntities = new ArrayList<>();

        if (offerFile != null && !offerFile.isEmpty()) {
            couponFileMapEntities.add(uploadCouponFile(offerFile, OFFER_FILE, tempEntity));
        }

        if (productsExcluded != null && !productsExcluded.isEmpty()) {
            couponFileMapEntities.add(uploadCouponFile(productsExcluded, PRODUCTS_EXCLUDE_FILE, tempEntity));
        }

        if (productsIncluded != null && !productsIncluded.isEmpty()) {
            couponFileMapEntities.add(uploadCouponFile(productsIncluded, PRODUCTS_INCLUDE_FILE, tempEntity));
        }

        if (usersIncluded != null && !usersIncluded.isEmpty()) {
            couponFileMapEntities.add(uploadCouponFile(usersIncluded, USERS_INCLUDE_FILE, tempEntity));
        }

        if (usersExcluded != null && !usersExcluded.isEmpty()) {
            couponFileMapEntities.add(uploadCouponFile(usersExcluded, USERS_EXCLUDE_FILE, tempEntity));
        }

        //saving existing files
        for (CouponFileDTO fileDTO : data.getFiles()) {
            CouponFileMapEntity couponFileMapEntity;
            couponFileMapEntity = new CouponFileMapEntity();
            couponFileMapEntity.setCouponEntity(couponEntity);
            couponFileMapEntity.setFileType(fileDTO.getFileType());

            couponFileMapEntity.setUrl(fileDTO.getUrl());

            couponFileMapEntities.add(couponFileMapEntity);
        }

        if (!couponFileMapEntities.isEmpty()) {
            couponFileMapRepository.saveAll(couponFileMapEntities);
        }

        CouponDTO response = new CouponDTO(savedCouponEntity);
        response.setCodes(data.getCodes());
        response.setDescriptions(data.getDescriptions());

        return response;
    }

    private CouponFileMapEntity uploadCouponFile(MultipartFile multipartFile, String type, CouponEntity couponEntity) {
        CouponFileMapEntity couponFileMapEntity;
        couponFileMapEntity = new CouponFileMapEntity();
        couponFileMapEntity.setCouponEntity(couponEntity);
        couponFileMapEntity.setFileType(type);
        String url = CampaignUtil.uploadFile(multipartFile);

        couponFileMapEntity.setUrl(url);

        return couponFileMapEntity;
    }

    @Override
    public List<CouponDTO> getActiveCoupons() {
        List<CouponEntity> activeCoupons = new ArrayList<>();
        List<Integer> partnerIds = new ArrayList<>();
        List<String> status= new ArrayList<>();

        status.add(Status.ACTIVE);
        status.add(Status.INACTIVE);
        status.add(Status.OFFLINE);

        if (userService.hasAdminAccess()) {
            activeCoupons = couponRepository.findAllValid(TimeUtil.getCurrentUTCTime(), status);
        } else {
            List<UserPartnerEntity> userPartnerEntities = userPartnerRepository.findByUserId(CurrentUserProvider.getUserId());

            for (UserPartnerEntity entity : userPartnerEntities) {
                partnerIds.add(entity.getPartner().getId());
            }


            activeCoupons = couponRepository.findAllValidCouponsByUserPartnerIds(TimeUtil.getCurrentUTCTime(), status, partnerIds);
        }

        return convertCouponEntitiesToCouponDTOs(activeCoupons);
    }

    private List<CouponDTO> convertCouponEntitiesToCouponDTOs(List<CouponEntity> couponEntities) {
        List<Integer> couponIds =couponEntities.stream().map(ac -> ac.getId()).collect(Collectors.toList());
        List<CouponDTO> couponDTOS = new ArrayList<>();
        List<CouponCodeLanguageMappingEntity> couponCodes = couponCodeLanguageMappingRepository.findByCouponEntity_idIn(couponIds);
        List<CouponDescriptionLanguageMappingEntity> coupondescs = couponDescriptionLanguageMappingRepository.findByCouponEntity_idIn(couponIds);
        Map<Integer, List<LangValueDTO>> couponCodeMap = new HashMap<>();
        Map<Integer, List<LangValueDTO>> couponDescMap = new HashMap<>();
        CouponDTO couponDTO;
        LangValueDTO langValueDTO;
        Integer id;

        for (CouponCodeLanguageMappingEntity entity : couponCodes) {
            id = entity.getCouponEntity().getId();
            couponCodeMap.putIfAbsent(id, new ArrayList<>());
            langValueDTO = new LangValueDTO(entity.getLanguage(), entity.getCouponCode());

            couponCodeMap.get(id).add(langValueDTO);
        }

        for (CouponDescriptionLanguageMappingEntity entity : coupondescs) {
            id = entity.getCouponEntity().getId();
            couponDescMap.putIfAbsent(id, new ArrayList<>());
            langValueDTO = new LangValueDTO(entity.getLanguage(), entity.getCouponDesc());

            couponDescMap.get(id).add(langValueDTO);
        }

        for (CouponEntity entity : couponEntities) {
            couponDTO = new CouponDTO(entity);
            couponDTO.setCodes(couponCodeMap.get(entity.getId()));
            couponDTO.setDescriptions(couponDescMap.get(entity.getId()));
            couponDTOS.add(couponDTO);
        }

        return couponDTOS;
    }

    @Override
    public CouponDataDTO getCouponDetails(Integer id) {
        Optional<CouponEntity> couponEntityOptional = couponRepository.findById(id);

        if (!couponEntityOptional.isPresent()) {
            throw new ResourceNotFoundException("Coupon", "id", id);
        }

        CouponEntity couponEntity = couponEntityOptional.get();
        CouponDataDTO response = couponDataMapper.mapEntityToDTO(couponEntity, CouponDataDTO.class);
        List<CouponCodeLanguageMappingEntity> couponCodes = couponCodeLanguageMappingRepository.findByCouponEntity_id(id);
        List<CouponDescriptionLanguageMappingEntity> couponDescs = couponDescriptionLanguageMappingRepository.findByCouponEntity_id(id);
        List<RuleCategoryMappingEntity> ruleCategoryMappingEntities = ruleCategoryMappingRepository.findByCouponEntity_id(id);
        List<RuleCalendarMappingEntity> ruleCalendarMappingEntities = ruleCalendarMappingRepository.findByCouponEntity_id(id);
        List<RuleTransactionMappingEntity> ruleTransactionMappingEntities = ruleTransactionMappingRepository.findByCouponEntity_id(id);
        List<RuleOfferMappingEntity> ruleOfferMappingEntityList = ruleOfferMappingRepository.findByCouponEntity_id(id);
        List<RuleEventEntity> ruleEventEntities = ruleEventRepository.findByCouponEntity_id(id);
        List<CouponProductRestrictionsEntity> couponProductRestrictionsEntities = couponProductRestrictionsrepository.findByCouponEntity_id(id);
        List<CouponFileMapEntity> fileMapEntities = couponFileMapRepository.findByCouponEntity_id(couponEntity.getId());
        List<LangValueDTO> couponCodeDTOs = new ArrayList<>();
        List<LangValueDTO> couponDescDTOs = new ArrayList<>();
        List<CouponFileDTO> files = new ArrayList<>();
        LangValueDTO langValueDTO;
        Map<Language, String> languageMap;

        for (CouponCodeLanguageMappingEntity entity : couponCodes) {
            langValueDTO = new LangValueDTO(entity.getLanguage(), entity.getCouponCode());
            couponCodeDTOs.add(langValueDTO);
        }

        for (CouponDescriptionLanguageMappingEntity entity : couponDescs) {
            langValueDTO = new LangValueDTO(entity.getLanguage(), entity.getCouponDesc());

            couponDescDTOs.add(langValueDTO);
        }

        for (CouponFileMapEntity entity : fileMapEntities) {
            files.add(couponFileMapper.mapEntityToDTO(entity, CouponFileDTO.class));
        }

        response.setCodes(couponCodeDTOs);
        response.setDescriptions(couponDescDTOs);
        response.setFiles(files);

        //converting category rules
        List<RuleCategoryDTO> ruleCategoryDTOS = new ArrayList<>();
        for (RuleCategoryMappingEntity entity : ruleCategoryMappingEntities) {
            ruleCategoryDTOS.add(ruleCategoryMapper.mapEntityToDTO(entity, RuleCategoryDTO.class));
        }

        response.setCategoryRules(ruleCategoryDTOS);

        //converting calendar rules
        List<RuleCalendarDTO> ruleCalendarDTOS = new ArrayList<>();
        for (RuleCalendarMappingEntity entity : ruleCalendarMappingEntities) {
            ruleCalendarDTOS.add(ruleCalendarMapper.mapEntityToDTO(entity, RuleCalendarDTO.class));
        }

        response.setCalendarRules(ruleCalendarDTOS);

        //converting event rules
        List<RuleEventDTO> ruleEventDTOS = new ArrayList<>();
        List<EventFieldDTO> fields;
        List<RuleEventFieldEntity> ruleEventFieldEntities;
        RuleEventDTO ruleEventDTO;
        for (RuleEventEntity entity : ruleEventEntities) {
            fields = new ArrayList<>();
            ruleEventFieldEntities = ruleEventFieldRepository.findByRuleEventEntity_id(entity.getId());

            for (RuleEventFieldEntity entity1 : ruleEventFieldEntities) {
                fields.add(eventFieldMapper.mapEntityToDTO(entity1, EventFieldDTO.class));
            }

            ruleEventDTO = ruleEventMapper.mapEntityToDTO(entity, RuleEventDTO.class);
            ruleEventDTO.setFields(fields);
            ruleEventDTOS.add(ruleEventDTO);
        }

        response.setEventRules(ruleEventDTOS);

        //converting offer rules
        List<RuleOfferDTO> ruleOfferDTOS = new ArrayList<>();
        for (RuleOfferMappingEntity entity : ruleOfferMappingEntityList) {
            ruleOfferDTOS.add(ruleOfferMapper.mapEntityToDTO(entity, RuleOfferDTO.class));
        }

        response.setOfferRules(ruleOfferDTOS);

        //converting transaction rules
        List<RuleTransactionDTO> ruleTransactionDTOS = new ArrayList<>();
        List<RuleTransactionFieldsMappingEntity> ruleTransactionFieldsMappingEntities;
        RuleTransactionDTO ruleTransactionDTO;
        Map<String, String> transactionFields;

        for (RuleTransactionMappingEntity entity : ruleTransactionMappingEntities) {
            ruleTransactionFieldsMappingEntities = ruleTransactionFieldsMappingRepository.findByRuleTransactionMappingEntity_id(entity.getId());
            transactionFields = new HashMap<>();
            ruleTransactionDTO = ruleTransactionMapper.mapEntityToDTO(entity, RuleTransactionDTO.class);

            for (RuleTransactionFieldsMappingEntity ruleTransactionFieldsMappingEntity : ruleTransactionFieldsMappingEntities) {
                transactionFields.put(ruleTransactionFieldsMappingEntity.getFieldName(), ruleTransactionFieldsMappingEntity.getFieldValue());
            }

            ruleTransactionDTO.setFields(transactionFields);
            ruleTransactionDTOS.add(ruleTransactionDTO);
        }

        response.setTransactionRules(ruleTransactionDTOS);

        return response;
    }

    @Override
    public void delete(Integer id) {
        Optional<CouponEntity> couponEntityOptional = couponRepository.findById(id);

        if (!couponEntityOptional.isPresent()) {
            throw new ResourceNotFoundException("Coupon", "id", id);
        }

        CouponEntity entity = couponEntityOptional.get();
        entity.setStatus(Status.DELETED);
        couponRepository.save(entity);
    }

    @Override
    public CouponDTO edit(MultipartFile offerFile, MultipartFile productsIncluded, MultipartFile productsExcluded, MultipartFile usersIncluded, MultipartFile usersExcluded, CouponDataDTO data) throws IOException {
        CouponEntity couponEntity = new CouponEntity();
        couponDataMapper.mapDTOToEntity(data, couponEntity);
        couponEntity.setUpdatedOn(TimeUtil.getCurrentUTCTime());
        Integer id = data.getId();
        couponEntity.setId(id);

        Optional<CouponEntity> couponEntityOptional = couponRepository.findById(id);

        if (!couponEntityOptional.isPresent()) {
            throw new ResourceNotFoundException("Coupon", "id", id);
        }

        List<CouponCodeLanguageMappingEntity> couponCodeLanguageMappingEntities = couponCodeLanguageMappingRepository.findByCouponEntity_id(id);
        List<CouponDescriptionLanguageMappingEntity> couponDescriptionLanguageMappingEntities = couponDescriptionLanguageMappingRepository.findByCouponEntity_id(id);
        List<CouponFileMapEntity> couponFileMapEntities = couponFileMapRepository.findByCouponEntity_id(id);
        List<RuleCalendarMappingEntity> ruleCalendarMappingEntities = ruleCalendarMappingRepository.findByCouponEntity_id(id);
        List<RuleCategoryMappingEntity> ruleCategoryMappingEntities = ruleCategoryMappingRepository.findByCouponEntity_id(id);
        List<RuleEventEntity> ruleEventEntities = ruleEventRepository.findByCouponEntity_id(id);
        List<RuleOfferMappingEntity> ruleOfferMappingEntities = ruleOfferMappingRepository.findByCouponEntity_id(id);
        List<RuleTransactionMappingEntity> ruleTransactionMappingEntities = ruleTransactionMappingRepository.findByCouponEntity_id(id);
        List<CouponProductRestrictionsEntity> couponProductRestrictionsEntities = couponProductRestrictionsrepository.findByCouponEntity_id(id);
        List<CouponUserRestrictionsEntity> couponUserRestrictionsEntities = couponUserRestrictionsRepository.findByCouponEntity_id(id);

        couponCodeLanguageMappingRepository.deleteAll(couponCodeLanguageMappingEntities);
        couponDescriptionLanguageMappingRepository.deleteAll(couponDescriptionLanguageMappingEntities);
        couponFileMapRepository.deleteAll(couponFileMapEntities);
        ruleCalendarMappingRepository.deleteAll(ruleCalendarMappingEntities);
        ruleCategoryMappingRepository.deleteAll(ruleCategoryMappingEntities);
        ruleEventRepository.deleteAll(ruleEventEntities);
        ruleOfferMappingRepository.deleteAll(ruleOfferMappingEntities);
        ruleTransactionMappingRepository.deleteAll(ruleTransactionMappingEntities);
        couponProductRestrictionsrepository.deleteAll(couponProductRestrictionsEntities);
        couponUserRestrictionsRepository.deleteAll(couponUserRestrictionsEntities);

        return this.save(offerFile, productsIncluded, productsExcluded, usersIncluded, usersExcluded, data, couponEntity);
    }

    private List<RuleOfferDTO> extractOfferRules(MultipartFile file, Boolean isMultiple) throws IOException {
        List<RuleOfferDTO> offerRules = new ArrayList<>();
        List<String> buySkuList = new ArrayList<>();
        List<String> offerSkuList = new ArrayList<>();
        String buySku, offerSku;
        Integer buyQuantity, offerQuantity;
        RuleOfferDTO ruleOffer;

        if (file != null && !file.isEmpty()) {
            try {
                XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
                XSSFSheet sheet = wb.getSheetAt(0);
                XSSFRow row;

                for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                    row = sheet.getRow(j);
                    if (row.getCell(0) == null) {
                        continue;
                    }

                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    buySku = row.getCell(0).getStringCellValue();

                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    offerSku = row.getCell(2).getStringCellValue();

                    if (isMultiple) {
                        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        buyQuantity = Integer.parseInt(row.getCell(1).getStringCellValue());

                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        offerQuantity = Integer.parseInt(row.getCell(3).getStringCellValue());

                        row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                        String description = row.getCell(4).getStringCellValue();

                        ruleOffer = new RuleOfferDTO();
                        ruleOffer.setBuyQuantity(buyQuantity);
                        ruleOffer.setBuySkus(buySku);
                        ruleOffer.setOfferQuantity(offerQuantity);
                        ruleOffer.setOfferSkus(offerSku);
                        ruleOffer.setOfferDescription(description);

                        offerRules.add(ruleOffer);
                    } else {
                        buySkuList.add(buySku);
                        offerSkuList.add(offerSku);
                    }
                }

                if (!isMultiple) {
                    ruleOffer = new RuleOfferDTO();
                    ruleOffer.setBuySkus(String.join(",", buySkuList));
                    ruleOffer.setOfferSkus(String.join(",", offerSkuList));
                    offerRules.add(ruleOffer);
                }
            } catch (XLSXException e) {
                throw new XLSXException("Error parsing file");
            }
        }

        return offerRules;
    }

    private Integer convertHourStringToMs (String str) {
        String[] tokens = str.split(":");
        int hoursToMs = Integer.parseInt(tokens[0]) * 3600000;
        int secondsToMs = 0;
        int minutesToMs = 0;

        if (tokens.length > 1) {
            minutesToMs = Integer.parseInt(tokens[1]) * 60000;
        }

        if (tokens.length > 2) {
            secondsToMs = Integer.parseInt(tokens[2]) * 1000;
        }

        return secondsToMs + minutesToMs + hoursToMs;
    }

    @Override
    public Boolean validateCalendarRule(List<RuleCalendarMappingEntity> ruleCalendarMappingEntities) {
        Relation relation = ruleCalendarMappingEntities.get(0).getRelation();
        ZonedDateTime now = TimeUtil.getCurrenTimeByZone("Asia/Dubai");
        Boolean valid = false, tempValid, previousValid = true;
        CalenderType calenderType;

        Integer date = now.getDayOfMonth();
        Integer hour = now.getHour();
        Integer minute = now.getMinute();
        Integer second = now.getSecond();
        Integer week = now.getDayOfWeek().getValue();
        Integer month = now.getMonthValue();
        Integer currentHrMs = convertHourStringToMs("" + hour + ":" + minute + ":" + second);
        String start, end;
        Integer startInt, endInt;
        List<String> split;

        for (RuleCalendarMappingEntity rule : ruleCalendarMappingEntities) {
            calenderType = rule.getCalType();
            start = rule.getTypeFrom();
            end = rule.getTypeTo();
            tempValid = false;

            switch (calenderType) {
                case hour:
                    startInt = convertHourStringToMs(start);
                    endInt = convertHourStringToMs(end);
                    tempValid = (startInt < currentHrMs) && (endInt > currentHrMs);
                    break;

                case date:
                    if (end != null) {
                        startInt = Integer.parseInt(start);
                        endInt = Integer.parseInt(end);
                        tempValid = (startInt <= date) && (endInt >= date);
                    } else {
                        split = Arrays.asList(start.split(","));
                        tempValid = split.contains(date + "");
                    }

                    break;

                case day:
                    split = Arrays.asList(start.split(","));
                    tempValid = split.contains(week + "");
                    break;

                case month:
                    split = Arrays.asList(start.split(","));
                    tempValid = split.contains(month + "");
                    break;

                case dateTime:
                    ZonedDateTime zonedDateTimeStart = ZonedDateTime.parse(start);
                    ZonedDateTime zonedDateTimeEnd = ZonedDateTime.parse(end);
                    tempValid = now.isBefore(zonedDateTimeEnd) && now.isAfter(zonedDateTimeStart);
            }

            if ((relation.equals(Relation.or) || relation.equals(Relation.na)) && tempValid) {
                valid = true;
            }

            previousValid = previousValid && tempValid;

            if (valid) {
                previousValid = true;
                break;
            }
        }

        return valid || previousValid;
    }

    @Override
    public List<Integer> filterInvalidCalendarCoupons(Map<Integer, CouponEntity> couponIdCouponMap) {
        List<Integer> invalidCoupons = new ArrayList<>();
        List<Integer> couponIds = new ArrayList<>(couponIdCouponMap.keySet());

        Iterable<RuleCalendarMappingEntity> rules = ruleCalendarMappingRepository.findByCouponEntityIdIn(couponIds);
        Map<Integer, List<RuleCalendarMappingEntity>> couponIdRuleMap = new HashMap<>();
        Integer couponId;

        for (RuleCalendarMappingEntity rule : rules) {
            couponId = rule.getCouponEntity().getId();

            if (!couponIdRuleMap.containsKey(couponId)) {
                couponIdRuleMap.put(couponId, new ArrayList<>());
            }

            couponIdRuleMap.get(couponId).add(rule);
        }

        for (Integer coupId : couponIdRuleMap.keySet()) {
            if (!validateCalendarRule(couponIdRuleMap.get(coupId))) {
                invalidCoupons.add(coupId);
            }
        }

        return invalidCoupons;
    }

    @Override
    public List<CouponDTO> findCouponsByUserId(Integer id) {
        List<String> status= new ArrayList<>();

        status.add(Status.ACTIVE);
        status.add(Status.INACTIVE);

        List<CouponEntity> allCoupons = couponRepository.findAllValid(TimeUtil.getCurrentUTCTime(), status);
        List<CouponEntity> validCoupons = new ArrayList<>();
        Map<Integer, CouponEntity> couponIdCouponMap = new HashMap<>();

        for (CouponEntity entity : allCoupons) {
            couponIdCouponMap.put(entity.getId(), entity);
        }

        List<Integer> invalidCalenderCouponIds = filterInvalidCalendarCoupons(couponIdCouponMap);

        for (CouponEntity entity : allCoupons) {
            if (!invalidCalenderCouponIds.contains(entity.getId())) {
                validCoupons.add(entity);
            }
        }

        return convertCouponEntitiesToCouponDTOs(validCoupons);
    }

    private CouponProductRestrictionDTO getProductRestrictionDTO(MultipartFile file, RestrictionType type) throws IOException {
        CouponProductRestrictionDTO couponProductRestrictionsDTO = new CouponProductRestrictionDTO();
        List<String> skuList = new ArrayList<>();
        String skuListString;

        if (file != null && !file.isEmpty()) {
            try {
                XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
                XSSFSheet sheet = wb.getSheetAt(0);
                XSSFRow row;

                for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                    row = sheet.getRow(j);
                    if (row.getCell(0) == null) {
                        continue;
                    }

                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    skuList.add(row.getCell(0).getStringCellValue());
                }

                skuListString = String.join(",", skuList);

                couponProductRestrictionsDTO.setType(type);
                couponProductRestrictionsDTO.setSkus(skuListString);
            } catch (XLSXException e) {
                throw new XLSXException("Error parsing file");
            }
        }

        return couponProductRestrictionsDTO;
    }

    private CouponUserRestrictionDTO getUserRestrictionDTO(MultipartFile file, RestrictionType type) throws IOException {
        CouponUserRestrictionDTO couponUserRestrictionDTO = new CouponUserRestrictionDTO();
        List<String> skuList = new ArrayList<>();
        String skuListString;

        if (file != null && !file.isEmpty()) {
            try {
                XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
                XSSFSheet sheet = wb.getSheetAt(0);
                XSSFRow row;

                for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                    row = sheet.getRow(j);
                    if (row.getCell(0) == null) {
                        continue;
                    }

                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    skuList.add(row.getCell(0).getStringCellValue());
                }

                skuListString = String.join(",", skuList);

                couponUserRestrictionDTO.setType(type);
                couponUserRestrictionDTO.setEmails(skuListString);
            } catch (XLSXException e) {
                throw new XLSXException("Error parsing file");
            }
        }

        return couponUserRestrictionDTO;
    }

    @Override
    public void updateStatus(CouponStatus data) {
        Optional<CouponEntity> couponEntityOptional = couponRepository.findById(data.getId());

        if (!couponEntityOptional.isPresent()) {
            throw new ResourceNotFoundException("Coupon", "id", data.getId());
        }

        CouponEntity entity = couponEntityOptional.get();
        entity.setStatus(data.getStatus());
        couponRepository.save(entity);
    }

    @Override
    public List<CouponDTO> getValidCoupons(String userId, String category) {
        List<String> status= new ArrayList<>();

        status.add(Status.ACTIVE);
        status.add(Status.INACTIVE);

        ZonedDateTime now = TimeUtil.getCurrentUTCTime();
        Iterable<CouponEntity> allCoupons = couponRepository.findAllValid(now, status);

        Map<Integer, CouponEntity> couponIdCouponMap = new HashMap<>();

        for (CouponEntity entity : allCoupons) {
            couponIdCouponMap.put(entity.getId(), entity);
        }

        List<Integer> invalidCategoryCoupons = new ArrayList<>();
        List<Integer> couponIds = new ArrayList<>(couponIdCouponMap.keySet());

        List<Integer> invalidCalendarCoupons = filterInvalidCalendarCoupons(couponIdCouponMap);
        List<Integer> invalidUserbasedCoupons = getInvalidUserbasedCoupons(allCoupons, userId);
        List<Integer> invalidReferrerCoupons = getInvalidReferrerCoupons(allCoupons, userId);
        List<Integer> invalidPartnerCoupons = getInvalidPartnerCoupons(allCoupons, userId);

        if (category != null)  {
            invalidCategoryCoupons = filterInvalidCategoryCoupons(category, couponIdCouponMap);
        }

        List<CouponEntity> validCoupons = new ArrayList<>();

        for (Integer couponId : couponIds) {
            if (!(invalidCalendarCoupons.contains(couponId)
                    || invalidCategoryCoupons.contains(couponId)
                    || invalidUserbasedCoupons.contains(couponId)
                    || invalidReferrerCoupons.contains(couponId)
                    || invalidPartnerCoupons.contains(couponId))
            ) {
                validCoupons.add(couponIdCouponMap.get(couponId));
            }
        }

        validCoupons = filterByUserLimit(validCoupons, userId);

        return convertCouponEntitiesToCouponDTOs(validCoupons);
    }

    @Override
    public List<Integer> getInvalidTransactionBasedCoupons(Map<Integer, CouponEntity> couponIdCouponMap, String userId) {
        List<Integer> couponIds = new ArrayList<>(couponIdCouponMap.keySet());
        Map<Integer, List<RuleTransactionMappingEntity>> entityMap = new HashMap<>();
        Map<Integer, List<RuleTransactionFieldsMappingEntity>> fieldsMap = new HashMap<>();
        List<Integer> invalidIds = new ArrayList<>();

        List<RuleTransactionMappingEntity> ruleTransactionMappingEntities = ruleTransactionMappingRepository.findByCouponEntity_idIn(couponIds);
        List<Integer> ruleTransactionIds = ruleTransactionMappingEntities.stream().map(ac -> ac.getId()).collect(Collectors.toList());
        List<RuleTransactionFieldsMappingEntity> ruleTransactionFieldsMappingEntities = ruleTransactionFieldsMappingRepository.findByRuleTransactionMappingEntity_idIn(ruleTransactionIds);
        Integer id;
        List<ConversionDataEntity> conversions = conversionDataRepository.findByUserIdAndStatus(userId, com.coupon.constants.Status.success);
        List<Integer> conversionIds = conversions.stream().map(ac -> ac.getCartDataEntity().getId()).collect(Collectors.toList());
        List<CartDataEntity> cartDataEntities = cartDataRepository.findByIdIn(conversionIds);
        List<ConversionDataEntity> validConversions;
        Map<Integer, CartDataEntity> cartIdMap = new HashMap<>();
        List<RuleTransactionMappingEntity> ruleTransactionMappingEntities1;
        Map<String, String> fieldMap;
        String condition;
        Double amount;
        Boolean valid;

        for (CartDataEntity entity : cartDataEntities) {
            cartIdMap.put(entity.getId(), entity);
        }

        for (RuleTransactionMappingEntity entity : ruleTransactionMappingEntities) {
            id = entity.getCouponEntity().getId();
            entityMap.putIfAbsent(id, new ArrayList<>());
            entityMap.get(id).add(entity);
        }

        for (RuleTransactionFieldsMappingEntity entity : ruleTransactionFieldsMappingEntities) {
            id = entity.getRuleTransactionMappingEntity().getId();
            fieldsMap.putIfAbsent(id, new ArrayList<>());
            fieldsMap.get(id).add(entity);
        }

        for (Integer key : entityMap.keySet()) {
            ruleTransactionMappingEntities1 = entityMap.get(key);
            valid = true;

            for (RuleTransactionMappingEntity entity : ruleTransactionMappingEntities1) {
                validConversions = new ArrayList<>();
                List<RuleTransactionFieldsMappingEntity> fields = fieldsMap.get(entity.getId());
                fieldMap = new HashMap<>();

                for (RuleTransactionFieldsMappingEntity e : fields) {
                    fieldMap.put(e.getFieldName(), e.getFieldValue());
                }

                for (ConversionDataEntity conversionDataEntity : conversions) {
                    if (entity.getTypeFrom() != null && entity.getTypeTo() != null &&
                        conversionDataEntity.getCreatedOn().isAfter(entity.getTypeFrom()) &&
                        conversionDataEntity.getCreatedOn().isBefore(entity.getTypeTo())){
                        if (fieldMap.get("amount") != null && fieldMap.get("condition") != null) {
                            condition = fieldMap.get("condition");
                            amount = Double.parseDouble(fieldMap.get("amount"));
                            CartDataEntity cartDataEntity = cartIdMap.get(conversionDataEntity.getCartDataEntity().getId());
                            switch (Condition.valueOf(condition)) {
                            case gt:
                                if (cartDataEntity.getInvoiceAmount() > amount) {
                                    validConversions.add(conversionDataEntity);
                                }

                                break;
                            case lt:
                                if (cartDataEntity.getInvoiceAmount() < amount) {
                                    validConversions.add(conversionDataEntity);
                                }

                                break;

                            case eq:
                                if (cartDataEntity.getInvoiceAmount().equals(amount)) {
                                    validConversions.add(conversionDataEntity);
                                }

                                break;
                            }
                        } else {
                            validConversions.add(conversionDataEntity);
                        }
                    }
                }

                if (entity.getTransactionNumber() != null) {
                    String[] split = entity.getTransactionNumber().split(",");
                    int[] splitInts = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
                    valid = false;

                    for (int i = 0; i < splitInts.length; i += 1) {
                        if (splitInts[i] == validConversions.size() + 1) {
                            valid = true;
                        }
                    }
                }

            }

            if (!valid) {
                invalidIds.add(key);
            }
        }

        return invalidIds;
    }

    @Override
    public List<Integer> getInvalidEventBasedCoupons(Map<Integer, CouponEntity> couponIdCouponMap, String userId) {
        List<Integer> couponIds = new ArrayList<>(couponIdCouponMap.keySet());
        Map<Integer, List<RuleEventEntity>> entityMap = new HashMap<>();
        Map<Integer, List<RuleEventFieldEntity>> fieldsMap = new HashMap<>();
        Map<Integer, EventEntity> eventMap = new HashMap<>();
        Map<Integer, Map<String, String>> eventFieldMap = new HashMap<>();
        List<Integer> invalidIds = new ArrayList<>();
        Map<String, String> eventFieldsMapByEventId;

        List<RuleEventEntity> ruleEventEntities = ruleEventRepository.findByCouponEntity_idIn(couponIds);
        List<Integer> ruleEventIds = ruleEventEntities.stream().map(ac -> ac.getId()).collect(Collectors.toList());
        List<RuleEventFieldEntity> ruleEventFieldEntities = ruleEventFieldRepository.findByRuleEventEntity_idIn(ruleEventIds);
        List<EventEntity> userEvents = eventRepository.findByUserId(userId);
        List<Integer> userEventIds = userEvents.stream().map(ac -> ac.getId()).collect(Collectors.toList());
        List<EventFieldsEntity> eventFieldsEntities = eventFieldRepository.findByEventEntity_idIn(userEventIds);
        Integer id;
        Boolean valid;
        List<RuleEventEntity> ruleEventEntities1;
        List<RuleEventFieldEntity> ruleEventFieldEntities1;

        for (EventEntity entity : userEvents) {
            eventMap.put(entity.getCustomEventId(), entity);
        }

        for (EventFieldsEntity entity : eventFieldsEntities) {
            id = entity.getEventEntity().getId();
            eventFieldMap.putIfAbsent(id, new HashMap<>());
            eventFieldMap.get(id).put(entity.getFieldName(), entity.getFieldValue());
        }

        for (RuleEventEntity entity : ruleEventEntities) {
            id = entity.getCouponEntity().getId();
            entityMap.putIfAbsent(id, new ArrayList<>());
            entityMap.get(id).add(entity);
        }

        for (RuleEventFieldEntity entity : ruleEventFieldEntities) {
            id = entity.getRuleEventEntity().getId();
            fieldsMap.putIfAbsent(id, new ArrayList<>());
            fieldsMap.get(id).add(entity);
        }

        for (Integer key : entityMap.keySet()) {
            ruleEventEntities1 = entityMap.get(key);
            valid = true;

            for (RuleEventEntity entity : ruleEventEntities1) {
                if (!eventMap.containsKey(entity.getEventId())) {
                    invalidIds.add(key);
                    break;
                } else {
                    eventFieldsMapByEventId = eventFieldMap.get(entity.getEventId());
                    ruleEventFieldEntities1 = fieldsMap.get(entity.getId());

                    for (RuleEventFieldEntity entity1 : ruleEventFieldEntities1) {
                        if (!eventFieldsMapByEventId.containsKey(entity1.getKey()) || !validRule(eventFieldsMapByEventId.get(entity1.getKey()), entity1)) {
                            invalidIds.add(key);
                            valid = false;
                            break;
                        }
                    }

                    if (!valid) {
                        break;
                    }
                }
            }
        }
        return invalidIds;
    }

    private boolean validRule(String value, RuleEventFieldEntity entity) {
        boolean valid = true;
        try {
            switch(entity.getType()) {
            case intType:
                Integer intValue = Integer.parseInt(value);
                Integer intRuleValue = Integer.parseInt(entity.getValue());

                if (entity.getCondition() == Condition.eq) {
                    valid = intValue.equals(intRuleValue);
                } else if (entity.getCondition() == Condition.gt) {
                    valid = intValue > intRuleValue;
                } else if (entity.getCondition() == Condition.lt) {
                    valid = intValue < intRuleValue;
                } else {
                    valid = false;
                }

                break;
            case floatType:
                Double doubleValue = Double.parseDouble(value);
                Double doubleRuleValue = Double.parseDouble(entity.getValue());

                if (entity.getCondition() == Condition.eq) {
                    valid = doubleValue.equals(doubleRuleValue);
                } else if (entity.getCondition() == Condition.gt) {
                    valid = doubleValue > doubleRuleValue;
                } else if (entity.getCondition() == Condition.lt) {
                    valid = doubleValue < doubleRuleValue;
                } else {
                    valid = false;
                }
                break;
            case booleanType:
                Boolean booleanValue = Boolean.parseBoolean(value);
                Boolean booleanRuleValue = Boolean.parseBoolean(entity.getValue());

                valid = booleanValue.equals(booleanRuleValue);
                break;
            case stringType:
                String stringRuleValue = entity.getValue();

                if (entity.getCondition() == Condition.eq) {
                    valid = value.equals(stringRuleValue);
                } else if (entity.getCondition() == Condition.like) {
                    valid = value.contains(stringRuleValue);
                } else if (entity.getCondition() == Condition.sw) {
                    valid = value.startsWith(stringRuleValue);
                } else if (entity.getCondition() == Condition.ew) {
                    valid = value.endsWith(stringRuleValue);
                } else {
                    valid = false;
                }
                break;
            case dateType:
                LocalDate dateValue = LocalDate.parse(value);
                LocalDate ruleDateValue = LocalDate.parse(entity.getValue());

                if (entity.getCondition() == Condition.eq) {
                    valid = dateValue.equals(ruleDateValue);
                } else if (entity.getCondition() == Condition.gt) {
                    valid = dateValue.isAfter(ruleDateValue);
                } else if (entity.getCondition() == Condition.lt) {
                    valid = dateValue.isBefore(ruleDateValue);
                } else {
                    valid = false;
                }

                break;
                case datetimeType:
                    ZonedDateTime zonedDateValue = ZonedDateTime.parse(value);
                    ZonedDateTime ruleZonedDateValue = ZonedDateTime.parse(entity.getValue());

                    if (entity.getCondition() == Condition.eq) {
                        valid = zonedDateValue.equals(ruleZonedDateValue);
                    } else if (entity.getCondition() == Condition.gt) {
                        valid = zonedDateValue.isAfter(ruleZonedDateValue);
                    } else if (entity.getCondition() == Condition.lt) {
                        valid = zonedDateValue.isBefore(ruleZonedDateValue);
                    } else {
                        valid = false;
                    }

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            valid = false;
        }

        return valid;
    }

    @Override
    public List<Integer> getInvalidUserbasedCoupons(Iterable<CouponEntity> coupons, String user) {
        List<Integer> userRestrictedCoupons = new ArrayList<>();
        List<Integer> couponIds = new ArrayList<>();
        Map<Integer, List<String>> couponIdUserExclusionMap = new HashMap<>();
        Map<Integer, List<String>> couponIdUserInclusionMap = new HashMap<>();

        for (CouponEntity couponEntity : coupons) {
            couponIds.add(couponEntity.getId());
        }

        List<CouponUserRestrictionsEntity> couponUserRestrictionsEntities = couponUserRestrictionsRepository.findByCouponEntity_idIn(couponIds);

        for (CouponUserRestrictionsEntity entity : couponUserRestrictionsEntities){
            if (entity.getType() == RestrictionType.inclusive) {
                String[] users = entity.getEmails().split(",");
                List<String> userList = Arrays.asList(users);
                Integer id = entity.getCouponEntity().getId();

                couponIdUserInclusionMap.put(id, userList);
            } else if (entity.getType() == RestrictionType.exclusive) {
                String[] users = entity.getEmails().split(",");
                List<String> userList = Arrays.asList(users);
                Integer id = entity.getCouponEntity().getId();

                couponIdUserExclusionMap.put(id, userList);
            }
        }

        for (CouponEntity entity : coupons) {
            if (couponIdUserExclusionMap.get(entity.getId()) != null && (!couponIdUserExclusionMap.get(entity.getId()).isEmpty()) && couponIdUserExclusionMap.get(entity.getId()).contains(user)) {
                userRestrictedCoupons.add(entity.getId());
            }

            if (couponIdUserInclusionMap.get(entity.getId()) != null && (!couponIdUserInclusionMap.get(entity.getId()).isEmpty()) && (!couponIdUserInclusionMap.get(entity.getId()).contains(user))) {
                userRestrictedCoupons.add(entity.getId());
            }
        }

        return userRestrictedCoupons;
    }

    @Override
    public List<Integer> getInvalidReferrerCoupons(Iterable<CouponEntity> coupons, String userId) {
        List<Integer> invalidCoupons = new ArrayList<>();
        Collection<ConversionDataEntity> userConversions = this.getSuccessfulConversionsByUserId(userId);

        if (userConversions.isEmpty()) {
            return invalidCoupons;
        }

        for (CouponEntity entity : coupons) {
            if (entity.getReferralCodeRequired() != null && entity.getReferralCodeRequired()) {
                invalidCoupons.add(entity.getId());
            }
        }

        return invalidCoupons;
    }

    @Override
    public Collection<ConversionDataEntity> getSuccessfulConversionsByUserId(String userId) {
        List<ConversionDataEntity> conversions = conversionDataRepository.findByUserId(userId);
        Map<String, ConversionDataEntity> conversionMap = new HashMap<>();
        com.coupon.constants.Status status;
        String txnId;

        for (ConversionDataEntity conversionDataEntity : conversions) {
            status = conversionDataEntity.getStatus();
            txnId = conversionDataEntity.getCartDataEntity().getTxnId();

            if (com.coupon.constants.Status.success.equals(status)) {
                if (conversionMap.containsKey(txnId) && conversionDataEntity.getCreatedOn().isAfter(conversionMap.get(txnId).getCreatedOn())) {
                    conversionMap.put(txnId, conversionDataEntity);
                } else if (!conversionMap.containsKey(txnId)) {
                    conversionMap.put(txnId, conversionDataEntity);
                }
            } else if (conversionMap.containsKey(txnId) && conversionDataEntity.getCreatedOn().isAfter(conversionMap.get(txnId).getCreatedOn())) {
                conversionMap.remove(txnId);
            }
        }

        return conversionMap.values();
    }

    @Override
    public List<Integer> getInvalidPartnerCoupons(Iterable<CouponEntity> coupons, String userId) {
        List<Integer> invalidCoupons = new ArrayList<>();
        List<Integer> partnerIds = new ArrayList<>();

        for (CouponEntity entity : coupons) {
            if (entity.getPartnerId() != null) {
                partnerIds.add(entity.getPartnerId());
            }
        }

        List<PartnerEntity> partnerEntities = partnerRepository.findByIdIn(partnerIds);
        Map<Integer, PartnerEntity> partnerEntityMap = new HashMap<>();

        for (PartnerEntity partnerEntity : partnerEntities) {
            partnerEntityMap.put(partnerEntity.getId(), partnerEntity);
        }

        for (CouponEntity entity : coupons) {
            if (entity.getPartnerId() != null && partnerEntityMap.containsKey(entity.getPartnerId())) {
                if (!userId.equals(partnerEntityMap.get(entity.getPartnerId()).getMerchantId())) {
                    invalidCoupons.add(entity.getId());
                }
            }
        }

        return invalidCoupons;
    }

    private List<Integer> filterInvalidCategoryCoupons(String Category, Map<Integer, CouponEntity> couponIdCouponMap) {
        List<Integer> invalidCoupons = new ArrayList<>();
        List<Integer> couponIds = new ArrayList<>(couponIdCouponMap.keySet());

        Iterable<RuleCategoryMappingEntity> rules = ruleCategoryMappingRepository.findByCouponEntityIdIn(couponIds);
        Map<Integer, List<RuleCategoryMappingEntity>> couponIdRuleMap = new HashMap<>();
        Integer couponId;

        for (RuleCategoryMappingEntity rule : rules) {
            couponId = rule.getCouponEntity().getId();

            if (!couponIdRuleMap.containsKey(couponId)) {
                couponIdRuleMap.put(couponId, new ArrayList<>());
            }

            couponIdRuleMap.get(couponId).add(rule);
        }

        for (Integer coupId : couponIdRuleMap.keySet()) {
            if (!validateCategoryRule(Category, couponIdRuleMap.get(coupId))) {
                invalidCoupons.add(coupId);
            }
        }

        return invalidCoupons;
    }

    private Boolean validateCategoryRule(String category, List<RuleCategoryMappingEntity> ruleCategoryMappingEntities) {
        Boolean valid = false;
        String categoryName;

        for (RuleCategoryMappingEntity rule : ruleCategoryMappingEntities) {
            categoryName = rule.getCategoryName();

            valid = categoryName.equals(category);

            if (valid) {
                break;
            }
        }

        return valid;
    }

    private List<CouponEntity> filterByUserLimit(List<CouponEntity> validCoupons, String userId) {
        if (userId == null) {
            return validCoupons;
        }

        List<CouponEntity> couponEntities = new ArrayList<>();
        List<ConversionDataEntity> validCouponConversions;
        List<Integer> conversionDataIds = new ArrayList<>();
        Map<Integer, List<Integer>> conversionDataIdCouponIdMap = new HashMap<>();
        Collection<ConversionDataEntity> successfulConversions = getSuccessfulConversionsByUserId(userId);
        List<Integer> coupons;

        for (ConversionDataEntity entity : successfulConversions) {
            conversionDataIds.add(entity.getId());
        }

        List<ConversionDataCouponMappingEntity> conversionDataCouponMappingEntities = conversionDataCouponMappingRepository.findByConversionDataEntity_idIn(conversionDataIds);

        for (ConversionDataCouponMappingEntity entity : conversionDataCouponMappingEntities) {
            if (conversionDataIdCouponIdMap.get(entity.getConversionDataEntity().getId()) == null) {
                conversionDataIdCouponIdMap.put(entity.getConversionDataEntity().getId(), new ArrayList<>());
                List l = conversionDataIdCouponIdMap.get(entity.getConversionDataEntity().getId());
                l.add(entity.getCouponEntity().getId());
                conversionDataIdCouponIdMap.put(entity.getConversionDataEntity().getId(), l);
            } else {
                List l = conversionDataIdCouponIdMap.get(entity.getConversionDataEntity().getId());
                l.add(entity.getCouponEntity().getId());
                conversionDataIdCouponIdMap.put(entity.getConversionDataEntity().getId(), l);
            }
        }

        for (CouponEntity couponEntity : validCoupons) {
            validCouponConversions = new ArrayList<>();

            for (ConversionDataEntity conversionDataEntity : successfulConversions) {
                coupons = conversionDataIdCouponIdMap.get(conversionDataEntity.getId());

                if (coupons == null) {
                    coupons = new ArrayList<>();
                }

                if (conversionDataEntity.getCreatedOn().isAfter(couponEntity.getStartedOn())
                        && conversionDataEntity.getCreatedOn().isBefore(couponEntity.getClosedOn()) && coupons.size() > 0) {

                    if (coupons.contains(couponEntity.getId())) {
                        validCouponConversions.add(conversionDataEntity);
                    }
                }
            }

            if (couponEntity.getLimitPerUser() > validCouponConversions.size()) {
                couponEntities.add(couponEntity);
            }
        }

        couponEntities = filterByCouponLimitAndCouponAmountLimit(couponEntities);

        return couponEntities;
    }

    private List<CouponEntity> filterByCouponLimitAndCouponAmountLimit(List<CouponEntity> validCoupons) {
        List<CouponEntity> couponEntities = new ArrayList<>();
        List<Integer> couponIds = new ArrayList<>();
        Map<Integer, Integer> couponIdAndCountMap = new HashMap<>();
        Map<Integer, Double> couponIdAndAmountMap = new HashMap<>();
        List<Integer> validCouponIds = new ArrayList<>();

        for (CouponEntity couponEntity : validCoupons) {
            couponIds.add(couponEntity.getId());
        }

        List<ConversionDataCouponMappingEntity> conversionDataCouponMappingEntities = conversionDataCouponMappingRepository.findByCouponEntity_IdIn(couponIds);

        for (ConversionDataCouponMappingEntity entity : conversionDataCouponMappingEntities) {
            if (entity.getActive()) {
                if (couponIdAndCountMap.get(entity.getCouponEntity().getId()) == null) {
                    couponIdAndCountMap.put(entity.getCouponEntity().getId(), 1);
                } else {
                    couponIdAndCountMap.put(entity.getCouponEntity().getId(), couponIdAndCountMap.get(entity.getCouponEntity().getId()) + 1);
                }

                if (couponIdAndAmountMap.get(entity.getCouponEntity().getId()) == null && entity.getCouponAmount() != null) {
                    couponIdAndAmountMap.put(entity.getCouponEntity().getId(), entity.getCouponAmount());
                } else {
                    couponIdAndAmountMap.put(entity.getCouponEntity().getId(), couponIdAndAmountMap.get(entity.getCouponEntity().getId()) + entity.getCouponAmount());
                }
            }
        }

        for (CouponEntity entity : validCoupons) {
            couponIdAndCountMap.putIfAbsent(entity.getId(), 0);
            couponIdAndAmountMap.putIfAbsent(entity.getId(), 0.0);
        }

        for (CouponEntity couponEntity : validCoupons) {
            if (couponEntity.getCouponLimit() == null && couponEntity.getCouponLimitAmount() == null) {
                if (validCouponIds.indexOf(couponEntity.getId()) == -1) {
                    couponEntities.add(couponEntity);
                    validCouponIds.add(couponEntity.getId());
                }
            } else if (couponEntity.getCouponLimit() == null &&
                    couponEntity.getCouponLimitAmount() != null &&
                    couponEntity.getCouponLimitAmount() > (couponIdAndAmountMap.get(couponEntity.getId()))) {
                if (validCouponIds.indexOf(couponEntity.getId()) == -1) {
                    couponEntities.add(couponEntity);
                    validCouponIds.add(couponEntity.getId());
                }
            } else if (couponEntity.getCouponLimit() != null &&
                    couponEntity.getCouponLimit() > couponIdAndCountMap.get(couponEntity.getId()) &&
                    couponEntity.getCouponLimitAmount() == null) {
                if (validCouponIds.indexOf(couponEntity.getId()) == -1) {
                    couponEntities.add(couponEntity);
                    validCouponIds.add(couponEntity.getId());
                }
            } else if (couponEntity.getCouponLimit() != null &&
                    couponEntity.getCouponLimitAmount() != null &&
                    couponEntity.getCouponLimit() > couponIdAndCountMap.get(couponEntity.getId()) &&
                    couponEntity.getCouponLimitAmount() > (couponIdAndAmountMap.get(couponEntity.getId()))) {
                if (validCouponIds.indexOf(couponEntity.getId()) == -1) {
                    couponEntities.add(couponEntity);
                    validCouponIds.add(couponEntity.getId());
                }
            }
        }

        return couponEntities;
    }
}
