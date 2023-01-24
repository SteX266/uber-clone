import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignUpDriverModalComponent } from './sign-up-driver-modal.component';

describe('SignUpDriverModalComponent', () => {
  let component: SignUpDriverModalComponent;
  let fixture: ComponentFixture<SignUpDriverModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SignUpDriverModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SignUpDriverModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
