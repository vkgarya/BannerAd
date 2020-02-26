import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserEventsListComponent } from './user-events-list.component';

describe('UserEventsListComponent', () => {
  let component: UserEventsListComponent;
  let fixture: ComponentFixture<UserEventsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserEventsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserEventsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
