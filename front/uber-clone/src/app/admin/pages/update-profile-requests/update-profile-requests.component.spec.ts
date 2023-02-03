import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateProfileRequestsComponent } from './update-profile-requests.component';

describe('UpdateProfileRequestsComponent', () => {
  let component: UpdateProfileRequestsComponent;
  let fixture: ComponentFixture<UpdateProfileRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateProfileRequestsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateProfileRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
