import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminOtherUserProfileComponent } from './admin-other-user-profile.component';

describe('AdminOtherUserProfileComponent', () => {
  let component: AdminOtherUserProfileComponent;
  let fixture: ComponentFixture<AdminOtherUserProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminOtherUserProfileComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminOtherUserProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
