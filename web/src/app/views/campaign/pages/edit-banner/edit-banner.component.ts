import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';

import { isValidUrl } from 'src/app/utilities/FormInputValidators';
import { BannerService } from 'src/app/core/services/banner/banner.service';
import { markFormGroupTouched } from 'src/app/utilities/MarkFormGroupAsTouched';
import { PartnersService } from 'src/app/core/services/partners/partners.service';

@Component({
  selector: 'app-edit-banner',
  templateUrl: './edit-banner.component.html',
  styleUrls: ['./edit-banner.component.scss']
})
export class EditBannerComponent implements OnInit {

  @Input() set bannerId(bannerId: number) {
    if (bannerId) {
      this.getDetailsOfBanner(bannerId);
    }
  }
  @Output() backToList: EventEmitter<any> = new EventEmitter<any>();
  editBannerForm: FormGroup;
  ageGroups = [
    { value: 'below18', name: 'Below 18' },
    { value: '18to25', name: '18 to 25' },
    { value: '26to35', name: '26 to 35' },
    { value: '36to45', name: '36 to 45' },
    { value: 'above45', name: 'Above 45' },
    
  ];
  genders = [
    { value: 'male', name: 'Male' },
    { value: 'female', name: 'Female' },
    
  ];
  bannerDetails: any;
  removedBannerFiles = [];
  disableEditBannerButton = false;
  partnersList: any;

  constructor(private readonly _formBuilder: FormBuilder, private readonly _bannerService: BannerService, private readonly _partnerService: PartnersService) { }

  ngOnInit() {
    this.getPartnersList();
    this.editBannerForm = this._formBuilder.group({
      partner: ['', Validators.required],
      country: ['', Validators.required],
      state: ['', Validators.required],
      clickType: ['', Validators.required],
      targetUrl: ['', [Validators.required, isValidUrl]],
      status: ['', Validators.required],
      bannerFiles: this._formBuilder.array([]),
      ageGroups: this._formBuilder.array([
        this._formBuilder.control(false),
        this._formBuilder.control(false),
        this._formBuilder.control(false),
        this._formBuilder.control(false),
        this._formBuilder.control(false),
        

      ], Validators.required),
      genders: this._formBuilder.array([
        this._formBuilder.control(false),
        this._formBuilder.control(false),
        
      ], Validators.required),
      genre: ['', Validators.required],
      nationality: ['', Validators.required],
      priority: ['', Validators.required],
      campaignLimit: this._formBuilder.group({
        limit: ['clicks', Validators.required],
        value: ['', Validators.required]
      }),
      startEndDate: ['', Validators.required]
      // startEndDate: [this.selectDates(), Validators.required]
    });
  }

  getPartnersList(): void {
    this._partnerService.getPartnersList().subscribe(partnersList => {
      //console.log(partnersList);
      this.partnersList = partnersList;
    });
  }

  getDetailsOfBanner(bannerId: number): void {
    this._bannerService.getBannerDetails(bannerId).subscribe(bannerDetails => {
      this.bannerDetails = bannerDetails.ad;
      console.log(bannerDetails);
      this.patchBannerFields(this.bannerDetails);
    });
  }

  patchBannerFields(bannerDetails: any): void {
    
    this.editBannerForm.patchValue({
      partner: bannerDetails.partner_id,
      country: bannerDetails.country,
      state: bannerDetails.state,
      clickType: bannerDetails.click_type,
      targetUrl: bannerDetails.click_url,
      status: bannerDetails.status,
      genre: bannerDetails.genre,
      nationality: bannerDetails.nationality,
      priority: bannerDetails.priority,
      campaignLimit: {
        limit: bannerDetails.balance_clicks > 0 ? 'clicks' : 'amount',
        value: bannerDetails.balance_clicks > 0 ? bannerDetails.balance_clicks : bannerDetails.balance_amount
      },
      startEndDate: [new Date(bannerDetails.started_on), new Date(bannerDetails.closed_on)]
    });

    if (bannerDetails.genders) {
      bannerDetails.genders.forEach(gender => {
        let genderIndex = this.genders.findIndex(genderOrg => genderOrg.value === gender);
        if (genderIndex > -1) {
          (this.editBannerForm.get('genders') as FormArray).controls[genderIndex].setValue(true);
        }
      });
    }

    if (bannerDetails.age_groups) {
      bannerDetails.age_groups.forEach(ageGroup => {
        let ageGroupIndex = this.ageGroups.findIndex(ageGroupOrg => ageGroupOrg.value === ageGroup);
        if (ageGroupIndex > -1) {
          (this.editBannerForm.get('ageGroups') as FormArray).controls[ageGroupIndex].setValue(true);
        }
      });
    }

    //console.log(this.editBannerForm.value);
  }

  onChangeClickType(): void {
    let clickType = this.editBannerForm.get('clickType').value;
    (this.editBannerForm.get('targetUrl') as FormControl).setValue('');
    if (clickType === 'Inapp' || clickType === 'External') {
      (this.editBannerForm.get('targetUrl') as FormControl).setValidators([Validators.required, isValidUrl]);
    } else {
      (this.editBannerForm.get('targetUrl') as FormControl).clearValidators();
      (this.editBannerForm.get('targetUrl') as FormControl).setValidators(Validators.required);
    }
  }

  onChooseFile(event: any, ind: number): void {
    let file = event.target.files[0];
    (this.editBannerForm.get('bannerFiles') as FormArray).controls[ind].get('file').setValue(file);
    let fr: FileReader = new FileReader();
    let bannerWidth = (this.editBannerForm.get('bannerFiles') as FormArray).controls[ind].get('bannerWidth').value;
    let bannerHeight = (this.editBannerForm.get('bannerFiles') as FormArray).controls[ind].get('bannerHeight').value;

    fr.onload = () => {
      let image: any = new Image();

      image.onload = () => {
        if (image.width !== bannerWidth && image.height !== bannerHeight) {
          alert('Please choose the image as per selected format dimensions only');
          (this.editBannerForm.get('bannerFiles') as FormArray).controls[ind].reset({
            file: '',
            fileName: '',
            format: '',
            bannerWidth: '',
            bannerHeight: ''
          });
        }
      };

      image.src = fr.result; // This is the data URL 
    };
    fr.readAsDataURL(file);
  }

  onSelectBannerFormat(ind: number): void {
    (this.editBannerForm.get('bannerFiles') as FormArray).controls[ind].get('file').setValue('');
    let bannerFormat = (this.editBannerForm.get('bannerFiles') as FormArray).controls[ind].get('format').value;
    //console.log(bannerFormat);
    let bannerDimensions = bannerFormat.split('*');

    (this.editBannerForm.get('bannerFiles') as FormArray).controls[ind].get('bannerWidth').setValue(Number(bannerDimensions[0]));
    (this.editBannerForm.get('bannerFiles') as FormArray).controls[ind].get('bannerHeight').setValue(Number(bannerDimensions[1]));
  }

  addMoreBannerFiles(): void {
    let bannerFile = this._formBuilder.group({
      format: ['', Validators.required],
      file: ['', Validators.required],
      fileName: ['', Validators.required],
      bannerWidth: [''],
      bannerHeight: ['']
    });

    (this.editBannerForm.get('bannerFiles') as FormArray).push(bannerFile);
  }

  onRemoveBannerFile(ind: number): void {
    (this.editBannerForm.get('bannerFiles') as FormArray).removeAt(ind);
  }

  onRemoveExistingBanner(ind: number): void {
    let bannerFileId = this.bannerDetails.banners[ind].id;
    this.bannerDetails.banners.splice(ind, 1);
    this.removedBannerFiles.push(bannerFileId);
  }

  onEditBanner(): any {
    //let editBannerData = this.editBannerForm.value;
    //console.log(editBannerData);
    if (this.editBannerForm.valid) {
      let editBannerData = this.editBannerForm.value;

      let atleastAgeGroupSelected = false;
      editBannerData.ageGroups.forEach(ageGroup => {
        if (ageGroup) {
          atleastAgeGroupSelected = true;
        }
      });

      if (!atleastAgeGroupSelected) {
        alert("Please choose atleast one age group");
        return false;
      }

      let atleastGenderAdded = false;
      editBannerData.genders.forEach(gender => {
        if (gender) {
          atleastGenderAdded = true;
        }
      });

      if (!atleastGenderAdded) {
        alert("Please choose atleast one gender");
        return false;
      }

      if (editBannerData.bannerFiles.length === 0 && this.bannerDetails.banners.length === 0) {
        alert("Please upload atleast one banner campign file");
        return false;
      }

      this.disableEditBannerButton = true;

      let startDateLocale = editBannerData.startEndDate[0].toLocaleString('en-GB', { hour12: false }).split(', ');
      let endDateLocale = editBannerData.startEndDate[1].toLocaleString('en-GB', { hour12: false }).split(', ');
      let startDate = startDateLocale[0].split('/');
      let endDate = endDateLocale[0].split('/');

      console.log(this.bannerDetails.coupon_id);
      let bannerData: any = {
        id: this.bannerDetails.id,
        coupon_id: this.bannerDetails.coupon_id ? this.bannerDetails.coupon_id : null,
        partner_id: editBannerData.partner,
        country: editBannerData.country,
        state: editBannerData.state,
        click_type: editBannerData.clickType,
        click_url: editBannerData.targetUrl,
        nationality: editBannerData.nationality,
        priority: editBannerData.priority,
        status: editBannerData.status,
        balance_amount: (editBannerData.campaignLimit.limit === 'amount') ? editBannerData.campaignLimit.value : 0,
        balance_clicks: (editBannerData.campaignLimit.limit === 'clicks') ? editBannerData.campaignLimit.value : 0,
        type: 'banner',
        genre: editBannerData.genre,
        started_on: `${startDate[2]}-${startDate[1]}-${startDate[0]}T00:00:00+0400`,
        closed_on: `${endDate[2]}-${endDate[1]}-${endDate[0]}T00:00:00+0400`,
        deleted_asset_ids: this.removedBannerFiles
      };

      let ageGroups = [];
      editBannerData.ageGroups.forEach((ageGroup, index) => {
        if (ageGroup) {
          ageGroups.push(this.ageGroups[index].value);
        }
      });

      bannerData.age_groups = ageGroups;

      let genders = [];
      editBannerData.genders.forEach((gender, index) => {
        if (gender) {
          genders.push(this.genders[index].value);
        }
      });
      bannerData.genders = genders;

      let bannerFormData: FormData = new FormData();

      let fileResolutions = [];
      editBannerData.bannerFiles.forEach(bannerFile => {
        bannerFormData.append('files', bannerFile.file);
        fileResolutions.push(bannerFile.format);
      });

      bannerData.file_resolutions = fileResolutions;

      const blobOverrides = new Blob([JSON.stringify(bannerData)], {
        type: 'application/json',
      });

      bannerFormData.append('data', blobOverrides);

      console.log(JSON.stringify(bannerData));
      //console.log(this.removedBannerFiles);
      
      this._bannerService.addBanner(bannerFormData).subscribe(res => {
        alert('Banner updated successfully!');
        //this._router.navigate(['/campaign/banner-list']);
        this.onBackToList();
      }, err => {
        this.disableEditBannerButton = false;
      });
    } else {
      markFormGroupTouched(this.editBannerForm);
    }
  }

  onBackToList(): void {
    this.backToList.emit();
  }

}
