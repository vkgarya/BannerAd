import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoyaltyPointListComponent } from './loyalty-point-list.component';

describe('LoyaltyPointListComponent', () => {
  let component: LoyaltyPointListComponent;
  let fixture: ComponentFixture<LoyaltyPointListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoyaltyPointListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoyaltyPointListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
