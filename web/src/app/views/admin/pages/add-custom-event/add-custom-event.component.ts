import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray,FormControl } from '@angular/forms';
import { markFormGroupTouched } from 'src/app/utilities/MarkFormGroupAsTouched';
import { CustomEventsService } from 'src/app/core/services/custom-events/custom-events.service';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/core/services/authentication/authentication.service';
@Component({
  selector: 'app-add-custom-event',
  templateUrl: './add-custom-event.component.html',
  styleUrls: ['./add-custom-event.component.scss']
})
export class AddCustomEventComponent implements OnInit {

  addCustomeventForm: FormGroup;
  disableAddEventButton = false;
  userId: string;

  constructor(private readonly _formBuilder: FormBuilder, private readonly _customeventsService: CustomEventsService, private readonly _router: Router, private readonly _authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.userId = this._authenticationService.getUserId();
    this.addCustomeventForm = this._formBuilder.group({
      eventCode: ['', Validators.required],
      eventName: ['', Validators.required],
      eventDesc: ['', Validators.required],
      eventFields: this._formBuilder.array([
        this._formBuilder.group({
          fieldName: ['', Validators.required],
          fieldDesc: ['', Validators.required],
          fieldType: ['', Validators.required],
          isMandatory: [false]
        }),
      ]),
    });

    this.addCustomeventForm.get('eventCode').setValue(this.constructCode());
  }

  constructCode(): string {
    if (this.userId) {
      return `${new Date().getTime()}${this.userId}`;
    } else {
      return `${new Date().getTime()}5`;
    }
  }

  onAddCustomEvent(): any {
    if (this.addCustomeventForm.valid) {
      this.disableAddEventButton = true;
      
      let addCustomEventData = this.addCustomeventForm.value;

      let eventFields = [];
      addCustomEventData.eventFields.forEach(eventField => {
        eventFields.push({
          field_name: eventField.fieldName,
          field_description: eventField.fieldDesc,
          type: eventField.fieldType,
          mandatory: eventField.isMandatory
        });
      });

      let customEventData: any = {
        event_code: addCustomEventData.eventCode,
        event_name: addCustomEventData.eventName,
        event_description: addCustomEventData.eventDesc,
        fields: eventFields
      };

      this._customeventsService.addCustomEvent(customEventData).subscribe(res => {
        alert('Event created successfully!');
        this._router.navigate(['/admin/custom-event-list']);
      }, err => {
        this.disableAddEventButton = false;
      });
    } else {
      markFormGroupTouched(this.addCustomeventForm);
    }
  }
  
  onRemoveFields(ind: number): void {
    (this.addCustomeventForm.get('eventFields') as FormArray).removeAt(ind);
  }
  
  addMoreFilelds(): void {
    let eventField = this._formBuilder.group({
      fieldName: ['', Validators.required],
      fieldDesc: ['', Validators.required],
      fieldType: ['', Validators.required],
      isMandatory: [false]
    });
    (this.addCustomeventForm.get('eventFields') as FormArray).push(eventField);
  }

  onSelectTypeInEvent(): void {
    
  }

}
