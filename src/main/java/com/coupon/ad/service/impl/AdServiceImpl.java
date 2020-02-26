package com.coupon.ad.service.impl;

import com.coupon.ad.bean.*;
import com.coupon.ad.bean.jpa.AdAssetEntity;
import com.coupon.ad.bean.jpa.AdEntity;
import com.coupon.ad.constants.*;
import com.coupon.ad.payload.AdRequest;
import com.coupon.ad.payload.AdResponse;
import com.coupon.ad.payload.AdResponseCMS;
import com.coupon.ad.payload.CreateAdRequest;
import com.coupon.ad.repository.AdAssetRepository;
import com.coupon.ad.repository.AdRepository;
import com.coupon.ad.service.AdAssetService;
import com.coupon.ad.service.AdClickService;
import com.coupon.ad.service.AdRequestService;
import com.coupon.ad.service.AdService;
import com.coupon.auth.security.CurrentUserProvider;
import com.coupon.bean.StatusDTO;
import com.coupon.constants.CmsStatusCodes;
import com.coupon.exceptions.ResourceNotFoundException;
import com.coupon.properties.PropertyManager;
import com.coupon.service.mapper.ServiceMapper;
import com.coupon.user.bean.jpa.UserPartnerEntity;
import com.coupon.user.repository.UserPartnerRepository;
import com.coupon.user.service.UserService;
import com.coupon.utils.CampaignUtil;
import com.coupon.utils.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdServiceImpl.class);
    @Resource
    private AdRepository adRepository;

    @Resource
    private AdClickService adClickService;

    @Resource
    private AdAssetRepository adAssetRepository;

    @Resource
    private AdRequestService adRequestService;

    @Resource
    private ServiceMapper<Ad, AdEntity> adServiceMapper;

    @Resource
    private ServiceMapper<AdAsset, AdAssetEntity> adAssetServiceMapper;

    @Resource
    private AdAssetService adAssetService;

    @Resource
    private UserService userService;

    @Resource
    private UserPartnerRepository userPartnerRepository;

    @Override
    public Ad findById(final Integer id) {
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ad", "id", id));
        Ad ad = adServiceMapper.mapEntityToDTO(adEntity, Ad.class);
        return ad;
    }

    @Override
    public List<Ad> findAllActive(String type) {
        ZonedDateTime now = TimeUtil.getCurrentUTCTime();
        List<AdEntity> adEntityList = adRepository.findAllValid(now, type, AdStatus.ACTIVE);

        List<Ad> adList = new ArrayList<>();

        for (AdEntity adEntity : adEntityList) {
            adList.add(adServiceMapper.mapEntityToDTO(adEntity, Ad.class));
        }

        List<Ad> ads = new ArrayList<>();
/*
        for(Ad ad : adList){
                ad.setAdAssetList(this.adAssetService.findAllByAdId(ad.getId()));
            ads.add(ad);
        }
        */
        return ads;
    }

    public Ad save(Ad ad) {
        AdEntity adEntity = new AdEntity(ad);
        return adRepository.save(adEntity).getAd();
    }

    @Override
    public List<Ad> findAll(String type) {
        /*
        List<AdEntity> adEntityList = adRepository.findAll(type);

        List<Ad> adList = new ArrayList<>();

        for (AdEntity adEntity : adEntityList) {
            adList.add(adServiceMapper.mapEntityToDTO(adEntity, Ad.class));
        }

        List<Ad> ads = new ArrayList<>();

        for(Ad ad : adList){
            ad.setAdAssetList(this.adAssetService.findAllByAdId(ad.getId()));
            ads.add(ad);
        }

        return ads;

         */
        return null;
    }

    @Override
    public AdResponse createAd(final CreateAdRequest createAdRequest, final MultipartFile[] files) {
        LOGGER.info("/cms/v1/create-ad : " + createAdRequest);
        LOGGER.info("Files : " + files);
        LOGGER.info("Files size : " + files.length);
        AdCMSDTO adCMSDTO = new AdCMSDTO(createAdRequest);

        AdEntity adEntity = new AdEntity(adCMSDTO);
        AdEntity savedAdEntity = adRepository.save(adEntity);
        Integer id = savedAdEntity.getId();
        LOGGER.info(" UPDATED AD OBJECT FROM DB : " + savedAdEntity);
        AdEntity tempAdEntity = new AdEntity();
        tempAdEntity.setId(id);

        if (createAdRequest.getId() != 0 && createAdRequest.getDeleted_asset_ids() != null && createAdRequest.getDeleted_asset_ids().size() > 0) {
            for (Integer assetId : createAdRequest.getDeleted_asset_ids()) {
                List<AdAssetEntity> list = adAssetRepository.findByAdEntity(tempAdEntity);
                LOGGER.info(" AD ID : " + createAdRequest.getId() + " ASSETS SIZE : " + list.size());
                for (AdAssetEntity adAssetEntity : list) {
                    if (assetId == adAssetEntity.getId()) {
                        LOGGER.info("DELETING EXISTING ASSET : " + adAssetEntity.getAdAsset());
                        adAssetRepository.deleteById(adAssetEntity.getId());
                    }
                }
            }
        }

        List<AdAssetEntity> adAssetEntityList = new ArrayList<AdAssetEntity>();
        AdAssetEntity adAssetEntity;
        String httpAssetUrl;
        for (int x = 0; x < files.length; x++) {
            httpAssetUrl = CampaignUtil.uploadFile(files[x]);
            if (httpAssetUrl == null) {
                httpAssetUrl = "";
            }
            if (httpAssetUrl != null) {
                if (createAdRequest.getFile_resolutions() != null && createAdRequest.getFile_resolutions().size() > x) {
                    adAssetEntity = new AdAssetEntity();
                    adAssetEntity.setAspect_ratio("");
                    adAssetEntity.setLink(httpAssetUrl);
                    adAssetEntity.setResolution(createAdRequest.getFile_resolutions().get(x));
                    adAssetEntity.setAdEntity(tempAdEntity);
                    adAssetEntityList.add(adAssetEntity);
                }
            }
        }
        adAssetRepository.saveAll(adAssetEntityList);

        StatusDTO statusDTO = CmsStatusCodes.VALUES.get(CmsStatusCodes.SUCCESS);
        AdResponse adResponse = new AdResponse(createAdRequest.getTxn_id(), statusDTO, null);
        return adResponse;
    }

    public AdResponse adRequest(final AdRequest requestBody, HttpServletRequest request) {
        StatusDTO statusDTO;
        AdResponse adResponse;
        AdRequestDTO adRequestDTO = new AdRequestDTO(requestBody, request);

        if (StringUtils.isEmpty(requestBody.getTxn_id())) {
            LOGGER.error(" invalid txn_id : " + requestBody.getTxn_id());
            statusDTO = AdStatusCodes.VALUES.get(AdStatusCodes.INVALID_TXN_ID);
            adResponse = new AdResponse(requestBody.getTxn_id(), statusDTO, null);
            adRequestDTO.setStatusCode(statusDTO.getStatus_code());
            adRequestService.save(adRequestDTO);
            return adResponse;
        }
        if (StringUtils.isEmpty(requestBody.getAd_type())) {
            LOGGER.error(" invalid ad_type : " + requestBody.getAd_type());
            statusDTO = AdStatusCodes.VALUES.get(AdStatusCodes.INVALID_FIELD_VALUES);
            adResponse = new AdResponse(requestBody.getTxn_id(), statusDTO, null);
            adRequestDTO.setStatusCode(statusDTO.getStatus_code());
            adRequestService.save(adRequestDTO);
            return adResponse;
        }
        ZonedDateTime now = TimeUtil.getCurrentUTCTime();
        List<AdEntity> adEntityList = adRepository.findAllValid(now, adRequestDTO.getType(), AdStatus.ACTIVE);
        LOGGER.info("Ad LIST SIZE FROM DB : " + adEntityList.size());
        if (!adEntityList.isEmpty()) {
            List<Ad> adList = new ArrayList<Ad>();
            String campaignRedirectionAPI = PropertyManager.getCampaignRedirectionAPI();
            String campaignNativeAPI = PropertyManager.getCampaignNativeAPI();
            String timestamp = CampaignUtil.getTimestamp();
            Ad ad;
            for (AdEntity adEntity : adEntityList) {
                ad = adEntity.getAd();
                String adClickTxnId = timestamp + "_" + CampaignUtil.getRandomTxnIdForCampaign();
                if (AdClickType.NATIVE.equalsIgnoreCase(adEntity.getClick_type())) {
                    ad.setClick_url(campaignNativeAPI.replace("@@campaign_id@@", adClickTxnId));
                } else {
                    ad.setClick_url(campaignRedirectionAPI.replace("@@campaign_id@@", adClickTxnId));
                }

                List<AdAsset> adAssetList = new ArrayList<AdAsset>();
                for (AdAssetEntity adAssetEntity : adEntity.getAssetEntityList()) {
                    adAssetList.add(adAssetEntity.getAdAsset());
                }

                if (AdType.BANNER.equalsIgnoreCase(adRequestDTO.getType())) {
                    ad.setBanners(adAssetList);
                } else if (AdType.BANNER.equalsIgnoreCase(adRequestDTO.getType())) {
                    ad.setVideos(adAssetList);
                }

                AdClickDTO adClickDTO = new AdClickDTO();
                adClickDTO.setUserid(requestBody.getUser_id());
                adClickDTO.setAdId(ad.getId());
                adClickDTO.setAdTxnId(adClickTxnId);
                adClickDTO.setClickCount(0);
                adClickDTO.setRemoteip(CampaignUtil.getClientIP(request));
                adClickService.save(adClickDTO);
                adList.add(ad);
            }

            List<Ad> filteredList = getFilteredAdList(adList, requestBody);

            if (requestBody.getLimit() != null && filteredList.size() > requestBody.getLimit()) {
                filteredList = filteredList.subList(0, requestBody.getLimit());
                LOGGER.info("Ads limit set to : " + requestBody.getLimit());
            }

            StringBuilder sb = new StringBuilder();
            for (Ad adObj : filteredList) {
                sb.append(adObj.getId());
                sb.append(", ");
            }
            LOGGER.info(" Final Ad ids : " + sb.toString());

            if (filteredList.isEmpty()) {
                statusDTO = AdStatusCodes.VALUES.get(AdStatusCodes.NO_ADS);
                adResponse = new AdResponse(requestBody.getTxn_id(), statusDTO, null);
                adRequestDTO.setStatusCode(statusDTO.getStatus_code());
                adRequestService.save(adRequestDTO);
                LOGGER.info("/v1/ad-request : " + adResponse);
                return adResponse;
            }


            statusDTO = AdStatusCodes.VALUES.get(AdStatusCodes.SUCCESS);
            adResponse = new AdResponse(requestBody.getTxn_id(), statusDTO, null);
            adResponse.setResults(filteredList);
            adRequestDTO.setStatusCode(statusDTO.getStatus_code());
            adRequestService.save(adRequestDTO);
            return adResponse;
        } else {
            statusDTO = AdStatusCodes.VALUES.get(AdStatusCodes.NO_ADS);
            adResponse = new AdResponse(requestBody.getTxn_id(), statusDTO, null);
            adRequestDTO.setStatusCode(statusDTO.getStatus_code());
            adRequestService.save(adRequestDTO);
            LOGGER.info("/v1/ad-request : " + adResponse);
            return adResponse;
        }

    }

    private Sort orderByIdDesc() {
        return new Sort(Sort.Direction.DESC, "id");
        //.and(new Sort(Sort.Direction.ASC, "name"));
    }

    @Override
    public AdResponseCMS adList(final String ad_type) {
        AdResponseCMS adResponse;
        StatusDTO statusDTO;
        if (ad_type == null || ad_type.trim().length() == 0) {
            statusDTO = CmsStatusCodes.VALUES.get(CmsStatusCodes.INVALID_FIELD_VALUES);
            adResponse = new AdResponseCMS(null, statusDTO, null);
            LOGGER.info("/cms/v1/ads/" + ad_type + " : " + adResponse);
            return adResponse;
        }

        List<AdEntity> adEntityList = new ArrayList<>();
        List<Integer> partnerIds = new ArrayList<>();
        if (userService.hasAdminAccess()) {
            LOGGER.info("Admin login");
            adEntityList = adRepository.findAllNotDeleted(ad_type, orderByIdDesc());
        } else {
            List<UserPartnerEntity> userPartnerEntities = userPartnerRepository.findByUserId(CurrentUserProvider.getUserId());
            for (UserPartnerEntity entity : userPartnerEntities) {
                partnerIds.add(entity.getPartner().getId());
            }
            LOGGER.info("Partner login : partner ids : " + partnerIds);
            if (partnerIds.size() > 0)
                adEntityList = adRepository.findAllNotDeletedByUserPartnerIds(ad_type, partnerIds, orderByIdDesc());
        }


        List<AdCMSDTO> adList = new ArrayList<AdCMSDTO>();
        if (adEntityList.isEmpty()) {
            LOGGER.info("/cms/v1/ads/" + ad_type + " ADS SIZE : " + adList.size());
            statusDTO = CmsStatusCodes.VALUES.get(CmsStatusCodes.NO_RECORDS_EXIST);
            adResponse = new AdResponseCMS(null, statusDTO, null);
            LOGGER.info("/cms/v1/ads/" + ad_type + " :" + adResponse);
            return adResponse;
        } else {
            for (AdEntity adEntity : adEntityList) {
                List<AdAssetCMSDTO> adAssetList = new ArrayList<AdAssetCMSDTO>();
                for (AdAssetEntity adAssetEntity : adEntity.getAssetEntityList()) {
                    adAssetList.add(adAssetEntity.getAdAssetCMSDTO());
                }
                AdCMSDTO adDTO = adEntity.getAdCMSDTO();
                if (AdType.BANNER.equalsIgnoreCase(ad_type)) {
                    adDTO.setBanners(adAssetList);
                } else if (AdType.BANNER.equalsIgnoreCase(ad_type)) {
                    adDTO.setVideos(adAssetList);
                }
                adList.add(adDTO);
            }
            statusDTO = CmsStatusCodes.VALUES.get(CmsStatusCodes.SUCCESS);
            adResponse = new AdResponseCMS(null, statusDTO, null);
            adResponse.setResults(adList);
            return adResponse;
        }
    }

    @Override
    public AdResponseCMS delete(Integer id, String ad_type) {
        LOGGER.info(" delete : " + id + " type : " + ad_type);
        AdResponseCMS adResponse;
        StatusDTO statusDTO;

        Optional<AdEntity> optionalAdEntity = adRepository.findByIdAndType(id, ad_type);
        if (!optionalAdEntity.isPresent()) {
            statusDTO = CmsStatusCodes.VALUES.get(CmsStatusCodes.INVALID_FIELD_VALUES);
            adResponse = new AdResponseCMS(null, statusDTO, null);
            return adResponse;
        }

        AdEntity adEntity = optionalAdEntity.get();
        adEntity.setStatus(AdStatus.DELETED);
        adRepository.save(adEntity);
        statusDTO = CmsStatusCodes.VALUES.get(CmsStatusCodes.SUCCESS);
        adResponse = new AdResponseCMS(null, statusDTO, null);
        return adResponse;
    }

    @Override
    public AdResponseCMS getAd(Integer id, String ad_type) {
        LOGGER.info(" getAd : " + id + " type : " + ad_type);
        AdResponseCMS adResponse;
        StatusDTO statusDTO;

        Optional<AdEntity> optionalAdEntity = adRepository.findByIdAndType(id, ad_type);
        if (!optionalAdEntity.isPresent()) {
            statusDTO = CmsStatusCodes.VALUES.get(CmsStatusCodes.INVALID_FIELD_VALUES);
            adResponse = new AdResponseCMS(null, statusDTO, null);
            return adResponse;
        }

        AdEntity adEntity = optionalAdEntity.get();
        AdCMSDTO adCMSDTO = adEntity.getAdCMSDTO();
        List<AdAssetCMSDTO> adAssetCMSDTOList = new ArrayList<AdAssetCMSDTO>();
        AdAssetCMSDTO adAssetCMSDTO;
        for (AdAssetEntity adAssetEntity : adEntity.getAssetEntityList()) {
            adAssetCMSDTO = adAssetEntity.getAdAssetCMSDTO();
            adAssetCMSDTOList.add(adAssetCMSDTO);
        }
        if (AdType.BANNER.equalsIgnoreCase(ad_type)) {
            adCMSDTO.setBanners(adAssetCMSDTOList);
        } else {
            adCMSDTO.setVideos(adAssetCMSDTOList);
        }

        statusDTO = CmsStatusCodes.VALUES.get(CmsStatusCodes.SUCCESS);
        adResponse = new AdResponseCMS(null, statusDTO, null);
        adResponse.setAd(adCMSDTO);
        return adResponse;
    }


    private List<Ad> getFilteredAdList(final List<Ad> adList, final AdRequest requestBody) {
        List<Ad> filteredList = new ArrayList<Ad>();
        boolean filterEnabled = false;
        for (Ad adObj : adList) {
            boolean filterSuccess = false;
            boolean previousfilterEnabled = false;
            LOGGER.info("checking filters for ad id : " + adObj.getId());
            Optional<AdEntity> optionalAdEntity = adRepository.findByIdAndType(adObj.getId(), adObj.getType());
            if (optionalAdEntity.isPresent()) {
                AdEntity adEntity = optionalAdEntity.get();
                AdCMSDTO adCMSDTO = adEntity.getAdCMSDTO();
                if (requestBody.getCountry() != null && requestBody.getCountry().trim().length() != 0) {
                    previousfilterEnabled = true;
                    filterEnabled = true;
                    if (adCMSDTO.getCountry() != null && adCMSDTO.getCountry().equalsIgnoreCase(requestBody.getCountry())) {
                        filterSuccess = true;
                    } else {
                        LOGGER.info("Ad id " + adCMSDTO.getId() + " not having country : " + requestBody.getCountry());
                        filterSuccess = false;
                    }
                }

                if (((previousfilterEnabled && filterSuccess) || !previousfilterEnabled) && requestBody.getState() != null && requestBody.getState().trim().length() != 0) {
                    previousfilterEnabled = true;
                    filterEnabled = true;
                    if (adCMSDTO.getState() != null && adCMSDTO.getState().equalsIgnoreCase(requestBody.getState())) {
                        filterSuccess = true;
                    } else {
                        LOGGER.info("Ad id " + adCMSDTO.getId() + " not having state : " + requestBody.getState());
                        filterSuccess = false;
                    }
                }

                if (((previousfilterEnabled && filterSuccess) || !previousfilterEnabled) && requestBody.getGenre() != null && requestBody.getGenre().trim().length() != 0) {
                    previousfilterEnabled = true;
                    filterEnabled = true;
                    if (adCMSDTO.getGenre() != null && adCMSDTO.getGenre().equalsIgnoreCase(requestBody.getGenre())) {
                        filterSuccess = true;
                    } else {
                        LOGGER.info("Ad id " + adCMSDTO.getId() + " not having genre : " + requestBody.getGenre());
                        filterSuccess = false;
                    }
                }

                if (((previousfilterEnabled && filterSuccess) || !previousfilterEnabled) && requestBody.getNationality() != null && requestBody.getNationality().trim().length() != 0) {
                    previousfilterEnabled = true;
                    filterEnabled = true;
                    if (adCMSDTO.getNationality() != null && adCMSDTO.getNationality().equalsIgnoreCase(requestBody.getNationality())) {
                        filterSuccess = true;
                    } else {
                        LOGGER.info("Ad id " + adCMSDTO.getId() + " not having nationality : " + requestBody.getNationality());
                        filterSuccess = false;
                    }
                }

                if (((previousfilterEnabled && filterSuccess) || !previousfilterEnabled) && requestBody.getGender() != null && requestBody.getGender().trim().length() != 0) {
                    previousfilterEnabled = true;
                    filterEnabled = true;
                    if (adCMSDTO.getGenders() != null && adCMSDTO.getGenders().size() > 0) {
                        for (String gender : adCMSDTO.getGenders()) {
                            if (gender.equalsIgnoreCase(requestBody.getGender())) {
                                filterSuccess = true;
                                break;
                            }
                        }
                    } else {
                        LOGGER.info("Ad id " + adCMSDTO.getId() + " not having gender : " + requestBody.getGender());
                        filterSuccess = false;
                    }
                }


                if (((previousfilterEnabled && filterSuccess) || !previousfilterEnabled) && requestBody.getAge() != null) {
                    previousfilterEnabled = true;
                    filterEnabled = true;
                    boolean ageFilterSuccess = false;
                    if (adCMSDTO.getAge_groups() != null && adCMSDTO.getAge_groups().size() > 0) {
                        for (String ageGroupStr : adCMSDTO.getAge_groups()) {
                            AgeGroup ageGroupObj = AgeGroups.getAgeGroup(ageGroupStr);
                            if (ageGroupObj != null && ageGroupObj.startYear <= requestBody.getAge() && ageGroupObj.endYear >= requestBody.getAge()) {
                                LOGGER.info("Ad id : " + adObj.getId() + " Found age group : " + ageGroupStr + " User age : " + requestBody.getAge());
                                ageFilterSuccess = true;
                                break;
                            }
                        }
                    }
                    if (!ageFilterSuccess) {
                        LOGGER.info("Ad id " + adCMSDTO.getId() + " not having age : " + requestBody.getAge());
                        filterSuccess = false;
                    } else {
                        filterSuccess = true;
                    }
                }
                if (filterSuccess) {
                    filteredList.add(adObj);
                    LOGGER.info("Ad id : " + adObj.getId() + " added to Filtered Ad List , size : " + filteredList.size());
                }
            }
        }


        if (!filterEnabled) {
            return adList;
        }
        return filteredList;

    }
}
