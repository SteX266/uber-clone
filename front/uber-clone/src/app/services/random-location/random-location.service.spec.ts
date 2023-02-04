import { TestBed } from '@angular/core/testing';

import { RandomLocationService } from './random-location.service';

describe('RandomLocationService', () => {
  let service: RandomLocationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RandomLocationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
