import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AuthService } from 'src/app/services/auth/auth.service';

import { SignInModalComponent } from './sign-in-modal.component';

describe('SignInModalComponent', () => {
  let component: SignInModalComponent;
  let fixture: ComponentFixture<SignInModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SignInModalComponent],
      providers: [{ provide: AuthService, useClass: AuthServiceMock }],
    }).compileComponents();
  });

  beforeEach(() => {});

  beforeEach(async () => {
    fixture = TestBed.createComponent(SignInModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
