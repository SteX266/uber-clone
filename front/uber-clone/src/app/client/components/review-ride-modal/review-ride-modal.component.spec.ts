import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewRideModalComponent } from './review-ride-modal.component';

describe('ReviewRideModalComponent', () => {
  let component: ReviewRideModalComponent;
  let fixture: ComponentFixture<ReviewRideModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReviewRideModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReviewRideModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
