import { Component, OnInit, TemplateRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-add-loyalty-point',
  templateUrl: './add-loyalty-point.component.html',
  styleUrls: ['./add-loyalty-point.component.scss']
})
export class AddLoyaltyPointComponent implements OnInit {


  isEditable = true;
  

  pageTitleInfo = {
    title: 'Loyalty Points/Add Loyalty',
    icon: 'assets/images/loyalty_points.png'
  };
  loyaltyForm: FormGroup;
  offerRuleForm: FormGroup;
  discountRuleForm: FormGroup;
  calendarBasedRuleForm: FormGroup;
  transactionBasedRuleForm: FormGroup;




  bsValue = new Date();
  bsRangeValue: Date[];
  maxDate = new Date();
  ruleBook: any = {
    offer: [],
    discount: [],
    calendarBased: [],
    transactionBased: [],
    productRestrictions: []
  };
  weekDays = [
    'Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'
  ];
  modalRef: BsModalRef;

  constructor(private readonly _formBuilder: FormBuilder, private readonly modalService: BsModalService) {
    
  }

  ngOnInit() {
    this.loyaltyForm = this._formBuilder.group({
      loyalty: this._formBuilder.group({
        couponDescriptions: this._formBuilder.array([
          this._formBuilder.group({
            language: ['', Validators.required],
            description: ['', Validators.required]
          })
        ]),
        couponCodes: this._formBuilder.array([
          this._formBuilder.group({
            language: ['', Validators.required],
            code: ['', Validators.required]
          })
        ]),
        //startDate: ['', Validators.required],
        //endDate: ['', Validators.required],
        startEndDate: [this.selectDates(), Validators.required],
        allowOtherOffers: [false],
        couponType: ['', Validators.required],
        limit: this._formBuilder.group({
          maxType: ['couponcount', Validators.required],
          value: ['', Validators.required]
        })
      }),
      firstFormGroup: this._formBuilder.group({
        firstCtrl: ['', Validators.required]
      }),
      secondFormGroup: this._formBuilder.group({
        secondCtrl: ['', Validators.required]
      })
    });

    this.offerRuleForm = this._formBuilder.group({
      if: this._formBuilder.group({
        buy: ['', Validators.required],
        of: this._formBuilder.group({
          code: [''],
          manualcode: [''],
          file: ['']
        })
      }),
      then: this._formBuilder.group({
        get: ['', Validators.required],
        of: this._formBuilder.group({
          code: [''],
          manualcode: [''],
          file: ['']
        })
      })
    });

    this.discountRuleForm = this._formBuilder.group({
      if: this._formBuilder.group({
        minSpend: ['', Validators.required],
        maxSpend: ['', Validators.required],
        noOfTransactions: ['', Validators.required]
      }),
      then: this._formBuilder.group({
        discount: [''],
        amount: ['']
      }),
      and: this._formBuilder.group({
        maxDiscount: ['', Validators.required] 
      })
    });

    this.calendarBasedRuleForm = this._formBuilder.group({
      if: this._formBuilder.group({
        startTime: ['', Validators.required],
        endTime: ['', Validators.required],
        date: ['', Validators.required],
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
      and: this._formBuilder.group({
        code: [''],
        manualcode: [''],
        file: ['']
      }),
      then: this._formBuilder.group({
        discount: [''],
        amount: [''],
        maxDiscount: ['', Validators.required]
      })
    });

    this.transactionBasedRuleForm = this._formBuilder.group({
      eventType: ['', Validators.required],
      noOfLastTransactions: ['', Validators.required],
      products: this._formBuilder.array([
        this._formBuilder.group({
          product: ['', Validators.required],
          condition: ['', Validators.required],
          productQuantity: ['', Validators.required],
        })
      ]),
      logicalCond: ['', Validators.required],
      couponPriority: ['once', Validators.required],
      couponCount: ['', Validators.required]
    });
  }

  selectDates(): any {
    this.maxDate.setDate(this.maxDate.getDate() + 7);
    this.bsRangeValue = [this.bsValue, this.maxDate];
    return this.bsRangeValue;
  }

  onAddCouponDescription(): void {
    let couponDescription = this._formBuilder.group({
      language: ['', Validators.required],
      description: ['', Validators.required]
    });
    (this.loyaltyForm.get('coupon').get('couponDescriptions') as FormArray).push(couponDescription);
  }

  onDeleteCouponDescription(couponDescriptionIndex: number): void {
    (this.loyaltyForm.get('coupon').get('couponDescriptions') as FormArray).removeAt(couponDescriptionIndex);
  }

  onAddCouponCode(): void {
    let couponCode = this._formBuilder.group({
      language: ['', Validators.required],
      code: ['', Validators.required]
    });
    (this.loyaltyForm.get('coupon').get('couponCodes') as FormArray).push(couponCode);
  }

  onDeleteCouponCode(couponCodeIndex: number): void {
    (this.loyaltyForm.get('coupon').get('couponCodes') as FormArray).removeAt(couponCodeIndex);
  }

  so() {
    console.log(this.loyaltyForm.get('coupon'));
  }

  onSelectCouponType(): void {
    let couponType = this.loyaltyForm.get('coupon').get('couponType').value;

    if (this.loyaltyForm.get('coupon').get('couponValue')) {
      (this.loyaltyForm.get('coupon') as FormGroup).removeControl('couponValue');
    }

    if (couponType === 'amount') {
      (this.loyaltyForm.get('coupon') as FormGroup).addControl('couponValue', new FormGroup({
        currency: new FormControl('', Validators.required),
        amount: new FormControl('', Validators.required)
      }));
    } else if (couponType === 'rebate') {
      (this.loyaltyForm.get('coupon') as FormGroup).addControl('couponValue', new FormGroup({
        minRebateValue: new FormControl('', Validators.required),
        maxRebateValue: new FormControl('', Validators.required)
      }));
    }
  }

  onAddNewOfferRule(): void {
    let offerRule = this.offerRuleForm.value;
    
    if ((!offerRule.if.of.code || offerRule.if.of.code === '') && (!offerRule.if.of.manualcode || offerRule.if.of.manualcode === '') && (!offerRule.if.of.file || offerRule.if.of.file === '')) {
      alert('Please select, enter, or choose rules');
      return;
    }

    if ((!offerRule.then.of.code || offerRule.then.of.code === '') && (!offerRule.then.of.manualcode || offerRule.then.of.manualcode === '') && (!offerRule.then.of.file && offerRule.then.of.file === '')) {
      alert('Please select, enter, or choose rules');
      return;
    }

    this.ruleBook.offer.push(offerRule);
    this.offerRuleForm.reset({
      if: {
        buy: '',
        of: {
          code: '',
          manualcode:'',
          file: ''
        },
      }, 
      then: {
        get: '',
        of: {
          code: '',
          manualcode:'',
          file: ''
        },
      }
    });
    console.log(this.ruleBook);
  }

  onSelectOfferCodeInOffer(formGroupName: string): void {
    if (this.offerRuleForm.get(formGroupName).get('of').get('code').value !== '') {
      (this.offerRuleForm.get(formGroupName).get('of').get('manualcode') as FormControl).setValue('');
      (this.offerRuleForm.get(formGroupName).get('of').get('file') as FormControl).setValue('');
    }
  }

  onSkuCodeEnteredInOffer(formGroupName: string): void {
    if (this.offerRuleForm.get(formGroupName).get('of').get('manualcode').value !== '') {
      (this.offerRuleForm.get(formGroupName).get('of').get('code') as FormControl).setValue('');
      (this.offerRuleForm.get(formGroupName).get('of').get('file') as FormControl).setValue('');
    }
  }

  onChooseFileInOffer(formGroupName: string): void {
    if (this.offerRuleForm.get(formGroupName).get('of').get('file').value !== '') {
      (this.offerRuleForm.get(formGroupName).get('of').get('code') as FormControl).setValue('');
      (this.offerRuleForm.get(formGroupName).get('of').get('manualcode') as FormControl).setValue('');
    }
  }

  onAddNewDiscountRule(): void {
    let discountRule = this.discountRuleForm.value;
    
    if ((!discountRule.then.discount || discountRule.then.discount === '') && (!discountRule.then.amount || discountRule.then.amount === '')) {
      alert('Please enter discount or amount in AED');
      return;
    }

    this.ruleBook.discount.push(discountRule);
    this.discountRuleForm.reset({
      if: {
        minSpend: '',
        maxSpend: '',
        noOfTransactions: ''
      },
      then: {
        discount: '',
        amount: ''
      },
      and: {
        maxDiscount: ''
      }
    });
    console.log(this.ruleBook);
  }

  onEnterDiscountInDiscountForm(): void {
    if (this.discountRuleForm.get('then').get('discount').value !== '') {
      this.discountRuleForm.get('then').get('amount').setValue('');
    }
  }

  onEnterAmountInDiscountForm(): void {
    if (this.discountRuleForm.get('then').get('amount').value !== '') {
      this.discountRuleForm.get('then').get('discount').setValue('');
    }
  }

  onAddNewCalendarBasedRule(): void {
    let calendarBasedRule = this.calendarBasedRuleForm.value;
    //console.log(calendarBasedRule);

    if ((!calendarBasedRule.and.code || calendarBasedRule.and.code === '') && (!calendarBasedRule.and.manualcode || calendarBasedRule.and.manualcode === '') && (!calendarBasedRule.and.file || calendarBasedRule.and.file === '')) {
      alert('Please select, enter, or choose rules');
      return;
    }

    if ((!calendarBasedRule.then.discount || calendarBasedRule.then.discount === '') && (!calendarBasedRule.then.amount || calendarBasedRule.then.amount === '')) {
      alert('Please enter discount or amount in AED');
      return;
    }

    this.ruleBook.calendarBased.push(calendarBasedRule);

    this.calendarBasedRuleForm.reset({
      if: {
        startTime: '',
        endTime: '',
        date: '',
        weekDays: [
          false,
          false,
          false,
          false,
          false,
          false,
          false 
        ]
      },
      and: {
        code: '',
        manualcode: '',
        file: ''
      },
      then: {
        discount: '',
        amount: '',
        maxDiscount: ''
      }
    });
  }

  onSelectSkuCodeInCalendarBasedForm(): void {
    if (this.calendarBasedRuleForm.get('and').get('code').value !== '') {
      (this.calendarBasedRuleForm.get('and').get('manualcode') as FormControl).setValue('');
      (this.calendarBasedRuleForm.get('and').get('file') as FormControl).setValue('');
    }
  }

  onEnterSkuCodeInCalendarBasedForm(): void {
    if (this.calendarBasedRuleForm.get('and').get('manualcode').value !== '') {
      (this.calendarBasedRuleForm.get('and').get('code') as FormControl).setValue('');
      (this.calendarBasedRuleForm.get('and').get('file') as FormControl).setValue('');
    }
  }

  onChooseFileInCalendarBasedForm(): void {
    if (this.calendarBasedRuleForm.get('and').get('file').value !== '') {
      (this.calendarBasedRuleForm.get('and').get('code') as FormControl).setValue('');
      (this.calendarBasedRuleForm.get('and').get('manualcode') as FormControl).setValue('');
    }
  }

  onAddNewTransactionBasedRule(): void {
    let transactionBasedRule = this.transactionBasedRuleForm.value;
    this.ruleBook.transactionBased.push(transactionBasedRule);
    
    this.transactionBasedRuleForm.reset({
      eventType: '',
      noOfLastTranscations: '',
      product: '',
      condition: '',
      productQuantity: '',
      logicalCond: 'and',
      couponPriority: 'once',
      couponCount: ''
    });
  }

  onChangeLogicalCondInTransaction(): void {
    if ((this.transactionBasedRuleForm.get('products') as FormArray).length < 2) {
      let product = this._formBuilder.group({
        product: ['', Validators.required],
        condition: ['', Validators.required],
        productQuantity: ['', Validators.required],
      });

      (this.transactionBasedRuleForm.get('products') as FormArray).push(product);
    }
  }

  onEnterDiscountInCalendarForm(): void {
    if (this.calendarBasedRuleForm.get('then').get('discount').value !== '') {
      (this.calendarBasedRuleForm.get('then').get('amount') as FormControl).setValue('');
    }
  }

  onEnterDiscountAmountInCalendarForm(): void {
    if (this.calendarBasedRuleForm.get('then').get('amount').value !== '') {
      (this.calendarBasedRuleForm.get('then').get('discount') as FormControl).setValue('');
    }
  }

  openRuleReviewModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

}
