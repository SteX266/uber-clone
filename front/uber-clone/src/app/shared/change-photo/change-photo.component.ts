import { Component } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-change-photo',
  templateUrl: './change-photo.component.html',
  styleUrls: ['./change-photo.component.scss'],
})
export class ChangePhotoComponent {
  constructor(private location: Location) {}

  back(): void {
    this.location.back();
  }
  picture = '';
  removePhoto() {}
}
