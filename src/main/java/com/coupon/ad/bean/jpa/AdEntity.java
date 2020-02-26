package com.coupon.ad.bean.jpa;

import com.coupon.ad.bean.Ad;
import com.coupon.ad.bean.AdCMSDTO;
import com.coupon.utils.UTCDateTimeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "ad")
public class AdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @Column(name = "click_url", nullable = false, length = 256)
    private String click_url;

    @Column(name = "click_type", nullable = false, length = 30)
    private String click_type;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "coupon_id")
    private Integer coupon_id;

    @Column(name = "partner_id")
    private Integer partner_id;

    @Column(name = "balance_clicks", nullable = false)
    private Integer balance_clicks;

    @Column(name = "balance_amount", nullable = false)
    private Double balance_amount;

    @OneToMany(mappedBy = "adEntity", targetEntity = AdAssetEntity.class)
    private List<AdAssetEntity> assetEntityList;

    @Column(name = "started_on", nullable = false)
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime started_on;

    @Column(name = "closed_on", nullable = false)
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime closed_on;

    @Column(name = "country", length = 30)
    private String country;
    @Column(name = "state", length = 30)
    private String state;
    @Column(name = "genre", length = 30)
    private String genre;
    @Column(name = "nationality", length = 30)
    private String nationality;
    @Column(name = "priority", nullable = false)
    private int priority;
    @Column(name = "age_groups", length = 100)
    private String age_groups;
    @Column(name = "genders", length = 30)
    private String genders;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Integer coupon_id) {
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

    public List<AdAssetEntity> getAssetEntityList() {
        return assetEntityList;
    }

    public void setAssetEntityList(List<AdAssetEntity> assetEntityList) {
        this.assetEntityList = assetEntityList;
    }

    public Integer getBalance_clicks() {
        return balance_clicks;
    }

    public void setBalance_clicks(Integer balance_clicks) {
        this.balance_clicks = balance_clicks;
    }

    public Double getBalance_amount() {
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

    public String getAge_groups() {
        return age_groups;
    }

    public void setAge_groups(String age_groups) {
        this.age_groups = age_groups;
    }

    public String getGenders() {
        return genders;
    }

    public void setGenders(String genders) {
        this.genders = genders;
    }

    public AdEntity() {
        super();
    }

    public AdEntity(Ad ad) {
        super();
        this.id = ad.getId();
        this.click_type = ad.getClick_type();
        this.click_url = ad.getClick_url();
        this.closed_on = ad.getClosed_on();
        this.started_on = ad.getStarted_on();
        this.status = ad.getStatus();
        this.coupon_id = ad.getCoupon_id();
        this.type = ad.getType();
        this.partner_id = ad.getPartner_id();
        this.setCoupon_id(ad.getCoupon_id());
    }

    public AdEntity(AdCMSDTO adCMSDTO) {
        super();
        this.id = adCMSDTO.getId();
        this.click_type = adCMSDTO.getClick_type();
        this.click_url = adCMSDTO.getClick_url();
        this.closed_on = adCMSDTO.getClosed_on();
        this.started_on = adCMSDTO.getStarted_on();
        this.status = adCMSDTO.getStatus();
        this.coupon_id = adCMSDTO.getCoupon_id();
        this.type = adCMSDTO.getType();
        this.partner_id = adCMSDTO.getPartner_id();
        this.age_groups = adCMSDTO.getAge_groups_str();
        this.genders = adCMSDTO.getGenders_str();
        this.genre = adCMSDTO.getGenre();
        this.nationality = adCMSDTO.getNationality();
        this.country = adCMSDTO.getCountry();
        this.state = adCMSDTO.getState();
        this.balance_amount = adCMSDTO.getBalance_amount();
        this.balance_clicks = adCMSDTO.getBalance_clicks();
        this.priority = adCMSDTO.getPriority();
        this.coupon_id = adCMSDTO.getCoupon_id();
    }


    public Ad getAd() {
        Ad ad = new Ad();
        ad.setClosed_on(this.closed_on);
        ad.setStarted_on(this.started_on);
        ad.setId(this.id);
        ad.setClick_url(this.click_url);
        ad.setClick_type(this.click_type);
        ad.setType(this.type);
        ad.setCoupon_id(this.coupon_id);
        ad.setStatus(this.status);
        ad.setPartner_id(this.partner_id);
        ad.setBalance_amount(this.balance_amount);
        ad.setBalance_clicks(this.balance_clicks);
        ad.setCoupon_id(this.coupon_id);
        return ad;
    }

    public AdCMSDTO getAdCMSDTO() {
        AdCMSDTO adDTO = new AdCMSDTO();
        adDTO.setClosed_on(this.closed_on);
        adDTO.setStarted_on(this.started_on);
        adDTO.setId(this.id);
        adDTO.setClick_url(this.click_url);
        adDTO.setClick_type(this.click_type);
        adDTO.setType(this.type);
        adDTO.setCoupon_id(this.coupon_id);
        adDTO.setStatus(this.status);
        adDTO.setPartner_id(this.partner_id);
        adDTO.setBalance_amount(this.balance_amount);
        adDTO.setBalance_clicks(this.balance_clicks);
        adDTO.setGenders_str(this.genders);
        adDTO.setGenre(this.getGenre());
        adDTO.setCountry(this.getCountry());
        adDTO.setState(this.getState());
        adDTO.setAge_groups_str(this.getAge_groups());
        adDTO.setNationality(this.getNationality());
        adDTO.setPriority(this.getPriority());
        adDTO.setCoupon_id(this.getCoupon_id());
        return adDTO;
    }

    @Override
    public String toString() {
        return "AdEntity{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", click_url='" + click_url + '\'' +
                ", click_type='" + click_type + '\'' +
                ", status='" + status + '\'' +
                ", coupon_id=" + coupon_id +
                ", partner_id=" + partner_id +
                ", balance_clicks=" + balance_clicks +
                ", balance_amount=" + balance_amount +
                ", assetEntityList=" + assetEntityList +
                ", started_on=" + started_on +
                ", closed_on=" + closed_on +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", genre='" + genre + '\'' +
                ", nationality='" + nationality + '\'' +
                ", priority=" + priority +
                ", age_groups='" + age_groups + '\'' +
                ", genders='" + genders + '\'' +
                '}';
    }
}
