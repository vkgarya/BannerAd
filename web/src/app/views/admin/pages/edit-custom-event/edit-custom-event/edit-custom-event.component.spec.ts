import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCustomEventComponent } from './edit-custom-event.component';

describe('EditCustomEventComponent', () => {
  let component: EditCustomEventComponent;
  let fixture: ComponentFixture<EditCustomEventComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditCustomEventComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCustomEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
