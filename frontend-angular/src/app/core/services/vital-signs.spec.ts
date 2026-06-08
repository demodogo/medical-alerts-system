import { TestBed } from '@angular/core/testing';

import { VitalSigns } from './vital-signs';

describe('VitalSigns', () => {
  let service: VitalSigns;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VitalSigns);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
