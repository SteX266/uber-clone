import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-admin-navbar',
  templateUrl: './admin-navbar.component.html',
  styleUrls: ['./admin-navbar.component.scss'],
})
export class AdminNavbarComponent {
  userId = '';

  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.userId = this.authService.getCurrentUserId();
  }

  logOut() {
    this.authService.logout();
  }
}
