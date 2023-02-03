import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormMapContainerComponent } from './form-map-container.component';

describe('FormMapContainerComponent', () => {
  let component: FormMapContainerComponent;
  let fixture: ComponentFixture<FormMapContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormMapContainerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormMapContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
