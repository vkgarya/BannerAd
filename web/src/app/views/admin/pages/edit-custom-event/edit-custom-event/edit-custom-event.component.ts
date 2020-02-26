import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';
import { markFormGroupTouched } from 'src/app/utilities/MarkFormGroupAsTouched';
import { CustomEventsService } from 'src/app/core/services/custom-events/custom-events.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-edit-custom-event',
  templateUrl: './edit-custom-event.component.html',
  styleUrls: ['./edit-custom-event.component.scss']
})
export class EditCustomEventComponent implements OnInit {

  @Input() set eventId(eventId: number) {
    if (eventId) {
      this.getDetailsOfEvents(eventId);
    }
  }
  @Output() backToList: EventEmitter<any> = new EventEmitter<any>();


  editCustomeventForm: FormGroup;
  customEventDetails: any;
  constructor(private readonly _formBuilder: FormBuilder, private readonly _customeventsService: CustomEventsService, private readonly _router: Router) { }

  ngOnInit() {
    this.editCustomeventForm = this._formBuilder.group({
      eventCode: [''],
      eventName: ['', Validators.required],
      eventDesc: ['', Validators.required],
      moreEventFields: this._formBuilder.array([
        this._formBuilder.group({
          fieldName: ['', Validators.required],
          fieldDesc: ['', Validators.required],
          fieldType: ['', Validators.required],
          mandatoryReq: [false]
        }),
      ]),
    })
  }

  getDetailsOfEvents(eventId: number): void {
    this._customeventsService.getCustomEventDetails(eventId).subscribe(customEventDetails => {
      this.customEventDetails = customEventDetails;
      this.patchCustomEventFields(this.customEventDetails);
    });
  }


  patchCustomEventFields(customEventDetails: any): void {
    this.editCustomeventForm.patchValue({
      eventCode: customEventDetails.event_code,
      eventName: customEventDetails.event_name,
      eventDesc: customEventDetails.event_description,
      moreEventFields: this.patchEventFields(customEventDetails)
    });
  }

  patchEventFields(customEventDetails: any): any[] {
    let moreEventFields = [];
    if (customEventDetails.fields) {
      customEventDetails.fields.forEach((eventField, index) => {
        let eventFieldData = {
          fieldName: eventField.field_name,
          fieldDesc: eventField.field_description,
          fieldType: eventField.type,
          mandatoryReq: eventField.mandatory
        };

        if (index > 0) {
          (this.editCustomeventForm.get('moreEventFields') as FormArray).push(this._formBuilder.group({
            fieldName: ['', Validators.required],
            fieldDesc: ['', Validators.required],
            fieldType: ['', Validators.required],
            mandatoryReq: [false]
          }));
        }

        moreEventFields.push(eventFieldData);
      });
    }

    return moreEventFields;
  }


    /* onAddBanner() -start */
    onEditCustomEvent(): any {
      if (this.editCustomeventForm.valid) {
        let addCustomEventData = this.editCustomeventForm.value;
  
        let eventFields = [];
        addCustomEventData.moreEventFields.forEach(eventField => {
          eventFields.push({
            field_name: eventField.fieldName,
            field_description: eventField.fieldDesc,
            type: eventField.fieldType,
            mandatory: eventField.mandatoryReq
          });
        });
        
        let customEventData: any = {
          id: this.customEventDetails.id,
          event_code: addCustomEventData.eventCode,
          event_name: addCustomEventData.eventName,
          event_description: addCustomEventData.eventDesc,
          fields: eventFields
        };

        console.log(customEventData);
  
        this._customeventsService.updateCustomEvent(customEventData).subscribe(res => {
          alert('Event updated successfully!');
          this.onBackToList();
        });
      
      } else {
        markFormGroupTouched(this.editCustomeventForm);
      }
    }
    /* -end */
  
    /* remove addmore fields  row function -start*/
    onRemoveFields(ind: number): void {
      (this.editCustomeventForm.get('moreEventFields') as FormArray).removeAt(ind);
    }
    /*end*/
  
  
    /* addmore fields  row function -start*/
    addMoreFilelds(): void {
      let eventField = this._formBuilder.group({
        fieldName: ['', Validators.required],
        fieldDesc: ['', Validators.required],
        fieldType: ['', Validators.required],
        mandatoryReq: [false]
      });
      (this.editCustomeventForm.get('moreEventFields') as FormArray).push(eventField);
    }
    /*end*/



    onBackToList(): void {
      this.backToList.emit();
    }

}
