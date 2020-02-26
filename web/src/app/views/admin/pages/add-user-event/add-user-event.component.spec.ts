import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserEventComponent } from './add-user-event.component';

describe('AddUserEventComponent', () => {
  let component: AddUserEventComponent;
  let fixture: ComponentFixture<AddUserEventComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddUserEventComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUserEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
