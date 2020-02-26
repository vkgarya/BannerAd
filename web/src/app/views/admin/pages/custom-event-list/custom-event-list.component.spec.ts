import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomEventListComponent } from './custom-event-list.component';

describe('CustomEventListComponent', () => {
  let component: CustomEventListComponent;
  let fixture: ComponentFixture<CustomEventListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomEventListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomEventListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
