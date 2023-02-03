import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RidesHistoryPageComponent } from './rides-history-page.component';

describe('RidesHistoryPageComponent', () => {
  let component: RidesHistoryPageComponent;
  let fixture: ComponentFixture<RidesHistoryPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RidesHistoryPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RidesHistoryPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
