import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavoriteRouteModalComponent } from './favorite-route-modal.component';

describe('FavoriteRouteModalComponent', () => {
  let component: FavoriteRouteModalComponent;
  let fixture: ComponentFixture<FavoriteRouteModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FavoriteRouteModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FavoriteRouteModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
