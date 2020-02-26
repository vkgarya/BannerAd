import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { CustomEventsService } from 'src/app/core/services/custom-events/custom-events.service';
import { DatatableComponent } from '@swimlane/ngx-datatable';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-custom-event-list',
  templateUrl: './custom-event-list.component.html',
  styleUrls: ['./custom-event-list.component.scss']
})
export class CustomEventListComponent implements OnInit {

  action = 'list';
  columns = [
    { prop: 'id', name: 'ID', flexSize: 1 },
    { prop: 'event_code', name: 'Event Code', flexSize: 2 },
    { prop: 'event_name', name: 'Event Name', flexSize: 2 }
  ];
  pageTitleInfo = {
    title: 'Events',
    icon: 'assets/images/events.png'
  };

  customEventsList: any;
  selectedEvent: any;

  @ViewChild(DatatableComponent, { static: false }) table: DatatableComponent;
  modalRef: BsModalRef;

  constructor(private readonly _customeventsService: CustomEventsService, private readonly modalService: BsModalService) { }

  ngOnInit() {
    this.getCustomEventsList();
  }

  getCustomEventsList(): void {
    this._customeventsService.getCustomEventsList().subscribe(customEventsList => {
      //console.log(customEventsList);
      this.customEventsList = customEventsList.filter(customEvent => customEvent.is_active);
    });
  }

  openCustomEventDetailModal(modalTemplate: TemplateRef<any>, event: any): void {
    this.modalRef = this.modalService.show(modalTemplate);
    this.selectedEvent = event;
  }

  onEditEvent(event: any): void {
    this.action = 'edit';
    this.pageTitleInfo.title = 'Admin/Edit event details';
    this.selectedEvent = event;
  }

  onDeleteEvent(event: any): void {
    if (confirm('Are you sure want to delete?')) {
      this._customeventsService.deleteCustomEvent(event.id).subscribe(res => {
        alert("Event removed successfully!");
        this.getCustomEventsList();
      });
    }
}

backToList(): void { 
  this.pageTitleInfo.title = "Custom-Events";
  this.getCustomEventsList();
  this.action = 'list';
}

}
