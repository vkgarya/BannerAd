package com.coupon.ad.bean.jpa;

import com.coupon.ad.bean.AdAsset;
import com.coupon.ad.bean.AdAssetCMSDTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ad_asset")
public class AdAssetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ad_id_ref", referencedColumnName = "id")
    private AdEntity adEntity;

    @Column(name = "aspect_ratio", nullable = false, length = 30)
    private String aspect_ratio;

    @Column(name = "resolution", nullable = false, length = 30)
    private String resolution;

    @Column(name = "link", nullable = false, length = 250)
    private String link;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(String aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public AdEntity getAdEntity() {
        return adEntity;
    }

    public void setAdEntity(AdEntity adEntity) {
        this.adEntity = adEntity;
    }

    public AdAssetEntity() {
        super();
    }

    public AdAssetEntity(AdAsset adAsset) {
        super();
        this.aspect_ratio = adAsset.getAspect_ratio();
        this.id = adAsset.getId();
        this.link = adAsset.getLink();
        this.resolution = adAsset.getResolution();
    }

    public AdAsset getAdAsset() {
        AdAsset adAsset = new AdAsset();
        adAsset.setId(this.id);
        adAsset.setAspect_ratio(this.aspect_ratio);
        adAsset.setLink(this.link);
        adAsset.setResolution(this.resolution);
        return adAsset;
    }

    public AdAssetCMSDTO getAdAssetCMSDTO() {
        AdAssetCMSDTO adAssetCMSDTO = new AdAssetCMSDTO();
        adAssetCMSDTO.setId(this.id);
        adAssetCMSDTO.setAspect_ratio(this.aspect_ratio);
        adAssetCMSDTO.setLink(this.link);
        adAssetCMSDTO.setResolution(this.resolution);
        return adAssetCMSDTO;
    }
}
