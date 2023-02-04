import {
  FacebookLoginProvider,
  GoogleLoginProvider,
  SocialAuthServiceConfig,
} from '@abacritt/angularx-social-login';
import { APP_BASE_HREF } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/compiler';
import {
  ComponentFixture,
  fakeAsync,
  flush,
  TestBed,
  tick,
} from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { Observable } from 'rxjs/internal/Observable';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';
import { SignInModalComponent } from '../sign-in-modal/sign-in-modal.component';

import { SignUpModalComponent } from './sign-up-modal.component';

describe('SignUpModalComponent', () => {
  let component: SignUpModalComponent;
  let fixture: ComponentFixture<SignUpModalComponent>;
  let dialogRef: any;
  let authService: any;
  let snackbarService: any;

  beforeEach(async () => {
    authService = jasmine.createSpyObj<AuthService>('mockAuthService', [
      'sendRegistrationRequest',
    ]);
    snackbarService = jasmine.createSpyObj<SnackBarService>(
      'mockSnackbarService',
      ['openFailureSnackBar', 'openSuccessSnackBar']
    );

    dialogRef = jasmine.createSpyObj<MatDialogRef<SignUpModalComponent>>(
      'mockDialogRef',
      ['close']
    );
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
      ],
      imports: [FormsModule, MatDialogModule],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    });
    fixture = TestBed.createComponent(SignUpModalComponent);
    component = fixture.componentInstance;
  });
  // 1
  it('should create', () => {
    expect(component).toBeTruthy();
  });
  // 2
  it('validateEmail should return false on no changes of email and invalid email regex', fakeAsync(() => {
    expect(component.validateEmail()).toBeFalsy();
    component.email = 'invalidemail';
    fixture.detectChanges();
    tick();
    expect(component.validateEmail()).toBeFalsy();
  }));
  // 3
  it('validateEmail should return true on valid email', fakeAsync(() => {
    component.email = 'valid.email@gmail.com';
    fixture.detectChanges();
    tick();
    expect(component.validateEmail()).toBeTruthy();
  }));
  // 4
  it('validateInputData should return false if passwords dont match', fakeAsync(() => {
    component.email = 'valid.email@gmail.com';
    component.password = '123456';
    component.repeatedPassword = 'abcdef';
    component.name = 'validName';
    component.surname = 'validSurname';
    component.city = 'valid city';
    component.phoneNumber = '0616060600';
    fixture.detectChanges();
    tick();
    expect(component.validateInputData()).toBeFalsy();
    expect(snackbarService.openFailureSnackBar).toHaveBeenCalled();
  }));
  // 5
  it('validateInputData should return false if password length less than 6', fakeAsync(() => {
    component.email = 'valid.email@gmail.com';
    component.password = '12345';
    component.repeatedPassword = '12345';
    component.name = 'validName';
    component.surname = 'validSurname';
    component.city = 'valid city';
    component.phoneNumber = '0616060600';
    fixture.detectChanges();
    tick();
    expect(component.validateInputData()).toBeFalsy();
    expect(snackbarService.openFailureSnackBar).toHaveBeenCalled();
  }));
  // 6
  it('validateInputData should return false if name invalid', fakeAsync(() => {
    component.email = 'valid.email@gmail.com';
    component.password = '123456';
    component.repeatedPassword = '123456';
    component.name = '';
    component.surname = 'validSurname';
    component.city = 'valid city';
    component.phoneNumber = '0616060600';
    fixture.detectChanges();
    tick();
    expect(component.validateInputData()).toBeFalsy();
    expect(snackbarService.openFailureSnackBar).toHaveBeenCalled();
  }));
  // 7
  it('validateInputData should return false if surname invalid', fakeAsync(() => {
    component.email = 'valid.email@gmail.com';
    component.password = '123456';
    component.repeatedPassword = '123456';
    component.name = 'validName';
    component.surname = '';
    component.city = 'valid city';
    component.phoneNumber = '0616060600';
    fixture.detectChanges();
    tick();
    expect(component.validateInputData()).toBeFalsy();
    expect(snackbarService.openFailureSnackBar).toHaveBeenCalled();
  }));
  // 8
  it('validateInputData should return false if city invalid', fakeAsync(() => {
    component.email = 'valid.email@gmail.com';
    component.password = '123456';
    component.repeatedPassword = '123456';
    component.name = 'validName';
    component.surname = 'validSurname';
    component.city = '';
    component.phoneNumber = '0616060600';
    fixture.detectChanges();
    tick();
    expect(component.validateInputData()).toBeFalsy();
    expect(snackbarService.openFailureSnackBar).toHaveBeenCalled();
  }));
  // 9
  it('validateInputData should return false if phoneNumber invalid', fakeAsync(() => {
    component.email = 'valid.email@gmail.com';
    component.password = '123456';
    component.repeatedPassword = '123456';
    component.name = 'validName';
    component.surname = 'validSurname';
    component.city = 'valid city';
    component.phoneNumber = '';
    fixture.detectChanges();
    tick();
    expect(component.validateInputData()).toBeFalsy();
    expect(snackbarService.openFailureSnackBar).toHaveBeenCalled();
  }));
  // 10
  it('validateInputData should return true if everything is valid', fakeAsync(() => {
    component.email = 'valid.email@gmail.com';
    component.password = '123456';
    component.repeatedPassword = '123456';
    component.name = 'validName';
    component.surname = 'validSurname';
    component.city = 'valid city';
    component.phoneNumber = '0616060600';
    fixture.detectChanges();
    tick();
    expect(component.validateInputData()).toBeTruthy();
  }));
  // 11
  it('sendRegistrationRequest works if all data is valid', fakeAsync(() => {
    authService.sendRegistrationRequest.and.returnValue(
      new Observable((subscriber) => {
        subscriber.next('blaa');
      })
    );
    component.email = 'valid.email@gmail.com';
    component.password = '123456';
    component.repeatedPassword = '123456';
    component.name = 'validName';
    component.surname = 'validSurname';
    component.city = 'valid city';
    component.phoneNumber = '0616060600';

    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('#signUpButton'));

    button.nativeElement.click();
    tick();
    fixture.detectChanges();
    expect(authService.sendRegistrationRequest).toHaveBeenCalled();
    flush();
  }));

  it('sendRegistration doesnt work if passwords dont match', fakeAsync(() => {
    authService.sendRegistrationRequest.and.returnValue(
      new Observable((subscriber) => {
        subscriber.next('blaa');
      })
    );
    component.email = 'valid.email@gmail.com';
    component.password = '123456';
    component.repeatedPassword = '1234';
    component.name = 'dasdasdas';
    component.surname = 'validSurname';
    component.city = 'valid city';
    component.phoneNumber = '0616060600';

    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('#signUpButton'));

    button.nativeElement.click();
    tick();
    fixture.detectChanges();
    expect(authService.sendRegistrationRequest).not.toHaveBeenCalled();
    flush();
  }));

  it('sendRegistration doesnt work if email invalid', fakeAsync(() => {
    authService.sendRegistrationRequest.and.returnValue(
      new Observable((subscriber) => {
        subscriber.next('blaa');
      })
    );
    component.email = 'valid.emaail.com';
    component.password = '123456';
    component.repeatedPassword = '123456';
    component.name = 'djshadjas';
    component.surname = 'validSurname';
    component.city = 'valid city';
    component.phoneNumber = '0616060600';

    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('#signUpButton'));

    button.nativeElement.click();
    tick();
    fixture.detectChanges();
    expect(authService.sendRegistrationRequest).not.toHaveBeenCalled();
    flush();
  }));

  it('sendRegistration doesnt work if name invalid', fakeAsync(() => {
    authService.sendRegistrationRequest.and.returnValue(
      new Observable((subscriber) => {
        subscriber.next('blaa');
      })
    );
    component.email = 'valid.email@gmail.com';
    component.password = '123456';
    component.repeatedPassword = '123456';
    component.name = '';
    component.surname = 'validSurname';
    component.city = 'valid city';
    component.phoneNumber = '0616060600';

    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('#signUpButton'));

    button.nativeElement.click();
    tick();
    fixture.detectChanges();
    expect(authService.sendRegistrationRequest).not.toHaveBeenCalled();
    flush();
  }));

  it('sendRegistration doesnt work if surname invalid', fakeAsync(() => {
    authService.sendRegistrationRequest.and.returnValue(
      new Observable((subscriber) => {
        subscriber.next('blaa');
      })
    );
    component.email = 'valid.email@gmail.com';
    component.password = '123456';
    component.repeatedPassword = '123456';
    component.name = 'djshadjas';
    component.surname = '';
    component.city = 'valid city';
    component.phoneNumber = '0616060600';

    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('#signUpButton'));

    button.nativeElement.click();
    tick();
    fixture.detectChanges();
    expect(authService.sendRegistrationRequest).not.toHaveBeenCalled();
    flush();
  }));

  it('sendRegistration doesnt work if city invalid', fakeAsync(() => {
    authService.sendRegistrationRequest.and.returnValue(
      new Observable((subscriber) => {
        subscriber.next('blaa');
      })
    );
    component.email = 'valid.email@gmail.com';
    component.password = '123456';
    component.repeatedPassword = '123456';
    component.name = 'djshadjas';
    component.surname = 'validSurname';
    component.city = '';
    component.phoneNumber = '0616060600';

    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('#signUpButton'));

    button.nativeElement.click();
    tick();
    fixture.detectChanges();
    expect(authService.sendRegistrationRequest).not.toHaveBeenCalled();
    flush();
  }));

  it('sendRegistration doesnt work if phoneNumber invalid', fakeAsync(() => {
    authService.sendRegistrationRequest.and.returnValue(
      new Observable((subscriber) => {
        subscriber.next('blaa');
      })
    );
    component.email = 'valid.email@gmail.com';
    component.password = '123456';
    component.repeatedPassword = '123456';
    component.name = 'djshadjas';
    component.surname = 'validSurname';
    component.city = 'valid city';
    component.phoneNumber = '';

    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('#signUpButton'));

    button.nativeElement.click();
    tick();
    fixture.detectChanges();
    expect(authService.sendRegistrationRequest).not.toHaveBeenCalled();
    flush();
  }));

  it('sendRegistration doesnt work if email length less than 6', fakeAsync(() => {
    authService.sendRegistrationRequest.and.returnValue(
      new Observable((subscriber) => {
        subscriber.next('blaa');
      })
    );
    component.email = 'valid.emaail.com';
    component.password = '12345';
    component.repeatedPassword = '12345';
    component.name = 'djshadjas';
    component.surname = 'validSurname';
    component.city = 'valid city';
    component.phoneNumber = '0616060600';

    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('#signUpButton'));

    button.nativeElement.click();
    tick();
    fixture.detectChanges();
    expect(authService.sendRegistrationRequest).not.toHaveBeenCalled();
    flush();
  }));
});
