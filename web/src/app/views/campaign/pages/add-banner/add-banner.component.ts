import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { filter } from 'rxjs/operators';

import { TestService } from 'src/app/core/services/test/test.service';
import { isValidUrl } from 'src/app/utilities/FormInputValidators';
import { markFormGroupTouched } from 'src/app/utilities/MarkFormGroupAsTouched';
import { BannerService } from 'src/app/core/services/banner/banner.service';
import { PartnersService } from 'src/app/core/services/partners/partners.service';

@Component({
  selector: 'app-add-banner',
  templateUrl: './add-banner.component.html',
  styleUrls: ['./add-banner.component.scss']
})
export class AddBannerComponent implements OnInit {

  addBannerForm: FormGroup;
  testForm: FormGroup;
  ageGroups = [
    { value: 'below18', name: 'Below 18' },
    { value: '18to25', name: '18 to 25' },
    { value: '26to35', name: '26 to 35' },
    { value: '36to45', name: '36 to 45' },
    { value: 'above45', name: 'Above 45' }
  ];
  genders = [
    { value: 'male', name: 'Male' },
    { value: 'female', name: 'Female' }
  ];
  couponId: any;
  disableAddBannerButton = false;
  minDate = new Date();
  partnersList: any;

  constructor(private readonly _formBuilder: FormBuilder, private readonly _testService: TestService, private readonly _bannerService: BannerService, private readonly _router: Router, private _activatedRoute: ActivatedRoute, private readonly _partnerService: PartnersService) { }

  ngOnInit() {
    this.getPartnersList();
    this._activatedRoute.queryParams.subscribe(params => {
      if (params.couponId) {
        this.couponId = params.couponId;
      }
    });

    this.addBannerForm = this._formBuilder.group({
      partner: ['', Validators.required],
      country: ['uae', Validators.required],
      state: ['all', Validators.required],
      clickType: ['Inapp', Validators.required],
      targetUrl: ['', [Validators.required, isValidUrl]],
      status: ['inactive', Validators.required],
      bannerFiles: this._formBuilder.array([
        this._formBuilder.group({
          format: ['320*240', Validators.required],
          fileName: ['', Validators.required],
          file: ['', Validators.required],
          bannerWidth: [''],
          bannerHeight: ['']
        })
      ]),
      ageGroups: this._formBuilder.array([
        this._formBuilder.control(true),
        this._formBuilder.control(true),
        this._formBuilder.control(true),
        this._formBuilder.control(true),
        this._formBuilder.control(true)

      ], Validators.required),
      genders: this._formBuilder.array([
        this._formBuilder.control(true),
        this._formBuilder.control(true)
      ], Validators.required),
      genre: ['all', Validators.required],
      nationality: ['all', Validators.required],
      priority: ['0', Validators.required],
      campaignLimit: this._formBuilder.group({
        limit: ['clicks', Validators.required],
        value: ['', Validators.required]
      }),
      startEndDate: ['', Validators.required]
      // startEndDate: [this.selectDates(), Validators.required]
    });

    this.testForm = this._formBuilder.group({
      name: ['', Validators.required],
      file: ['', Validators.required]
    });

    this.onSelectBannerFormat(0);
  }

  getPartnersList(): void {
    this._partnerService.getPartnersList().subscribe(partnersList => {
      //console.log(partnersList);
      this.partnersList = partnersList;
      this.addBannerForm.get('partner').setValue(partnersList[2].id);
    });
  }

  onChangeClickType(): void {
    let clickType = this.addBannerForm.get('clickType').value;
    (this.addBannerForm.get('targetUrl') as FormControl).setValue('');
    if (clickType === 'Inapp' || clickType === 'External') {
      (this.addBannerForm.get('targetUrl') as FormControl).setValidators([Validators.required, isValidUrl]);
    } else {
      (this.addBannerForm.get('targetUrl') as FormControl).clearValidators();
      (this.addBannerForm.get('targetUrl') as FormControl).setValidators(Validators.required);
    }
  }

  onChooseFile(event: any, ind: number): void {
    let file = event.target.files[0];
    (this.addBannerForm.get('bannerFiles') as FormArray).controls[ind].get('file').setValue(file);
    let fr: FileReader = new FileReader();
    let bannerWidth = (this.addBannerForm.get('bannerFiles') as FormArray).controls[ind].get('bannerWidth').value;
    let bannerHeight = (this.addBannerForm.get('bannerFiles') as FormArray).controls[ind].get('bannerHeight').value;

    fr.onload = () => {
      let image: any = new Image();

      image.onload = () => {
        if (image.width !== bannerWidth && image.height !== bannerHeight) {
          alert('Please choose the image as per selected format dimensions only');
          (this.addBannerForm.get('bannerFiles') as FormArray).controls[ind].reset({
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
    (this.addBannerForm.get('bannerFiles') as FormArray).controls[ind].get('file').setValue('');
    let bannerFormat = (this.addBannerForm.get('bannerFiles') as FormArray).controls[ind].get('format').value;
    //console.log(bannerFormat);
    let bannerDimensions = bannerFormat.split('*');

    (this.addBannerForm.get('bannerFiles') as FormArray).controls[ind].get('bannerWidth').setValue(Number(bannerDimensions[0]));
    (this.addBannerForm.get('bannerFiles') as FormArray).controls[ind].get('bannerHeight').setValue(Number(bannerDimensions[1]));
  }

  addMoreBannerFiles(): void {
    let bannerFile = this._formBuilder.group({
      format: ['', Validators.required],
      file: ['', Validators.required],
      fileName: ['', Validators.required],
      bannerWidth: [''],
      bannerHeight: ['']
    });

    (this.addBannerForm.get('bannerFiles') as FormArray).push(bannerFile);
  }

  onRemoveBannerFile(ind: number): void {
    (this.addBannerForm.get('bannerFiles') as FormArray).removeAt(ind);
  }

  onAddBanner(): any {
    //let addBannerData = this.addBannerForm.value;
    //console.log(addBannerData);
    if (this.addBannerForm.valid) {
      let addBannerData = this.addBannerForm.value;

      let atleastAgeGroupSelected = false;
      addBannerData.ageGroups.forEach(ageGroup => {
        if (ageGroup) {
          atleastAgeGroupSelected = true;
        }
      });

      if (!atleastAgeGroupSelected) {
        alert("Please choose atleast one age group");
        return false;
      }

      let atleastGenderAdded = false;
      addBannerData.genders.forEach(gender => {
        if (gender) {
          atleastGenderAdded = true;
        }
      });

      if (!atleastGenderAdded) {
        alert("Please choose atleast one gender");
        return false;
      }

      this.disableAddBannerButton = true;

      let startDateLocale = addBannerData.startEndDate[0].toLocaleString('en-GB', { hour12: false }).split(', ');
      let endDateLocale = addBannerData.startEndDate[1].toLocaleString('en-GB', { hour12: false }).split(', ');
      let startDate = startDateLocale[0].split('/');
      let endDate = endDateLocale[0].split('/');

      let bannerData: any = {
        partner_id: addBannerData.partner,
        country: addBannerData.country,
        state: addBannerData.state,
        click_type: addBannerData.clickType,
        click_url: addBannerData.targetUrl,
        nationality: addBannerData.nationality,
        priority: addBannerData.priority,
        status: addBannerData.status,
        balance_amount: (addBannerData.campaignLimit.limit === 'amount') ? addBannerData.campaignLimit.value : 0,
        balance_clicks: (addBannerData.campaignLimit.limit === 'clicks') ? addBannerData.campaignLimit.value : 0,
        type: 'banner',
        genre: addBannerData.genre,
        started_on: `${startDate[2]}-${startDate[1]}-${startDate[0]}T00:00:00+0400`,
        closed_on: `${endDate[2]}-${endDate[1]}-${endDate[0]}T00:00:00+0400`
      };

      let ageGroups = [];
      addBannerData.ageGroups.forEach((ageGroup, index) => {
        if (ageGroup) {
          ageGroups.push(this.ageGroups[index].value);
        }
      });

      bannerData.age_groups = ageGroups;

      let genders = [];
      addBannerData.genders.forEach((gender, index) => {
        if (gender) {
          genders.push(this.genders[index].value);
        }
      });
      bannerData.genders = genders;

      let bannerFormData: FormData = new FormData();

      let fileResolutions = [];
      addBannerData.bannerFiles.forEach(bannerFile => {
        bannerFormData.append('files', bannerFile.file);
        fileResolutions.push(bannerFile.format);
      });

      bannerData.file_resolutions = fileResolutions;

      if (this.couponId) {
        bannerData.coupon_id = this.couponId;
      }

      const blobOverrides = new Blob([JSON.stringify(bannerData)], {
        type: 'application/json',
      });

      bannerFormData.append('data', blobOverrides);

      this._bannerService.addBanner(bannerFormData).subscribe(res => {
        alert('Banner created successfully!');
        this._router.navigate(['/campaign/banner-list']);
      }, err => {
        this.disableAddBannerButton = false;
      });
      //console.log(bannerData);
    } else {
      markFormGroupTouched(this.addBannerForm);
    }
  }

  onSubmitTestForm(): void {
    let testData = this.testForm.value;
    console.log(testData);

    const testFormData = new FormData();
    testFormData.append('file', testData.file);
    testFormData.append('name', testData.name);
    console.log(testFormData);

    this._testService.test(testFormData).subscribe(res => {
      console.log(res);
    });
  }

  onChooseFile2(event): void {
    this.testForm.get('file').setValue(event.target.files[0]);
  }

  backToList(): void {
    this._router.navigate(['/campaign/banner-list']);
  }
}
