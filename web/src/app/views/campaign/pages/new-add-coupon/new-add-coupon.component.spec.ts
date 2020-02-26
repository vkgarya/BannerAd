import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewAddCouponComponent } from './new-add-coupon.component';

describe('NewAddCouponComponent', () => {
  let component: NewAddCouponComponent;
  let fixture: ComponentFixture<NewAddCouponComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewAddCouponComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewAddCouponComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
