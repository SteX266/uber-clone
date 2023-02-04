import { TestBed } from '@angular/core/testing';

import { UpdateRequestService } from './update-request.service';

describe('UpdateRequestService', () => {
  let service: UpdateRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpdateRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
