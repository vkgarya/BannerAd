package com.coupon.ad.bean;

import com.coupon.ad.constants.AdType;
import com.coupon.ad.payload.CreateAdRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AdCMSDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int coupon_id = 0;
    private String type;
    private String click_url;
    private String click_type;
    private Integer partner_id;
    private String status;
    private String country;
    private String state;
    private String genre;
    private String nationality;
    private int priority;
    private List<String> age_groups;
    private List<String> genders;
    private String age_groups_str;
    private String genders_str;
    private Integer balance_clicks = 0;
    private Double balance_amount = 0.0;
    private List<AdAssetCMSDTO> banners;
    private List<AdAssetCMSDTO> videos;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime started_on;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime closed_on;

    private List<AdAssetCMSDTO> adAssetList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClick_url() {
        return click_url;
    }

    public void setClick_url(String click_url) {
        this.click_url = click_url;
    }

    public String getClick_type() {
        return click_type;
    }

    public void setClick_type(String click_type) {
        this.click_type = click_type;
    }

    public void setAdAssetList(List<AdAssetCMSDTO> adAssetList) {
        this.adAssetList = adAssetList;
        if (AdType.BANNER.equalsIgnoreCase(this.type) && !adAssetList.isEmpty()) {
            setBanners(adAssetList);
        }
        if (AdType.VIDEO.equalsIgnoreCase(this.type) && !adAssetList.isEmpty()) {
            setVideos(adAssetList);
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<AdAssetCMSDTO> getBanners() {
        return banners;
    }

    public void setBanners(List<AdAssetCMSDTO> banners) {
        this.banners = banners;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<AdAssetCMSDTO> getVideos() {
        return videos;
    }

    public void setVideos(List<AdAssetCMSDTO> videos) {
        this.videos = videos;
    }

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public Integer getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(Integer partner_id) {
        this.partner_id = partner_id;
    }

    public ZonedDateTime getStarted_on() {
        return started_on;
    }

    public void setStarted_on(ZonedDateTime started_on) {
        this.started_on = started_on;
    }

    public ZonedDateTime getClosed_on() {
        return closed_on;
    }

    public void setClosed_on(ZonedDateTime closed_on) {
        this.closed_on = closed_on;
    }

    public String getStatus() {
        return status;
    }

    public Integer getBalance_clicks() {
        if (balance_clicks == null) {
            balance_clicks = 0;
        }
        return balance_clicks;
    }

    public void setBalance_clicks(Integer balance_clicks) {
        this.balance_clicks = balance_clicks;
    }

    public Double getBalance_amount() {
        if (balance_amount == 0) {
            balance_amount = 0.0;
        }
        return balance_amount;
    }

    public void setBalance_amount(Double balance_amount) {
        this.balance_amount = balance_amount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    public List<String> getAge_groups() {
        return age_groups;
    }

    public void setAge_groups(List<String> age_groups) {
        this.age_groups = age_groups;
    }

    public List<String> getGenders() {
        return genders;
    }

    public void setGenders(List<String> genders) {
        this.genders = genders;
    }

    @JsonIgnore
    public String getAge_groups_str() {
        StringBuilder sb = new StringBuilder();
        if (getAge_groups() != null) {
            for (String age_group : getAge_groups()) {
                sb.append(age_group);
                sb.append(";");
            }
            age_groups_str = sb.toString();
        }
        return age_groups_str;
    }

    public void setAge_groups_str(String age_groups_str) {
        if (age_groups_str != null) {
            this.age_groups = new ArrayList<String>();
            StringTokenizer st = new StringTokenizer(age_groups_str, ";");
            while (st.hasMoreTokens()) {
                this.age_groups.add(st.nextToken());
            }
        }
        this.age_groups_str = age_groups_str;
    }

    @JsonIgnore
    public String getGenders_str() {
        StringBuilder sb = new StringBuilder();
        if (getGenders() != null) {
            for (String gender : getGenders()) {
                sb.append(gender);
                sb.append(";");
            }
            genders_str = sb.toString();
        }
        return genders_str;
    }

    public void setGenders_str(String genders_str) {
        if (genders_str != null) {
            this.genders = new ArrayList<String>();
            StringTokenizer st = new StringTokenizer(genders_str, ";");
            while (st.hasMoreTokens()) {
                this.genders.add(st.nextToken());
            }
        }
        this.genders_str = genders_str;
    }

    public AdCMSDTO() {
        super();
    }

    public AdCMSDTO getAd(Ad ad) {
        AdCMSDTO adDTO = new AdCMSDTO();
        adDTO.setId(ad.getId());
        adDTO.setStatus(ad.getStatus());
        adDTO.setCoupon_id(ad.getCoupon_id());
        adDTO.setType(ad.getType());
        adDTO.setClick_type(ad.getClick_type());
        adDTO.setClick_url(ad.getClick_url());
        adDTO.setStarted_on(ad.getStarted_on());
        adDTO.setClosed_on(ad.getClosed_on());
        adDTO.setPartner_id(ad.getPartner_id());
        adDTO.setBalance_clicks(ad.getBalance_clicks());
        adDTO.setBalance_amount(ad.getBalance_amount());
        adDTO.setCoupon_id(ad.getCoupon_id());
        return adDTO;
    }

    public AdCMSDTO(CreateAdRequest createAdRequest) {
        super();
        this.id = createAdRequest.getId();
        this.click_type = createAdRequest.getClick_type();
        this.click_url = createAdRequest.getClick_url();
        this.type = createAdRequest.getType();
        this.status = createAdRequest.getStatus();
        this.partner_id = createAdRequest.getPartner_id();
        this.started_on = createAdRequest.getStarted_on();
        this.closed_on = createAdRequest.getClosed_on();
        this.country = createAdRequest.getCountry();
        this.state = createAdRequest.getState();
        this.genders = createAdRequest.getGenders();
        this.genre = createAdRequest.getGenre();
        this.age_groups = createAdRequest.getAge_groups();
        this.priority = createAdRequest.getPriority();
        this.balance_clicks = createAdRequest.getBalance_clicks();
        this.balance_amount = createAdRequest.getBalance_amount();
        this.nationality = createAdRequest.getNationality();
        this.coupon_id = createAdRequest.getCoupon_id();
    }

    @Override
    public String toString() {
        return "AdCMSDTO{" +
                "id=" + id +
                ", coupon_id=" + coupon_id +
                ", type='" + type + '\'' +
                ", click_url='" + click_url + '\'' +
                ", click_type='" + click_type + '\'' +
                ", partner_id=" + partner_id +
                ", status='" + status + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", genre='" + genre + '\'' +
                ", nationality='" + nationality + '\'' +
                ", priority=" + priority +
                ", age_groups=" + age_groups +
                ", genders=" + genders +
                ", age_groups_str='" + age_groups_str + '\'' +
                ", genders_str='" + genders_str + '\'' +
                ", balance_clicks=" + balance_clicks +
                ", balance_amount=" + balance_amount +
                ", banners=" + banners +
                ", videos=" + videos +
                ", started_on=" + started_on +
                ", closed_on=" + closed_on +
                ", adAssetList=" + adAssetList +
                '}';
    }
}
