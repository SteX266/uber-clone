import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrentClientProfileComponent } from './current-client-profile.component';

describe('CurrentClientProfileComponent', () => {
  let component: CurrentClientProfileComponent;
  let fixture: ComponentFixture<CurrentClientProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CurrentClientProfileComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CurrentClientProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
