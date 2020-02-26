import { TestBed } from '@angular/core/testing';

import { CustomEventsService } from './custom-events.service';

describe('CustomEventsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CustomEventsService = TestBed.get(CustomEventsService);
    expect(service).toBeTruthy();
  });
});
