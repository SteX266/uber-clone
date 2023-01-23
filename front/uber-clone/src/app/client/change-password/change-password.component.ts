import { Component } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss'],
})
export class ChangePasswordComponent {
  constructor(private location: Location) {}

  back(): void {
    this.location.back();
  }
  submitPasswordChanges() {}
}
