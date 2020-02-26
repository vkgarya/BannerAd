import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
import { Router } from '@angular/router';

import { markFormGroupTouched } from 'src/app/utilities/MarkFormGroupAsTouched';
import { numberValidation, floatingNumberValidation } from 'src/app/utilities/FormInputValidators';

import { CouponService } from 'src/app/core/services/coupon/coupon.service';
import { CustomEventsService } from 'src/app/core/services/custom-events/custom-events.service';

@Component({
  selector: 'app-new-edit-coupon',
  templateUrl: './new-edit-coupon.component.html',
  styleUrls: ['./new-edit-coupon.component.scss']
})
export class NewEditCouponComponent implements OnInit {

  @Input() set couponId(couponId: number) {
    if (couponId) {
      this.getDetailsOfCoupon(couponId);
    }
  }
  @Output() backToList: EventEmitter<any> = new EventEmitter<any>();

  isEditable = true;

  monthsList = [
    { value: 1, label: 'January' },
    { value: 2, label: 'February' },
    { value: 3, label: 'March' },
    { value: 4, label: 'April' },
    { value: 5, label: 'May' },
    { value: 6, label: 'June' },
    { value: 7, label: 'July' },
    { value: 8, label: 'August' },
    { value: 9, label: 'September' },
    { value: 10, label: 'October' },
    { value: 11, label: 'November' },
    { value: 12, label: 'December' }
  ];

  weekDays = [
    'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa', 'Su'
  ];

  couponDetails: any;
  couponEditForm: FormGroup;
  customEventsList: any;
  customEventFields: any;
  disableAddCouponButton = false;
  minDate = new Date();
  
  constructor(private readonly _formBuilder: FormBuilder, private readonly _couponService: CouponService, private readonly _router: Router, private readonly _customEventsService: CustomEventsService) { }

  ngOnInit() {
    this.getCustomEventsList();
    this.couponEditForm = this._formBuilder.group({
      coupon: this._formBuilder.group({
        couponDescriptions: this._formBuilder.array([
          this._formBuilder.group({
            language: ['en', Validators.required],
            description: ['', Validators.required]
          })
        ]),
        couponCodes: this._formBuilder.array([
          this._formBuilder.group({
            language: ['en', Validators.required],
            code: ['', Validators.required]
          })
        ]),
        //startDate: ['', Validators.required],
        //endDate: ['', Validators.required],
        startEndDate: ['', Validators.required],
        allowOtherOffers: [false],
        couponType: ['offer', Validators.required],
        currency: ['AED', Validators.required],
        couponLimit: ['', Validators.required],
        couponLimitAmount: ['', Validators.required],
        minCartValue: ['', Validators.required],
        limitPerUser: ['', Validators.required],
        referralCodeReq: [false]
      }),
      offer: this._formBuilder.group({
        offerType: ['sku'],
        offerFileName: [''],
        offerFile: [''],
        buyQuantity: [''],
        buySkus: [''],
        getQuantity: [''],
        getSkus: [''],
        offerDescription: ['']
      }),

      categoryBased: this._formBuilder.group({
        categoryName: [''],
        quantity: ['']
      }),

      calendarBased: this._formBuilder.group({
        startTime: [''],
        endTime: [''],
        startAndEndDate: [''],
        month: [[]],
        weekDays: this._formBuilder.array([
          this._formBuilder.control(false),
          this._formBuilder.control(false),
          this._formBuilder.control(false),
          this._formBuilder.control(false),
          this._formBuilder.control(false),
          this._formBuilder.control(false),
          this._formBuilder.control(false),
        ])
      }),
      
      eventBased: this._formBuilder.group({
        event: ['']
      }),
      transactionBased: this._formBuilder.group({
        startAndEndDate: [''],
        transactionCondition: [''],
        transactions: [''],
        amountCondition: [''],
        amount: ['']
      }),
      productRestrictions: this._formBuilder.group({
        excludingSkuFileName: [''],
        excludingSkuFile: [''],
        includingSkuFileName: [''],
        includingSkuFile: ['']
      }),
    });
  }


  getDetailsOfCoupon(couponId: number): void {
    this._couponService.getCouponDetails(couponId).subscribe(couponDetails => {
      this.couponDetails = couponDetails.ad;
      console.log(couponDetails);
      this.patchCouponFields(this.couponDetails);
    });
  }

  patchCouponFields(couponDetails: any): void {

    this.couponEditForm.patchValue({

    });
  }

  getCustomEventsList(): void {
    this._customEventsService.getCustomEventsList().subscribe(customEventsList => {
      this.customEventsList = customEventsList;
    });
  }

  onAddCouponDescription(): void {
    let couponDescription = this._formBuilder.group({
      language: ['', Validators.required],
      description: ['', Validators.required]
    });
    (this.couponEditForm.get('coupon').get('couponDescriptions') as FormArray).push(couponDescription);
  }

  onDeleteCouponDescription(couponDescriptionIndex: number): void {
    (this.couponEditForm.get('coupon').get('couponDescriptions') as FormArray).removeAt(couponDescriptionIndex);
  }

  onAddCouponCode(): void {
    let couponCode = this._formBuilder.group({
      language: ['', Validators.required],
      code: ['', Validators.required]
    });
    (this.couponEditForm.get('coupon').get('couponCodes') as FormArray).push(couponCode);
  }

  onDeleteCouponCode(couponCodeIndex: number): void {
    (this.couponEditForm.get('coupon').get('couponCodes') as FormArray).removeAt(couponCodeIndex);
  }

  onSelectCouponType(): void {
    
    let couponType = this.couponEditForm.get('coupon').get('couponType').value;
    //console.log(couponType);
    if (this.couponEditForm.get('coupon').get('couponValue')) {
      (this.couponEditForm.get('coupon') as FormGroup).removeControl('couponValue');
    }

    if (couponType === 'flat') {
      (this.couponEditForm.get('coupon') as FormGroup).addControl('couponValue', new FormGroup({
        //currency: new FormControl('', Validators.required),
        amount: new FormControl('', Validators.required)
      }));
    } else if (couponType === 'percentage') {
      (this.couponEditForm.get('coupon') as FormGroup).addControl('couponValue', new FormGroup({
        minRebateValue: new FormControl('', Validators.required),
        maxRebateValue: new FormControl('', Validators.required)
      }));
    }
  }

  onSelectOfferType(): void {
    let offerType = this.couponEditForm.get('offer').get('offerType').value;
    let offerGroup = (this.couponEditForm.get('offer') as FormGroup);

    if (offerType === 'sku') {
      if (this.couponEditForm.get('offer').get('merchant')) {        
        offerGroup.removeControl('merchant');
        offerGroup.addControl('offerFileName', new FormControl(''));
        offerGroup.addControl('offerFile', new FormControl(''));
        offerGroup.addControl('buySkus', new FormControl(''));
        offerGroup.addControl('getSkus', new FormControl(''));
      }
    } else if (offerType === 'merchant') {
      offerGroup.removeControl('offerFileName');
      offerGroup.removeControl('offerFile');
      offerGroup.removeControl('buySkus');
      offerGroup.removeControl('getSkus');
      offerGroup.addControl('merchant', new FormControl(''));
    }
  }

  onChooseOfferFile(event): any {
    let offerGroup = (this.couponEditForm.get('offer') as FormGroup);

    if (event.target.files.length > 0) {
      let file = event.target.files[0];
      let fileNameSplit = file.name.split('.');
      let fileNameExt = fileNameSplit[fileNameSplit.length - 1];
      if (fileNameExt === 'xlsx') {
        offerGroup.get('offerFile').setValue(file);
        this.setOfferFormValidations(offerGroup);
        offerGroup.removeControl('buySkus');
        offerGroup.removeControl('getSkus');
      } else {
        offerGroup.get('offerFileName').setValue('');
        offerGroup.get('offerFile').setValue('');
        offerGroup.addControl('buySkus', new FormControl(''));
        offerGroup.addControl('getSkus', new FormControl(''));
        this.clearOfferFormValidations(offerGroup);
        alert('Please upload excel format file only');
      }
    } else {
      let offerGroup = (this.couponEditForm.get('offer') as FormGroup);
      offerGroup.get('offerFileName').setValue('');
      offerGroup.get('offerFile').setValue('');
      offerGroup.addControl('buySkus', new FormControl(''));
      offerGroup.addControl('getSkus', new FormControl(''));
      this.clearOfferFormValidations(offerGroup);
    }
  }

  onEnterMerchantId(): void {
    let merchantId = this.couponEditForm.get('offer').get('merchant').value;
    let offerGroup = (this.couponEditForm.get('offer') as FormGroup);
    if (merchantId !== '') {
      this.setOfferFormValidations(offerGroup);
    } else {
      this.clearOfferFormValidations(offerGroup);
    }
  }

  setOfferFormValidations(offerGroup: FormGroup): void {
    offerGroup.get('buyQuantity').setValidators([Validators.required]);
    offerGroup.get('buyQuantity').updateValueAndValidity();
    offerGroup.get('getQuantity').setValidators([Validators.required]);
    offerGroup.get('getQuantity').updateValueAndValidity();
    offerGroup.get('offerDescription').setValidators([Validators.required]);
    offerGroup.get('offerDescription').updateValueAndValidity();
    offerGroup.updateValueAndValidity();
  }

  clearOfferFormValidations(offerGroup: FormGroup): void {
    offerGroup.get('buyQuantity').clearValidators();
    offerGroup.get('buyQuantity').updateValueAndValidity();
    offerGroup.get('getQuantity').clearValidators();
    offerGroup.get('getQuantity').updateValueAndValidity();
    offerGroup.get('offerDescription').clearValidators();
    offerGroup.get('offerDescription').updateValueAndValidity();
    offerGroup.updateValueAndValidity();
  }

  onSelectCategoryName(): void {
    let categoryFormGroup = (this.couponEditForm.get('categoryBased') as FormGroup);
    let categoryName = categoryFormGroup.get('categoryName').value;

    if (categoryName !== '') {
      this.setCategoryValidatons(categoryFormGroup);
    } else {
      this.removeCategoryValidations(categoryFormGroup);
    }
  }

  setCategoryValidatons(categoryFormGroup: FormGroup): void {
    categoryFormGroup.get('quantity').setValidators(Validators.required);
    categoryFormGroup.get('quantity').updateValueAndValidity();
  }

  removeCategoryValidations(categoryFormGroup: FormGroup): void {
    categoryFormGroup.get('quantity').clearValidators();
    categoryFormGroup.get('quantity').updateValueAndValidity();
  }

  onEnterStartTime(): void {
    let calendarFormGroup = (this.couponEditForm.get('calendarBased') as FormGroup);
    let startTime = calendarFormGroup.get('startTime').value;

    if (startTime !== '') {
      calendarFormGroup.get('endTime').setValidators(Validators.required);
      calendarFormGroup.get('endTime').updateValueAndValidity();
    } else {
      calendarFormGroup.get('endTime').clearValidators();
      calendarFormGroup.get('endTime').updateValueAndValidity();
    }
  }

  onSelectEvent(): void {
    let eventInd = this.couponEditForm.get('eventBased').get('event').value;
    let eventGroup = (this.couponEditForm.get('eventBased') as FormGroup);
    if (eventInd !== '') {
      this.customEventFields = this.customEventsList[eventInd].fields;
      eventGroup.addControl('key', new FormControl('', Validators.required));
      eventGroup.addControl('condition', new FormControl('', Validators.required));
    } else {
      eventGroup.removeControl('key');
      eventGroup.removeControl('condition');
      this.removeEventFields(eventGroup);
    }
  }

  onSelectEventField(): void {
    let eventFieldInd = this.couponEditForm.get('eventBased').get('key').value;
    let eventGroup = (this.couponEditForm.get('eventBased') as FormGroup);
    this.removeEventFields(eventGroup);
    if (eventFieldInd !== '') {
      if (this.customEventFields[eventFieldInd].mandatory) {
        eventGroup.addControl('type', new FormControl(this.customEventFields[eventFieldInd].type));
                
        if (this.customEventFields[eventFieldInd].type === 'intType') {
          eventGroup.addControl('value', new FormControl('', [Validators.required, numberValidation]));
        } else if (this.customEventFields[eventFieldInd].type === 'floatType') {
          eventGroup.addControl('value', new FormControl('', [Validators.required, floatingNumberValidation]));
        } else {
          eventGroup.addControl('value', new FormControl('', Validators.required));
        }

        if (this.customEventFields[eventFieldInd].type === 'datetimeType') {
          eventGroup.addControl('time', new FormControl('', Validators.required));
        }
      } else {
        eventGroup.addControl('type', new FormControl(this.customEventFields[eventFieldInd].field_type));
        
        if (this.customEventFields[eventFieldInd].type === 'intType') {
          eventGroup.addControl('value', new FormControl('', numberValidation));
        } else if (this.customEventFields[eventFieldInd].type === 'floatType') {
          eventGroup.addControl('value', new FormControl('', floatingNumberValidation));
        } else {
          eventGroup.addControl('value', new FormControl(''));
        }

        if (this.customEventFields[eventFieldInd].type === 'datetimeType') {
          eventGroup.addControl('time', new FormControl(''));
        }
      }
    }
  }

  removeEventFields(eventGroup: FormGroup): void {
    eventGroup.removeControl('value');
    eventGroup.removeControl('type');
    eventGroup.removeControl('time');
  }

  onSelectDatesInTran(): void {
    let transactionFormGroup = (this.couponEditForm.get('transactionBased') as FormGroup);
    setTimeout(() => {
      if (transactionFormGroup.get('startAndEndDate').value !== '') {
        transactionFormGroup.get('transactionCondition').setValidators(Validators.required);
        transactionFormGroup.get('transactionCondition').updateValueAndValidity();
        transactionFormGroup.get('transactions').setValidators(Validators.required);
        transactionFormGroup.get('transactions').updateValueAndValidity();
        transactionFormGroup.get('amountCondition').setValidators(Validators.required);
        transactionFormGroup.get('amountCondition').updateValueAndValidity();
        transactionFormGroup.get('amount').setValidators(Validators.required);
        transactionFormGroup.get('amount').updateValueAndValidity();
      } else {
        transactionFormGroup.get('transactionCondition').clearValidators();
        transactionFormGroup.get('transactionCondition').updateValueAndValidity();
        transactionFormGroup.get('transactions').clearValidators();
        transactionFormGroup.get('transactions').updateValueAndValidity();
        transactionFormGroup.get('amountCondition').clearValidators();
        transactionFormGroup.get('amountCondition').updateValueAndValidity();
        transactionFormGroup.get('amount').clearValidators();
        transactionFormGroup.get('amount').updateValueAndValidity();
      }
    }, 500);
  }

  onChooseExcludingSkuFile(event: any): void {
    if (event.target.files.length > 0) {
      let file = event.target.files[0];
      //console.log(file);
      //file.type === 'application/vnd.ms-excel' || file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      let fileNameSplit = file.name.split('.');
      let fileNameExt = fileNameSplit[fileNameSplit.length - 1];
      if (fileNameExt === 'xlsx') {
        this.couponEditForm.get('productRestrictions').get('excludingSkuFile').setValue(file);
      } else {
        this.couponEditForm.get('productRestrictions').get('excludingSkuFileName').setValue('');
        this.couponEditForm.get('productRestrictions').get('excludingSkuFile').setValue('');
        alert('Please upload excel format file only');
      }
    } else {
      this.couponEditForm.get('productRestrictions').get('excludingSkuFileName').setValue('');
      this.couponEditForm.get('productRestrictions').get('excludingSkuFile').setValue('');
    }
  }

  onChooseIncludingSkuFile(event: any): void {
    if (event.target.files.length > 0) {
      let file = event.target.files[0];
      let fileNameSplit = file.name.split('.');
      let fileNameExt = fileNameSplit[fileNameSplit.length - 1];
      if (fileNameExt === 'xlsx') {
        this.couponEditForm.get('productRestrictions').get('includingSkuFile').setValue(file);
      } else {
        this.couponEditForm.get('productRestrictions').get('includingSkuFileName').setValue('');
        this.couponEditForm.get('productRestrictions').get('includingSkuFile').setValue('');
        alert('Please upload excel format file only');
      }
    } else {
      this.couponEditForm.get('productRestrictions').get('includingSkuFileName').setValue('');
      this.couponEditForm.get('productRestrictions').get('includingSkuFile').setValue('');
    }
  }

  markFormGroupAsTouched(formGroup: FormGroup): void {
    markFormGroupTouched(formGroup);
  }

  onEditNewCoupon(): void {
    if (this.couponEditForm.valid) {
      this.disableAddCouponButton = true;
      let couponEditFormData = this.couponEditForm.value.coupon;
      let offerFormData = this.couponEditForm.value.offer;
      let categoryFormData = this.couponEditForm.value.categoryBased;
      let calendarFormData = this.couponEditForm.value.calendarBased;
      let transactionFormData = this.couponEditForm.value.transactionBased;
      let eventData = this.couponEditForm.value.eventBased;

      let startDateLocaleInCoupon = couponEditFormData.startEndDate[0].toLocaleString('en-GB', { hour12: false }).split(', ');
      let endDateLocaleInCoupon = couponEditFormData.startEndDate[1].toLocaleString('en-GB', { hour12: false }).split(', ');
      let startDateInCoupon = startDateLocaleInCoupon[0].split('/');
      let endDateInCoupon = endDateLocaleInCoupon[0].split('/');

      let couponData: any = {
        currency: couponEditFormData.currency,
        coupon_type: couponEditFormData.couponType,
        limit_per_user: couponEditFormData.limitPerUser,
        coupon_limit: couponEditFormData.couponLimit,
        coupon_limit_amount: couponEditFormData.couponLimitAmount,
        discount_percentage: couponEditFormData.couponValue ? couponEditFormData.couponValue.minRebateValue ? couponEditFormData.couponValue.minRebateValue : null : null,
        is_manual: true,
        min_cart_value: couponEditFormData.minCartValue,
        max_discount: couponEditFormData.couponValue ? couponEditFormData.couponValue.maxRebateValue ? couponEditFormData.couponValue.maxRebateValue : couponEditFormData.couponValue.amount ? couponEditFormData.couponValue.amount : null : null,
        is_mergeable: couponEditFormData.allowOtherOffers,
        started_on: `${startDateInCoupon[2]}-${startDateInCoupon[1]}-${startDateInCoupon[0]}T${startDateLocaleInCoupon[1]}+0400`,
        closed_on: `${endDateInCoupon[2]}-${endDateInCoupon[1]}-${endDateInCoupon[0]}T${endDateLocaleInCoupon[1]}+0400`,
        referral_code_required: couponEditFormData.referralCodeReq,
        offer_rules: [
        ],
        category_rules: [
        ],
        calendar_rules: [
        ],
        transaction_rules: [
        ],
        event_rules: [
        ]
      };

      let couponDescriptions = [];
      couponEditFormData.couponDescriptions.forEach((couponDescription: any) =>{
        couponDescriptions.push({
          language: couponDescription.language,
          value: couponDescription.description
        });
      });

      couponData.descriptions = couponDescriptions;

      let couponCodes = [];
      couponEditFormData.couponCodes.forEach(couponCode => {
        couponCodes.push({
          language: couponCode.language,
          value: couponCode.code
        });
      });

      couponData.codes = couponCodes;

      if (offerFormData.merchantId !== '' || offerFormData.offerFileName !== '') {
        couponData.offer_rules.push({
          offer_type: offerFormData.offerType,
          buy_skus: offerFormData.buySkus ? offerFormData.buySkus : "",
          buy_quantity: offerFormData.buyQuantity,
          offer_skus: offerFormData.getSkus ? offerFormData.getSkus : "",
          offer_quantity: offerFormData.getQuantity,
          offer_description: offerFormData.offerDescription,
          merchant_id: offerFormData.merchant ? offerFormData.merchant : null,
          is_multiple: false
        });
      }

      if (categoryFormData.categoryName !== '') {
        couponData.category_rules.push({
          category_name: categoryFormData.categoryName,
          quantity: categoryFormData.quantity
        });
      }

      if (calendarFormData.startTime !== '') {
        couponData.calendar_rules.push({
          cal_type: 'hour',
          exact_match: true,
          type_from: calendarFormData.startTime,
          type_to: calendarFormData.endTime,
          relation: "and"
        });
      }

      if (calendarFormData.startAndEndDate !== '') {
        let startDateLocaleInCal = calendarFormData.startAndEndDate[0].toLocaleString('en-GB', { hour12: false }).split(', ');
        let endDateLocaleInCal = calendarFormData.startAndEndDate[1].toLocaleString('en-GB', { hour12: false }).split(', ');
        let startDateInCal = startDateLocaleInCoupon[0].split('/');
        let endDateInCal = endDateLocaleInCoupon[0].split('/');

        couponData.calendar_rules.push({
          cal_type: 'date',
          exact_match: true,
          type_from: `${startDateInCal[2]}-${startDateInCal[1]}-${startDateInCal[0]}T${startDateLocaleInCal[1]}+0400`,
          type_to: `${endDateInCal[2]}-${endDateInCal[1]}-${endDateInCal[0]}T${endDateLocaleInCal[1]}+0400`,
          relation: "and"
        });
      }

      if (calendarFormData.month.length > 0) {
        couponData.calendar_rules.push({
          cal_type: 'month',
          exact_match: true,
          type_from: calendarFormData.month.join(),
          type_to: undefined,
          relation: "and"
        });
      }

      let weekDays = calendarFormData.weekDays.map((weekDay, index) => {
        if (weekDay) {
          return (index + 1);
        }
      }).filter(weekDay => weekDay);

      if (weekDays.length > 0) {
        couponData.calendar_rules.push({
          cal_type: 'day',
          exact_match: true,
          type_from: weekDays.join(),
          type_to: undefined,
          relation: "and"
        });
      }

      if (transactionFormData.startAndEndDate !== '') {
        let startDateLocaleInTran = transactionFormData.startAndEndDate[0].toLocaleString('en-GB', { hour12: false }).split(', ');
        let endDateLocaleInTran = transactionFormData.startAndEndDate[1].toLocaleString('en-GB', { hour12: false }).split(', ');
        let startDateInTran = startDateLocaleInTran[0].split('/');
        let endDateInTran = endDateLocaleInTran[0].split('/');

        couponData.transaction_rules.push({
          cal_type: "day",
          type_from: `${startDateInTran[2]}-${startDateInTran[1]}-${startDateInTran[0]}T${startDateLocaleInTran[1]}+0400`,
          type_to: `${endDateInTran[2]}-${endDateInTran[1]}-${endDateInTran[0]}T${endDateLocaleInTran[1]}+0400`,
          transaction_number: transactionFormData.transactions,
          condition: transactionFormData.transactionCondition,
          fields: {
            amount: transactionFormData.amount,
            condition: transactionFormData.amountCondition
          },
          relation: "and"
        });
      }

      if (eventData.event !== '') {

        let eventFieldData: any = {
          key: this.customEventFields[eventData.key].field_name,
          condition: eventData.condition,
          type: eventData.type
        };

        if (eventFieldData.type === 'datetimeType') {
          eventFieldData.value = `${eventData.value}T${eventData.time}:00+0400`
        } else {
          eventFieldData.value = eventData.value;
        }

        couponData.event_rules.push({
          event_id: this.customEventsList[eventData.event].id,
          fields: [
            eventFieldData
          ],
          relation: "and"
        });
      }

      let couponReqFormData = new FormData();
      if (offerFormData.offerFileName !== '') {
        couponReqFormData.append('offer_file', offerFormData.offerFile);
      }
      //console.log(couponData);
      //console.log(JSON.stringify(couponData));

      const blobOverrides = new Blob([JSON.stringify(couponData)], {
        type: 'application/json',
      });

      couponReqFormData.append('data', blobOverrides);
      
      this._couponService.editCoupon(couponReqFormData).subscribe(couponRes => {
        if (confirm('Do you want to add banner campaign for this coupon?')) {
          this._router.navigate(['/campaign/add-banner'], { queryParams: { couponId: couponRes.id } });
        } else {
          this._router.navigate(['/campaign/coupon-list']);
        }
      }, err => {
        this.disableAddCouponButton = false;
      });
    } else {
      markFormGroupTouched(this.couponEditForm);
    }
  }

  validateFormGroup(formGroup: FormGroup): void {
    markFormGroupTouched(formGroup);
  }

  onBackToList(): void {
    this.backToList.emit();
  }

}
