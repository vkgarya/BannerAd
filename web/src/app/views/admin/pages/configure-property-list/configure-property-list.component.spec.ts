import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigurePropertyListComponent } from './configure-property-list.component';

describe('ConfigurePropertyListComponent', () => {
  let component: ConfigurePropertyListComponent;
  let fixture: ComponentFixture<ConfigurePropertyListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigurePropertyListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigurePropertyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
