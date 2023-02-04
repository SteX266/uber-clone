import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapSmallComponent } from './map-small.component';

describe('MapSmallComponent', () => {
  let component: MapSmallComponent;
  let fixture: ComponentFixture<MapSmallComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MapSmallComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapSmallComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
