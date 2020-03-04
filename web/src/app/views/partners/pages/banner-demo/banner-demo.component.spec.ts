import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BannerDemoComponent } from './banner-demo.component';

describe('BannerDemoComponent', () => {
  let component: BannerDemoComponent;
  let fixture: ComponentFixture<BannerDemoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BannerDemoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BannerDemoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
