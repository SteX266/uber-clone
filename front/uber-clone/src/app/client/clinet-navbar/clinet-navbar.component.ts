import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-clinet-navbar',
  templateUrl: './clinet-navbar.component.html',
  styleUrls: ['./clinet-navbar.component.scss'],
})
export class ClinetNavbarComponent {
  userId = '';

  constructor(private auth: AuthService) {}

  ngOnInit() {
    this.userId = this.auth.getCurrentUserId();
  }
}
