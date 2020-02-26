import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoyaltyPointReportComponent } from './loyalty-point-report.component';

describe('LoyaltyPointReportComponent', () => {
  let component: LoyaltyPointReportComponent;
  let fixture: ComponentFixture<LoyaltyPointReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoyaltyPointReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoyaltyPointReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
