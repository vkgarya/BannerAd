import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DataTableColumnFilterComponent } from './data-table-column-filter.component';

describe('DataTableColumnFilterComponent', () => {
  let component: DataTableColumnFilterComponent;
  let fixture: ComponentFixture<DataTableColumnFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DataTableColumnFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DataTableColumnFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
