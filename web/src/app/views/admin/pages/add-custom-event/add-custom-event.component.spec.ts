import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCustomEventComponent } from './add-custom-event.component';

describe('AddCustomEventComponent', () => {
  let component: AddCustomEventComponent;
  let fixture: ComponentFixture<AddCustomEventComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddCustomEventComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCustomEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
