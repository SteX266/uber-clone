import {
  FacebookLoginProvider,
  GoogleLoginProvider,
  SocialAuthService,
  SocialAuthServiceConfig,
} from '@abacritt/angularx-social-login';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import {
  ComponentFixture,
  fakeAsync,
  flush,
  TestBed,
  tick,
} from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';

import { SignInModalComponent } from './sign-in-modal.component';

fdescribe('SignInModalComponent', () => {
  let component: SignInModalComponent;
  let fixture: ComponentFixture<SignInModalComponent>;
  let dialogRef: any;
  let authService: any;
  let snackbarService: any;
  let router: any;
  let email = 'testmail@gmail.com';
  let password = 'passwordTest';

  beforeEach(() => {
    authService = jasmine.createSpyObj<AuthService>('mockAuthService', [
      'sendLoginRequest',
      'saveToken',
      'saveCurrentUserEmail',
      'saveCurrentUserId',
      'saveCurrentUserRole',
      'getToken',
    ]);
    snackbarService = jasmine.createSpyObj<SnackBarService>(
      'mockSnackbarService',
      ['openFailureSnackBar', 'openSuccessSnackBar']
    );

    dialogRef = jasmine.createSpyObj<MatDialogRef<SignInModalComponent>>(
      'mockDialogRef',
      ['close']
    );
    router = {
      navigate: jasmine.createSpy('navigate'),
    };
    TestBed.configureTestingModule({
      declarations: [SignInModalComponent],
      providers: [
        { provide: AuthService, useValue: authService },
        { provide: MatDialogRef, useValue: dialogRef },
        { provide: SnackBarService, useValue: snackbarService },
        {
          provide: 'SocialAuthServiceConfig',
          useValue: {
            autoLogin: false,
            providers: [
              {
                id: FacebookLoginProvider.PROVIDER_ID,
                provider: new FacebookLoginProvider('518524193712114'),
              },
              {
                id: GoogleLoginProvider.PROVIDER_ID,
                provider: new GoogleLoginProvider(
                  '164509104596-3kctgprgablt8h80f8av9palueb5817k.apps.googleusercontent.com'
                ),
              },
            ],
            onError: (err) => {
              console.log(err);
            },
          } as SocialAuthServiceConfig,
        },
        { provide: Router, useValue: router },
      ],
      imports: [FormsModule, MatDialogModule],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    });
    fixture = TestBed.createComponent(SignInModalComponent);
    component = fixture.componentInstance;
    dialogRef = TestBed.inject(MatDialogRef<SignInModalComponent>);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('email and password should be empty strings', () => {
    expect(component.email == '' && component.password == '').toBeTruthy();
  });

  it('validateInputData should return false on no changes of email and password', () => {
    expect(component.validateInputData()).toBeFalsy();
  });

  it('model email should be changed', fakeAsync(() => {
    component.email = email;
    fixture.detectChanges();
    tick();
    expect(component.email).toBe(email);
  }));

  it('model password should be changed', fakeAsync(() => {
    component.password = password;
    fixture.detectChanges();
    tick();
    expect(component.password).toBe(password);
  }));

  it('validateInputData should return true when email and password exist', fakeAsync(() => {
    component.password = password;
    component.email = email;
    fixture.detectChanges();
    tick();
    expect(component.validateInputData()).toBeTruthy();
  }));

  it('login should be called', fakeAsync(() => {
    authService.sendLoginRequest.and.returnValue(
      new Observable((subscriber) => {
        subscriber.next({
          accessToken: 'testToken',
          id: 'testId',
          email: email,
          userRole: 'testRole',
        });
      })
    );
    component.email = email;
    component.password = password;
    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('#submitLogin'));

    button.nativeElement.click();
    fixture.detectChanges();
    expect(authService.sendLoginRequest).toHaveBeenCalled();
    expect(authService.saveToken).toHaveBeenCalled();
    expect(authService.saveCurrentUserEmail).toHaveBeenCalled();
    expect(authService.saveCurrentUserRole).toHaveBeenCalled();
    expect(authService.saveCurrentUserId).toHaveBeenCalled();
    expect(authService.getToken).toHaveBeenCalled();
    expect(snackbarService.openSuccessSnackBar).toHaveBeenCalled();
    spyOn(component, 'Redirect');
    flush();
  }));
});
