import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-driver-navbar',
  templateUrl: './driver-navbar.component.html',
  styleUrls: ['./driver-navbar.component.scss'],
})
export class DriverNavbarComponent {
  userId = '';

  constructor(private auth: AuthService) {}

  ngOnInit() {
    this.userId = this.auth.getCurrentUserId();
  }
}
