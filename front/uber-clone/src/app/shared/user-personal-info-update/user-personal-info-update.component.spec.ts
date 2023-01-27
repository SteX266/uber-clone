import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPersonalInfoUpdateComponent } from './user-personal-info-update.component';

describe('UserPersonalInfoUpdateComponent', () => {
  let component: UserPersonalInfoUpdateComponent;
  let fixture: ComponentFixture<UserPersonalInfoUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserPersonalInfoUpdateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserPersonalInfoUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
