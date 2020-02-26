import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddLoyaltyPointComponent } from './add-loyalty-point.component';

describe('AddLoyaltyPointComponent', () => {
  let component: AddLoyaltyPointComponent;
  let fixture: ComponentFixture<AddLoyaltyPointComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddLoyaltyPointComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddLoyaltyPointComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
