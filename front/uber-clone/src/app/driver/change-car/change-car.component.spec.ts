import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeCarComponent } from './change-car.component';

describe('ChangeCarComponent', () => {
  let component: ChangeCarComponent;
  let fixture: ComponentFixture<ChangeCarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeCarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChangeCarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
