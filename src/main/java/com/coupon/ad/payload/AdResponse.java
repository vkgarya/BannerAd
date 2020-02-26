package com.coupon.ad.payload;

import com.coupon.ad.bean.Ad;
import com.coupon.bean.StatusDTO;
import com.coupon.user.bean.Partner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class AdResponse {
    private String status;
    private String message;
    private Integer status_code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String txn_id;
    @JsonIgnore
    private StatusDTO statusDTO;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Ad> results;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String link;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Partner> partners;


    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public List<Ad> getResults() {
        return results;
    }

    public void setResults(List<Ad> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }

    public AdResponse(String txn_id, StatusDTO statusDTO, List<Ad> results) {
        this.txn_id = txn_id;
        this.status=statusDTO.getStatus();
        this.status_code=statusDTO.getStatus_code();
        this.message=statusDTO.getDescription();
        this.results = results;
    }

    @Override
    public String toString() {
        return "AdResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", status_code=" + status_code +
                ", txn_id='" + txn_id + '\'' +
                ", statusDTO=" + statusDTO +
                ", results=" + results +
                ", link='" + link + '\'' +
                ", partners=" + partners +
                '}';
    }
}
