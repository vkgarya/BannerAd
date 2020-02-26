import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewEditCouponComponent } from './new-edit-coupon.component';

describe('NewEditCouponComponent', () => {
  let component: NewEditCouponComponent;
  let fixture: ComponentFixture<NewEditCouponComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewEditCouponComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewEditCouponComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
