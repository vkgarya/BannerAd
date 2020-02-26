import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { markFormGroupTouched } from 'src/app/utilities/MarkFormGroupAsTouched';
import { numberValidation, floatingNumberValidation } from 'src/app/utilities/FormInputValidators';

import { CouponService } from 'src/app/core/services/coupon/coupon.service';
import { CustomEventsService } from 'src/app/core/services/custom-events/custom-events.service';
import { PartnersService } from 'src/app/core/services/partners/partners.service';
import { element } from 'protractor';

@Component({
  selector: 'app-new-add-coupon',
  templateUrl: './new-add-coupon.component.html',
  styleUrls: ['./new-add-coupon.component.scss']
})
export class NewAddCouponComponent implements OnInit {

  isEditable = true;
  couponStart = new Date();
  pageTitleInfo = {
    title: 'Coupons/Add Coupon',
    icon: 'assets/images/coupon.png'
  };
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
  datesList = [];
  weekDays = [
    'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa', 'Su'
  ];
  couponForm: FormGroup;
  customEventsList: any;
  customEventFields: any;
  disableAddCouponButton = false;
  minDate = new Date();
  partnersList: any;
  couponId: number;
  applyManual: any;
  couponDetails: any;
  files = {
    offers: undefined,
    productsExcluded: undefined,
    productsIncluded: undefined,
    usersExcluded: undefined,
    usersIncluded: undefined
  };
  
  constructor(private route: ActivatedRoute, private readonly _formBuilder: FormBuilder, private readonly _couponService: CouponService,
     private readonly _router: Router, private readonly _customEventsService: CustomEventsService,
      private readonly _partnerService: PartnersService) {
    for (let i = 1; i <= 31; i++) {
      this.datesList.push({
        value: i,
        label: i
      });
    }
  }

  ngOnInit() {
    if (this.route.snapshot.paramMap.get('id')) {
      this.couponId = Number(this.route.snapshot.paramMap.get('id'));
    }
    
    this.getCustomEventsList();
    this.getPartnersList();
    this.couponForm = this._formBuilder.group({
      
      coupon: this._formBuilder.group({
        id: [''],
        applyManual: ['isManual', Validators.required],
        status: ['inactive', Validators.required],
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
        couponLimit: [''],
        couponLimitAmount: [''],
        minCartValue: [''],
        limitPerUser: [''],
        partner: [''],
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
        dates: [[]],
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
        indOrSum: ['individual'],
        amount: ['']
      }),
      productRestrictions: this._formBuilder.group({
        excludingSkuFileName: [''],
        excludingSkuFile: [''],
        includingSkuFileName: [''],
        includingSkuFile: ['']
      }),
    });

    if (this.couponId) {
      this.getDetailsOfCoupon(this.couponId);
    }
  }

  getDetailsOfCoupon(couponId: number): void {
    this._couponService.getCouponDetails(couponId).subscribe(couponDetails => {
      this.couponDetails = couponDetails;
      console.log(couponDetails);
      this.patchCouponFields(this.couponDetails);
    });
  }

  patchCouponFields(data) {
    let couponForm = this.couponForm.get('coupon');
    //adding manual
    if (data.is_manual) {
      couponForm.get('applyManual').setValue("isManual");
    } else {
      couponForm.get('applyManual').setValue("automatic");
    }

    //adding status
    couponForm.get('status').setValue(data.status);
   
    //adding descriptions
    let descs = data.descriptions;
    (couponForm.get('couponDescriptions') as FormArray).removeAt(0);
    descs.forEach(element => {
      let couponDescription = this._formBuilder.group({
        language: [element.language, Validators.required],
        description: [element.value, Validators.required]
      });

      (couponForm.get('couponDescriptions') as FormArray).push(couponDescription);
    });

    //adding codes
    let codes = data.codes;
    (couponForm.get('couponCodes') as FormArray).removeAt(0);
    codes.forEach(element => {
      let code = this._formBuilder.group({
        language: [element.language, Validators.required],
        code: [element.value, Validators.required]
      });
      (couponForm.get('couponCodes') as FormArray).push(code);
    });

    //adding start and end date
    
    //adding amount values
    couponForm.get('limitPerUser').setValue(data.limit_per_user);
    couponForm.get('couponLimit').setValue(data.coupon_limit);
    couponForm.get('couponLimitAmount').setValue(data.coupon_limit_amount);
    couponForm.get('couponType').setValue(data.coupon_type);
    couponForm.get('id').setValue(data.id);

    if (this.couponStart.valueOf() > new Date(data.started_on).valueOf()) {
      this.couponStart = new Date(data.started_on);
    }

    couponForm.get('startEndDate').setValue([new Date(data.started_on), new Date(data.closed_on)]);

    let couponType = data.coupon_type;

    if (couponType === 'flat') {
      (couponForm as FormGroup).addControl('couponValue', new FormGroup({
        //currency: new FormControl('', Validators.required),
        amount: new FormControl(data.max_discount, Validators.required)
      }));
    } else if (couponType === 'percentage') {
      (couponForm as FormGroup).addControl('couponValue', new FormGroup({
        minRebateValue: new FormControl(data.discount_percentage, Validators.required),
        maxRebateValue: new FormControl(data.max_discount, Validators.required)
      }));
    }
    
    couponForm.get('minCartValue').setValue(data.min_cart_value);
    couponForm.get("allowOtherOffers").setValue(data.is_mergeable);
    couponForm.get("referralCodeReq").setValue(data.referral_code_required);
    couponForm.get("partner").setValue(data.partner_id);

    this.patchOfferRules(data.offer_rules);
    this.patchCategoryRules(data.category_rules);
    this.patchCalenderRules(data.calendar_rules);
    this.patchTransactionRules(data.transaction_rules);
    this.patchEventRules(data.event_rules);
    this.patchUploadedFiles(data.files);
  }

  patchOfferRules(data) {
    let offerForm = this.couponForm.get('offer');
    data.forEach(element => {
      offerForm.get('buyQuantity').setValue(element.buy_quantity);
      offerForm.get('buySkus').setValue(element.buy_skus);
      offerForm.get('getQuantity').setValue(element.offer_quantity);
      offerForm.get('getSkus').setValue(element.offer_skus);
      offerForm.get('offerDescription').setValue(element.offer_description);
    });
  }

  patchCategoryRules(data) {
    let form = this.couponForm.get('categoryBased');
    data.forEach(element => {
      form.get('quantity').setValue(element.quantity);
      form.get('categoryName').setValue(element.category_name);
    });
  }

  patchCalenderRules(data) {
    let form = this.couponForm.get('calendarBased');
    let intSplit: any;
    data.forEach(element => {
      if (element.cal_type === 'hour') {
        form.get('startTime').setValue(element.type_from);
        form.get('endTime').setValue(element.type_to);
      } else if (element.cal_type === 'date') {
        intSplit = [];
        element.type_from.split(',').forEach(element => {
          intSplit.push(parseInt(element));
        });
        form.get('dates').setValue(intSplit);
      } else if(element.cal_type === 'month') {
        intSplit = [];
        element.type_from.split(',').forEach(element => {
          intSplit.push(parseInt(element));
        });
        form.get('month').setValue(intSplit);
      } else if(element.cal_type === 'day') {
        element.type_from.split(',').forEach(element => {
          (form.get('weekDays') as FormArray).controls[parseInt(element)].setValue(true);
        });
      }
    });
  }

  patchTransactionRules(data) {
    let form = this.couponForm.get('transactionBased');

    data.forEach(element => {
      form.get('transactions').setValue(element.transaction_number);
      form.get('transactionCondition').setValue(element.condition);

      let fields = element.fields;
      form.get('amountCondition').setValue(fields.condition);
      form.get('indOrSum').setValue(fields.ind_sum);
      form.get('amount').setValue(fields.amount);

      if (element.type_from != null && element.type_to != null) {
        form.get('startAndEndDate').setValue([new Date(element.type_from), new Date(element.type_to)]);
      }
    });
  }

  patchEventRules(data) {
    let form = this.couponForm.get('eventBased');
    let eventGroup = (this.couponForm.get('eventBased') as FormGroup);

    data.forEach(element => {
      form.get('event').setValue(element.event_id);
      this.onSelectEvent();

      // let fields = element.fields;
      // fields.forEach(element1 => {
      //   eventGroup.get('key').setValue(element1.key);
      //   this.onSelectEventField();
      //   form.get('condition').setValue(element1.condition);
      //   form.get('indOrSum').setValue(element1.type);
      //   form.get('value').setValue(element1.value);
      // });
    });

  }

  patchUploadedFiles(data) {
    data.forEach(element => {
      this.files[element.file_type] = element.url;
    });
  }

  getCustomEventsList(): void {
    this._customEventsService.getCustomEventsList().subscribe(customEventsList => {
      this.customEventsList = customEventsList.filter(customEvent => customEvent.is_active);
    });
  }

  getPartnersList(): void {
    this._partnerService.getPartnersList().subscribe(partnersList => {
      //console.log(partnersList);
      this.partnersList = partnersList;
    });
  }

  onAddCouponDescription(): void {
    let couponDescription = this._formBuilder.group({
      language: ['', Validators.required],
      description: ['', Validators.required]
    });
    (this.couponForm.get('coupon').get('couponDescriptions') as FormArray).push(couponDescription);
  }

  onDeleteCouponDescription(couponDescriptionIndex: number): void {
    (this.couponForm.get('coupon').get('couponDescriptions') as FormArray).removeAt(couponDescriptionIndex);
  }

  onAddCouponCode(): void {
    let couponCode = this._formBuilder.group({
      language: ['', Validators.required],
      code: ['', Validators.required]
    });
    (this.couponForm.get('coupon').get('couponCodes') as FormArray).push(couponCode);
  }

  onDeleteCouponCode(couponCodeIndex: number): void {
    (this.couponForm.get('coupon').get('couponCodes') as FormArray).removeAt(couponCodeIndex);
  }

  onSelectCouponType(): void {
    
    let couponType = this.couponForm.get('coupon').get('couponType').value;
    //console.log(couponType);
    if (this.couponForm.get('coupon').get('couponValue')) {
      (this.couponForm.get('coupon') as FormGroup).removeControl('couponValue');
    }

    if (couponType === 'flat') {
      (this.couponForm.get('coupon') as FormGroup).addControl('couponValue', new FormGroup({
        //currency: new FormControl('', Validators.required),
        amount: new FormControl('', Validators.required)
      }));
    } else if (couponType === 'percentage') {
      (this.couponForm.get('coupon') as FormGroup).addControl('couponValue', new FormGroup({
        minRebateValue: new FormControl('', Validators.required),
        maxRebateValue: new FormControl('', Validators.required)
      }));
    }
  }

  onChooseOfferFile(event): any {
    let offerGroup = (this.couponForm.get('offer') as FormGroup);

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
      let offerGroup = (this.couponForm.get('offer') as FormGroup);
      offerGroup.get('offerFileName').setValue('');
      offerGroup.get('offerFile').setValue('');
      offerGroup.addControl('buySkus', new FormControl(''));
      offerGroup.addControl('getSkus', new FormControl(''));
      this.clearOfferFormValidations(offerGroup);
    }
  }

  onEnterQuantities(): void {
    let buyQuantity = this.couponForm.get('offer').get('buyQuantity').value;
    let getQuantity = this.couponForm.get('offer').get('getQuantity').value;
    let offerGroup = (this.couponForm.get('offer') as FormGroup);
    let offerFileName = offerGroup.get('offerFileName').value;
    if ((buyQuantity && buyQuantity !== '') || (getQuantity && getQuantity !== '')) {
      this.setOfferFormValidations(offerGroup, offerFileName !== '');
    } else {
      this.clearOfferFormValidations(offerGroup, offerFileName !== '');
    }
  }

  setOfferFormValidations(offerGroup: FormGroup, isSkuFileUploaded = true): void {
    offerGroup.get('buyQuantity').setValidators([Validators.required]);
    offerGroup.get('buyQuantity').updateValueAndValidity();
    offerGroup.get('getQuantity').setValidators([Validators.required]);
    offerGroup.get('getQuantity').updateValueAndValidity();
    offerGroup.get('offerDescription').setValidators([Validators.required]);
    offerGroup.get('offerDescription').updateValueAndValidity();

    if (!isSkuFileUploaded) {
      offerGroup.get('buySkus').setValidators([Validators.required]);
      offerGroup.get('buySkus').updateValueAndValidity();
      offerGroup.get('getSkus').setValidators([Validators.required]);
      offerGroup.get('getSkus').updateValueAndValidity();
    }

    offerGroup.updateValueAndValidity();
  }

  clearOfferFormValidations(offerGroup: FormGroup, isSkuFileUploaded = true): void {
    offerGroup.get('buyQuantity').clearValidators();
    offerGroup.get('buyQuantity').updateValueAndValidity();
    offerGroup.get('getQuantity').clearValidators();
    offerGroup.get('getQuantity').updateValueAndValidity();
    offerGroup.get('offerDescription').clearValidators();
    offerGroup.get('offerDescription').updateValueAndValidity();

    if (!isSkuFileUploaded) {
      offerGroup.get('buySkus').clearValidators();
      offerGroup.get('buySkus').updateValueAndValidity();
      offerGroup.get('getSkus').clearValidators();
      offerGroup.get('getSkus').updateValueAndValidity();
    }

    offerGroup.updateValueAndValidity();
    console.log(offerGroup.get('buySkus'));
  }

  onSelectCategoryName(): void {
    let categoryFormGroup = (this.couponForm.get('categoryBased') as FormGroup);
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
    let calendarFormGroup = (this.couponForm.get('calendarBased') as FormGroup);
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
    let eventInd = this.couponForm.get('eventBased').get('event').value;
    let eventGroup = (this.couponForm.get('eventBased') as FormGroup);
    this.removeEventFields(eventGroup);
    
    if (eventInd !== '') {
      this.customEventFields = this.customEventsList[eventInd].fields;
      if (eventGroup.get('key')) {
        eventGroup.get('key').setValue('');
        eventGroup.get('key').updateValueAndValidity();
        eventGroup.get('condition').setValue('');
        eventGroup.get('condition').updateValueAndValidity();
        eventGroup.updateValueAndValidity();
      } else {
        eventGroup.addControl('key', new FormControl('', Validators.required));
        eventGroup.addControl('condition', new FormControl('', Validators.required));
      }
    } else {
      eventGroup.removeControl('key');
      eventGroup.removeControl('condition');
    }
  }

  onSelectEventField(): void {
    let eventFieldInd = this.couponForm.get('eventBased').get('key').value;
    let eventGroup = (this.couponForm.get('eventBased') as FormGroup);
    this.removeEventFields(eventGroup);
    if (eventFieldInd !== '') {
      if (this.customEventFields[eventFieldInd].mandatory) {
        eventGroup.addControl('type', new FormControl(this.customEventFields[eventFieldInd].type));
                
        if (this.customEventFields[eventFieldInd].type === 'intType') {
          eventGroup.addControl('value', new FormControl('', [Validators.required, numberValidation]));
          eventGroup.addControl('indOrSum', new FormControl('individual', [Validators.required]));
        } else if (this.customEventFields[eventFieldInd].type === 'floatType') {
          eventGroup.addControl('value', new FormControl('', [Validators.required, floatingNumberValidation]));
          eventGroup.addControl('indOrSum', new FormControl('individual', [Validators.required]));
        } else {
          eventGroup.addControl('value', new FormControl('', Validators.required));
        }

        if (this.customEventFields[eventFieldInd].type === 'datetimeType') {
          eventGroup.addControl('time', new FormControl('', Validators.required));
        }
      } else {
        eventGroup.addControl('type', new FormControl(this.customEventFields[eventFieldInd].type));
        
        if (this.customEventFields[eventFieldInd].type === 'intType') {
          eventGroup.addControl('value', new FormControl('', numberValidation));
          eventGroup.addControl('indOrSum', new FormControl('individual'));
        } else if (this.customEventFields[eventFieldInd].type === 'floatType') {
          eventGroup.addControl('value', new FormControl('', floatingNumberValidation));
          eventGroup.addControl('indOrSum', new FormControl('individual'));
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
    eventGroup.removeControl('indOrSum');
    eventGroup.removeControl('type');
    eventGroup.removeControl('time');
    eventGroup.updateValueAndValidity();
  }

  onSelectDatesInTran(): void {
    let transactionFormGroup = (this.couponForm.get('transactionBased') as FormGroup);
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

  onEnterTransactions(): void {
    let transactionFormGroup = (this.couponForm.get('transactionBased') as FormGroup);
    let transactions = this.couponForm.get('transactionBased').get('transactions').value;
    let transactionsAmount = this.couponForm.get('transactionBased').get('amount').value;

    if (transactions && transactions !== '') {
      transactionFormGroup.get('startAndEndDate').setValidators(Validators.required);
      transactionFormGroup.get('startAndEndDate').updateValueAndValidity();
      transactionFormGroup.get('transactionCondition').setValidators(Validators.required);
      transactionFormGroup.get('transactionCondition').updateValueAndValidity();
    } else {
      if (!(transactionsAmount && transactionsAmount !== '')) {
        transactionFormGroup.get('startAndEndDate').clearValidators();
        transactionFormGroup.get('startAndEndDate').updateValueAndValidity();
      }
      transactionFormGroup.get('transactionCondition').clearValidators();
      transactionFormGroup.get('transactionCondition').updateValueAndValidity();
    }
  }

  onEnterTransactionsAmount(): void {
    let transactionFormGroup = (this.couponForm.get('transactionBased') as FormGroup);
    let transactions = this.couponForm.get('transactionBased').get('transactions').value;
    let transactionsAmount = this.couponForm.get('transactionBased').get('amount').value;

    if (transactionsAmount && transactionsAmount !== '') {
      transactionFormGroup.get('startAndEndDate').setValidators(Validators.required);
      transactionFormGroup.get('startAndEndDate').updateValueAndValidity();
      transactionFormGroup.get('amountCondition').setValidators(Validators.required);
      transactionFormGroup.get('amountCondition').updateValueAndValidity();
    } else {
      if (!(transactions && transactions !== '')) {
        transactionFormGroup.get('startAndEndDate').clearValidators();
        transactionFormGroup.get('startAndEndDate').updateValueAndValidity();
      }

      transactionFormGroup.get('amountCondition').clearValidators();
      transactionFormGroup.get('amountCondition').updateValueAndValidity();
    }
  }

  onChooseExcludingSkuFile(event: any): void {
    if (event.target.files.length > 0) {
      let file = event.target.files[0];
      //console.log(file);
      //file.type === 'application/vnd.ms-excel' || file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      let fileNameSplit = file.name.split('.');
      let fileNameExt = fileNameSplit[fileNameSplit.length - 1];
      if (fileNameExt === 'xlsx') {
        this.couponForm.get('productRestrictions').get('excludingSkuFile').setValue(file);
      } else {
        this.couponForm.get('productRestrictions').get('excludingSkuFileName').setValue('');
        this.couponForm.get('productRestrictions').get('excludingSkuFile').setValue('');
        alert('Please upload excel format file only');
      }
    } else {
      this.couponForm.get('productRestrictions').get('excludingSkuFileName').setValue('');
      this.couponForm.get('productRestrictions').get('excludingSkuFile').setValue('');
    }
  }

  onChooseIncludingSkuFile(event: any): void {
    if (event.target.files.length > 0) {
      let file = event.target.files[0];
      let fileNameSplit = file.name.split('.');
      let fileNameExt = fileNameSplit[fileNameSplit.length - 1];
      if (fileNameExt === 'xlsx') {
        this.couponForm.get('productRestrictions').get('includingSkuFile').setValue(file);
      } else {
        this.couponForm.get('productRestrictions').get('includingSkuFileName').setValue('');
        this.couponForm.get('productRestrictions').get('includingSkuFile').setValue('');
        alert('Please upload excel format file only');
      }
    } else {
      this.couponForm.get('productRestrictions').get('includingSkuFileName').setValue('');
      this.couponForm.get('productRestrictions').get('includingSkuFile').setValue('');
    }
  }

  markFormGroupAsTouched(formGroup: FormGroup): void {
    markFormGroupTouched(formGroup);
  }

  onAddNewCoupon(): void {
    if (this.couponForm.valid) {
      this.disableAddCouponButton = true;
      let couponFormData = this.couponForm.value.coupon;
      let offerFormData = this.couponForm.value.offer;
      let categoryFormData = this.couponForm.value.categoryBased;
      let calendarFormData = this.couponForm.value.calendarBased;
      let transactionFormData = this.couponForm.value.transactionBased;
      let eventData = this.couponForm.value.eventBased;
      let productRestrictionsFormData = this.couponForm.value.productRestrictions;

      let startDateLocaleInCoupon = couponFormData.startEndDate[0].toLocaleString('en-GB', { hour12: false }).split(', ');
      let endDateLocaleInCoupon = couponFormData.startEndDate[1].toLocaleString('en-GB', { hour12: false }).split(', ');
      let startDateInCoupon = startDateLocaleInCoupon[0].split('/');
      let endDateInCoupon = endDateLocaleInCoupon[0].split('/');

      let couponData: any = {
        id: couponFormData.id,
        currency: couponFormData.currency,
        coupon_type: couponFormData.couponType,
        limit_per_user: couponFormData.limitPerUser,
        coupon_limit: couponFormData.couponLimit,
        coupon_limit_amount: couponFormData.couponLimitAmount,
        discount_percentage: couponFormData.couponValue ? couponFormData.couponValue.minRebateValue ? couponFormData.couponValue.minRebateValue : null : null,
        //is_manual: true,
        is_manual: (couponFormData.applyManual === 'isManual') ? true : false,
        status: couponFormData.status,
        min_cart_value: couponFormData.minCartValue,
        max_discount: couponFormData.couponValue ? couponFormData.couponValue.maxRebateValue ? couponFormData.couponValue.maxRebateValue : couponFormData.couponValue.amount ? couponFormData.couponValue.amount : null : null,
        is_mergeable: couponFormData.allowOtherOffers,
        started_on: `${startDateInCoupon[2]}-${startDateInCoupon[1]}-${startDateInCoupon[0]}T00:00:00+0400`,
        closed_on: `${endDateInCoupon[2]}-${endDateInCoupon[1]}-${endDateInCoupon[0]}T23:59:59+0400`,
        partner_id: couponFormData.partner,
        referral_code_required: couponFormData.referralCodeReq,
        offer_rules: [
        ],
        category_rules: [
        ],
        calendar_rules: [
        ],
        transaction_rules: [
        ],
        event_rules: [
        ],
        files: []
      };

      let couponDescriptions = [];
      couponFormData.couponDescriptions.forEach((couponDescription: any) =>{
        couponDescriptions.push({
          language: couponDescription.language,
          value: couponDescription.description
        });
      });

      couponData.descriptions = couponDescriptions;

      let couponCodes = [];
      couponFormData.couponCodes.forEach(couponCode => {
        couponCodes.push({
          language: couponCode.language,
          value: couponCode.code
        });
      });

      couponData.codes = couponCodes;

      if (offerFormData.buyQuantity && offerFormData.buyQuantity !== '') {
        couponData.offer_rules.push({
          buy_skus: offerFormData.buySkus ? offerFormData.buySkus : "",
          buy_quantity: offerFormData.buyQuantity,
          offer_skus: offerFormData.getSkus ? offerFormData.getSkus : "",
          offer_quantity: offerFormData.getQuantity,
          offer_description: offerFormData.offerDescription,
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

      /*if (calendarFormData.startAndEndDate !== '') {
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
      }*/

      if (calendarFormData.dates.length > 0) {
        couponData.calendar_rules.push({
          cal_type: 'date',
          exact_match: true,
          type_from: calendarFormData.dates.join(),
          type_to: undefined,
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

      if (transactionFormData.startAndEndDate !== '' && ((transactionFormData.transactions && transactionFormData.transactions !== '') || (transactionFormData.amount && transactionFormData.amount !== ''))) {
        let startDateLocaleInTran = transactionFormData.startAndEndDate[0].toLocaleString('en-GB', { hour12: false }).split(', ');
        let endDateLocaleInTran = transactionFormData.startAndEndDate[1].toLocaleString('en-GB', { hour12: false }).split(', ');
        let startDateInTran = startDateLocaleInTran[0].split('/');
        let endDateInTran = endDateLocaleInTran[0].split('/');

        couponData.transaction_rules.push({
          cal_type: "dateTime",
          type_from: `${startDateInTran[2]}-${startDateInTran[1]}-${startDateInTran[0]}T${startDateLocaleInTran[1]}+0400`,
          type_to: `${endDateInTran[2]}-${endDateInTran[1]}-${endDateInTran[0]}T${endDateLocaleInTran[1]}+0400`,
          transaction_number: transactionFormData.transactions,
          condition: transactionFormData.transactionCondition,
          fields: {
            amount: transactionFormData.amount,
            ind_sum: transactionFormData.indOrSum,
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
        } else if (eventFieldData.type === 'intType' || eventFieldData.type === 'floatType')  {
          eventFieldData.value = eventData.value;
          eventFieldData.ind_sum = eventData.indOrSum;
        } else  {
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
      } else if (this.files["offers"]) {
        couponData.files.push({file_type: "offers", url: this.files["offers"]});
      }

      if (productRestrictionsFormData.excludingSkuFileName != '') {
        couponReqFormData.append('product_restrictions_exclude', productRestrictionsFormData.excludingSkuFile);
      } else if (this.files["productsExcluded"]) {
        couponData.files.push({file_type: "productsExcluded", url: this.files["productsExcluded"]});
      }

      if (productRestrictionsFormData.includingSkuFileName != '') {
        couponReqFormData.append('product_restrictions_include', productRestrictionsFormData.includingSkuFile);
      } else if (this.files["productsIncluded"]) {
        couponData.files.push({file_type: "productsIncluded", url: this.files["productsIncluded"]});
      }
      //console.log(couponData);
      //console.log(JSON.stringify(couponData));

      const blobOverrides = new Blob([JSON.stringify(couponData)], {
        type: 'application/json',
      });

      couponReqFormData.append('data', blobOverrides);
      
      if (this.couponId) {
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
        this._couponService.createCoupon(couponReqFormData).subscribe(couponRes => {
          if (confirm('Do you want to add banner campaign for this coupon?')) {
            this._router.navigate(['/campaign/add-banner'], { queryParams: { couponId: couponRes.id } });
          } else {
            this._router.navigate(['/campaign/coupon-list']);
          }
        }, err => {
          this.disableAddCouponButton = false;
        });
      }
      
    } else {
      markFormGroupTouched(this.couponForm);
    }
  }

  validateFormGroup(formGroup: FormGroup): void {
    markFormGroupTouched(formGroup);
  }

}
